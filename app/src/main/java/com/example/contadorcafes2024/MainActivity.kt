package com.example.contadorcafes2024

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import com.example.contadorcafes2024.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var contador: Contador
    private lateinit var contadorDescendente: MyCountDownTimer
    private val PAUSA = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)

        contador = Contador(0, PAUSA)
        binding.tiempo.text = PAUSA.toString() + ":00"
        binding.botonMenos.setOnClickListener(this)
        binding.botonMas.setOnClickListener(this)
        binding.botonComenzar.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        if (view === binding.botonMenos) binding.tiempo.text = contador.disminuirTiempo()

        if (view === binding.botonMas) binding.tiempo.text = contador.aumentarTiempo()

        if (view === binding.botonComenzar) {
            contadorDescendente = MyCountDownTimer((contador.obtenerTiempo() * 60 * 1000).toLong(), 1000)
            contadorDescendente.start()
            binding.botonMenos.isEnabled = false
            binding.botonMas.isEnabled = false
            binding.botonComenzar.isEnabled = false
        }
    }

    inner class MyCountDownTimer(millisInFuture: Long, countDownInterval: Long) : CountDownTimer(millisInFuture, countDownInterval) {

        override fun onTick(l: Long) {
            val minutos: Long
            val segundos: Long
            minutos = l / 1000 / 60
            segundos = l / 1000 % 60
            binding.tiempo.text = "$minutos:$segundos"
        }

        override fun onFinish() {
            Toast.makeText(this@MainActivity, "Pausa terminada", Toast.LENGTH_LONG).show()
            binding.cuenta.text = contador.aumentarCafes()
            binding.tiempo.text = contador.obtenerTiempo().toString() + ":00"
            binding.botonMenos.isEnabled = true
            binding.botonMas.isEnabled = true
            binding.botonComenzar.isEnabled = true
        }
    }
}