package ledgercache.kinesis

import java.nio.ByteBuffer

import com.amazonaws.AmazonClientException
import com.amazonaws.regions.Region
import com.amazonaws.services.kinesis.AmazonKinesisClient
import com.amazonaws.services.kinesis.model.{PutRecordsRequest, PutRecordsRequestEntry}
import io.gatling.core.protocol._
import ledgercache.infrastructure.{EventConfig, EventFileLoader}
import org.joda.time.DateTime

import scala.collection.JavaConverters._

class KinesisProtocol(config : EventConfig,kinesisStream: String) extends Protocol {
  val kinesisClient = new AmazonKinesisClient()
  kinesisClient.setRegion(Region.getRegion(config.region))
  checkIsAuthorised(kinesisClient)

  def putRecords(data_blob_count : Int):Unit = {

    val eventList = (0 until data_blob_count).map { _ =>
      val eventEntry = new PutRecordsRequestEntry()
      val jsonPayload = serialNumberGenerator(getBaseEventJson)

      eventEntry.setData(ByteBuffer.wrap(jsonPayload.getBytes()))
      eventEntry.setPartitionKey(util.Random.nextInt(10000).toString)
      eventEntry
    }.asJava

    val request = new PutRecordsRequest()
    request.setStreamName(kinesisStream)
    request.setRecords(eventList)
    kinesisClient.putRecords(request)
  }

  private def serialNumberGenerator(jsonData:String): String ={
    val randomGuid = util.Random.nextInt(1000000)

    jsonData.replace("${guid}", randomGuid.toString)
                   .replace("${sentAt}", DateTime.now().toString())
  }

  private def getBaseEventJson: String = {
    EventFileLoader.getEventJson(config.eventType)
  }

  private def checkIsAuthorised(kinesisClient: AmazonKinesisClient) {
    try {
      kinesisClient.describeStream(kinesisStream)
    } catch {
      case awsEx : AmazonClientException =>
        println("\nCheck you are logged in to AWS using ADFS (aws-adfs login --profile <ProfileType> --adfs-host <hostName> --region <some-region>)\n")
        throw awsEx
      case ex : Exception => throw ex
    }
  }
}