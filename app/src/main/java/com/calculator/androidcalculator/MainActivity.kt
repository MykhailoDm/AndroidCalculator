package com.calculator.androidcalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {

    private var expressionTv: TextView? = null
    var lastNumberic: Boolean = false
    var lastDot: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        expressionTv = findViewById(R.id.expression)
    }

    fun onDigit(view: View) {
        expressionTv?.append((view as Button).text)
        lastNumberic = true
        lastDot = false
    }

    fun onClear(view: View) {
        expressionTv?.text = ""
    }

    fun onDecimalPoint(view: View) {
        if (lastNumberic && !lastDot) {
            expressionTv?.append(".")
            lastNumberic = false
            lastDot = true
        }
    }

    fun onOperator(view: View) {
        expressionTv?.text?.let {
            if (lastNumberic && !isOperatorAdded(it.toString())) {
                expressionTv?.append((view as Button).text)
                lastNumberic = false
                lastDot = false
            }
        }
    }

    fun onEqual(view: View) {
        if (lastNumberic) {
            var expressionValue = expressionTv?.text.toString()
            var prefix = ""
            try {
                if (expressionValue.startsWith("-")) {
                    prefix = "-"
                    expressionValue = expressionValue.substring(1)
                }

                val splitValue = expressionValue.split("-")
                val one = splitValue[0]
                val two = splitValue[1]

                expressionTv?.text = (one.toDouble() - two.toDouble()).toString()
            } catch (e: ArithmeticException) {
                e.printStackTrace()

            }
        }
    }

    private fun isOperatorAdded(value: String): Boolean {
        return if (value.startsWith("-")) {
            false
        } else {
            value.contains("/") || value.contains("*")
                    || value.contains("+") || value.contains("-")
        }
    }
}