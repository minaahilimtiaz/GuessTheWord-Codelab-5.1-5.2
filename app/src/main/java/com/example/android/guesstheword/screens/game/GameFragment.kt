package com.example.android.guesstheword.screens.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.example.android.guesstheword.R
import com.example.android.guesstheword.databinding.GameFragmentBinding

class GameFragment : Fragment() {

    private lateinit var binding: GameFragmentBinding
    private lateinit var viewModel: GameViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.game_fragment, container, false)
        viewModel = ViewModelProviders.of(this).get(GameViewModel::class.java)
        binding.gameViewModel = viewModel;
        binding.lifecycleOwner = viewLifecycleOwner
        viewModel.eventGameFinish.observe(viewLifecycleOwner, Observer<Boolean> { hasFinished -> if (hasFinished) onEnd() })
        return binding.root

    }

    private fun onEnd() {
        val endAction = GameFragmentDirections.actionGameToScore()
        endAction.score = viewModel.score.value ?: 0
        Toast.makeText(context, "Game Ended!", Toast.LENGTH_SHORT).show()
        viewModel.onGameFinishComplete()
        findNavController(this).navigate(endAction)
    }

}
