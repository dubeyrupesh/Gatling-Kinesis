package ledgercache.infrastructure

import java.nio.file.Paths
import scala.io.Source

object EventFileLoader {

  val jsonResources: String = Paths.get(System.getProperty("user.dir"), "src", "test", "resources", "json").toString

  def getEventJson(choice:String):String = choice match {

    case Event.Event1 => getFile("sample_event_1.json")
    case Event.Event2 => getFile("sample_event_2.json")
    case _ => throw new IllegalStateException("No such event as "+ choice);
  }

  def getFile(filename : String) : String = {
    val filepath = Paths.get(jsonResources, filename).toString
    Source.fromFile(filepath).getLines.mkString
  }
}
