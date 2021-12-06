package com.example.gpslesson.fragment_lessons

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.gpslesson.R
import com.example.gpslesson.databinding.FragmentFirstBinding
import com.example.gpslesson.extensions.animateScale
import kotlin.random.Random


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
