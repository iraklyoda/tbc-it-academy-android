package messanger.example.baseproject

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import messanger.example.baseproject.messenger.MessengerFragment
import com.example.baseproject.R
import com.example.baseproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
            .replace(R.id.main, MessengerFragment())
            .commit()
    }
}