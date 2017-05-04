package ledgercache.helpers

import io.gatling.core.Predef.ElFileBody
import io.gatling.http.Predef.http
import io.gatling.core.Predef._
import io.gatling.http.request.builder.HttpRequestBuilder

object Helpers {
  def getEnvOrDefault(env_name: String, default: Int) : Int = {
    val env_value = System.getenv(env_name)
    if (env_value != null) env_value.toInt else default
  }

  def getEnvOrDefault(env_name: String, default: String) : String = {
    val env_value = System.getenv(env_name)
    if (env_value != null) env_value else default
  }

}
