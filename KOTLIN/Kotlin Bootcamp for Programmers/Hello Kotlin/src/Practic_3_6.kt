fun main(){
    var otvet = "Пусто"
    while (otvet != "Успокойся и наслаждайся жизнью!")  {
        val birthday = getBirthday()
        if (birthday<0) break
        otvet = getFortuneCookie(birthday)
        println("Ваша удача: $otvet !!")
    }
}

fun getFortuneCookie(birthday:Int): String {
    val Sostoyanie = listOf(
        "У тебя будет отличный день!",
        "Сегодня у тебя все будет хорошо",
        "Наслаждайтесь прекрасным днем ​​успеха",
        "Будь скромным, и все будет хорошо",
        "Сегодня хороший день для того, чтобы проявить сдержанность",
        "Успокойся и наслаждайся жизнью!",
        "Цени своих друзей, потому что они твоя самая большая удача"
    )
    return when (birthday){
        29 , 31 -> "Birthday 29,31"
        in 1..7 -> "First week"
        else -> (Sostoyanie[birthday % Sostoyanie.size])
    }
}
fun getBirthday():Int {
    print("Введите день рождения:")
    return readLine()?.toIntOrNull() ?: -1
}

