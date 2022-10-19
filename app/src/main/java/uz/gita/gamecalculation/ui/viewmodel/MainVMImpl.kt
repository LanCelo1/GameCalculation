package uz.gita.gamecalculation.ui.viewmodel

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import uz.gita.gamecalculation.app.App
import uz.gita.gamecalculation.data.local.MySharedPreference
import uz.gita.gamecalculation.ui.utils.Game
import uz.gita.gamecalculation.ui.utils.SharedPreference

class MainVMImpl : MainVM, ViewModel() {
    private var firstPlayerScore = 0
    private var secondPlayerScore = 0
    private var countDownTimer : CountDownTimer? = null
    private var _time = 10
        get() =  MySharedPreference(App.getInstance()).defaultTime
    private var saveTime = 0
    private var isStart = false
    private var endGame = 1

    override fun setStartValue(bol : Boolean){
        isStart = bol
    }

    override fun getStartValue() : Boolean{
        return isStart
    }

    override fun getEndGameState(): Int {
        return endGame
    }
    override fun setEndGameState(s : Int) {
        endGame = s
    }

    override fun navigateSetting() {
        if(endGame != 1 || isStart) return
        navigateSettingLiveData.value = Unit
    }

    override fun navigateHistory() {
        if(endGame != 1 || isStart) return
        navigateHistoryLiveData.value = Unit
    }

    override fun setSaveTime(time : Int){
        _time = time
    }

    override fun getSaveTime() : Int{
        return if (saveTime == 0) _time else saveTime
    }

    override fun clickStartButton() {
        isStart = !isStart
        if (isStart) {
            buttonTextLiveData.value = "STOP"
            getTime(getSaveTime())
            endGame = -1
        } else {
            buttonTextLiveData.value = "START"
            cancelTimer()
        }
    }


    override fun increaseFirstPlayerScore() {
        if (!isStart) return
        firstPlayerScore++
        firstPlayerScoreLiveData.value = firstPlayerScore
    }

    override fun decreaseFirstPlayerScore() {
        if (!isStart) return
        if (firstPlayerScore <= 0) return
        firstPlayerScore--
        firstPlayerScoreLiveData.value = firstPlayerScore
    }

    override fun increaseSecondPlayerScore() {
        if (!isStart) return
        secondPlayerScore++
        secondPlayerScoreLiveData.value = secondPlayerScore
    }

    override fun decreaseSecondPlayerScore() {
        if (!isStart) return
        if (secondPlayerScore <= 0) return
        secondPlayerScore--
        secondPlayerScoreLiveData.value = secondPlayerScore
    }

    override var timeLiveData = MutableLiveData<Int>()
    override var firstPlayerScoreLiveData = MutableLiveData<Int>()
    override var secondPlayerScoreLiveData = MutableLiveData<Int>()
    override val dialogInfoLiveData = MutableLiveData<Game>()
    override val buttonTextLiveData = MutableLiveData<String>()
    override val navigateSettingLiveData =  MutableLiveData<Unit>()
    override val navigateHistoryLiveData =  MutableLiveData<Unit>()

    /*override fun getTime(time: Int){
        job?.cancel()
        job = viewModelScope.launch {
            var t = time
            while (t > 0) {
                t--
                delay(1000)
                timeLiveData.value = t
            }
            timeOverLiveData.value = Unit
        }
    }*/
    override fun cancelTimer(){
        countDownTimer?.cancel()
    }

    override fun getTime(time: Int){
        countDownTimer?.cancel()
        countDownTimer = object : CountDownTimer(time * 1000L, 1000L ){
            override fun onTick(millisUntilFinished: Long) {
                timeLiveData.value = (millisUntilFinished/1000L).toInt()
                saveTime = (millisUntilFinished/1000L).toInt()
            }
            override fun onFinish() {
                finishTimer()
            }
        }
        countDownTimer?.start()
    }
    private fun finishTimer() {
        endGame = 0
        buttonTextLiveData.value = "START"
        isStart = false
        saveHistory()
        if (firstPlayerScore > secondPlayerScore){
            dialogInfoLiveData.value = Game.Win("Player 1 ! Total-> player1 $firstPlayerScore : $secondPlayerScore player2")
        }
        if (firstPlayerScore < secondPlayerScore) {
            dialogInfoLiveData.value = Game.Win("Player 2 win! Total-> player1 $firstPlayerScore : $secondPlayerScore player2")
        }
        if (firstPlayerScore == secondPlayerScore) dialogInfoLiveData.value = Game.Draw(firstPlayerScore)
//        isStart = !isStart
        firstPlayerScore = 0
        firstPlayerScoreLiveData.value = firstPlayerScore
        secondPlayerScore = 0
        secondPlayerScoreLiveData.value = secondPlayerScore
    }

    private fun saveHistory() {
        var history = MySharedPreference(App.getInstance()).history
        MySharedPreference(App.getInstance()).history = if (history.isEmpty()){
            history + "Player1 $firstPlayerScore : $secondPlayerScore Player2"
        }else{
            "$history/Player1 $firstPlayerScore : $secondPlayerScore Player2"
        }

    }


}