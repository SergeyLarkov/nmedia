package ru.netology.nmedia

fun toText(value: Int): String {
    val i: Int
    val d: Int
    val units: String
    if (value >= 1_000_000) {
        i = value / 1_000_000
        d = (value % 1_000_000) / 100_000
        units = "M"
    } else if (value >= 1_000) {
        i = value / 1_000
        d = if (value >= 10_000) 0 else (value % 1_000) / 100
        units = "K"
    } else {
        i = value
        d = 0
        units = ""
    }
    return if (d != 0) i.toString() + "." + d.toString() + units else i.toString() + units
}