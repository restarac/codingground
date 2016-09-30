import java.io._

object HelloWorld {
   def main(args: Array[String]) {
    val f = new File("generated.txt")
    val pw = new PrintWriter(f)
    val r = scala.util.Random
    
    val values50To700 = () => { scala.util.Random.nextInt(650) + 50 }
    val values701To2000 = () => { scala.util.Random.nextInt(1299) + 701 }
    val values2001To10000 = () => { scala.util.Random.nextInt(7999) + 2001 }
    val portion1To12 = () => { scala.util.Random.nextInt(11) + 1 }
    val createLoses = (i:Int) => i match { 
            case 1 => 0.1
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
        pw.write(a + ";" + value + ";" + portion + ";" + loses)
    }
    
    for (a <- 1 to 8000) {
        val portion = portion1To12()
        val value = values701To2000()
        val loses = createLoses(portion)
        pw.write(a + ";" + value + ";" + portion + ";" + loses)
    }
    
    for (a <- 1 to 1000) {
        val portion = portion1To12()
        val value = values2001To10000()
        val loses = createLoses(portion)
        pw.write(a + ";" + value + ";" + portion + ";" + loses)
    }
    
    pw.close
   }
}
