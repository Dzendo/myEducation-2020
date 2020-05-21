@file:Suppress("UNUSED_PARAMETER")

package lesson12.task1

/**
 * Класс "расписание поездов".
 *
 * Общая сложность задания -- средняя.
 * Объект класса хранит расписание поездов для определённой станции отправления.
 * Для каждого поезда хранится конечная станция и список промежуточных.
 * Поддерживаемые методы:
 * добавить новый поезд, удалить поезд,
 * добавить / удалить промежуточную станцию существующему поезду,
 * поиск поездов по времени.
 *
 * В конструктор передаётся название станции отправления для данного расписания.
 */
class TrainTimeTable(val baseStationName: String) {

    val Schedule: MutableList<Train> = mutableListOf()

    /**
     * Добавить новый поезд.
     *
     * Если поезд с таким именем уже есть, следует вернуть false и ничего не изменять в таблице
     *
     * @param train название поезда
     * @param depart время отправления с baseStationName
     * @param destination конечная станция
     * @return true, если поезд успешно добавлен, false, если такой поезд уже есть
     */
    fun addTrain(train: String, depart: Time, destination: Stop): Boolean {
        for (tr in Schedule) if (tr.name == train) return false
        return Schedule.add(Train(train, Stop(baseStationName, depart), destination))
    }
    fun addTrain(train: String, stops: List<Stop>): Boolean {   // Перегруженная функция
        for (tr in Schedule) if (tr.name == train) return false
        return Schedule.add(Train(train, stops))
    }

    /**
     * Удалить существующий поезд.
     *
     * Если поезда с таким именем нет, следует вернуть false и ничего не изменять в таблице
     *
     * @param train название поезда
     * @return true, если поезд успешно удалён, false, если такой поезд не существует
     */
    fun removeTrain(train: String): Boolean {
        for (tr in Schedule) if (tr.name == train) return Schedule.remove(tr)
        return false
    }

    /**
     * Добавить/изменить начальную, промежуточную или конечную остановку поезду.
     *
     * Если у поезда ещё нет остановки с названием stop, добавить её и вернуть true.
     * Если stop.name совпадает с baseStationName, изменить время отправления с этой станции и вернуть false.
     * Если stop совпадает с destination данного поезда, изменить время прибытия на неё и вернуть false.
     * Если stop совпадает с одной из промежуточных остановок, изменить время прибытия на неё и вернуть false.
     *
     * Функция должна сохранять инвариант: время прибытия на любую из промежуточных станций
     * должно находиться в интервале между временем отправления с baseStation и временем прибытия в destination,
     * иначе следует бросить исключение IllegalArgumentException.
     * Также, время прибытия на любую из промежуточных станций не должно совпадать с временем прибытия на другую
     * станцию или с временем отправления с baseStation, иначе бросить то же исключение.
     *
     * @param train название поезда
     * @param stop начальная, промежуточная или конечная станция
     * @return true, если поезду была добавлена новая остановка, false, если было изменено время остановки на старой
     */
    fun addStop(train: String, stop: Stop): Boolean
    {   print(train) ; println(stop)
        var ttrain: Train? = null
        for (tr in Schedule) if (tr.name == train) { ttrain = tr ;  break }
        if (ttrain == null) return false


        if (ttrain.stops[0].name == stop.name)
            if (stop.time > ttrain.stops[1].time) throw IllegalArgumentException()
                else  { ttrain.stops[0].time = stop.time; return false }

        if (ttrain.stops.last().name == stop.name)
            if (stop.time < ttrain.stops[ttrain.stops.size - 2].time) throw IllegalArgumentException()
                else { ttrain.stops.last().time = stop.time; return false }

        for (stp in ttrain.stops.indices)
            if (ttrain.stops[stp].name == stop.name)
                if (ttrain.stops[stp-1].time > stop.time || ttrain.stops[stp + 1].time < stop.time )
                    throw IllegalArgumentException()
                    else {ttrain.stops[stp].time = stop.time ; return false}
        // Добавляем новый:
        for (stp in ttrain.stops) if (stp.time == stop.time ) throw IllegalArgumentException()

       // println(ttrain.stops)
        var newStopIndex = -1
        for (stp in 0 until ttrain.stops.size - 1)
            if (stop.time > ttrain.stops[stp].time && stop.time < ttrain.stops[stp+1].time)
            {newStopIndex = stp + 1 ; break }
        //println(newStopIndex)
        if (newStopIndex == -1) throw IllegalArgumentException()

        val stopsNew: MutableList<Stop> = ttrain.stops.toMutableList()
        stopsNew.add(newStopIndex, stop)

        removeTrain(ttrain.name)
        //println(stopsNew)

        return addTrain(ttrain.name, stopsNew)
    }

