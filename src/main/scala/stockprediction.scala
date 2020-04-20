import scala.collection.mutable.ListBuffer
import scala.io.Source

object stockprediction {
  def main(args: Array[String]): Unit = {
    //    try {

    val path = "C:\\Users\\Siddesh\\Desktop\\stockdata.csv"
    //Getting csv contenets in cleaned format to inject it into python script
    val stram_data = getFileContents(path)


    //      val spark = SparkSession.builder().master("local[2]").appName("StreamingApp").getOrCreate()
    //      val sc = spark.sparkContext
    //      val stm = new StreamingContext(sc, Seconds(1))
    //      val line = stm.socketTextStream("localhost", 9999)
    //      val words = line.flatMap(_.split(" "))
    //      words.print()
    //      stm.start()
    //      stm.awaitTermination()
    //    } catch {
    //      case exception1: NoSuchMethodError => println(exception1)
    //      case exception2: ClassNotFoundException => println(exception2)
    //      case _ => println("Unknown Error occured!")
    //    }
  }

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

