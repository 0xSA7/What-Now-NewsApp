package com.example.whatnow.API

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.whatnow.R
import com.example.whatnow.databinding.FragmentSearchBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class SearchFragment : Fragment(R.layout.fragment_search) {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private var listener: OnSearchListener? = null

    interface OnSearchListener {
        fun onSearch(query: String, language: String, fromDate: String, toDate: String)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnSearchListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnSearchListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val calendar = Calendar.getInstance()
        setupDatePickerDialog(binding.fromDateIb, binding.selectedFromDateTv, calendar)
        setupDatePickerDialog(binding.toDateIb, binding.selectedToDateTv, calendar)

        val adapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.languages_array,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.LanguageSpinner.adapter = adapter

        binding.findBtn.setOnClickListener {
            val queryText = binding.editQuery.editText?.text.toString()
            val selectedLanguage = binding.LanguageSpinner.selectedItem.toString()
            val fromDate = binding.selectedFromDateTv.text.toString()
            val toDate = binding.selectedToDateTv.text.toString()

            listener?.onSearch(queryText, selectedLanguage, fromDate, toDate)
            parentFragmentManager.popBackStack()
        }
    }

    private fun setupDatePickerDialog(
        button: ImageButton,
        dateTextView: TextView,
        calendar: Calendar
    ) {
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, year, monthOfYear, dayOfMonth ->
                calendar.set(year, monthOfYear, dayOfMonth)
                val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                dateTextView.text = dateFormat.format(calendar.time)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        button.setOnClickListener {
            datePickerDialog.show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}