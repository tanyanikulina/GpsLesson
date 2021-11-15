package com.example.gpslesson

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.gpslesson.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), FragmentInteraction {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

    }

    override fun addFragment(needBackStack: Boolean, colorId: Int, addMethod: AddMethod) {

        val transaction = supportFragmentManager.beginTransaction()

        when(addMethod) {
            AddMethod.Replace -> transaction.replace(binding.fragmentChild.id, SecondFragment.newInstance(colorId))
            AddMethod.Add -> transaction.add(binding.fragmentChild.id, SecondFragment.newInstance(colorId))
        }

        if (needBackStack)
            transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun removeFragment() {

    }


}