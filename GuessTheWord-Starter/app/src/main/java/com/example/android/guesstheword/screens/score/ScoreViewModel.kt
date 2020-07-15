package com.example.android.guesstheword.screens.score

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ScoreViewModel(finalScore: Int): ViewModel() {

    private val _score: MutableLiveData<Int> by lazy {
        val liveData = MutableLiveData<Int>()
        liveData.value = finalScore
        liveData
    }
    val score: LiveData<Int>
        get() = _score

    private val _evtPlayAgain: MutableLiveData<Boolean> by lazy {
        val liveData = MutableLiveData<Boolean>()
        liveData.value = false
        liveData
    }
    val evtPlayAgain: LiveData<Boolean>
        get() = _evtPlayAgain

    init {
        Log.wtf("ScoreViewModel", "final score => $finalScore")
    }

    fun onPlayAgain() {
        _evtPlayAgain.value = true
    }

    fun onPlayAgainComplete() {
        _evtPlayAgain.value = false
    }

}