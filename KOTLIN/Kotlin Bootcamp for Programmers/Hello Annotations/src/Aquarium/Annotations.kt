package Aquarium
// Мы даже можем создавать собственные аннотации:
import kotlin.reflect.KClass
import kotlin.reflect.full.declaredMemberFunctions
import kotlin.reflect.full.findAnnotation
import kotlin.test.assertFalse

@ImAPlant class Plant {
        fun trim(){}
        fun fertilize(){}

    @get:OnGet
    val IsGrowing: Boolean = true

    @set:OnSet
    var needsFood: Boolean= false
}

annotation class ImAPlant

@Target(AnnotationTarget.PROPERTY_GETTER)
annotation class OnGet                      // аннотация нацеленная на получателя

@Target(AnnotationTarget.PROPERTY_SETTER)
annotation class OnSet                      // аннотация нацеленная на установщиков


fun reflections() {
    val classObj: KClass<Plant> = Plant::class   // получить информацию о классе во время выполнения
    // print all annotation? or null
    for (annotation:Annotation in classObj.annotations) {
        println(annotation.annotationClass.simpleName)
    }
    // find all annotation? or null
    val annotated:ImAPlant? = classObj.findAnnotation<ImAPlant>()

    annotated?.apply {
        println("Found a plant annotation")
    }
}