package cache.kinesis

import java.nio.ByteBuffer

import com.amazonaws.AmazonClientException
import com.amazonaws.regions.{Region, Regions}
import com.amazonaws.services.kinesis.{AmazonKinesis, AmazonKinesisClientBuilder}
import com.amazonaws.services.kinesis.model.{PutRecordsRequest, PutRecordsRequestEntry}
import io.gatling.core.protocol._
import cache.infrastructure.{EventConfig, EventFileLoader}
import org.joda.time.DateTime
import com.amazonaws.auth.profile.ProfileCredentialsProvider

import scala.collection.JavaConverters._

class KinesisProtocol(config : EventConfig,kinesisStream: String) extends Protocol {

     val kinesisClient: AmazonKinesis = AmazonKinesisClientBuilder.standard()
       .withRegion(Regions.US_EAST_2).withCredentials(new ProfileCredentialsProvider("sam_project"))
                                            .build();
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
            .replace("${serialNumber}",randomGuid.toString())

  }

  private def getBaseEventJson: String = {
    EventFileLoader.getEventJson(config.eventType)
  }

  private def checkIsAuthorised(kinesisClient: AmazonKinesis) {
    try {
      println(kinesisClient.describeStream(kinesisStream))
    } catch {
      case awsEx : AmazonClientException =>
        println(kinesisStream + "\nCan't connect with Kinesis stream. Check your credentials \n")
        throw awsEx
      case ex : Exception => throw ex
    }
  }
}



