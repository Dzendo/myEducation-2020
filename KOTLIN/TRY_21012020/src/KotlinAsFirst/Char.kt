package KotlinAsFirst

import kotlin.math.sign

fun main() {
    val a = "123456789"
    val b = a[1].toInt()
    val a33 = 25 + if (5>8) 5 else 3
    var aa = 'a'

    println(aa.toInt())
    aa = (aa.toInt() - 1).toChar()
    println(aa)
    var aaaI: Int = 96
    var aaa:Char = aaaI.toChar()
    aaa = '`'
    println((aaa.toInt() +1).toChar())
    println(aaa.toInt())
    aaa=(aaa.toInt() + 5).toChar()
    println(aaa.toInt())
    println(aaa)
    println("${(96 + 7).toChar()}")
    val bb:Char = aa - 48
    println(bb)
    println("bb= $bb")
    println(a33)
    println("${a[1]}  ${a[1].toInt()}    ${a.substring(1,2)}  $b  ")
    val notation:String = "c2"
    val rez1 = notation[0].minus(48)//.toString().toInt()
    println(rez1)
    //var ggg = notation[1].
    var aaaa:Int = -0
    println(" Знак= ${aaaa.sign}")



    println(notation[1].toString().toInt())
    //val rez = (notation[0].toInt()-48).toChar().toString().toInt()
    //println(rez)
}