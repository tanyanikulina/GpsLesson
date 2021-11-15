package com.example.gpslesson

interface FragmentInteraction {

    fun addFragment(needBackStack:Boolean, colorId:Int, addMethod:AddMethod)

    fun removeFragment()

}

enum class AddMethod {
    Replace, Add
}