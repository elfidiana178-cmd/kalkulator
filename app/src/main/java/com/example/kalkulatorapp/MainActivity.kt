package com.example.kalkulatorapp

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var tvResult: TextView
    private var operand1: Int? = null
    private var pendingOperation: String? = null
    private var userIsTypingNumber = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvResult = findViewById(R.id.tvResult)
        tvResult.text = "0"
    }

    fun onButtonClick(view: View) {
        val button = view as Button
        val buttonText = button.text.toString()

        when (buttonText) {
            "C" -> {
                operand1 = null
                pendingOperation = null
                userIsTypingNumber = false
                tvResult.text = "0"
            }
            "+", "-", "*", "/" -> {
                val value = tvResult.text.toString().toIntOrNull() ?: 0
                if (operand1 == null) {
                    operand1 = value
                } else {
                    performOperation(value)
                }
                pendingOperation = buttonText
                userIsTypingNumber = false
            }
            "=" -> {
                val value = tvResult.text.toString().toIntOrNull() ?: 0
                if (operand1 != null && pendingOperation != null) {
                    performOperation(value)
                    pendingOperation = null
                }
                userIsTypingNumber = false
            }
            else -> {
                if (!userIsTypingNumber || tvResult.text == "0") {
                    tvResult.text = buttonText
                } else {
                    tvResult.append(buttonText)
                }
                userIsTypingNumber = true
            }
        }
    }

    private fun performOperation(value: Int) {
        when (pendingOperation) {
            "+" -> operand1 = operand1!! + value
            "-" -> operand1 = operand1!! - value
            "*" -> operand1 = operand1!! * value
            "/" -> {
                operand1 = if (value != 0) operand1!! / value else {
                    tvResult.text = "Error"
                    return
                }
            }
        }
        tvResult.text = operand1.toString()
    }
}
