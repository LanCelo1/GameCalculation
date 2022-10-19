package uz.gita.gamecalculation.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import uz.gita.gamecalculation.ui.utils.Game

interface MainVM {
    val timeLiveData : LiveData<Int>
    val dialogInfoLiveData : LiveData<Game>
    fun increaseFirstPlayerScore()
    fun decreaseFirstPlayerScore()
    fun increaseSecondPlayerScore()
    fun decreaseSecondPlayerScore()
    fun getTime(time: Int)
    fun cancelTimer()
    val firstPlayerScoreLiveData: LiveData<Int>
    val secondPlayerScoreLiveData: LiveData<Int>
    fun setSaveTime(time: Int)
    fun getSaveTime(): Int
    fun clickStartButton()
    fun setStartValue(bol: Boolean)
    fun getStartValue(): Boolean
    fun getEndGameState() : Int
    val buttonTextLiveData: LiveData<String>
    val navigateSettingLiveData: LiveData<Unit>
    val navigateHistoryLiveData: LiveData<Unit>
    fun setEndGameState(s: Int)
    fun navigateSetting()
    fun navigateHistory()
}