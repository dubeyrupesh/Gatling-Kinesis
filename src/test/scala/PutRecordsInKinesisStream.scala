import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import ledgercache.helpers.Helpers
import ledgercache.infrastructure.{Event, EventConfig}
import ledgercache.kinesis.{KinesisActionBuilder, KinesisProtocol}
import scala.concurrent.duration._

class PutRecordsInKinesisStream extends Simulation{

  private val batchSize = Helpers.getEnvOrDefault("BatchSize", 20)
  private val testDuration = Helpers.getEnvOrDefault("TestDuration", 1)
  private val kinesisStreamName =  Helpers.getEnvOrDefault("kinesis-stream","cache-perf-essentials")

  val config = new EventConfig(Event.Event1)

  val kinesisProtocol = new KinesisProtocol(config,kinesisStreamName)
  val kinesisAction = new KinesisActionBuilder(batchSize, kinesisProtocol)

  val testScenario: ScenarioBuilder = scenario("Put Records into Kinesis Stream").exec(kinesisAction)

  setUp(
    testScenario.inject(
      nothingFor(5 seconds),
      constantUsersPerSec(1) during(testDuration minutes)
    )
  ).protocols(kinesisProtocol)
}
