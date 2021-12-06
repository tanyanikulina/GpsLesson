package com.example.gpslesson.fragment_lessons

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.gpslesson.databinding.FragmentSecondBinding

private const val COLOR_PARAM = "color_param"

class SecondFragment : Fragment() {
    private var colorId: Int? = null

    private lateinit var binding: FragmentSecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            colorId = it.getInt(COLOR_PARAM)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        colorId?.let {
            binding.clMain.setBackgroundResource(it)
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(colorId: Int) =
            SecondFragment().apply {
                arguments = Bundle().apply {
                    putInt(COLOR_PARAM, colorId)
                }
            }
    }
}
