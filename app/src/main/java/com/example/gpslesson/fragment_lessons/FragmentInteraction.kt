package com.example.gpslesson.fragment_lessons

interface FragmentInteraction {

    fun addFragment(needBackStack: Boolean, colorId: Int, addMethod: AddMethod)

    fun removeFragment()

}

enum class AddMethod {
    Replace, Add
}