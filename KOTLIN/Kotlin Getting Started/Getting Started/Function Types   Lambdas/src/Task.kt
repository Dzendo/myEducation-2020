val sum_d  = { a1: Int, a2: Int -> a1+a2  }

fun <T> operateOnEach_d  (list: List<T>,num: T, operation:(T,T) -> T): List<T> =
        list.map { operation(it, num) }

fun sumOnEach_d(values: List<Int>, v2: Int) = operateOnEach_d(values,v2,sum)

// Starter:
val sum: (Int, Int) -> Int = { v1, v2 ->
    v1 + v2
}

fun operateOnEach(values: List<Int>, v2: Int, operation: (Int, Int) -> Int) = values.map{ operation(it, v2) }

fun sumOnEach(values: List<Int>, v2: Int) = operateOnEach(values, v2, sum)