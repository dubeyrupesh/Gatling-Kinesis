package ledgercache.helpers

import org.joda.time.DateTime

import scala.collection.JavaConverters._

object Mappers {
  def setSentAtDate = Map("sentAt" -> DateTime.now)

  var serialNumber: Int = 0

  def generateSerialNumber = {
    Map("serialNumber" -> {
      serialNumber += 1
      serialNumber
    })
  }.asJava
}
