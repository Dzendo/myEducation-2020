package SAM
// 7. Задание: узнать об отдельных абстрактных методах
/*
public class JavaRun {
    public static void runNow(Runnable runnable) {
        runnable.run();
    }
}
Один абстрактный метод - это просто интерфейс с одним методом.
Они очень распространены при использовании API, написанных на языке программирования Java,
поэтому для этого есть аббревиатура SAM.
Вот некоторые примеры Runnable, у которых есть один абстрактный метод run(),
и у Callable которых есть один абстрактный метод call()
 */
fun runExample() {
    val runnable = object: Runnable {
        override fun run() {
            println("I'm a Runnable")
        }
    }
    JavaRun.runNow(runnable)   // ⇒ Я бегущий

    // Kotlin предоставляет более простой способ сделать это - использовать лямбду вместо объекта,
    // чтобы сделать этот код намного более компактным.
    JavaRun.runNow({
        println("Passing a lambda as a Runnable")
    })      // ⇒ Передача лямбда в качестве Runnable

    // Вы можете сделать это еще более лаконичным, используя синтаксис последнего вызова параметра,
    // и избавиться от скобок.
    JavaRun.runNow {
        println("Last parameter is a lambda as a Runnable")
    }       // ⇒ Последний параметр - это лямбда в качестве Runnable

}

fun main() {
    runExample()
    // I'm a Runnable
}

/*
Это основы SAM, Единого Абстрактного Метода.
Вы можете создать экземпляр, переопределить и сделать вызов SAM с одной строкой кода, используя шаблон:
Class.singleAbstractMethod { lambda_of_override }
 */