package uz.gita.gamecalculation.ui.utils

sealed class Game {
    data class Win(var player : String) : Game()
    data class Draw(var score : Int) : Game()
}