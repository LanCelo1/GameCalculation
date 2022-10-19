package uz.gita.gamecalculation.data.local

import android.content.Context
import uz.gita.gamecalculation.ui.utils.SharedPreference

class MySharedPreference(context : Context) : SharedPreference(context) {
    var defaultTime : Int by IntPreference(10)
    var history : String by StringPreference("")
}