    /**
     * Удалить одну из промежуточных остановок.
     *
     * Если stopName совпадает с именем одной из промежуточных остановок, удалить её и вернуть true.
     * Если у поезда нет такой остановки, или stopName совпадает с начальной или конечной остановкой, вернуть false.
     *
     * @param train название поезда
     * @param stopName название промежуточной остановки
     * @return true, если удаление успешно
     */
    fun removeStop(train: String, stopName: String): Boolean
    {
        var ttrain: Train? = null
        for (tr in Schedule) if (tr.name == train) { ttrain = tr ;  break }
        if (ttrain == null) return false
        if (ttrain.stops[0].name == stopName) return false
        if (ttrain.stops.last().name == stopName) return false

        // Удаляем новый:

        var remStp: Stop? = null
        for (stp in ttrain.stops)
            if (stp.name == stopName) {remStp = stp ; break }
        //println(newStopIndex)
        if (remStp == null) return false

        val stopsNew: MutableList<Stop> = ttrain.stops.toMutableList()
        stopsNew.remove(remStp)

        removeTrain(ttrain.name)
        //println(stopsNew)

        return addTrain(ttrain.name, stopsNew)
    }

    /**
     * Вернуть список всех поездов, упорядоченный по времени отправления с baseStationName
     */
    fun trains(): List<Train> = Schedule.sorted()


    /**
     * Вернуть список всех поездов, отправляющихся не ранее currentTime
     * и имеющих остановку (начальную, промежуточную или конечную) на станции destinationName.
     * Список должен быть упорядочен по времени прибытия на станцию destinationName
     */
    fun trains(currentTime: Time, destinationName: String): List<Train> //= TODO()
    {
        val rez: MutableList<Train> = mutableListOf()
        for (tr in Schedule)
            if (tr.stops[0].time >= currentTime)
                for (str in tr.stops)
                    if (str.name == destinationName) rez.add(tr)
        return rez.sortedBy { it.stops.first { it.name == destinationName }.time }
    }
    /**
     * Сравнение на равенство.
     * Расписания считаются одинаковыми, если содержат одинаковый набор поездов,
     * и поезда с тем же именем останавливаются на одинаковых станциях в одинаковое время.
     */
    override fun equals(other: Any?): Boolean //= TODO()
    {
        if (other !is TrainTimeTable) return false
        other as TrainTimeTable
        if (this === other) return true
        if (this.baseStationName != other.baseStationName) return false
        if (this.Schedule === other.Schedule) return true
        if (this.Schedule.map { it.name }.toSet().sorted() !=
            other.Schedule.map { it.name }.toSet().sorted()) return false
        println(this.Schedule);println(other.Schedule)
        for (thisTr in this.Schedule)
            for (otherTr in other.Schedule)
                if (otherTr.name == thisTr.name)
                    for (thisStop in thisTr.stops)
                        for (otherStop in otherTr.stops)
                            if (thisStop.name == otherStop.name)
                                if (thisStop.time != otherStop.time) return false

        return true
    }
}

/**
 * Время (часы, минуты)
 */
data class Time(var hour: Int, var minute: Int) : Comparable<Time> {
    /**
     * Сравнение времён на больше/меньше (согласно контракту compareTo)
     */
    override fun compareTo(other: Time): Int = when {
        this.hour > other.hour -> 1
        this.hour < other.hour -> -1
        this.hour == other.hour && this.minute > other.minute -> 1
        this.hour == other.hour && this.minute < other.minute -> -1
        this.hour == other.hour && this.minute == other.minute -> 0
        else -> throw IllegalArgumentException("Time")
    }
}

/**  ***************************** -1 *************************************
 * Остановка (название, время прибытия)
 */
data class Stop(val name: String, var time: Time) : Comparable<Stop> {
    override fun compareTo(other: Stop): Int = this.time.compareTo(other.time)
}

/**  ***************************** -1 *************************************
 * Поезд (имя, список остановок, упорядоченный по времени).
 * Первой идёт начальная остановка, последней конечная.
 */
data class Train(val name: String, val stops: List<Stop>) : Comparable<Train> {
    constructor(name: String, vararg stops: Stop) : this(name, stops.asList())

    override fun compareTo(other: Train): Int = this.stops[0].time.compareTo(other.stops[0].time)
}
