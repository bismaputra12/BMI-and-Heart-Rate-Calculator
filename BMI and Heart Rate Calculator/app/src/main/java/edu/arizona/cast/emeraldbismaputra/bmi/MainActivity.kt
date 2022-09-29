package edu.arizona.cast.emeraldbismaputra.bmi

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.registerForActivityResult
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import java.sql.Types.NULL

class MainActivity : AppCompatActivity() {

    private val viewModel: MainActivityViewModel by viewModels()

    private lateinit var calculateButton: Button
    private lateinit var clearButton: Button
    private lateinit var heartRateButton: Button
    private lateinit var feetInput: EditText
    private lateinit var inchesInput: EditText
    private lateinit var poundsInput: EditText
    private lateinit var message: TextView
    private lateinit var bmiNumber: TextView
    private var age: Int = 0
    private var feet: Int = 0
    private var inches: Int = 0
    private var pounds: Int = 0
    private var bmiResult: Float = 0F

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // the code below is to take the intent that is passed from HeartRateActivity
        // registerForActivityResult is used to hear from the child activity because MainActivity
        // is set as the launcher by default in the android manifest
        val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
                result: ActivityResult ->
            // check if result = RESULT_OK
            if(result.resultCode == Activity.RESULT_OK){
                val data = result.data
                data?.getIntExtra("age", 0)?.let {
                    //store the data to the age variable
                    age = it
                }
            }
        }

        calculateButton = findViewById(R.id.calculateButton)
        clearButton = findViewById(R.id.clearButton)
        heartRateButton = findViewById(R.id.heartRateButton)
        feetInput = findViewById(R.id.feetInput)
        inchesInput = findViewById(R.id.inchesInput)
        poundsInput = findViewById(R.id.poundsInput)
        message = findViewById(R.id.message)
        bmiNumber = findViewById(R.id.bmiNumber)

        calculateButton.setOnClickListener { view: View ->
            //if one or more of the edittext is empty, then display error
            if (feetInput.text.toString() == "" || inchesInput.text.toString() == "" || poundsInput.text.toString() == "") {
                displayMissingData()
            }
            else {
                feet = Integer.valueOf(feetInput.text.toString())
                inches = Integer.valueOf(inchesInput.text.toString())
                pounds = Integer.valueOf(poundsInput.text.toString())
                bmiResult = viewModel.bmiResult(feet, inches, pounds)
                displayMessage()
            }
        }

        clearButton.setOnClickListener {view: View ->
            clearSearch()
        }

        heartRateButton.setOnClickListener { view: View ->
            //if age variable has a value in it, pass the value back to the HeartRateActivity (using intent)
            val intentToHeartRateActivity = Intent(this, HeartRateActivity::class.java)
            intentToHeartRateActivity.putExtra("age", age)
            startForResult.launch(intentToHeartRateActivity)
        }

        if (viewModel.returnBmiResult() > 0) {
            displayMessage()
        }
    }

    private fun displayMessage(){
        if (viewModel.bmiResultMessage() == "Underweight") {
            message.setText(R.string.underweight)
            val aString = getString(R.string.bmi, viewModel.returnBmiResult())
            bmiNumber.text = aString
            message.setTextColor(getColor(R.color.yellow))
            message.visibility = View.VISIBLE
            bmiNumber.visibility = View.VISIBLE
        }
        else if (viewModel.bmiResultMessage() == "Normal"){
            message.setText(R.string.normal)
            val aString = getString(R.string.bmi, viewModel.returnBmiResult())
            bmiNumber.text = aString
            message.setTextColor(getColor(R.color.green))
            message.visibility = View.VISIBLE
            bmiNumber.visibility = View.VISIBLE
        }
        else if (viewModel.bmiResultMessage() == "Overweight"){
            message.setText(R.string.overweight)
            val aString = getString(R.string.bmi, viewModel.returnBmiResult())
            bmiNumber.text = aString
            message.setTextColor(getColor(R.color.yellow))
            message.visibility = View.VISIBLE
            bmiNumber.visibility = View.VISIBLE
        }
        else {
            message.setText(R.string.obese)
            val aString = getString(R.string.bmi, viewModel.returnBmiResult())
            bmiNumber.text = aString
            message.setTextColor(getColor(R.color.red))
            message.visibility = View.VISIBLE
            bmiNumber.visibility = View.VISIBLE
        }
    }

    private fun clearSearch(){
        message.visibility = View.INVISIBLE
        bmiNumber.visibility = View.INVISIBLE
        feetInput.text.clear()
        inchesInput.text.clear()
        poundsInput.text.clear()
    }

    private fun displayMissingData(){
        message.setText(R.string.missingData)
        message.setTextColor(getColor(R.color.red))
        message.visibility = View.VISIBLE
        bmiNumber.visibility = View.INVISIBLE
    }
}
