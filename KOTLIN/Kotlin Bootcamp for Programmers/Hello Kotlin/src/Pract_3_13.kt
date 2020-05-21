fun main(){
    println(whatShouldIDoToday("appy"))
    print("How do you feel?")
    println(whatShouldIDoToday(readLine()!!))
}
fun whatShouldIDoToday(mood:String, weather: String = "Sunny", temperature: Int = 24) :String {

    fun isWalk (mood:String, weather: String) = mood == "happy" && weather == "Sunny"
    fun isBed(mood:String, weather: String,temperature: Int) = mood == "sad" && weather == "rainy" && temperature == 0
    fun isSwimming(temperature: Int) = temperature > 35
    return when {
        isWalk (mood, weather) -> "go for a walk"
        isBed(mood, weather,temperature) -> "stay in bed"
        isSwimming(temperature) -> "go swimming"
        else -> "Stay home and read."
    }
}

