package KotlinAsFirst

import java.io.File
import java.util.function.Function
import java.util.regex.Matcher
import java.util.regex.Pattern
import java.util.stream.Collector
import java.util.stream.Collectors


fun main() {
    val line = "Мама мыла раму"
    val strin = "ра"
    val col = line.contains("ра",true)
    val a = line.count { line.contains("ра",true)}
    //val b = line.count(strin.toRegex())
    val li = arrayListOf("па", "па", "па", "па", "па", "па", "пп", "па").toList()
    val count1 = li.count { it== "па" }
    println("count1= $count1")
    val count2 = li.groupingBy {  }
    println(count2)
    // https://mkyong.com/java8/java-8-collectors-groupingby-and-mapping-example/
    //val count3 = li.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting() ))
    println("count3= $ count3")
    val count4 = li.groupBy { it }.map{ it.key to it.value.count()}.toMap()
    println("count4= $count4")
    val m = li.map{lii ->lii to li.count { it== lii } }.toMap()
    println(m)
    val pattern: Pattern = Pattern.compile("ep")
    val matcher: Matcher = pattern.matcher("elephant")
    val d = strin.toRegex().toPattern().matcher(line)
    var count = 0
    while (matcher.find()) {
        count++
    }

    var c = 0
    for (strin in line.indices) c++
    //val e = line.count(strin)


    println("a= $a  b= $ b count=$count с = $c")
    println(matcher)
}
    //operator  fun List.couuu():Map<String,Int> =(a,2)

fun countSubstrings(inputName: String, substrings: List<String>): Map<String, Int> =
    substrings.map { sub ->
        sub to File(inputName).readLines().sumBy { line ->
            line.count {line.contains(sub,true)}}
    }.toMap()