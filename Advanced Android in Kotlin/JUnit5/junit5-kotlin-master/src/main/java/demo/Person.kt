package demo

import java.time.LocalDate
import java.time.Period

data class Person(val firstName: String, val lastName: String, val birthDate: LocalDate?) {

    val age
        get() = Period.between(this.birthDate, LocalDate.now()).years
}