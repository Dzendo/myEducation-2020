package example.myapp

// Шаг 1. Создайте абстрактный класс
abstract class AquariumFish {
    abstract val color: String
}

class Shark: AquariumFish(), FishAction {
    override val color = "gray"
    override fun eat() {
        println("hunt and eat fish")
    }
}

class Plecostomus: AquariumFish(), FishAction  {
    override val color = "gold"
    override fun eat() {
        println("eat algae")
    }
}

// Шаг 2. Создайте интерфейс
interface FishAction  {
    fun eat()
}

// Когда использовать абстрактные классы против интерфейсов

// Используйте интерфейс, если у вас много методов и одна или две реализации по умолчанию,
// например, как AquariumAction показано ниже.
interface AquariumAction {
    fun eat()
    fun jump()
    fun clean()
    fun catchFish()
    fun swim()  {
        println("swim")
    }
}

// Используйте абстрактный класс каждый раз, когда вы не можете закончить класс.
// Например, возвращаясь к AquariumFish классу, вы можете сделать все AquariumFish реализации FishAction
// и предоставить реализацию по умолчанию eat, оставляя color абстрактные,
// потому что на самом деле для рыбы нет цвета по умолчанию.
abstract class AquariumFishAction: FishAction {
    abstract val color: String
    override fun eat() = println("yum")
}

// 8. Задача: использовать делегирование интерфейса
//В предыдущей задаче были представлены абстрактные классы, интерфейсы и идея композиции.
// Делегирование интерфейса - это сложный метод,
// в котором методы интерфейса реализуются вспомогательным (или делегирующим) объектом,
// который затем используется классом.
// Этот метод может быть полезен, когда вы используете интерфейс в ряду не связанных между собой классов:
// вы добавляете необходимые функциональные возможности интерфейса в отдельный вспомогательный класс,
// и каждый из классов использует экземпляр вспомогательного класса для реализации функциональности.

//  Вместо того , чтобы наследовать от AquariumFish класса,
//  Plecostomus и Shark собираюсь реализовывать интерфейсы как для действия рыбы и их цвета.
// Создайте новый интерфейс, FishColorкоторый определяет цвет как строку.
interface FishColor {
    val color: String
}
// Изменить Plecostomus реализовать два интерфейса FishAction, а также FishColor.
// Вы должны переопределить color от FishColor и eat() от FishAction.
class PlecostomusD: FishAction, FishColor {
    override val color = "gold"
    override fun eat() {
        println("eat algae")
    }
}
// Измените свой Shark класс так, чтобы он также реализовывал
// два интерфейса FishAction и FishColor вместо того, чтобы наследовать от AquariumFish.
class SharkD: FishAction, FishColor {
    override val color = "gray"
    override fun eat() {
        println("hunt and eat fish")
    }
}

//Шаг 2: Сделайте урок синглтона
// Затем вы реализуете установку для части делегирования,
// создавая вспомогательный класс, который реализует FishColor.
// Вы создаете базовый класс, GoldColor который реализует FishColor
// - все, что он делает, это говорит, что его цвет - золотой.
object GoldColor : FishColor {
    override val color = "gold"
}
// Шаг 3: Добавить делегирование интерфейса для FishColor
class PlecostomusBy(fishColor: FishColor = GoldColor):  FishAction,
    FishColor by fishColor {
    override fun eat() {
        println("eat algae")
    }
}
// Шаг 4: Добавить делегирование интерфейса для FishAction
// Таким же образом вы можете использовать делегирование интерфейса для FishAction.
// PrintingFishAction класс , который реализует FishAction, который принимает String, food,
// а затем печатает то , что рыба ест.
class PrintingFishAction(val food: String) : FishAction {
    override fun eat() {
        println(food)
    }
}
// В Plecostomus классе удалите функцию переопределения eat(), потому что вы замените ее делегированием.
class PlecostomusByD (fishColor: FishColor = GoldColor):
    FishAction by PrintingFishAction("eat algae"),
    FishColor by fishColor

class SharkByD (fishColor: FishColor = GrayColor):
    FishAction by PrintingFishAction("hunt and eat fish"),
    FishColor by fishColor

object GrayColor : FishColor {
    override val color = "gray"
}

sealed class Seal
class SeaLion : Seal()
class Walrus : Seal()

fun matchSeal(seal: Seal): String {
    return when(seal) {
        is Walrus -> "walrus"
        is SeaLion -> "sea lion"
    }
}

// SealКласс не может быть подклассы в другом файле.
// Если вы хотите добавить больше Sealтипов, вы должны добавить их в тот же файл.
// Это делает запечатанные классы безопасным способом представления фиксированного числа типов.
// Например, закрытые классы отлично подходят для возврата успеха или ошибки из сетевого API .