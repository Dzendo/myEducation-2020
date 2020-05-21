package TRY

object RoManiac {
    /*
Основные числа:
I - 1
V - 5
X - 10
L - 50
C - 100
D - 500
M - 1000
Ноль отсутствует вообще,поэтому будем использовать пустое значение
*/
// Основной метод преобразования из арабских в римские
    fun convert(arab: Int): String {
        val a = StringBuffer("")
        // Выделяем тысячи
        val m1 = arab / 1000
        a.append(M(m1))
        // то что осталось после тысяч
        val m2 = arab % 1000
        // Выделяем пятьсот из остатка от тысячи
        val d1 = m2 / 500
        a.append(D(d1))
        // остаток после выделения полтысячи
        val d2 = m2 % 500
        // Выделяем сотни из остатка
        val c1 = d2 / 100
        a.append(C(c1))
        // остаток из сотен
        val c2 = d2 % 100
        // Выделяем полсотни
        val l1 = c2 / 50
        a.append(L(l1))
        // остаток
        val l2 = c2 % 50
        // Выделяем десятки
        val x1 = l2 / 10
        a.append(X(x1))
        // остаток
        val x2 = l2 % 10
        // Выделяем то что осталось
        a.append(basic(x2))
        return a.toString()
    }

    // преобразовываем целые тысячи
// с значениями,кратными десяти, но не 5,всё просто
    private fun M(`in`: Int): String {
        val a = StringBuffer("")
        var i = 0
        while (i < `in`) {
            a.append("M")
            i++
        }
        return a.toString()
    }

    // преобразовываем целые сотни
    private fun C(`in`: Int): String {
        return if (`in` == 4) "CD" //если 400, то 500-100
        else if (`in` != 0 && `in` < 4) {
            val a = StringBuffer("")
            var i = 0
            while (i < `in`) {
                a.append("C")
                i++
            }
            a.toString()
        } else ""
    }

    // целые десятки
    private fun X(`in`: Int): String {
        return if (`in` == 4) "XL" // если 40, то 50-10
        else if (`in` != 0 && `in` < 4) {
            val a = StringBuffer("")
            var i = 0
            while (i < `in`) {
                a.append("X")
                i++
            }
            a.toString()
        } else ""
    }

    // преобразовываем пол тысячи
    private fun D(`in`: Int): String {
        return if (`in` == 4) "CM" // если 900, то 1000-100
        else "D"
    }

    private fun L(`in`: Int): String {
        return if (`in` == 4) "XC" else "L" // если90 то100 - 10
    }

    // От 1 до 9, то что осталось
    private fun basic(`in`: Int): String {
        val a = arrayOf(
            "",
            "I",
            "II",
            "III",
            "IV",
            "V",
            "VI",
            "VII",
            "VIII",
            "IX"
        )
        return a[`in`]
    }
}