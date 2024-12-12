package com.example.baseproject

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.baseproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setTexts(context = this, binding = binding)
    }
}

fun setTexts(context: Context, binding: ActivityMainBinding) {
    binding.textAndes?.text = context.getString(R.string.andes_mountain)
    binding.textSouthAmerica.text = context.getString(R.string.south_america)
    binding.textPrice?.text = context.getString(R.string.price)
    binding.textDollarSign?.text = context.getString(R.string.dollar_sign)
    binding.text230?.text = context.getString(R.string._230)

    binding.textOverview?.text = context.getString(R.string.overview)
    binding.textDetails?.text = context.getString(R.string.details)

    binding.text8Hours?.text = context.getString(R.string._8_hours)
    binding.textTemp?.text = context.getString(R.string._16_c)
    binding.textReview?.text = context.getString(R.string._4_5)

    binding.textDescription?.text = context.getString(R.string.description)
    binding.textBookNow?.text = context.getString(R.string.book_now)
}



