package com.example.baseproject

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.baseproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setText()
    }

    private fun setText() {
        binding.textAndes?.text = getString(R.string.andes_mountain)
        binding.textSouthAmerica.text = getString(R.string.south_america)
        binding.textPrice?.text = getString(R.string.price)
        binding.textDollarSign?.text = getString(R.string.dollar_sign)
        binding.text230?.text = getString(R.string._230)

        binding.textOverview?.text = getString(R.string.overview)
        binding.textDetails?.text = getString(R.string.details)

        binding.text8Hours?.text = getString(R.string._8_hours)
        binding.textTemp?.text = getString(R.string._16_c)
        binding.textReview?.text = getString(R.string._4_5)

        binding.textDescription?.text = getString(R.string.description)
        binding.textBookNow?.text = getString(R.string.book_now)
    }
}



