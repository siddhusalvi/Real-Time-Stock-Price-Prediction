import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.spark.sql.SparkSession
import org.apache.spark.streaming.{Seconds, StreamingContext}
import scala.io.Source

object stockprediction extends Serializable {
  def main(args: Array[String]): Unit = {
//    try {
      val spark = SparkSession.builder().master("local[2]").appName("StreamingApp").getOrCreate()
      val sc = spark.sparkContext
      val stm = new StreamingContext(sc, Seconds(20))
      val line = stm.socketTextStream("localhost", 9999)

      //Python files
      val path = "C:\\Users\\Siddesh\\IdeaProjects\\streamingdata\\src\\main\\resources\\"

      //Path to save file
      val path1 = "C:\\Users\\Siddesh\\IdeaProjects\\streamingdata\\src\\main\\resources"

      var command = "python " + path + "py_predictor.py"
      // Create FileSystem object from Hadoop Configuration
      val fs = FileSystem.get(spark.sparkContext.hadoopConfiguration)
      line.foreachRDD(rdd => {
        if (!rdd.partitions.isEmpty)
          rdd.saveAsTextFile(path1)

        val fileExists = fs.exists(new Path("C:\\Users\\Siddesh\\IdeaProjects\\streamingdata\\src\\main\\resources\\part-00000"))
        if (fileExists) {
          val file = Source.fromFile(path + "part-00000").getLines()
          var file_data: String = ""
          for (line <- file) {
            file_data += line
          }
//          val vector = file_data.split(" ").toList
//          println("list is  ")
//          println(vector.mkString(" "))
//          print("list : ")
//          command += " " + file_data
//          print(command)
          val dst = Array(file_data)
          //print(lst.mkString(" ")+"|")
          val dta = sc.parallelize(dst)
          val op = dta.pipe(command)
          val output = op.collect()
          println("python output : ")
          output.foreach(println(_))
        }
      })
      line.print()
      stm.start()
      stm.awaitTermination()
//    } catch {
//      case exception1: NoSuchMethodError => println(exception1)
//      case exception2: ClassNotFoundException => println(exception2)
//      case _ => println("Unknown Error occured!")
//    }
  }
}
//115.1600 115.7000 114.7100 114.7700
