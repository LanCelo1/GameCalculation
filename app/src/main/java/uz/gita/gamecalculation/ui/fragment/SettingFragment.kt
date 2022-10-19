package uz.gita.gamecalculation.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import uz.gita.gamecalculation.R
import uz.gita.gamecalculation.app.App
import uz.gita.gamecalculation.data.local.MySharedPreference
import uz.gita.gamecalculation.databinding.ScreenSettingBinding

class SettingFragment : Fragment(R.layout.screen_setting) {
    private var _binding: ScreenSettingBinding? = null
    private val binding: ScreenSettingBinding get() = _binding!!
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = ScreenSettingBinding.bind(view)

        val time_array = resources.getStringArray(R.array.time_array)
        val adapter = ArrayAdapter(requireContext(),
            android.R.layout.simple_spinner_item, time_array)
        binding.spinner.adapter = adapter

        binding.spinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>,
                                        view: View, position: Int, id: Long) {
              /*  Toast.makeText(this@MainActivity,
                    getString(R.string.selected_item) + " " +
                            "" + languages[position], Toast.LENGTH_SHORT).show()*/
                MySharedPreference(App.getInstance()).defaultTime = time_array[position].toInt()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                val position = time_array.indexOf(MySharedPreference(App.getInstance()).defaultTime.toString())
                Log.d("TTT",position.toString())
                parent.setSelection(position)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}