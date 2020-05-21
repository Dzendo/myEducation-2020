import java.util.*

fun main(args:Array<String>){
    dayOfWeek()
    dayOfWeekUD()
    dayOfWeekUDO()
}

fun dayOfWeek(){
    when (Calendar.getInstance().get(Calendar.DAY_OF_WEEK)){
        1 ->   println("Воскресенье")
        2 ->   println("Понедельник")
        3 ->   println("Вторник")
        4 ->   println("Среда")
        5 ->   println("Четверг")
        6 ->   println("Пятница")
        7 ->   println("Суббота")
        else ->  println("Никакой")
    }

}

fun dayOfWeekUD() {
    println("What day is it today?")
    val day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
    println( when (day) {
        1 -> "Sunday"
        2 -> "Monday"
        3 -> "Tuesday"
        4 -> "Wednesday"
        5 -> "Thursday"
        6 -> "Friday"
        7 -> "Saturday"
        else -> "Time has stopped"
    })
}
fun dayOfWeekUDO() =
       println( "What day is it today? " +
        when (Calendar.getInstance().get(Calendar.DAY_OF_WEEK)) {
        1 -> "Sunday"
        2 -> "Monday"
        3 -> "Tuesday"
        4 -> "Wednesday"
        5 -> "Thursday"
        6 -> "Friday"
        7 -> "Saturday"
        else -> "Time has stopped"
    })



