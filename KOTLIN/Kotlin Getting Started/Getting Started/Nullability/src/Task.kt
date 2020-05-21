fun String.firstCharacterOrNull() = if (this.isBlank()) null else this[0]

fun toUpperCaseIfNotNull(value: String?) = value?.toUpperCase()