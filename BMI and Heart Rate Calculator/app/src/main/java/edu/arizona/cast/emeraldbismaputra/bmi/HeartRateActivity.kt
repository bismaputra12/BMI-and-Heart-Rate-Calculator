package edu.arizona.cast.emeraldbismaputra.bmi

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity

class HeartRateActivity : AppCompatActivity() {

    private val heartRateViewModel: HeartRateViewModel by viewModels()

    private lateinit var calculateButton: Button
    private lateinit var clearButton: Button
    private lateinit var maximumTargetTextView: TextView
    private lateinit var maximumTargetResult: TextView
    private lateinit var ageInput: EditText
    private lateinit var missingData: TextView
    private var maxHeartRate: Int = 0
    private var maxTargetRate: Int = 0
    private var minTargetRate: Int = 0
    private var age: Int = 0
    private var calculateClicked: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_heart_rate)

        calculateButton = findViewById(R.id.calculateButton)
        clearButton = findViewById(R.id.clearButton)
        maximumTargetTextView = findViewById(R.id.maximumAndTarget)
        maximumTargetResult = findViewById(R.id.maximumTargetNumber)
        missingData = findViewById(R.id.missingData)
        ageInput = findViewById(R.id.ageInput)

        calculateButton.setOnClickListener { view: View ->
            if (ageInput.text.toString() == ""){
                displayMissingData()
            }
            else {
                age = Integer.valueOf(ageInput.text.toString())
                maxHeartRate = heartRateViewModel.maxHeartRate(age)
                maxTargetRate = heartRateViewModel.maxTargetRate(maxHeartRate)
                minTargetRate = heartRateViewModel.minTargetRate(maxHeartRate)
                displayMessage()
            }
            calculateClicked = true
        }
        clearButton.setOnClickListener { view: View ->
            clearSearch()
        }

        if (heartRateViewModel.returnMaxHeartRate() > 0) {
            displayMessage()
        }

        val savedAge = intent.getIntExtra("age", 0)
        ageInput.setText(savedAge.toString())
    }

    private fun displayMessage(){
        maximumTargetTextView.setText(R.string.maximum_and_target)
        maximumTargetTextView.visibility = View.VISIBLE
        //show minTargetRate and maxTargetRate
        val result = getString(R.string.maximum_and_target_number, heartRateViewModel.returnMaxHeartRate(),
            heartRateViewModel.returnMinTargetRate(), heartRateViewModel.returnMaxTargetRate())
        maximumTargetResult.text = result
        maximumTargetResult.visibility = View.VISIBLE
    }

    private fun clearSearch(){
        maximumTargetTextView.visibility = View.INVISIBLE
        maximumTargetResult.visibility = View.INVISIBLE
        missingData.visibility = View.INVISIBLE
        ageInput.text.clear()
    }

    private fun displayMissingData() {
        missingData.setText(R.string.missingData)
        missingData.visibility = View.VISIBLE
        maximumTargetTextView.visibility = View.INVISIBLE
        maximumTargetResult.visibility = View.INVISIBLE
    }

    override fun onBackPressed() {
        if(calculateClicked){
            val intentToMainActivity = Intent() //create intent
            intentToMainActivity.putExtra("age", age) //give the data to the intent
            setResult(RESULT_OK, intentToMainActivity) //set result to ok
            finish()
        }
        super.onBackPressed()
    }
}

