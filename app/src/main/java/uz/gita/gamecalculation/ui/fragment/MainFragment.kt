package uz.gita.gamecalculation.ui.fragment

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import uz.gita.gamecalculation.R
import uz.gita.gamecalculation.app.App
import uz.gita.gamecalculation.data.local.MySharedPreference
import uz.gita.gamecalculation.databinding.ScreenMainBinding
import uz.gita.gamecalculation.ui.utils.Game
import uz.gita.gamecalculation.ui.utils.convertToTime
import uz.gita.gamecalculation.ui.viewmodel.MainVM
import uz.gita.gamecalculation.ui.viewmodel.MainVMImpl

class MainFragment : Fragment(R.layout.screen_main) {
    private var _binding: ScreenMainBinding? = null
    private val binding: ScreenMainBinding get() = _binding!!
    private val viewModel: MainVM by viewModels<MainVMImpl>()
    private val navController by lazy(LazyThreadSafetyMode.NONE) { findNavController() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = ScreenMainBinding.bind(view)

        initListeners()
        initObserver()
        initFields()
    }

    private fun initFields() {
        binding.time.text = MySharedPreference(App.getInstance()).defaultTime.convertToTime()
    }


    @SuppressLint("FragmentLiveDataObserve")
    private fun initObserver() {
        viewModel.firstPlayerScoreLiveData.observe(viewLifecycleOwner, firstPlayerObserver)
        viewModel.secondPlayerScoreLiveData.observe(viewLifecycleOwner, secondPlayerObserver)
        viewModel.timeLiveData.observe(this, timeObserver)
        viewModel.dialogInfoLiveData.observe(this@MainFragment, dialogInfoObserver)
        viewModel.buttonTextLiveData.observe(this@MainFragment, buttonTextObserver)
        viewModel.navigateSettingLiveData.observe(this, navigateSettingObserver)
        viewModel.navigateHistoryLiveData.observe(this, navigateHistoryObserver)
    }


    private fun initListeners() {
        binding.setting.setOnClickListener {
            viewModel.navigateSetting()
        }
        binding.history.setOnClickListener {
            viewModel.navigateHistory()
        }
        binding.firstButtonIncrease.setOnClickListener {
            viewModel.increaseFirstPlayerScore()
        }
        binding.secondButtonDecrease.setOnClickListener {
            viewModel.decreaseSecondPlayerScore()
        }
        binding.firstButtonDecrease.setOnClickListener {
            viewModel.decreaseFirstPlayerScore()
        }
        binding.secondButtonIncrease.setOnClickListener {
            viewModel.increaseSecondPlayerScore()
        }
        binding.btnGame.setOnClickListener {
            viewModel.clickStartButton()
        }
    }

    private val timeObserver = Observer<Int> {
        binding.time.text = it.convertToTime()
    }

    private val firstPlayerObserver = Observer<Int> {
        binding.firstPlayerScore.text = it.toString()
    }

    private val secondPlayerObserver = Observer<Int> {
        binding.secondPlayerScore.text = it.toString()
    }
    private val buttonTextObserver = Observer<String> {
        binding.btnGame.text = it
    }
    private val navigateSettingObserver = Observer<Unit> {
        navController.navigate(R.id.action_mainFragment_to_settingFragment)
    }
    private val navigateHistoryObserver = Observer<Unit> {
        navController.navigate(R.id.action_mainFragment_to_historyFragment)
    }
    private val dialogInfoObserver = Observer<Game> { game ->
        if (viewModel.getEndGameState() == 0) {
            var message = ""
            when (game) {
                is Game.Win -> {
                    message = "${game.player}"
                }
                is Game.Draw -> {
                    message = "Draw! ${game.score} : ${game.score}"
                }
            }
            AlertDialog.Builder(requireContext())
                .setMessage(message)
                .setCancelable(false)
                .setNeutralButton(
                    "Ok"
                ) { dialog, which ->
                    dialog.cancel()
                    viewModel.setEndGameState(viewModel.getEndGameState() + 1)
                }
                .create()
                .show()
        }
//        viewModel.setStartValue(!viewModel.getStartValue())
//        viewModel.cancelTimer()
    }

    override fun onStart() {
        super.onStart()
        if (!viewModel.getStartValue() || viewModel.getEndGameState() == 1) return
        viewModel.getTime(viewModel.getSaveTime())
    }

    override fun onStop() {
        super.onStop()
        if (!viewModel.getStartValue() || viewModel.getEndGameState() == 1) return
        viewModel.cancelTimer()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}