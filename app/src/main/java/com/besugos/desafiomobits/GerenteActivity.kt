package com.besugos.desafiomobits

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import com.google.android.material.textfield.TextInputEditText
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class GerenteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gerente)

        val conta = intent.getStringExtra("CONTA")
        val saldo = intent.getStringExtra("SALDO")
        val isVip = intent.getBooleanExtra("VIP", false)
        val extrato = intent.getStringExtra("EXTRATO")

        val header = findViewById<TextView>(R.id.gerenteContaHeader)
        header.text = "Conta: $conta"

        findViewById<Button>(R.id.btnGerente).setOnClickListener {
            var saldoNum = saldo!!.toDouble()

            val current = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("dd/MM HH:mm")
            val formatted = current.format(formatter)

            saldoNum = saldoNum - 50

            val keySaldo = conta + "SALDO"
            val keyExtrato = conta + "EXTRATO"

            val sharedPreferences = getSharedPreferences("banco_mobits_usuarios", Context.MODE_PRIVATE)

            var extratoConta = sharedPreferences.getString(keyExtrato, "")

            extratoConta = extratoConta + formatted + " | VISIT | -50.00#"

            val editor = sharedPreferences.edit()
            editor.apply {
                putString(keySaldo, saldoNum.toString())
                putString(keyExtrato, extratoConta)
            }.apply()

            Toast.makeText(this, "Visita solicitada", Toast.LENGTH_SHORT).show()

            val intent = Intent(this@GerenteActivity, HomeActivity::class.java)
            with(intent) {
                putExtra("CONTA", conta)
                putExtra("SALDO", saldoNum.toString())
                putExtra("VIP", isVip)
                putExtra("EXTRATO", extrato)

                startActivity(this)
                finish()
            }
        }

        findViewById<Button>(R.id.btnGerenteSair).setOnClickListener {
            val intent = Intent(this@GerenteActivity, MainActivity::class.java)
            with(intent) {
                startActivity(this)
                finish()
            }
        }

        findViewById<Button>(R.id.btnGerenteVoltar).setOnClickListener {
            val intent = Intent(this@GerenteActivity, HomeActivity::class.java)
            with(intent) {
                putExtra("CONTA", conta)
                putExtra("SALDO", saldo)
                putExtra("VIP", isVip)
                putExtra("EXTRATO", extrato)

                startActivity(this)
                finish()
            }
        }

    }
}
