package com.example.baseproject

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.baseproject.databinding.ActivityMainBinding

private lateinit var binding: ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val inputAnagram: EditText = binding.inputAnagram
        val btnSave: Button = binding.btnSave
        val btnOutput: Button = binding.btnOutput
        val btnClear: Button = binding.btnClear
        val textAnagrams: TextView = binding.txtAnagram

        val btnConnect: Button = binding.btnConnect
        val textConnect: TextView = binding.txtConnect

        val anagramsList: MutableList<String> = mutableListOf()

        fun checkAnagram(text1: String, text2: String): Boolean {
            val textOne: String = text1.lowercase()
            val textSecond: String = text2.lowercase()

            if (text1.length != text2.length) {
                return false
            }

            if (textOne.toCharArray().sorted() == textSecond.toCharArray().sorted()) {
                return true
            }
            return false

        }

        btnSave.setOnClickListener() {
            val anagramValue = inputAnagram.text.toString().toString()
            if (anagramValue.isEmpty()) {
                inputAnagram.error = "Input shouldn't be empty"
            } else if (anagramsList.contains(anagramValue)) {
                inputAnagram.error = "Anagram already in the list"
            } else {
                anagramsList.add(anagramValue)
                inputAnagram.text.clear()
            }
        }

        btnOutput.setOnClickListener() {
            // Copy original anagrams list
            val currentAnagrams = anagramsList.toMutableList()

            val outputAnagrams: MutableList<List<String>> = mutableListOf<List<String>>()

            while (currentAnagrams.size > 0) {
                // Create list of grouped anagram lists
                var currentAnagramList: MutableList<String> = mutableListOf()
                var currentAnagram: String = currentAnagrams[0]

                for (index in currentAnagrams.indices) {
                    if (checkAnagram(text1 = currentAnagram, text2 = currentAnagrams[index])) {
                        currentAnagramList.add(currentAnagrams[index])
                    }
                }
                // Remove already checked items in copied list
                currentAnagrams.removeIf { checkAnagram(text1 = currentAnagram, text2 = it) }
                outputAnagrams.add(currentAnagramList)
            }

            if (outputAnagrams.size > 0) {
                var outputText: String = ""
                for (anagramList in outputAnagrams) {
                    outputText += "["
                    outputText += anagramList.joinToString(", ")
                    outputText += "]\n"
                }
                textAnagrams.text = outputText
            }
        }

        btnClear.setOnClickListener {
            anagramsList.clear()
            inputAnagram.text.clear()
            textAnagrams.text = ""
            textConnect.text = ""
        }

        btnConnect.setOnClickListener {
            textConnect.text = getString(R.string.never_had_the_makings_of_a_varsity_hacker)
        }
    }
}