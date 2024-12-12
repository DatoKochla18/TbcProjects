package com.example.tbcexercises

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tbcexercises.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setUp()
    }

    private fun setUp() {
        binding.txtDetailLocation.text = getString(R.string.andes_mountain)
        binding.txtPrice.text = getString(R.string.price)
        binding.txtHours.text = getString(R.string._8_hours)
        binding.txtDescribe.text = getString(R.string.basic_text)
        binding.txtRating.text = getString(R.string.rating)
        binding.txtTemperature.text = getString(R.string.temperature)
        binding.txtDetails.text = getString(R.string.details)
        binding.txtOverview.text = getString(R.string.overview)
        binding.txtBroadLocation.text = getString(R.string.south_america)
        binding.btnBookNow.text = getString(R.string.book_now)
        /*
        i did not want to nest even more so i created price_value which has only number and its white
        and txtWithDollarPriceValue -this have number as well but first char is $ and it's black
        so i'm using framelayot first is txtWithDollarPriceValue and then price_value because of this it overrides
        so i have white number and black dollar sign!!!
        * */

        binding.txtPriceValue.text = getString(R.string.price_value)
        binding.txtWithDollarPriceValue.text = getString(R.string.with_dollar)
    }

}