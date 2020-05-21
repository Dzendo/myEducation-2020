package Delegate_interface

fun main(){
    delegate()
}
fun delegate(){
    val pleco = Plecostomus()
    println ("Fish has color ${pleco.color}")
    pleco.eat()
}
interface FishAction {
    fun eat()
}
interface FishColor {
    val color: String
}
class Plecostomus(fishColor: FishColor = YellowColor):
        FishAction by PrintingFishAction("a lot of algee"), // если это поставить то не нужно тело
        FishColor by fishColor
/*
{  // все его переопределения обрабатываются делегированием интерфейса
    override fun eat() {
        println("eat algae")
    }
}*/


object GoldColor: FishColor {
    override val color = "gold"
}
object RedColor: FishColor {
    override val color = "red"
}
object YellowColor: FishColor {
    override val color = "yellow"
}
class PrintingFishAction(val food: String): FishAction {
    override fun eat() {
        println(food)
    }
}
