package KotlinAsFirst.Regex

import java.util.regex.Matcher
import java.util.regex.Pattern

    fun main() {

        val line = "Мfмff .мылffff .раму .мfffмfмff"
        val strin = "."
    val pattern: Pattern = Pattern.compile(strin.toLowerCase())
    val matcher: Matcher = pattern.matcher(line.toLowerCase())
      //  val c = line.count(strin)
    val d = matcher.groupCount()
    var count = 0
    while (matcher.find()) {
        count++
    }

        //val regex = Regex(strin)
        val regex = Regex(pattern = "(?=\\Q$strin\\E)")
        println(regex.toString())
        val c = regex.findAll(line)
        val r = regex.findAll(line.toLowerCase())
        val a = r.count()


        val b = strin.toRegex().findAll(line.toLowerCase()).count()




        println("a= $a  b= $b count=$count d = $d")
        println(matcher)
        println(r)
}