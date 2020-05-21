package TRY.src

class DateRange(val start: MyDate, val end: MyDate)

fun iterateOverDateRange(firstDate: MyDate, secondDate: MyDate, handler: (MyDate) -> Unit) {
  /*  for (date in firstDate..secondDate) {
        handler(date)
    }

   */
}

/*
Цикл Kotlin for может выполнять итерацию через любой объект,
если соответствующий элемент итератора или функция расширения доступны.

Сделайте реализацию класса DateRange итеративной, чтобы его можно было перебирать.
Используйте функцию MyDate.следующие данные() определены в DateUtil.kt;
вам не нужно реализовывать логику поиска следующей даты самостоятельно.

Используйте выражение объекта, которое играет ту же роль в Kotlin, что и анонимный класс в Java.

   MyDate.followingDate()  Вроде как это функци расширение для  MyDate
 * Возвращает следующую дату после заданной.
 * Например, для 31 декабря 2019 года возвращается дата 1 января 2020 года.
 */

/*
A Kotlin for loop can iterate through any object
 if the corresponding iterator member or extension function is available.

Make the class DateRange implement Iterable, so that it can be iterated over.
 Use the function MyDate.followingDate() defined in DateUtil.kt; you don't have to implement the logic for finding the following date on your own.

Use an object expression which plays the same role in Kotlin as an anonymous class in Java.
 * Returns the following date after the given one.
 * For example, for Dec 31, 2019 the date Jan 1, 2020 is returned.
 */

/*
Как уже упоминалось ранее, для перебирает все, что предоставляет итератор, т. е.

имеет член-или функцию расширения iterator(), чей тип возврата
имеет член-или функцию расширения next(), и
имеет член-или функцию расширения hasNext() Boolean.
Все эти три функции должны быть помечены как operator.
 */