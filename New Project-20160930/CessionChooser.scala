import java.io._
import scala.collection._

case class Cession(id:Int, value:Int, portion:Int, loses:Double)

object CessionChooser {
    
    val sourceFile = "generated.csv" //sample.csv
    
   def main(args: Array[String]) {
       val cessions = readAllCessionsFromStorage()
       
       val list = chooseCession(cessions, 700000)
       println(list.size)
   }
   
   def chooseCession(cessios:Seq[Cession], totalValue:Int):Seq[Cession] = {
       val groupedByPortion = cessios.groupBy(_.portion)
       println(groupedByPortion)
       
       val resultList = mutable.ListBuffer[Cession]()
       var totalAdded = 0
       for (i <- 1 to 12; if totalAdded < totalValue) {
           for(cession <- groupedByPortion(i); if totalAdded < totalValue) {
              val temp = totalAdded + cession.value
              if (temp <= totalValue) {
                  totalAdded = temp
                  resultList += cession
              }
           }
       }
       
       println(s"Required:${totalValue} Added:${totalAdded} Count:${resultList.size}")
       println(s"MaxLoses:${resultList.maxBy(_.loses)} MinLoses:${resultList.minBy(_.loses)}")
       
       return resultList
   }
   
   def readAllCessionsFromStorage():Seq[Cession] = {
        val bufferedSource = io.Source.fromFile(sourceFile)
        val resultList = mutable.ListBuffer[Cession]()
        
        for (line <- bufferedSource.getLines) {
           val cols = line.split(";").map(_.trim)
           val cessionConverted = new Cession(cols(0).toInt, cols(1).toInt, cols(2).toInt, cols(3).toDouble)
           resultList += cessionConverted // do the append #http://docs.scala-lang.org/overviews/collections/performance-characteristics.html
        }
        return resultList
   }
}