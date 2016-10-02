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
   
   def chooseCession(cessionsToChoose:Seq[Cession], totalValue:Int):Seq[Cession] = {
       val cessionGroupedUnsorted = cessionsToChoose.groupBy(_.loses) //group
       val resultList = ListBuffer[Cession]()
       val sortedKeys = cessionGroupedUnsorted.keySet.toSeq.sorted //sort to begin on the least element
       var totalAdded = scalaBigDecimal("0.0")
       
       println(sortedKeys)
       println(s"Total Portions: ${sortedKeys.size}")
       
        for (key <- sortedKeys; if totalAdded < totalValue) {
           val cessions = cessionGroupedUnsorted(key)
           //preenche com todos os valores com as menores perdas até valor total
           for(cession <- cessions; if totalAdded < totalValue) {
              val temp = cession.value + totalAdded
              if (temp <= totalValue) {
                  totalAdded = temp
                  resultList += cession
              }
           }
           
           //degub...
           println(s"Total:${cessions.size} Diff:${cessions.diff(resultList).size} Portion:${key}")
           
           //elementos da mesma categoria que não foram selecionados.
           val leftCessions = cessions.diff(resultList)
           if (leftCessions.size > 0) {
               val totalLeft = totalValue - totalAdded
               println(s"Doing the last verifying (value left ${totalLeft})...")
               
               //Verifica na leftCession o elemento que tem o valor exato da diferença do valor total entre que há e o que valorTotal + diferença)
               for(leftCession <- leftCessions; if totalAdded < totalValue) {
                   for(cession <- cessions; if totalAdded < totalValue) {
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
       
       val totalWithLoss = resultList.map(_.valueWithLoses().toInt).sum
       
       //Printing detailed info
        println(s"Required:${totalValue} TotalWithLoses:${totalWithLoss} AmountLeftByLoses:${totalValue - totalWithLoss} ")
        println(s"Total Amount:${totalAdded} Total Cessions:${resultList.size}")
        println(s"MaxLoses:${resultList.maxBy(_.loses).loses} MinLoses:${resultList.minBy(_.loses).loses}")
        print(s"MaxValue:${resultList.maxBy(_.value).value}|${resultList.maxBy(_.valueWithLoses()).valueWithLoses}")
        println(s"MinValue:${resultList.minBy(_.value).value}|${resultList.minBy(_.valueWithLoses()).valueWithLoses}")
        println(s"MaxPortion:${resultList.maxBy(_.portion).portion} MinPortion:${resultList.minBy(_.portion).portion}")
       
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
