import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, Encoder, Encoders, SparkSession}
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.streaming.{Seconds, StreamingContext}

import scala.collection.mutable.{ArrayBuffer, ListBuffer}
import scala.io.Source

object stockprediction extends Serializable {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession
      .builder
        .master("local")
      .appName("StructuredNetworkWordCount")
      .getOrCreate()

    import spark.implicits._
    val lines = spark.readStream
      .format("socket")
      .option("host", "localhost")
      .option("port", 9999)
      .load()

    // Split the lines into words
    val words = lines.as[String].flatMap(_.split(" "))

    // Generate running word count
    val wordCounts = words.groupBy("value").count()


    val query = wordCounts.writeStream
      .outputMode("complete")
      .format("console")
      .start()

    query.awaitTermination()

  }
//
//    val spark = SparkSession.builder().master("local[*]").getOrCreate()
//    val df : DataFrame = spark.readStream
//      .format("socket")
//      .option("host","localhost")
//      .option("port","9090")
//      .load()
//
//    val stringEncoder: Encoder[String] = Encoders.STRING
//    val t = df.map( r => r.mkString)(stringEncoder)
//      .groupByKey( s => "")(stringEncoder)
//      .mapGroups((k,v) => {
//        v.mkString("|")
//      })(stringEncoder)
//      .foreach(println(_))
//
//  }
    //    try {
    //    val path = "C:\\Users\\Siddesh\\Desktop\\stockdata.csv"
    //    //Getting csv contenets in cleaned format to inject it into python script
    //    val stram_data = getFileContents(path)
    //    stram_data.foreach(println(_))
    //
    //    val stream_command ="c:\\netcat-1.11\\nc localhost 9999"
    //    val data = spark.sparkContext.parallelize(stram_data)
    //    val streamer = data.pipe(stream_command)
    //    val somedata = streamer.collect()
    //
//        val spark = SparkSession.builder().master("local[2]").appName("StreamingApp").getOrCreate()
//              val sc = spark.sparkContext
//              val stm = new StreamingContext(sc,Seconds(10))
//              val socketStream = stm.socketTextStream("localhost", 9999)
//
//              //val script = "python C:\\Users\\Siddesh\\Downloads\\py_predictor.py"
//              val script = "python C:\\Users\\Siddesh\\Desktop\\normal.py"




//        val pipeline = sc.parallelize(lst)
//
//        val operation = pipeline.pipe(script)
//
//        val python_output = operation.collect()

      //printing python script output
//       python_output.foreach(println(_))

//    val lines = socketStream.flatMap(_.split(" "))
//
//    val buf = ListBuffer[Double]()
//    val words = lines.map(word => buf += word.toDouble)
//    lines.print()
//    buf.foreach(println(_))
//    println(buf.length)


    //          val script = "C:\\Users\\Siddesh\\Desktop\\normal.py"

    //        val rdd1 = sc.parallelize(Seq(socketStream.))
    //        rdd1.collect()
    //        rdd1.foreach(println(_))
    //    socketStream.foreachRDD(rdd => {
    //      if(!rdd.partitions.isEmpty){
    //        val op = rdd1.pipe(script).collect()
    //        println(op)
    //      }
    //    })
//
//        socketStream.print()
//
//        stm.start()
//        stm.awaitTermination()
    //
    //    val spark = SparkSession.builder().appName("streamingapp").master("local").getOrCreate()
    //    //val stm = new StreamingContext(spark.sparkContext,Seconds(1))
    //    val arr = Array("115.1600 115.7000 114.7100 114.7700")
    //    arr.foreach(println(_))
    //    val line = spark.sparkContext.parallelize(arr)
    //    val command = "python C:\\Users\\Siddesh\\Downloads\\py_predictor.py"
    //    val op = line.pipe(command).collect()
    //    op.foreach(println(_))
    //
    //
    //    } catch {
    //      case exception1: NoSuchMethodError => println(exception1)
    //      case exception2: ClassNotFoundException => println(exception2)
    //      case _ => println("Unknown Error occured!")
    //    }
//  }

  def getFileContents(path: String): List[String] = {
    val lines = Source.fromFile(path).getLines()
    var temp_data = ListBuffer[String]()
    for (line <- lines) {
      var buffer = line.split(",")
      temp_data += (buffer(1).toDouble * 10000).toInt + " " + (buffer(2).toDouble * 10000).toInt + " " + (buffer(3).toDouble * 10000).toInt + " " + (buffer(4).toDouble * 10000).toInt
    }
    temp_data.toList
  }
}

