package TRY.Invoke

import TRY.invokeTwice
import java.lang.Double.sum
/* результат т.к. два вызова и скобки две:()()
 Вызов 1
 Вызов 2
 Вызов 3
 Вызов 4
 */


fun main() {
    val invokeale = Invokable()
    invokeTwice(invokeale)
    invokeTwice(invokeale)
}
class Invokable {
    var numberOfInvocations: Int = 0
        private set

    operator fun invoke(): Invokable {
      //  TODO()
        numberOfInvocations++                   // это сам написал
        println(" Вызов $numberOfInvocations")
        return this                             // здесь подсмотрел вместо Invokable() this
    }
}

fun invokeTwice(invokable: Invokable) = invokable()()

/*
Invoke
Objects with an invoke() method can be invoked as a function.
You can add an invoke extension for any class, but it's better not to overuse it:
fun Int.invoke() { println(this) }
1() //huh?..
Implement the function Invokable.invoke() so it can count the number of times it is invoked.
 */

/*
Взывать
Объекты с помощью метода invoke () можно вызывать как функцию.
Вы можете добавить расширение invoke для любого класса, но лучше не злоупотреблять им:
забавный Инт.invoke () { println (this) }
1 () / / а?..
Реализовать функцию вызываться.invoke (), так что он может считать количество раз, когда он вызывается.
 */

/*
## Invoke

Objects with an [`invoke()`](https://kotlinlang.org/docs/reference/operator-overloading.html#invoke)
method can be invoked as a function.

You can add an `invoke` extension for any class, but it's better not to overuse it:

```kotlin
fun Int.invoke() { println(this) }

1() //huh?..
```

Implement the function `Invokable.invoke()` so it can count the number of times it is invoked.

 */
