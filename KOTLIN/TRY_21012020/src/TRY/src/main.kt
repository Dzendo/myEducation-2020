package TRY.src

import TRY.MyDate
import TRY.iterateOverDateRange

fun main() {
    val actualDateRange = arrayListOf<MyDate>()
    iterateOverDateRange(MyDate(2016, 5, 1), MyDate(2016, 5, 5)) { date ->
        actualDateRange.add(date)
    }
    val expectedDateRange = arrayListOf(
        MyDate(2016, 5, 1), MyDate(2016, 5, 2), MyDate(2016, 5, 3), MyDate(2016, 5, 4), MyDate(2016, 5, 5))
  //  Assert.assertEquals("Incorrect iteration over the following dates:\n",
   //     expectedDateRange, actualDateRange)
}
