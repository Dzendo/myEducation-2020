package KotlinAsFirst.Complex

import kotlin.math.roundToInt
import kotlin.math.round

fun main() {
    val s = "-11-2i"
    val posZnakPlus = s.indexOf('+',1)
    val posZnakMinus = s.indexOf('-',1)
    val posZnak = if (posZnakPlus == -1) posZnakMinus else posZnakPlus
    println("$posZnak  $posZnakPlus $posZnakMinus lenth= ${s.length}")
    //val re = 0.0
    //if (s.contains("-")) posZnak = s.find
    //val a = s.split("[+|-]".toRegex())
   // val re = s.substring(0,posZnak-1).toDouble()
   // val im = s.substring(posZnak,s.length-1).toDouble()
    val re =  s.substring(0,(if (s.indexOf('+',1) == -1) s.indexOf('-',1) else s.indexOf('+',1))).toDouble()
    val im =  s.substring((if (s.indexOf('+',1) == -1) s.indexOf('-',1) else s.indexOf('+',1)),
        s.length-1).toDouble()
    println("re= $re im= $im    s $s posZnak = $posZnak ")
    //println(a)
    val str = "000135.38"
    println(str.length -(str.indexOf('.')+1))
    println(str.toDouble())
    val ssss = round(str.toDouble())
    println(message = ssss)
    val aaaa = arrayListOf<Int>(2,5,6,7,10,1)
    val aaa1= aaaa.sorted()
    // 'a' + 1
    val c1 = 'a' + 1
println(c1)
// 'a' + 25
    val c2 = 'a' + 25
println(c2)
// 'E' - 2
    val c3 = 'E' - 2
println(c3)
}