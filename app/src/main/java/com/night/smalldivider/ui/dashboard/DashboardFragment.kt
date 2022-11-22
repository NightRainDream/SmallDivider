package com.night.smalldivider.ui.dashboard

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.night.divider.SmallItemDecoration
import com.night.smalldivider.Adapter
import com.night.smalldivider.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root


        return root
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

//        rv_main.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        _binding?.rvHome?.setLayoutManager( GridLayoutManager(requireContext(), 2));
//        rv_main.setLayoutManager(new GridLayoutManager(this,2));
        _binding?.rvHome?.addItemDecoration( SmallItemDecoration(10, Color.BLUE))
        _binding?.rvHome?.setAdapter( Adapter());
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}