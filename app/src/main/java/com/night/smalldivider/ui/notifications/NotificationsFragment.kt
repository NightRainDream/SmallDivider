package com.night.smalldivider.ui.notifications

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.night.divider.SmallItemDecoration
import com.night.smalldivider.Adapter
import com.night.smalldivider.databinding.FragmentNotificationsBinding

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root


        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

//        rv_main.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        _binding?.rvHome?.setLayoutManager( LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL,false));
//        rv_main.setLayoutManager(new GridLayoutManager(this,2));
        _binding?.rvHome?.addItemDecoration( SmallItemDecoration(10, Color.BLUE))
        _binding?.rvHome?.setAdapter( Adapter());
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}