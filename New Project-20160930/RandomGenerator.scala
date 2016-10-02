import java.io._
import scala.util._

object RandomGenerator {
   def main(args: Array[String]) {
    val f = new File("generated.csv")
    val pw = new PrintWriter(f)
    val r = scala.util.Random
    
    val values50To700 = () => { 50 + Random.nextInt(651) }
    val values701To2000 = () => { 701 + Random.nextInt(1300) }
    val values2001To10000 = () => { 2001 + Random.nextInt(8000)}
    val portion1To12 = () => { 1 + Random.nextInt(12) }
    val createLoses = (i:Int) => i match { 
            case 1 => 0.10
            case 2 => 0.08
            case 3 => 0.06
            case 4 => 0.04
            case 5 => 0.03
            case 6 => 0.02
            case 7 | 8 | 9 | 10 | 11 | 12 => 0.01
        }
    
    var i = 0
    for (a <- 1 to 1000) {
        val portion = portion1To12()
        val value = values50To700()
        val loses = createLoses(portion)
        pw.println(a + ";" + value + ";" + portion + ";" + loses)
    }
    
    for (a <- 1001 to 9000) {
        val portion = portion1To12()
        val value = values701To2000()
        val loses = createLoses(portion)
        pw.println(a + ";" + value + ";" + portion + ";" + loses)
    }
    
    for (a <- 9001 to 10000) {
        val portion = portion1To12()
        val value = values2001To10000()
        val loses = createLoses(portion)
        pw.println(a + ";" + value + ";" + portion + ";" + loses)
    }
    
    pw.close
   }
}
