package TRY.src

import java.util.Calendar

/*
 * Returns the following date after the given one.
 * For example, for Dec 31, 2019 the date Jan 1, 2020 is returned.
 * Возвращает следующую дату после заданной.
 * Например, для 31 декабря 2019 года возвращается дата 1 января 2020 года.
 */
fun MyDate.followingDate(): MyDate {
    val c = Calendar.getInstance()
    c.set(year, month, dayOfMonth)
    val millisecondsInADay = 24 * 60 * 60 * 1000L
    val timeInMillis = c.timeInMillis + millisecondsInADay
    val result = Calendar.getInstance()
    result.timeInMillis = timeInMillis
    return MyDate(result.get(Calendar.YEAR), result.get(Calendar.MONTH), result.get(Calendar.DATE))
}

// вроде функция расширение для MyDate.followingDate()
// попробую добавить оператор как в описании:
/*
Как уже упоминалось ранее, для перебирает все, что предоставляет итератор, т. е.
НЕТ дает ошибку неверное имя
имеет член-или функцию расширения iterator(), чей тип возврата
имеет член-или функцию расширения next(), и
имеет член-или функцию расширения hasNext()Boolean.
Все эти три функции должны быть помечены как operator.
 */