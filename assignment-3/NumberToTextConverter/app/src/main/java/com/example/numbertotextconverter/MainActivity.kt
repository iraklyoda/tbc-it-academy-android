package com.example.numbertotextconverter

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val myInput: EditText = findViewById<EditText>(R.id.input)
        val myButton: Button = findViewById(R.id.button)
        val myText: TextView = findViewById(R.id.text)

        myButton.setOnClickListener {
            myText.text = numberToWords(myInput.text.toString().toIntOrNull())
        }
    }
}

// ვიცი კოდი ქაოსურად და არა სუფთად წერია, თუმცა იმპლანტების ოპერაციის გამო მაქსიმალური
// ენერგია ვერ ჩავდე. შემდეგიდან დავბრუნდები სრული შემართებით 😄💪

fun numberToWords(input: Int?): String {
    if (input == null) {
        return "please enter number"
    }

    val int = input.toInt()
    if(int < 1 || int > 1000) {
        return "Please enter number from 1 to 1000"
    }

    var result = ""

    val underTwenty: List<String> = listOf(
        "ერთი",
        "ორი",
        "სამი",
        "ოთხი",
        "ხუთი",
        "ექვსი",
        "შვიდი",
        "რვა",
        "ცხრა",
        "ათი",
        "თერთმეტი",
        "თორმეტი",
        "ცამეტი",
        "თოთხმეტი",
        "თხუთმეტი",
        "თექვსმეტი",
        "ჩვიდმეტი",
        "თვრამეტი",
        "ცხრამეტი"
    )
    val tenths: Map<Int, String> = mapOf(2 to "ოცი", 3  to "ოცი", 4 to "ორმოცი", 5 to "ორმოცი",
        6 to "სამოცი", 7 to "სამოცი", 8 to "ოთხმოცი", 9 to "ოთხმოცი")


    if (int < 20) {
        result = underTwenty[int - 1]
        return result
    }

    if (int in 1..99) {
        val lastDigitValue: String = if (int/10 % 2 == 0) {
            underTwenty[int%10 - 1]
        } else {
            underTwenty[int%10-1 + 10]
        }
        result = "${tenths[int/10]?.dropLast(1)}და$lastDigitValue"
    }

    // x$x
    val tenthsValue: Int = if(int > 199) {
       int - (int / 100 * 100)
    } else {
        int - 100
    }

    // xx$ -> String
    val lastDigitValue: String = if (tenthsValue/10 % 2 == 0) {
        underTwenty[tenthsValue%10 - 1]
    } else {
        underTwenty[tenthsValue%10-1 + 10]
    }


    if (int in 100 .. 199) {
        if (int == 100) {
            return "ასი"
        }

        result = "${tenths[tenthsValue/10]?.dropLast(1)}და$lastDigitValue"

        if(tenthsValue < 20) {
            result = underTwenty[tenthsValue - 1]
        }

        result = addPrefix("ას", result)
    }

    if (int in 199 .. 1000) {

        if (int % 100 == 0) {
            result = underTwenty[int/100 - 1].dropLast(1) + "ასი"
            return result
        }

        result = "${tenths[tenthsValue/10]?.dropLast(1)}და$lastDigitValue"

        if(tenthsValue < 20) {
            result = underTwenty[tenthsValue - 1]
        }

        result = addPrefix("${underTwenty[int/100 - 1].dropLast(1)}ას", result)
    }
    return result
}

fun addPrefix(prefix: String, text: String): String {
    return "$prefix$text"
}



