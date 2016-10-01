import java.io._

case class Cession(id:Int, value:Int, portion:Int, loses:Double)

object CessionChooser {
   def main(args: Array[String]) {
       
       val bufferedSource = io.Source.fromFile("generated.csv")
       for (line <- bufferedSource.getLines) {
           val cols = line.split(";").map(_.trim)
           val cession = new Cession(cols(0).toInt, cols(1).toInt, cols(2).toInt, cols(4).toDouble)
           println(cession)
       }
   }
}