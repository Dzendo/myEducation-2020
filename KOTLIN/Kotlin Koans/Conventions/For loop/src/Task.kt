/*class DateRange(val start: MyDate, val end: MyDate) : Iterator<MyDate> {


   override fun next(): MyDate {
        return start.followingDate()
    }
    override fun hasNext(): Boolean {
       // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    return true
    }
}
*/
fun iterateOverDateRange(firstDate: MyDate, secondDate: MyDate, handler: (MyDate) -> Unit) {
    for (date in firstDate..secondDate) {
        handler(date)
    }
}

/*
class DateRange(val start: MyDate, val end: MyDate) {
    operator fun iterator(): Iterator<MyDate> {

    }
}
 */

// подсказка:
class DateRange(val start: MyDate, val end: MyDate) : Iterable<MyDate> {
    override fun iterator(): Iterator<MyDate> {
        return object : Iterator<MyDate> {
            var current: MyDate = start

            override fun next(): MyDate {
                if (!hasNext()) throw NoSuchElementException()
                val result = current
                current = current.followingDate()
                return result
            }

            override fun hasNext(): Boolean = current <= end
        }
    }
}
