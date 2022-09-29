package edu.arizona.cast.emeraldbismaputra.bmi

import androidx.lifecycle.ViewModel

//main use of the view model class is to save user data
//so if the user rotated the device and the main activity is destroyed
//the data still exists in the view model class and can be returned by the functions

//to use view model, I have to add some dependencies in the build.gradle file first

open class MainActivityViewModel : ViewModel() {

    private var feetInInches: Int = 0
    private var totalInches: Int = 0
    private lateinit var message: String
    private var bmiResult: Float = 0.0f

    fun bmiResult(feet: Int, inches: Int, pounds: Int): Float{
        feetInInches = feet * 12
        totalInches = feetInInches + inches
        bmiResult =  (pounds.toFloat() * 703)/(totalInches.toFloat() * totalInches.toFloat())
        return bmiResult
    }

    fun bmiResultMessage(): String {
        message = if (bmiResult < 18.5) {
            "Underweight"
        } else if ((bmiResult >= 18.5) && (bmiResult <= 24.9)){
            "Normal"
        } else if ((bmiResult > 24.9) && (bmiResult < 30)){
            "Overweight"
        } else {
            "Obese"
        }
        return message
    }

    fun returnBmiResult(): Float {
        return bmiResult
    }
}