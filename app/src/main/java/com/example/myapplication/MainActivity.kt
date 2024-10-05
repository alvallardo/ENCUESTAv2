package com.example.myapplication

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var selectedOption: String
    private var selectedRadio: String = "" // Inicializa como cadena vacía

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val mySpinner: Spinner = findViewById(R.id.mySpinner)

        // Crea un ArrayAdapter usando un array de cadenas
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.spinner_items,
            android.R.layout.simple_spinner_item
        )

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        mySpinner.adapter = adapter

        mySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                selectedOption = parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                selectedOption = "" // Resetear si no hay selección
            }
        }

        // Inicializa el RadioGroup
        val radioGroup: RadioGroup = findViewById(R.id.radioGroup)

        // Configura el OnCheckedChangeListener para el RadioGroup
        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId != -1) { // Verifica que se haya seleccionado un botón
                val radioButton: RadioButton = findViewById(checkedId)
                selectedRadio = radioButton.text.toString()
            } else {
                selectedRadio = "" // Si no se selecciona nada, asigna cadena vacía
            }
        }

        // Inicializa el botón de envío
        val submitButton: Button = findViewById(R.id.submitButton)
        submitButton.setOnClickListener {
            handleSubmit()
        }
    }

    private fun handleSubmit() {
        // Recoge el EditText
        val editText: EditText = findViewById(R.id.editTextText)
        val userInput = editText.text.toString()

        // Validación de campos
        if (userInput.isEmpty()) {
            Toast.makeText(this, "Por favor, ingresa tu nombre.", Toast.LENGTH_SHORT).show()
            return
        }

        if (selectedOption.isEmpty()) {
            Toast.makeText(this, "Por favor, selecciona un trago.", Toast.LENGTH_SHORT).show()
            return
        }

        if (selectedRadio.isEmpty()) {
            Toast.makeText(this, "Por favor, selecciona una frecuencia de consumo.", Toast.LENGTH_SHORT).show()
            return
        }

        // Crea un mensaje con las opciones seleccionadas
        val message = "Nombre: $userInput\n" +
                "Trago seleccionado: $selectedOption\n" +
                "Frecuencia de consumo: $selectedRadio"

        // Muestra el mensaje en un Toast
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()

        // Si deseas mostrar el mensaje en un TextView en lugar de un Toast, puedes hacerlo así:
        // val textViewSummary: TextView = findViewById(R.id.textViewSummary)
        // textViewSummary.text = message
    }
}
