package com.example.gpslesson

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.gpslesson.databinding.FragmentFirstBinding
import com.example.gpslesson.databinding.FragmentSecondBinding
import kotlin.random.Random
import kotlin.random.nextInt
import android.animation.ValueAnimator
import android.animation.ValueAnimator.AnimatorUpdateListener
import android.animation.ValueAnimator.INFINITE
import com.example.gpslesson.extensions.animateScale


class FirstFragment : Fragment() {

    val colorList = arrayListOf(
        R.color.orange_50,
        R.color.teal_50,
        R.color.green_50,
        R.color.rose_50,
        R.color.purple_200
    )

    private lateinit var binding: FragmentFirstBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnCreate.animateScale()

        binding.btnCreate.setOnClickListener {
            if (requireActivity() is FragmentInteraction) {

                val needBackStack = binding.sw.isChecked
                val rnd = Random.Default.nextInt(colorList.size)
                val color = colorList[rnd]

                val addMethod =
                    if (binding.rg.checkedRadioButtonId == binding.rbAdd.id)
                        AddMethod.Add
                    else AddMethod.Replace

                (requireActivity() as FragmentInteraction).addFragment(needBackStack, color, addMethod)


            }
        }

    }



}