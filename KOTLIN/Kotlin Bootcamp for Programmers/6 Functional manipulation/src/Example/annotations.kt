// 2. Task: Learn about annotations
// Это говорит о том, что экспортированное имя этого файла InteropFish с JvmName аннотацией;
@file:JvmName("Example.InteropFish")

package Example

import kotlin.reflect.full.*

//@file:JvmName("InteropFish") - говорит только перед пакетом
class InteropFish {
    companion object {
        @JvmStatic fun interop() {}
    }
}
// Это говорит о том, что экспортированное имя этого файла Example.InteropFish с JvmName аннотацией;
// JvmName аннотацию принимает аргумент "Example.InteropFish".
// В объекте-компаньоне @JvmStatic говорит Kotlin сделать interop() статическую функцию в Example.InteropFish.


fun main() {
    testAnnotations()
}

// Создать простую аннотацию ImAPlant. Это не делает ничего, кроме как говорит, что это аннотировано.
annotation class ImAPlant1

// Шаг 2. Создайте собственную аннотацию
@ImAPlant1 class Plant1 {
    fun trim(){}
    fun fertilize(){}
}
// Cоздайте функцию, которая печатает все методы в классе.
// Используйте ::class для получения информации о классе во время выполнения.
// Используйте, declaredMemberFunctions чтобы получить список методов класса.
// (Для доступа к этому вам необходимо импортировать kotlin.reflect.full.*


fun testAnnotations() {
    // Шаг 2. Создайте собственную аннотацию
    val classObj = Plant1::class
    for (m in classObj.declaredMemberFunctions) println(m.name)
    // fertilize
    // trim
    val plantObject = Plant1::class
    for (a in plantObject.annotations) println(a.annotationClass.simpleName)
    // ImAPlant1

    val myAnnotationObject2 = plantObject.findAnnotation<ImAPlant2>()
    println(myAnnotationObject2)
    // @Example.ImAPlant1()

    // Шаг 3: Создайте целевую аннотацию
    val classObj2 = Plant2::class
    for (m in classObj2.declaredMemberFunctions) println(m.name)
    // null

    val plantObject2 = Plant2::class
    for (a in plantObject2.annotations) println(a.annotationClass.simpleName)
    // ImAPlant2

    val myAnnotationObject22 = plantObject2.findAnnotation<ImAPlant2>()
    println(myAnnotationObject22)
    // @Example.ImAPlant2()


}

// Шаг 3: Создайте целевую аннотацию
annotation class ImAPlant2
@Target(AnnotationTarget.PROPERTY_GETTER)
annotation class OnGet
@Target(AnnotationTarget.PROPERTY_SETTER)
annotation class OnSet
@ImAPlant2 class Plant2 {
    @get:OnGet
    val isGrowing: Boolean = true

    @set:OnSet
    var needsFood: Boolean = false
}
/*
Аннотации действительно эффективны для создания библиотек,
которые проверяют вещи как во время выполнения,
так и иногда во время компиляции.
Однако типичный код приложения просто использует аннотации, предоставляемые фреймворками.
 */