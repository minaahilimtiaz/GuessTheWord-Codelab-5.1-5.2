package com.example.android.guesstheword.screens.score

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.example.android.guesstheword.R
import com.example.android.guesstheword.databinding.ScoreFragmentBinding


class ScoreFragment : Fragment() {

    private lateinit var viewModel: ScoreViewModel
    private lateinit var viewModelFactory: ScoreViewModelFactory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: ScoreFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.score_fragment, container, false)
        viewModelFactory = ScoreViewModelFactory(ScoreFragmentArgs.fromBundle(arguments!!).score)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ScoreViewModel::class.java)
        viewModel.score.observe(viewLifecycleOwner, Observer { newScore -> binding.scoreText.text = newScore.toString() })
        viewModel.eventPlayAgain.observe(viewLifecycleOwner, Observer { playAgain -> playAgainComplete(playAgain) })
        binding.playAgainButton.setOnClickListener { onPlayAgain() }
        return binding.root
    }

    private fun onPlayAgain() {
        viewModel.onPlayAgain()
    }

    private fun playAgainComplete(playAgain: Boolean) {
        if (playAgain) {
            findNavController(this).navigate(ScoreFragmentDirections.actionRestart())
            viewModel.onPlayAgainComplete()
        }
    }
}
