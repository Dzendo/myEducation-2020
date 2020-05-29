import higherorderfunction.Fish
import higherorderfunction.myWith



fun main() {
    val fish = Fish("splashy")
    myWith(fish.name) {
        println(capitalize())
    }

    // actually creates an object that looks like this
    // фактически создается объект, который выглядит следующим образом
   myWith(fish.name, object : Function1<String, Unit> {
        override fun invoke(name: String) {
            name.capitalize()
        }
    })

     myWith1(fish.name) {
        capitalize()
    }
    // with myWith() inline, this becomes это становится
    val m = fish.name.capitalize()
    println(m)
}
inline fun myWith1(name: String, block: String.() -> Unit) {
    // применять принятый в функции block(), к объекту приемника name.
    name.block()
}
/*
Стоит отметить, что встраивание больших функций увеличивает размер кода,
поэтому лучше всего его использовать для простых функций, которые используются многократно myWith().
Функции расширения из библиотек, о которых вы узнали ранее, помечены inline,
поэтому вам не нужно беспокоиться о создании дополнительных объектов.
 */
