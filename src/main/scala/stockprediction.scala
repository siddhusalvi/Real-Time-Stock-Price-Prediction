import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.streaming._

object stockprediction {
  def main(args: Array[String]): Unit = {
    //    try {
    //      val spark = SparkSession.builder().appName("streamingapp").master("local").getOrCreate()
    //      val sc=new SparkConf().setAppName("Sentiment").setMaster("local")
    //      val sc = spark.sparkContext
    //      val stm = new StreamingContext(sc, Seconds(100))
    //      val arr = Array("1600 7000 7100 7700")

    val conf = new SparkConf().setAppName("wordcounter").setMaster("local[2]")
    val st = new SparkContext(conf)
    val stm = new StreamingContext(st, Seconds(1))
    val line = stm.socketTextStream("localhost", 9999)
    val words = line.flatMap(_.split(" "))
    words.print()
    //      arr.foreach(println(_))
    //      val line = spark.sparkContext.parallelize(arr)
    //      val command = "python C:\\Users\\Siddesh\\Downloads\\py_predictor.py"
    //      val op = line.pipe(command).collect()
    //      op.foreach(println(_))

    stm.start()
    stm.awaitTermination()
    //    } catch {
    //      case exception1 : NoSuchMethodError => println(exception1)
    //      case _ => println("Unknown Error occured!")
    //
    //    }

    // class not found error


  }
}
