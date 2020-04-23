import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.spark.sql.SparkSession
import org.apache.spark.streaming.{Seconds, StreamingContext}

object stockprediction extends Serializable {
  def main(args: Array[String]): Unit = {
    try {
      val spark = SparkSession.builder().master("local[2]").appName("StreamingApp").getOrCreate()
      val sc = spark.sparkContext
      val stm = new StreamingContext(sc, Seconds(1))
      val line = stm.socketTextStream("localhost", 9999)
      val command = "python C:\\Users\\Siddesh\\Downloads\\py_predictor.py"

      // Create FileSystem object from Hadoop Configuration
      val fs = FileSystem.get(spark.sparkContext.hadoopConfiguration)

      line.foreachRDD(rdd => {
        if (!rdd.partitions.isEmpty)
          rdd.saveAsTextFile("C:\\Users\\Siddesh\\Downloads\\data")

        val fileExists = fs.exists(new Path("C:\\Users\\Siddesh\\Downloads\\data\\part-00000"))
        if (fileExists) {
          val arr = Array("a")
          val dta = spark.sparkContext.parallelize(arr)
          val op = dta.pipe(command)
          val output = op.collect()
          output.foreach(println(_))
        }
      })
      line.print()
      stm.start()
      stm.awaitTermination()
    } catch {
      case exception1: NoSuchMethodError => println(exception1)
      case exception2: ClassNotFoundException => println(exception2)
      case _ => println("Unknown Error occured!")
    }
  }
}

