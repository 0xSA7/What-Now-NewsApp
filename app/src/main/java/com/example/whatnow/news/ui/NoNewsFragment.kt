package com.example.whatnow.news.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.whatnow.core.ui.MainActivity
import com.example.whatnow.databinding.FragmentNoNewsBinding

class NoNewsFragment : Fragment() {

    private var _binding: FragmentNoNewsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout using view binding
        _binding = FragmentNoNewsBinding.inflate(inflater, container, false)

        setupUI()

        return binding.root
    }

    private fun setupUI() {
        binding.tryAgainBtn.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
            val intent = Intent(requireContext(), MainActivity::class.java)
            intent.putExtra("openSearchFragment", true)
            startActivity(intent)
            requireActivity().finish()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
