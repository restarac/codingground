import java.io._
import scala.util._

object RandomGenerator {
   def main(args: Array[String]) {
    val f = new File("generated2.csv")
    val pw = new PrintWriter(f)
    
    def randomNextDouble(rangeMin:Int, rangeMax:Int):Double = (rangeMin + (rangeMax - rangeMin) * Random.nextDouble())
    def createLoses(portion:Int) = portion match {
        case 1 => 0.10
        case 2 => 0.08
        case 3 => 0.06
        case 4 => 0.04
        case 5 => 0.03
        case 6 => 0.02
        case 7 | 8 | 9 | 10 | 11 | 12 => 0.01
    }
    
    //criação de numeros randomicos    
    val values50To700 = () => randomNextDouble(50, 700)
    val values701To2000 = () => randomNextDouble(701, 2000)
    val values2001To10000 = () => randomNextDouble(2001, 10000)
    val randomPortion = () => { 1 + (Random nextInt 12) }
    
    //Função para criação dos blocos de registros
    def createRecords(range:Range, randomValues:()=>Double) = {
        for (a <- range) {
            val portion = randomPortion()
            val value = randomValues()
            val loses = createLoses(portion)
            pw.println(f"$a;$value%.2f;$portion;$loses%.2f")
        }
    }
    
    createRecords((1 to 1000), values50To700)
    // createRecords((1001 to 9000), values701To2000)
    // createRecords((9001 to 10000), values2001To10000)
    
    //fechar o arquivo
    pw.close
   }
}
