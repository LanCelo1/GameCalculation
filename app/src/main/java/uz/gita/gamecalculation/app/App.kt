package uz.gita.gamecalculation.app

import android.app.Application

class App : Application() {
    companion object{
        private lateinit var INSTANCE : App
        fun getInstance() : App{
            return INSTANCE
        }
    }
    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }
}