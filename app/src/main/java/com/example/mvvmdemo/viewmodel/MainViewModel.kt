package com.example.mvvmdemo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mvvmdemo.model.Data
import io.reactivex.rxjava3.subjects.BehaviorSubject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel : ViewModel() {

    val liveData: LiveData<String> get() = _liveData
    private val _liveData = MutableLiveData(Data.liveDataText)

    val stateFlow: StateFlow<String> get() = _stateFlow.asStateFlow()
    private val _stateFlow = MutableStateFlow(Data.liveDataText)

    val behaviorSubject: BehaviorSubject<String> = BehaviorSubject.create()

    init {
        behaviorSubject.onNext(Data.rxText)
    }

    fun updateLivaData(text: String) {
        Data.liveDataText = text
        _liveData.value = text
    }

    fun updateStateFlow(text: String) {
        Data.flowText = text
        _stateFlow.value = text
    }

    fun updateRx(text: String) {
        Data.rxText = text
        behaviorSubject.onNext(text)
    }
}