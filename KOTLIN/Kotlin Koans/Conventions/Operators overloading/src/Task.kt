import TimeInterval.*

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int)

// Supported intervals that might be added to dates:
enum class TimeInterval { DAY, WEEK, YEAR }

operator fun MyDate.plus(timeInterval: TimeInterval): MyDate =
        addTimeIntervals(timeInterval, 1)

class RepeatedTimeInterval(val timeInterval: TimeInterval, val number: Int)

operator fun TimeInterval.times(number: Int) =
        RepeatedTimeInterval(this, number)

operator fun MyDate.plus(timeIntervals: RepeatedTimeInterval) =
        addTimeIntervals(timeIntervals.timeInterval, timeIntervals.number)

//operator fun MyDate.times(timeInterval: TimeInterval): MyDate = TODO()

fun task1(today: MyDate): MyDate {
    return today + YEAR + WEEK
}

fun task2(today: MyDate): MyDate {
    return today + YEAR * 2 + WEEK * 3 + DAY * 5
}



/* Переписал ответ
Реализовать арифметику дат. Поддержка добавления лет, недель и дней к дате.
Вы можете написать код следующим образом: дата + год * 2 + неделя * 3 + ДЕНЬ * 15.

Во-первых, добавьте функцию расширения plus() к моей дате,
взяв временной интервал в качестве аргумента.
Используйте служебную функцию MyDate.добавьте временные интервалы (),
объявленные в DateUtil.уз

Затем попробуйте поддержать добавление нескольких временных интервалов к дате.
Возможно, Вам понадобится дополнительный класс.
 */