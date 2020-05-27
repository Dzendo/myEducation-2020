package example.myapp.decor


fun main() {
    println(Direction.EAST.name)
    println(Direction.EAST.ordinal)
    println(Direction.EAST.degrees)
}
// Перечисления немного похожи на синглтоны - в перечислении может быть только одно и только одно из каждого значения.
// Например, может быть только один Color.RED, один Color.GREENи один Color.BLUE.
// В этом примере значения RGB присваиваются rgb свойству для представления компонентов цвета.
// Вы также можете получить порядковый номер перечисления, используя ordinal свойство,
// и его имя, используя name свойство.
enum class Color(val rgb: Int) {
    RED(0xFF0000), GREEN(0x00FF00), BLUE(0x0000FF);
}
enum class Direction(val degrees: Int) {
    NORTH(0), SOUTH(180), EAST(90), WEST(270)
}