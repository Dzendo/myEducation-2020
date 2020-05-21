package KotlinAsFirst

fun main() {
    val str = "aa          bb       cc     d"
    val str1 = str.replace("\\s+".toRegex()," ")
    val str2 = str.replace("(?=  )".toRegex(),"")
    //val d = countSubstrings(str," ")
     //   str.countMatches
    println(str)
    println(str1)
    println(str2)
}