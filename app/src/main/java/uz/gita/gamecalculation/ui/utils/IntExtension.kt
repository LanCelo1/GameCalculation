package uz.gita.gamecalculation.ui.utils

fun Int.convertToTime() : String{
    var hour = if (this / 60 < 10) "0${this / 60}" else "${this / 60}"
    var minute = if (this % 60 < 10) "0${this % 60}" else "${this % 60}"
    return "$hour:$minute"
}