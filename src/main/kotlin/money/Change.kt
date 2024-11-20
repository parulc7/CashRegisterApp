import java.util.TreeMap

class Change {
    private val map by lazy {
        TreeMap<MonetaryElement, Int>(Comparator { lhs, rhs ->
            lhs.minorValue.compareTo(rhs.minorValue)
        })
    }

    var total: Long = 0
        private set

    fun getCount(element: MonetaryElement): Int {
        return map[element] ?: 0
    }

    fun add(element: MonetaryElement, count: Int): Change {
        return modify(element, count)
    }

    fun remove(element: MonetaryElement, count: Int): Change {
        return modify(element, -count)
    }

    private fun modify(element: MonetaryElement, count: Int): Change {
        val newCount = (map[element] ?: 0) + count
        require (newCount >= 0) {"Resulting count is less than zero." }
        if (newCount == 0) {
            map.remove(element)
        } else {
            map[element] = newCount
        }
        total += element.minorValue/100 * count
        return this
    }

    override fun toString(): String {
        return "$map"
    }

    companion object {

        fun none(): Change =
            Change()
    }
}
