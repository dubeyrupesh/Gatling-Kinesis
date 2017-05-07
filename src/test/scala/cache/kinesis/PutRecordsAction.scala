package cache.kinesis

import io.gatling.commons.stats.OK
import io.gatling.commons.util.TimeHelper
import io.gatling.core.action.{Action, ActionActor}
import io.gatling.core.session.Session
import io.gatling.core.stats.StatsEngine
import io.gatling.core.stats.message.ResponseTimings

class PutRecordsAction(protocol: KinesisProtocol, val next: Action, statsEngine: StatsEngine, eventCount : Int) extends ActionActor {

  override def execute(session: Session): Unit = {
    val start = TimeHelper.nowMillis
    protocol.putRecords(eventCount)
    val end = TimeHelper.nowMillis

    val timings = ResponseTimings(start, end)
    statsEngine.logResponse(session, "Put Records", timings, OK, None, None)
    next ! session
  }
}
