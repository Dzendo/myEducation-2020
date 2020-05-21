fun main(){

    println(canAddFish(10.0, listOf(3,3,3)))                             //---> false
    println(canAddFish(8.0, listOf(2,2,2), hasDecorations = false))     // ---> true
    println(canAddFish(9.0, listOf(1,1,3), 3))                  //---> false
    println(canAddFish(10.0, listOf(), 7, true)) //---> true
}

fun canAddFish_old(tankSize: Double, currentFish: List<Int>, fishSize: Int = 2, hasDecorations: Boolean = true): Boolean  {

    val maxFish = if (hasDecorations) tankSize * 0.8 else tankSize
    var nowFish = 0
    for (fsize in currentFish)  nowFish = nowFish + fsize
    println (" tank= $tankSize maxFish = $maxFish nowFish = $nowFish ")
    if (nowFish + fishSize > maxFish ) return false else   return true
}

fun canAddFish(tankSize: Double, currentFish: List<Int>, fishSize: Int = 2, hasDecorations: Boolean = true) = currentFish.sum() + fishSize <= tankSize * if (hasDecorations) 0.8 else 1.0
