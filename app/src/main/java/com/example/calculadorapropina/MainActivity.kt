package com.example.calculadorapropina

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    // Referencias a los elementos de la UI
    private lateinit var etPagoTotal: EditText
    private lateinit var spinnerPorcentaje: Spinner
    private lateinit var btnCalcular: Button
    private lateinit var tvResultadoPropina: TextView
    private lateinit var tvTotalAPagar: TextView
    private lateinit var tvCantidadIngresada: TextView  // Nuevo TextView para mostrar la cantidad ingresada

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializar los elementos de la UI
        etPagoTotal = findViewById(R.id.etPagoTotal)
        spinnerPorcentaje = findViewById(R.id.spinnerPorcentaje)
        btnCalcular = findViewById(R.id.btnCalcular)
        tvResultadoPropina = findViewById(R.id.tvResultadoPropina)
        tvTotalAPagar = findViewById(R.id.tvTotal_a_Pagar)
        tvCantidadIngresada = findViewById(R.id.tvCantidadIngresada)  // Inicializamos el nuevo TextView

        // Configurar el Spinner para seleccionar el porcentaje de la propina
        val tipOptions = arrayOf("5%", "10%", "15%", "20%", "25%", "30%", "35%", "40%", "45%", "50%",
            "55%", "60%", "65%", "70%", "75%", "80%", "85%", "90%", "95%", "100%")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, tipOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerPorcentaje.adapter = adapter

        // Configurar el botón para realizar el cálculo
        btnCalcular.setOnClickListener {
            calcularPropina()
        }

        // Agregar un TextWatcher para actualizar el TextView con la cantidad ingresada en el EditText
        etPagoTotal.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val cantidad = s.toString()
                if (cantidad.isNotEmpty()) {
                    tvCantidadIngresada.text = "Cantidad ingresada: $$cantidad"
                } else {
                    tvCantidadIngresada.text = "Cantidad ingresada: $0.00"
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    // Función para calcular la propina y el total a pagar
    private fun calcularPropina() {
        // Obtener el valor del total de la cuenta
        val totalCuenta = etPagoTotal.text.toString().toDoubleOrNull()

        if (totalCuenta != null) {
            // Obtener el porcentaje de la propina seleccionado
            val porcentajeSeleccionado = spinnerPorcentaje.selectedItem.toString().replace("%", "").toInt()

            // Calcular la propina
            val propina = totalCuenta * (porcentajeSeleccionado / 100.0)

            // Calcular el total a pagar
            val totalPagar = totalCuenta + propina

            // Mostrar los resultados en los TextView
            tvResultadoPropina.text = getString(R.string.tip) + ": $${"%.2f".format(propina)}"
            tvTotalAPagar.text = getString(R.string.total_to_pay) + ": $${"%.2f".format(totalPagar)}"
        } else {
            // Mostrar un mensaje de error si no se ingresó un valor válido para el total
            tvResultadoPropina.text = getString(R.string.error_message)
            tvTotalAPagar.text = ""
        }
    }
}
