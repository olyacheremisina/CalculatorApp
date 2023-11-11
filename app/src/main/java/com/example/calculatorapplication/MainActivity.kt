package com.example.calculatorapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculatorapplication.ui.theme.CalculatorApplicationTheme
import com.example.calculatorapplication.ui.theme.KeyBoardBackgroundColor
import com.example.calculatorapplication.ui.theme.KeyboardButton
import com.example.calculatorapplication.ui.theme.MainColor
import com.example.calculatorapplication.ui.theme.OutputColor

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var outputState = remember {
                mutableStateOf("0")
            }

            Box(modifier = Modifier.background(MainColor)) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp)
                        .clip(RoundedCornerShape(5.dp))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxSize(0.5f)
                            .background(OutputColor),
                        verticalAlignment = Alignment.Bottom,
                        horizontalArrangement = Arrangement.End
                    ) {
                        Text(
                            modifier = Modifier.padding(20.dp, 5.dp),
                            text = outputState.value, fontSize = 45.sp
                        )
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(KeyBoardBackgroundColor)
                            .padding(10.dp),
                        verticalArrangement = Arrangement.SpaceBetween,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween) {
                            CustomButton(value = "7", outputState)
                            CustomButton(value = "8", outputState)
                            CustomButton(value = "9", outputState)
                            CustomButton(value = "x", outputState)
                        }
                        Row(modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween) {
                            CustomButton(value = "4", outputState)
                            CustomButton(value = "5", outputState)
                            CustomButton(value = "6", outputState)
                            CustomButton(value = "/", outputState)
                        }
                        Row(modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween) {
                            CustomButton(value = "1", outputState)
                            CustomButton(value = "2", outputState)
                            CustomButton(value = "3", outputState)
                            CustomButton(value = "+", outputState)
                        }
                        Row(modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween) {
                            CustomButton(value = "0", outputState)
                            CustomButton(value = ".", outputState)
                            CustomButton(value = "C", outputState)
                            CustomButton(value = "-", outputState)
                        }
                        Row(modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.End) {
                            CustomButton(value = "=", outputState)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CustomButton(value: String, outputState: MutableState<String>){
    Button(onClick = {
        when(value){
            "C" -> outputState.value = "0"
            "=" -> DoCalculate(outputState)
            else -> {
                if (outputState.value == "0") outputState.value = ""
                outputState.value += value
            }
        }
    },
        colors = ButtonDefaults.buttonColors(
            containerColor = KeyboardButton
        )) {
        Text(text = value,
            fontSize = 30.sp)
    }
}

fun DoCalculate(outputState: MutableState<String>) {
    val expression = outputState.value
    try {
        val result = Calculate(expression).toString()
        outputState.value = result.toString()
    } catch (e: Exception) {
        outputState.value = e.toString()
    }
}

fun Calculate(expression: String): Double {
    val parts = expression.split(Regex("[-+x/]"))
    val operand1 = parts[0].toDouble()
    val operator = expression.substring(parts[0].length, parts[0].length + 1)
    val operand2 = parts[1].toDouble()
    return when (operator) {
        "+" -> operand1 + operand2
        "-" -> operand1 - operand2
        "x" -> operand1 * operand2
        "/" -> operand1 / operand2
        else -> throw IllegalArgumentException("Такого оператора не существует!")
    }
}