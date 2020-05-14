enablePlugins(GatlingPlugin)
scalaVersion := "2.11.8"
name := "KinesisGatling"

libraryDependencies += "io.gatling.highcharts" % "gatling-charts-highcharts" % "2.2.2" % "test"
libraryDependencies += "io.gatling"            % "gatling-test-framework"    % "2.2.2" % "test"
libraryDependencies += "joda-time" % "joda-time" % "2.9.6"
libraryDependencies += "com.amazonaws"  % "aws-java-sdk"    % "1.11.779"
libraryDependencies += "org.joda" % "joda-convert" % "2.2.1"
