package com.example.mvvmdemo.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.mvvmdemo.databinding.FragmentMainBinding
import com.example.mvvmdemo.viewmodel.MainViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding get() = checkNotNull(_binding)

    private val viewModel by viewModel<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentMainBinding.inflate(layoutInflater).also {
        _binding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupClickListeners()

        subscribeLiveData()
        subscribeStateFlow()
    }

    override fun onResume() {
        super.onResume()

        viewModel.behaviorSubject.subscribe() {
            binding.textViewRx.text = it
        }
    }

    override fun onPause() {
        super.onPause()

        viewModel.behaviorSubject.unsubscribeOn(AndroidSchedulers.mainThread())
    }

    private fun setupClickListeners() = with(binding) {
        buttonLiveData.setOnClickListener {
            viewModel.updateLivaData(editTextLiveData.text.toString())
        }

        buttonFlow.setOnClickListener {
            viewModel.updateStateFlow(editTextLiveData.text.toString())
        }

        buttonRx.setOnClickListener {
            viewModel.updateRx(editTextRx.text.toString())
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun subscribeLiveData() {
        viewModel.liveData.observe(viewLifecycleOwner) {
            binding.textViewLiveData.text = it
        }
    }

    private fun subscribeStateFlow() {
        lifecycleScope.launchWhenStarted {
            viewModel.stateFlow.collectLatest {
                binding.textViewFlow.text = it
            }
        }
    }
}