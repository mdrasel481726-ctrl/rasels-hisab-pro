package com.hisab.rasels.ui.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.hisab.rasels.R
import com.hisab.rasels.data.DataManager
import com.hisab.rasels.data.Transaction
import com.hisab.rasels.databinding.FragmentHomeBinding
import java.time.LocalDate
import java.time.YearMonth

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var dataManager: DataManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        dataManager = DataManager(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState()
        
        binding.dateInput.setText(LocalDate.now().toString())
        
        binding.dateInput.setOnClickListener {
            val today = LocalDate.now()
            DatePickerDialog(
                requireContext(),
                { _, year, month, dayOfMonth ->
                    binding.dateInput.setText(
                        String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth)
                    )
                },
                today.year, today.monthValue - 1, today.dayOfMonth
            ).show()
        }

        binding.saveButton.setOnClickListener { addTransaction() }
        
        render()
    }

    private fun addTransaction() {
        val amount = binding.amountInput.text.toString().toDoubleOrNull()
        if (amount == null || amount <= 0) {
            Toast.makeText(requireContext(), "সঠিক টাকার পরিমাণ দিন।", Toast.LENGTH_SHORT).show()
            return
        }

        val data = dataManager.getData()
        data.tx.add(
            Transaction(
                amount = amount,
                type = binding.typeSpinner.selectedItem.toString(),
                category = binding.categorySpinner.selectedItem.toString(),
                date = binding.dateInput.text.toString(),
                note = binding.noteInput.text.toString().trim()
            )
        )
        
        dataManager.saveData(data)
        binding.amountInput.text.clear()
        binding.noteInput.text.clear()
        
        Toast.makeText(requireContext(), "হিসাব সংরক্ষিত হয়েছে।", Toast.LENGTH_SHORT).show()
        render()
    }

    private fun render() {
        val data = dataManager.getData()
        val currentMonth = YearMonth.now().toString()
        val txMonth = data.tx.filter { it.date.startsWith(currentMonth) }
        
        val income = txMonth.filter { it.type == "income" }.sumOf { it.amount }
        val expense = txMonth.filter { it.type == "expense" }.sumOf { it.amount }
        val balance = income - expense
        
        binding.incomeValue.text = formatMoney(income)
        binding.expenseValue.text = formatMoney(expense)
        binding.balanceValue.text = formatMoney(balance)
    }

    private fun formatMoney(amount: Double): String {
        return "৳" + String.format("%.2f", amount)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}