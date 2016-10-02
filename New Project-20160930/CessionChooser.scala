import java.io._
import scala.collection._
import scala.collection.immutable.ListMap

case class Cession(id:Int, value:Int, portion:Int, loses:Double){
    def valueWithLoses() = value - (value * loses)
}

object CessionChooser {
    
    val sourceFile = "generated.csv" //sample.csv
    
   def main(args: Array[String]) {
       val cessions = readAllCessionsFromStorage()
       
       val list = chooseCession(cessions, 700000)
   }
   
   def chooseCession(cessionsToChoose:Seq[Cession], totalValue:Int):Seq[Cession] = {
       
       val cessionsGrouped = ListMap(cessionsToChoose.groupBy(_.loses).toSeq.sortBy(_._1):_*)
       val resultList = mutable.ListBuffer[Cession]()
       var totalAdded = 0
       
       println(s"Keys:${cessionsGrouped.keySet.size}")
        for ((key,cessions) <- cessionsGrouped; if totalAdded < totalValue) {
           //preenche com todos os valores com as menores perdas até valor total
           for(cession <- cessions; if totalAdded < totalValue) {
              val temp = totalAdded + cession.value
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
               val totalLeft = totalValue - totalAdded // = 17
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
       println(s"Required:${totalValue} TotalWithLoss:${totalWithLoss} AmountLeft:${totalValue - totalWithLoss} ")
       println(s"Total Amount:${totalAdded} Total Cessions:${resultList.size}")
       println(s"MaxLoses:${resultList.maxBy(_.loses).loses} MinLoses:${resultList.minBy(_.loses).loses}")
       print(s"MaxValue:${resultList.maxBy(_.value).value}|${resultList.maxBy(_.valueWithLoses()).valueWithLoses}")
       println(s"MinValue:${resultList.minBy(_.value).value}|${resultList.minBy(_.valueWithLoses()).valueWithLoses}")
       println(s"MaxPortion:${resultList.maxBy(_.portion).portion} MinPortion:${resultList.minBy(_.portion).portion}")
       
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
