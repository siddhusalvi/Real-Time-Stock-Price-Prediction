name := "streamingdata"

version := "0.1"

scalaVersion := "2.12.10"


// https://mvnrepository.com/artifact/org.apache.hadoop/hadoop-aws
libraryDependencies += "org.apache.hadoop" % "hadoop-aws" % "2.7.0"

// https://mvnrepository.com/artifact/org.apache.spark/spark-core
libraryDependencies += "org.apache.spark" %% "spark-core" % "3.0.0-preview"

// https://mvnrepository.com/artifact/org.apache.spark/spark-sql
libraryDependencies += "org.apache.spark" %% "spark-sql" % "3.0.0-preview"

// https://mvnrepository.com/artifact/org.apache.spark/spark-streaming
libraryDependencies += "org.apache.spark" %% "spark-streaming" % "3.0.0-preview" % "provided"
