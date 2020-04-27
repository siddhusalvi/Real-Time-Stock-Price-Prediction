import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.spark.sql.SparkSession
import org.apache.spark.streaming.{Seconds, StreamingContext}
import scala.io.Source

object stockprediction {
  def main(args: Array[String]): Unit = {
    try {
      val spark = SparkSession.builder().master("local[2]").appName("StreamingApp").getOrCreate()
      val sc = spark.sparkContext
      val stm = new StreamingContext(sc, Seconds(20))
      val line = stm.socketTextStream("localhost", 9999)

      //Resource  files
      val path = "C:\\Users\\Siddesh\\IdeaProjects\\streamingdata\\src\\main\\resources"
      val script = "py_predictor.py"
      val spark_op = "part-00000"
      var command = "python " + path + "\\" + script

      // Create FileSystem object from Hadoop Configuration
      val fs = FileSystem.get(spark.sparkContext.hadoopConfiguration)

      line.foreachRDD(rdd => {
        if (!rdd.partitions.isEmpty)
          rdd.saveAsTextFile(path + "\\")

        val fileExists = fs.exists(new Path(path + "\\"+spark_op))
        if (fileExists) {
          var file_data = getFileContent(path + "\\" + spark_op)
          command  += " " + file_data
          val list = Array(" ")
          val operation = sc.parallelize(list)
          val std_op = operation.pipe(command)
          val output = std_op.collect()
          output.foreach(println(_))
        }
      })
      line.print()
      stm.start()
      stm.awaitTermination()
    } catch {
      case exception1: NoSuchMethodError => println(exception1)
      case exception2: ClassNotFoundException => println(exception2)
      case exception3: InterruptedException => println(exception3)
      case _ => println("Unknown Error occured!")
    }
  }
  //function to get file contents
  def getFileContent(path: String):String={
    val file = Source.fromFile(path).getLines()
    var file_data: String = ""
    for (line <- file) {
      file_data += line
    }
    file_data
  }
}



