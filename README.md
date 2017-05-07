# Gatling-Kinesis
This project aims at doing performance testing of AWS Kinesis stream
using Gatling 

Currently it allows you to putRecords in your kinesis Stream via Gatling.

A typical Kinesis Test will look like the following : 
![Alt text](/examples/kinesisLambdaInfra.jpg)

ToDo List:
============
1. Create a documentation page for contribution
2. Create a sample terraform script so to provision Kinesis and Lambda so that examples can be more concrete


Requirement
=========================
1. SBT 
2. AWS_CLI 
3. SCALA

This project uses SBT 0.13.15, which is available [here](http://www.scala-sbt.org/download.html).

LogIn to AWS_CLI
---------
```bash
aws-adfs login --profile default --adfs-host <Your Host> --region <region>
```

Start SBT
---------
```bash
$ sbt
```

Run all simulations
-------------------

```bash
> gatling:test
```

Run a single simulation
-----------------------

```bash
> gatling:testOnly PutRecordsInKinesisStream 
```

Passing commandLine Arguments
-----------------------
More on Passing command line arguments can be found [here](http://gatling.io/docs/current/general/c7onfiguration/#command-line-options)

How to Interpret Results:
=========================
I would highly recommend using the combination of the following tools to monitor all aspects of performance metrics:

1. Gatling detailed reports. Intergrate with your pipeline 
![Jenkins Integration](/examples/jenkinsIntegration.jpg)
2. Create dashboard in AWS cloud watch  for Kinesis and Lambda
    1. Kinesis - PutRecords.Records , PutRecords.Latency , PutRecords.Success
    2. Kinesis - GetRecords.IteratorAgeMilliseconds, GetRecords.Latency, IncomingRecords 
    3. Lambda-  Invocations, Duration, Throttles , IteratorAge 
    ![Dash board](/examples/SampleDashboard.jpg)
    AWS has a very detailed and well written document :
    1. [Kinesis Metrics](http://docs.aws.amazon.com/streams/latest/dev/monitoring-with-cloudwatch.html)
    2. [Lambda Metrics](http://docs.aws.amazon.com/lambda/latest/dg/monitoring-functions-metrics.html)
 3. AWS-XRAY . Documentation [here](http://docs.aws.amazon.com/xray/latest/devguide/aws-xray.html)    