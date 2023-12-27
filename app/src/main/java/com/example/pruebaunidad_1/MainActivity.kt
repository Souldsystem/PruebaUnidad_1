package com.example.pruebaunidad_1

import android.os.Bundle
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.pruebaunidad_1.Modelo.CuentaMesa
import com.example.pruebaunidad_1.Modelo.ItemMenu
import com.example.pruebaunidad_1.Modelo.ItemMesa

class MainActivity : AppCompatActivity() {
    private lateinit var cantPastelEditText: EditText
    private lateinit var cantCazuelaEditText: EditText
    private lateinit var switchPropina: Switch
    private lateinit var comidaValTextView: TextView
    private lateinit var propinaValTextView: TextView
    private lateinit var totalValTextView: TextView

    private val pastelPrecio = 36000
    private val cazuelaPrecio = 10000
    private val cuentaMesa = CuentaMesa(1) // Crear una cuenta para la mesa 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializar vistas
        cantPastelEditText = findViewById(R.id.cantpastel)
        cantCazuelaEditText = findViewById(R.id.cantcazuela)
        switchPropina = findViewById(R.id.switchPropina)
        comidaValTextView = findViewById(R.id.ComidaVal)
        propinaValTextView = findViewById(R.id.propinaval)
        totalValTextView = findViewById(R.id.totalval)

        // Manejar cambios en la cantidad de Pastel de Choclo
        cantPastelEditText.addTextChangedListener(object : SimpleTextWatcher() {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val cantidad = s.toString().toIntOrNull() ?: 0
                cuentaMesa.agregarItem(ItemMenu("Pastel de Choclo", pastelPrecio), cantidad)
                actualizarTotales()
            }
        })

        // Manejar cambios en la cantidad de Cazuela
        cantCazuelaEditText.addTextChangedListener(object : SimpleTextWatcher() {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val cantidad = s.toString().toIntOrNull() ?: 0
                cuentaMesa.agregarItem(ItemMenu("Cazuela", cazuelaPrecio), cantidad)
                actualizarTotales()
            }
        })

        // Manejar cambios en el switch de propina
        switchPropina.setOnCheckedChangeListener { _, isChecked ->
            cuentaMesa.aceptarPropina = isChecked
            actualizarTotales()
        }
    }

    private fun actualizarTotales() {
        val totalSinPropina = cuentaMesa.calcularTotalSinPropina()
        val propina = cuentaMesa.calcularPropina()
        val totalConPropina = cuentaMesa.calcularTotalConPropina()

        val formatoMoneda = java.text.NumberFormat.getCurrencyInstance(java.util.Locale("es", "CL"))
        formatoMoneda.maximumFractionDigits = 0 // Mostrar sin decimales (entero)

        comidaValTextView.text = formatoMoneda.format(totalSinPropina.toLong())
        propinaValTextView.text = formatoMoneda.format(propina.toLong())
        totalValTextView.text = formatoMoneda.format(totalConPropina.toLong())
    }
}

// Clase auxiliar para evitar implementar todos los métodos de TextWatcher
open class SimpleTextWatcher : android.text.TextWatcher {
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    override fun afterTextChanged(s: android.text.Editable?) {}
}
