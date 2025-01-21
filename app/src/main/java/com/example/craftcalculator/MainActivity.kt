package com.example.craftcalculator

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var resultTV: TextView
    private lateinit var inputET: EditText
    private lateinit var resultBTN:Button
    private lateinit var delBTN: Button
    private lateinit var clearBTN: Button
    private lateinit var toolbarTB: Toolbar
    var numberOne = 0.0
    var numberTwo = 0.0
    var currentOperation = "" //Запоминаем операцию
    var currentPosition = 0 //Запоминаем позицию в строке

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        resultBTN = findViewById(R.id.resultBTN)
        resultTV = findViewById(R.id.resultTV)
        inputET = findViewById(R.id.inputET)
        delBTN = findViewById(R.id.delBTN)
        clearBTN = findViewById(R.id.clearBTN)
        toolbarTB =findViewById(R.id.toolbarTB)
        setSupportActionBar(toolbarTB)
        delBTN.setOnClickListener {
            if(inputET.text.isNotEmpty()) {
                val string = inputET.text.toString()
                if (string.length>currentPosition) {
                    inputET.setText(string.substring(0, string.length - 1))
                }
            }
        }

        resultBTN.setOnClickListener {
            if(currentOperation.isEmpty()) return@setOnClickListener
            val numberString = inputET.text.toString()
            if(numberString.length <= currentPosition) return@setOnClickListener
            try{
               numberTwo = numberString.substring(currentPosition,numberString.length).toDouble()
            } catch (e:Exception){
                return@setOnClickListener
            }
            inputET.append("=")
            resultTV.text = operation().toString()
            currentOperation = ""
            currentPosition = 0
            numberOne = 0.0
            numberTwo = 0.0
        }
        clearBTN.setOnClickListener {
            currentOperation = ""
            currentPosition = 0
            numberOne = 0.0
            numberTwo = 0.0
            resultTV.text = ""
            inputET.setText("")
        }
        findViewById<Button>(R.id.n0).setOnClickListener { onNumberClick("0") }
        findViewById<Button>(R.id.n1).setOnClickListener { onNumberClick("1") }
        findViewById<Button>(R.id.n2).setOnClickListener { onNumberClick("2") }
        findViewById<Button>(R.id.n3).setOnClickListener { onNumberClick("3") }
        findViewById<Button>(R.id.n4).setOnClickListener { onNumberClick("4") }
        findViewById<Button>(R.id.n5).setOnClickListener { onNumberClick("5") }
        findViewById<Button>(R.id.n6).setOnClickListener { onNumberClick("6") }
        findViewById<Button>(R.id.n7).setOnClickListener { onNumberClick("7") }
        findViewById<Button>(R.id.n8).setOnClickListener { onNumberClick("8") }
        findViewById<Button>(R.id.n9).setOnClickListener { onNumberClick("9") }
        findViewById<Button>(R.id.pointBTN).setOnClickListener { onNumberClick(".") }


        findViewById<Button>(R.id.difBTN).setOnClickListener { onOperationClick("-") }
        findViewById<Button>(R.id.addBTN).setOnClickListener { onOperationClick("+") }
        findViewById<Button>(R.id.multBTN).setOnClickListener { onOperationClick("X") }
        findViewById<Button>(R.id.divBTN).setOnClickListener { onOperationClick("/") }

        }

    fun onOperationClick(operation: String){
        if (currentOperation.isNotEmpty()) return
        try{
            numberOne = inputET.text.toString().toDouble()
        } catch (e: Exception){
            return
        }
        currentOperation = operation
        inputET.append(operation)
        currentPosition = inputET.text.toString().length

    }

    fun onNumberClick(number: String) {

        inputET.append( number)

    }

    fun operation():Double{

        when(currentOperation){
            "+" -> return numberOne+numberTwo
            "-" -> return numberOne-numberTwo
            "X" -> return numberOne*numberTwo
            "/" -> return if(numberTwo != 0.0) numberOne/numberTwo else 0.0
        }
        return 0.0
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.exitMN ->finish()
            else -> return super.onOptionsItemSelected(item)
        }
        return super.onOptionsItemSelected(item)
    }
}
