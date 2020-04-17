import org.apache.spark._
import org.apache.spark.sql.SparkSession
import org.apache.spark.streaming._
import org.apache.spark.streaming.StreamingContext._
object stockprediction {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().appName("streamingapp").master("local").getOrCreate()
    //val stm = new StreamingContext(spark.sparkContext,Seconds(1))
    val arr = Array("1600 7000 7100 7700")
    arr.foreach(println(_))
    val line = spark.sparkContext.parallelize(arr)
    val command = "python C:\\Users\\Siddesh\\Downloads\\py_predictor.py"
    val op = line.pipe(command).collect()
    op.foreach(println(_))
  }
}
