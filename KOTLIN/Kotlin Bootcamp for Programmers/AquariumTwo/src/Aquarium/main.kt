package Aquarium

//  public Все объявления публичны -- видны всем и везде, даже внутренние класса
//  private для val var fun set get - только внутри файла
//  protected - Могут видеть подклассы
//  internal видно где угодно в том же модуле
// модуль - набор файлов котлин скомпилированных вместе

fun main(){
    bildAquarium()
    fishexample()
    makeFish()
}

private fun bildAquarium(){
    val myAquarium = Aquarium(10,20,30)  // создает новый экземпляр, вызывая конструктор у него

    println("Length: ${myAquarium.lenght}  " +
            "Width: ${myAquarium.width}  " +
            "Height: ${myAquarium.height}   ")

    myAquarium.height = 80
    println("Length: ${myAquarium.lenght}  " +
            "Width: ${myAquarium.width}  " +
            "Height: ${myAquarium.height}  cm ")
    println (" Volume: ${myAquarium.volume} liters")

    val smallAruarium = Aquarium (20,15,30)
    println("Small aquarium: ${smallAruarium.volume} litres")

    // вызов второго-другого конструктора класса:
    val myAquarium2 = Aquarium(numberOfFish = 22)
    println("Small aquarium2: ${myAquarium2.volume} litres with " +
            "Length: ${myAquarium2.lenght}  " +
            "Width: ${myAquarium2.width}  " +
            "Height: ${myAquarium2.height}  cm ")

    val TowerTank2 = TowerTank()
    println("TowerTank2: ${TowerTank2.volume} litres with " +
            "Length: ${TowerTank2.lenght}  " +
            "Width: ${TowerTank2.width}  " +
            "Height: ${TowerTank2.height}  cm ")
}

// В котлине можно определять классы, которые могут делать ТОЛЬКО интерфейсы
// когда масса действий и классов - помогает организоваться
fun feedFish(fish: FishAction) {
    // make some food then
    fish.eat()
}



// эта функция создает объекты shark b pleco из наследованных от абстратного класса
fun makeFish() {
    val shark = Shark()
    val pleco = Plecostomus()
    println("Shark: ${shark.color} \n Plecostomus ${pleco.color }")
    shark.eat()
    pleco.eat()
}