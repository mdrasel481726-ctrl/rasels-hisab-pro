package com.hisab.rasels.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hisab.rasels.data.DataManager
import com.hisab.rasels.databinding.FragmentReportsBinding
import java.time.YearMonth

class ReportsFragment : Fragment() {

    private var _binding: FragmentReportsBinding? = null
    private val binding get() = _binding!!
    private lateinit var dataManager: DataManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReportsBinding.inflate(inflater, container, false)
        dataManager = DataManager(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        render()
    }

    private fun render() {
        val data = dataManager.getData()
        val currentMonth = YearMonth.now().toString()
        val txMonth = data.tx.filter { it.date.startsWith(currentMonth) && it.type == "expense" }
        
        val categoryMap = mutableMapOf<String, Double>()
        txMonth.forEach { tx ->
            categoryMap[tx.category] = (categoryMap[tx.category] ?: 0.0) + tx.amount
        }
        
        // Display report data
        // Implementation for charts/graphs would go here
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}