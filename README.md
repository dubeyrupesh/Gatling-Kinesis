# Gatling-Kinesis
This project aims at doing performance testing of AWS Kinesis stream
using Gatling 


Installation list
=========================
1. SBT 

This project uses SBT 0.13.15, which is available [here](http://www.scala-sbt.org/download.html).

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
More on Passing command line arguments can be found [here](http://gatling.io/docs/current/general/configuration/#command-line-options)