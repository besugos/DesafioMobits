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

class SaqueActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saque)
        val conta = intent.getStringExtra("CONTA")
        val saldo = intent.getStringExtra("SALDO")
        val isVip = intent.getBooleanExtra("VIP", false)
        val extrato = intent.getStringExtra("EXTRATO")

        val header = findViewById<TextView>(R.id.saqueContaHeader)
        header.text = "Conta: $conta"
        findViewById<TextView>(R.id.saqueHeaderVip).isVisible = isVip

        findViewById<Button>(R.id.btnSaque).setOnClickListener {
            var saldoNum = saldo!!.toDouble()
            val saque = findViewById<TextInputEditText>(R.id.etSaque).text.toString()
            val saqueNum = saque!!.toDouble()

            if (isVip || saldoNum >= saqueNum) {
                saldoNum = saldoNum - saqueNum

                val keySaldo = conta + "SALDO"

                val sharedPreferences = getSharedPreferences("banco_mobits_usuarios", Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.apply {
                    putString(keySaldo, saldoNum.toString())
                }.apply()

                Toast.makeText(this, "Saque efetudo com sucesso", Toast.LENGTH_SHORT).show()

                val intent = Intent(this@SaqueActivity, HomeActivity::class.java)
                with(intent) {
                    putExtra("CONTA", conta)
                    putExtra("SALDO", saldoNum.toString())
                    putExtra("VIP", isVip)
                    putExtra("EXTRATO", extrato)

                    startActivity(this)
                    finish()
                }

            } else {
                Toast.makeText(this, "Saldo insuficiente", Toast.LENGTH_SHORT).show()
            }
        }

        findViewById<Button>(R.id.btnSaqueSair).setOnClickListener {
            val intent = Intent(this@SaqueActivity, MainActivity::class.java)
            with(intent) {
                startActivity(this)
                finish()
            }
        }

        findViewById<Button>(R.id.btnSaqueVoltar).setOnClickListener {
            val intent = Intent(this@SaqueActivity, HomeActivity::class.java)
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
