import TimeInterval.*

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int)

// Supported intervals that might be added to dates:
enum class TimeInterval { DAY, WEEK, YEAR }

operator fun MyDate.plus(timeInterval: TimeInterval): MyDate = TODO()

fun task1(today: MyDate): MyDate {
    return today + YEAR + WEEK
}

fun task2(today: MyDate): MyDate {
    TODO("Uncomment") //return today + YEAR * 2 + WEEK * 3 + DAY * 5
}
/*
Operators overloading

Implement date arithmetic. Support adding years, weeks, and days to a date.
 You could write the code like this: date + YEAR * 2 + WEEK * 3 + DAY * 15.

First, add the extension function plus() to MyDate, taking the TimeInterval as an argument.
 Use the utility function MyDate.addTimeIntervals() declared in DateUtil.kt

Then, try to support adding several time intervals to a date. You may need an extra class.
 */

/*
Перегрузка операторов

Реализовать арифметику дат. Поддержка добавления лет, недель и дней к дате.
 Вы можете написать код следующим образом: дата + год * 2 + неделя * 3 + ДЕНЬ * 15.

Во-первых, добавьте функцию расширения plus() к моей дате, взяв временной интервал в качестве аргумента.
 Используйте служебную функцию MyDate.addTimeIntervals(), объявленные в DateUtil.kt

Затем попробуйте поддержать добавление нескольких временных интервалов к дате.
 Возможно, Вам понадобится дополнительный класс.
 */