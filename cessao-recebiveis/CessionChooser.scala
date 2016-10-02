import java.io._
import scala.collection._
import scala.collection.immutable.ListMap
import scala.collection.immutable.Seq
import scala.collection.mutable.ListBuffer
import scala.math.BigDecimal

//id - identificador unico
//value - valor em real
//portion - numero de parcela
//loses - perdas em percentual
case class Cession(id:Int, value:scala.math.BigDecimal, portion:Int, loses:Double){
    def valueWithLoses() = value - (value * loses)
}

object CessionChooser {
   def scalaBigDecimal(a:String) = new scala.math.BigDecimal(new java.math.BigDecimal(a))
    
   val sourceFile = "generated.csv" //sample.csv
    
   def main(args: Array[String]) {
       val cessions = readAllCessionsFromStorage()
       val list = chooseCession(cessions, 700000)
   }
   
   def chooseCession(cessionsToChoose:Seq[Cession], valueRequest:Int):Seq[Cession] = {
       val cessionGroupedUnsorted = cessionsToChoose.groupBy(_.loses) //group
       val resultList = ListBuffer[Cession]()
       val sortedKeys = cessionGroupedUnsorted.keySet.toSeq.sorted //sort the cessions to begin on the least element
       var totalAdded = scalaBigDecimal("0.0")
       
       println(sortedKeys)
       println(s"Total Portions: ${sortedKeys.size}")
        
        for (key <- sortedKeys; if totalAdded < valueRequest) { // loop with guards
           val cessions = cessionGroupedUnsorted(key)
           //preenche com todos os valores, com as menores perdas, até atingir o valor total
           for(cession <- cessions; if totalAdded < valueRequest) {
              val temp = cession.value + totalAdded
              if (temp <= valueRequest) {
                  totalAdded = temp
                  resultList += cession
              }
           }
           
           //degub...
           println(s"Total:${cessions.size} Diff:${cessions.diff(resultList).size} Portion:${key}")
           
           //elementos da mesma categoria que não foram selecionados.
           val leftCessions = cessions.diff(resultList)
           if (leftCessions.size > 0 && resultList.size > 0) {
               val totalLeft = valueRequest - totalAdded
               println(s"Doing the last verifying (value left ${totalLeft})...")
               
               //Para cada leftCession, verifica se há na cessions um elemento com valorLeft + cession.value igual ao leftcession.value
               for(leftCession <- leftCessions; if totalAdded < valueRequest) {
                   for(cession <- cessions; if totalAdded < valueRequest) {
                       if (leftCession.value == cession.value + totalLeft) {
                           //substitui o adicionado anteriormente
                           println(s"Best replace found! Removed:${cession} Add:${leftCession}")
                           resultList -= cession
                           resultList += leftCession
                           totalAdded += totalLeft
                       }
                   }
               }
           }
       }
       
       //Printing detailed info
       val totalWithLoss = resultList.map(_.valueWithLoses()).sum
        println(s"Required:${valueRequest} TotalWithLoses:${totalWithLoss} AmountLeftByLoses:${valueRequest - totalWithLoss} ")
        if (resultList.size > 0) {
            println(s"Total Amount:${totalAdded} Total Cessions:${resultList.size}")
            println(s"MaxLoses:${resultList.maxBy(_.loses).loses} MinLoses:${resultList.minBy(_.loses).loses}")
            print(s"MaxValue:${resultList.maxBy(_.value).value}|${resultList.maxBy(_.valueWithLoses()).valueWithLoses}")
            println(s"MinValue:${resultList.minBy(_.value).value}|${resultList.minBy(_.valueWithLoses()).valueWithLoses}")
            println(s"MaxPortion:${resultList.maxBy(_.portion).portion} MinPortion:${resultList.minBy(_.portion).portion}")
        }
       
       return resultList.toList
   }
   
   def readAllCessionsFromStorage():Seq[Cession] = {
        val bufferedSource = io.Source.fromFile(sourceFile)
        // #http://docs.scala-lang.org/overviews/collections/performance-characteristics.html
        val resultList = ListBuffer[Cession]()
        
        for (line <- bufferedSource.getLines) {
            val cols = line.split(",").map(_.trim)
            // do the append
            resultList += new Cession(cols(0).toInt, scalaBigDecimal(cols(1)), cols(2).toInt, cols(3).toDouble)
        }
        return resultList.toList
   }
}
