package com.example.android.guesstheword.screens.game

import android.os.CountDownTimer
import android.text.format.DateUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {

    companion object {
        private const val TOTAL_TIME = 60000L
        private const val ONE_SECOND = 1000L
        private const val OVER = 0L
    }

    private val _word = MutableLiveData<String>()
    val word: LiveData<String> get() = _word

    private val _score = MutableLiveData<Int>()
    val score: LiveData<Int> get() = _score

    private val _eventGameFinish = MutableLiveData<Boolean>()
    val eventGameFinish: LiveData<Boolean> get() = _eventGameFinish

    private val _displayedTime = MutableLiveData<Long>()
    val displayedTime: LiveData<Long> get() = _displayedTime

    private val timer: CountDownTimer
    private lateinit var wordList: MutableList<String>
    val displayedTimeToString = Transformations.map(displayedTime) { time ->
        DateUtils.formatElapsedTime(time)
    }

    init {
        _word.value = " "
        _score.value = 0
        _eventGameFinish.value = false;

        timer = object : CountDownTimer(TOTAL_TIME, ONE_SECOND) {
            override fun onTick(millisUntilFinished: Long) {
                _displayedTime.value = millisUntilFinished / 1000
            }

            override fun onFinish() {
                _displayedTime.value = OVER
                onGameFinish()
            }
        }.start()
        resetList()
        nextWord()
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("ViewModel", "Destroyed!");
    }


    private fun resetList() {
        wordList = mutableListOf("queen", "hospital", "basketball", "cat", "change", "snail",
                "soup", "calendar", "sad", "desk", "guitar", "home", "railway", "zebra", "jelly",
                "car", "crow", "trade", "bag", "roll", "bubble")
        wordList.shuffle()
    }


    private fun nextWord() {
        if (!wordList.isEmpty()) {
            _word.value = wordList.removeAt(0)
        } else{
            resetList()
        }
    }

    fun onSkip() {
        _score.value = _score.value?.minus(1)
        nextWord()
    }

    fun onCorrect() {
        _score.value = _score.value?.plus(1)
        nextWord()
    }

    fun onGameFinish() {
        _eventGameFinish.value = true
        timer.cancel()
    }

    fun onGameFinishComplete() {
        _eventGameFinish.value = false
    }
}