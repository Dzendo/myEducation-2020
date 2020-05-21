package Aquarium5

class MyIntList {
    // начинаем объявлять класс кот может содержать целые числа
    fun get(pos: Int): Int {return 0 }
    fun addItem(item: Int){}
}
class MyShortList {
    fun get(pos: Int): Short { return 0}
    fun addItem(item: Short) {}
}
// без Generic нам нужно реализовать новую копию списка для каждого типа

class MyList<T> {         // T - это любая буква или имя ПРОСТО подстановка
    fun get(pos: Int): T { TODO( "implements")}
    fun addItem(item: T) {}
}
// здесь мы создаем MyList из строки и Mylist из рыбы:

fun workWithMyList(){
    val intList: MyList<String>
    val fishList: MyList<Fish>
}