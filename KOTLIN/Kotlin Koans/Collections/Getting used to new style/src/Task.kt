fun doSomethingWithCollection(collection: Collection<String>): Collection<String>? {

    val groupsByLength = collection.groupBy { s -> s.length }

    val maximumSizeOfGroup = groupsByLength.values.map { group -> group.size }.max()

    return groupsByLength.values.firstOrNull { group -> group.size == maximumSizeOfGroup }
}


fun doSomethingWithCollectionOldStyle(       // И що енто такое?
        collection: Collection<String>
): Collection<String>? {
    val groupsByLength = mutableMapOf<Int,List<String>>()  //mutableMapOf&gt;()
    for (s in collection) {
        var strings: MutableList<String>? = groupsByLength[s.length]?.toMutableList()
        if (strings == null) {
            strings = mutableListOf()
            groupsByLength[s.length] = strings
        }
        strings.add(s)
    }

    var maximumSizeOfGroup = 0
    for (group in groupsByLength.values) {
        if (group.size > maximumSizeOfGroup) {
            maximumSizeOfGroup = group.size
        }
    }

    for (group in groupsByLength.values) {
        if (group.size == maximumSizeOfGroup) {
            return group
        }
    }
    return null
}