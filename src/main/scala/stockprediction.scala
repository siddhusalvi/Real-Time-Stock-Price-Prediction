import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.spark.sql.SparkSession
import org.apache.spark.streaming.{Seconds, StreamingContext}
import scala.collection.mutable.ListBuffer
import scala.io.Source

object stockprediction extends Serializable {
  def main(args: Array[String]): Unit = {
//    try {
      val spark = SparkSession.builder().master("local[2]").appName("StreamingApp").getOrCreate()
      val sc = spark.sparkContext
      val stm = new StreamingContext(sc, Seconds(20))
      val line = stm.socketTextStream("localhost", 9999)
      var command = "python C:\\Users\\Siddesh\\Downloads\\py_predictor.py"
//      val command = "python C:\\Users\\Siddesh\\Desktop\\normal.py"
      var flag = true
      // Create FileSystem object from Hadoop Configuration
      val fs = FileSystem.get(spark.sparkContext.hadoopConfiguration)
      line.foreachRDD(rdd => {
        if (!rdd.partitions.isEmpty && flag)
          rdd.saveAsTextFile("C:\\Users\\Siddesh\\IdeaProjects\\streamingdata\\src\\main\\resources")

        val fileExists = fs.exists(new Path("C:\\Users\\Siddesh\\IdeaProjects\\streamingdata\\src\\main\\resources\\part-00000"))
        if (fileExists) {
          val file = Source.fromFile("C:\\Users\\Siddesh\\IdeaProjects\\streamingdata\\src\\main\\resources\\part-00000").getLines()
          var lst:String = ""
          for (line <- file){
            lst += line
          }
          print("list : ")
          command += " "+lst
          print(command)
          var dst :List[String]= List(lst)
          //print(lst.mkString(" ")+"|")
          val dta = sc.parallelize(dst)
          val op = dta.pipe(command)
          val output = op.collect()
          println("python output : ")
          output.foreach(println(_))
          flag = false
          stm.awaitTerminationOrTimeout(0)
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
