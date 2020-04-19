import org.apache.spark.sql.SparkSession
import org.apache.spark.streaming._

object stockprediction {
  def main(args: Array[String]): Unit = {
    try {
      val spark = SparkSession.builder().master("local[2]").appName("StreamingApp").getOrCreate()
      val sc = spark.sparkContext
      val stm = new StreamingContext(sc, Seconds(1))
      val line = stm.socketTextStream("localhost", 9999)
      val words = line.flatMap(_.split(" "))
      words.print()
      stm.start()
      stm.awaitTermination()
    } catch {
      case exception1: NoSuchMethodError => println(exception1)
      case exception2: ClassNotFoundException => println(exception2)
      case _ => println("Unknown Error occured!")
    }
  }
  }
