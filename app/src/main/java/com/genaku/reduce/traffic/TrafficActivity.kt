package com.genaku.reduce.traffic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.genaku.reduce.R
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class TrafficActivity : AppCompatActivity() {

    private val viewModel: TrafficViewModel by viewModels()

    private var cars = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_traffic)

        findViewById<Button>(R.id.button2).setOnClickListener {
            viewModel.addCar()
        }
        val street1 = findViewById<TextView>(R.id.tvStreet1)
        val street2 = findViewById<TextView>(R.id.tvStreet2)
        val street3 = findViewById<TextView>(R.id.tvStreet3)
        val tl1 = findViewById<TextView>(R.id.tvL1)
        val tl2 = findViewById<TextView>(R.id.tvL2)
        val tl3 = findViewById<TextView>(R.id.tvL3)
        lifecycleScope.launch {
            viewModel.street1State.collect {
                street1.text = "Street 1: $it cars"
                sumCars()
            }
        }
        lifecycleScope.launch {
            viewModel.street2State.collect {
                street2.text = "Street 2: $it cars"
                sumCars()
            }
        }
        lifecycleScope.launch {
            viewModel.street3State.collect {
                street3.text = "Street 3: $it cars"
                sumCars()
            }
        }
        lifecycleScope.launch {
            viewModel.tl1State.collect {
                tl1.text = "Traffic light 1 is $it"
            }
        }
        lifecycleScope.launch {
            viewModel.tl2State.collect {
                tl2.text = "Traffic light 2 is $it"
            }
        }
        lifecycleScope.launch {
            viewModel.tl3State.collect {
                tl3.text = "Traffic light 3 is $it"
            }
        }
    }

    private fun sumCars() {
        cars = viewModel.street1State.value.value + viewModel.street2State.value.value + viewModel.street3State.value.value
        findViewById<TextView>(R.id.tvCars).text = cars.toString()
    }
}