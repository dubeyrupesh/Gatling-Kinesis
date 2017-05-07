package cache.kinesis

import akka.actor.Props
import io.gatling.core.action.builder.ActionBuilder
import io.gatling.core.action.{Action, ExitableActorDelegatingAction}
import io.gatling.core.structure.ScenarioContext
import io.gatling.jms.action.JmsReqReply.genName

class KinesisActionBuilder(eventCount: Int, protocol: KinesisProtocol) extends ActionBuilder {

  override def build(context: ScenarioContext, nextAction: Action): Action = {
    val statsEngine = context.coreComponents.statsEngine
    val putRecordsActor = Props(new PutRecordsAction(protocol, nextAction, statsEngine, eventCount))

    val actor = context.system.actorOf(putRecordsActor)
    new ExitableActorDelegatingAction(genName("jmsReqReply"), statsEngine, nextAction, actor)
  }
}
