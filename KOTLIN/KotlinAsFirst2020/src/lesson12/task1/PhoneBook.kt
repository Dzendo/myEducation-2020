@file:Suppress("UNUSED_PARAMETER")

package lesson12.task1

/**
 * Класс "Телефонная книга".
 *
 * Общая сложность задания -- средняя.
 * Объект класса хранит список людей и номеров их телефонов,
 * при чём у каждого человека может быть более одного номера телефона.
 * Человек задаётся строкой вида "Фамилия Имя".
 * Телефон задаётся строкой из цифр, +, *, #, -.
 * Поддерживаемые методы: добавление / удаление человека,
 * добавление / удаление телефона для заданного человека,
 * поиск номера(ов) телефона по заданному имени человека,
 * поиск человека по заданному номеру телефона.
 *
 * Класс должен иметь конструктор по умолчанию (без параметров).
 */
class PhoneBook {

    val phoneBook: MutableMap<String, MutableSet<String>> = mutableMapOf()

    /**
     * Добавить человека.
     * Возвращает true, если человек был успешно добавлен,
     * и false, если человек с таким именем уже был в телефонной книге
     * (во втором случае телефонная книга не должна меняться).
     */
    fun addHuman(name: String): Boolean //= TODO()
    {
        if (phoneBook.containsKey(name)) return false
        phoneBook.put(name, mutableSetOf())
        return true
    }
    /**
     * Убрать человека.
     * Возвращает true, если человек был успешно удалён,
     * и false, если человек с таким именем отсутствовал в телефонной книге
     * (во втором случае телефонная книга не должна меняться).
     */
    fun removeHuman(name: String): Boolean //= TODO()
    {
        if (!phoneBook.containsKey(name)) return false
        phoneBook.remove(name)
        return true
    }
    /**
     * Добавить номер телефона.
     * Возвращает true, если номер был успешно добавлен,
     * и false, если человек с таким именем отсутствовал в телефонной книге,
     * либо у него уже был такой номер телефона,
     * либо такой номер телефона зарегистрирован за другим человеком.
     */
    fun addPhone(name: String, phone: String): Boolean //= TODO()
    {
        if (!phoneBook.containsKey(name)) return false
        if (humanByPhone(phone) != null) return false
        val phones = phoneBook.get(name)
        if (phones?.contains(phone) ?: false) return false
        return phones!!.add(phone)
    }
    /**
     * Убрать номер телефона.
     * Возвращает true, если номер был успешно удалён,
     * и false, если человек с таким именем отсутствовал в телефонной книге
     * либо у него не было такого номера телефона.
     */
    fun removePhone(name: String, phone: String): Boolean //= TODO()
    {
        if (!phoneBook.containsKey(name)) return false
        val phones = phoneBook.get(name)
        if (!(phones?.contains(phone) ?: true)) return false
        return phones!!.remove(phone)
    }
    /**
     * Вернуть все номера телефона заданного человека.
     * Если этого человека нет в книге, вернуть пустой список
     */
    fun phones(name: String): Set<String> //= TODO()
    {
        if (!phoneBook.containsKey(name)) return setOf()
        val phones = phoneBook.get(name)
        return phones?.toSet() ?: setOf()
    }
    /**
     * Вернуть имя человека по заданному номеру телефона.
     * Если такого номера нет в книге, вернуть null.
     */
    fun humanByPhone(phone: String): String? //= TODO()
    {
        for (names in phoneBook)
            if (names.value.contains(phone)) return names.key
        return null
    }
    /**
     * Две телефонные книги равны, если в них хранится одинаковый набор людей,
     * и каждому человеку соответствует одинаковый набор телефонов.
     * Порядок людей / порядок телефонов в книге не должен иметь значения.
     */

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PhoneBook
        for ((key, value) in phoneBook)
            if (value.sorted() != other.phoneBook.getValue(key).sorted()) return false
        //if (phoneBook != other.phoneBook) return false

        return true
    }

    override fun hashCode(): Int {
        return phoneBook.hashCode()
    }

}