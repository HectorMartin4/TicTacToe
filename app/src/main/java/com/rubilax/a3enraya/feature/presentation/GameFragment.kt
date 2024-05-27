package com.rubilax.a3enraya.feature.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.rubilax.a3enraya.databinding.FragmentGameBinding
import com.rubilax.a3enraya.feature.domain.Turn
import com.rubilax.a3enraya.feature.presentation.adapter.GameAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GameFragment : Fragment() {

    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!
    private val gameAdapter = GameAdapter()
    private val viewModel by viewModels<GameViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        setupView()
        return binding.root
    }

    private fun setupView() {
        binding.apply {
            gameBoard.apply {
                layoutManager = GridLayoutManager(
                    requireContext(),
                    3,
                    LinearLayoutManager.VERTICAL,
                    false
                )
                addItemDecoration(
                    DividerItemDecoration(
                        requireContext(),
                        LinearLayoutManager.VERTICAL
                    )
                )
                addItemDecoration(
                    DividerItemDecoration(
                        requireContext(),
                        LinearLayoutManager.HORIZONTAL
                    )
                )
                gameAdapter.setBoardSquare {

                }
                adapter = gameAdapter
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadBoard()
        viewModel.loadTurn()
        setupObservers()
    }

    private fun setupObservers() {
        val observer = Observer<GameViewModel.UiState> {
            gameAdapter.submitList(it.boardSquares)
        }
        viewModel.uiState.observe(viewLifecycleOwner, observer)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}