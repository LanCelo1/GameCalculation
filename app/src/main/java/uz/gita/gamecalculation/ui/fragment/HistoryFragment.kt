package uz.gita.gamecalculation.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.SimpleAdapter
import androidx.fragment.app.Fragment
import uz.gita.gamecalculation.R
import uz.gita.gamecalculation.app.App
import uz.gita.gamecalculation.data.local.MySharedPreference
import uz.gita.gamecalculation.databinding.ScreenHistoryBinding
import uz.gita.gamecalculation.databinding.ScreenMainBinding

class HistoryFragment : Fragment(R.layout.screen_history) {
    private var _binding: ScreenHistoryBinding? = null
    private val binding: ScreenHistoryBinding get() = _binding!!
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = ScreenHistoryBinding.bind(view)

        var histories = MySharedPreference(App.getInstance()).history.split("/")
        var arr_history = Array(histories.size){
            it ->""
        }
        histories.forEachIndexed {index,value->
            arr_history[arr_history.size - index - 1] = value
        }

        val adapter = ArrayAdapter(requireContext(),R.layout.item_recyclerview,arr_history)
        binding.listview.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}