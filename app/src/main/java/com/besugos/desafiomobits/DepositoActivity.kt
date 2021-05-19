package com.besugos.desafiomobits

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import com.google.android.material.textfield.TextInputEditText

class DepositoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deposito)

        val conta = intent.getStringExtra("CONTA")
        val saldo = intent.getStringExtra("SALDO")
        val isVip = intent.getBooleanExtra("VIP", false)
        val extrato = intent.getStringExtra("EXTRATO")

        val header = findViewById<TextView>(R.id.depositoContaHeader)
        header.text = "Conta: $conta"
        findViewById<TextView>(R.id.depositoHeaderVip).isVisible = isVip

        findViewById<Button>(R.id.btnDeposito).setOnClickListener {
            var saldoNum = saldo!!.toDouble()
            val deposito = findViewById<TextInputEditText>(R.id.etDeposito).text.toString()
            val depositoNum = deposito!!.toDouble()

            saldoNum = saldoNum + depositoNum

            val keySaldo = conta + "SALDO"

            val sharedPreferences = getSharedPreferences("banco_mobits_usuarios", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.apply {
                putString(keySaldo, saldoNum.toString())
            }.apply()

            Toast.makeText(this, "Depósito efetudo com sucesso", Toast.LENGTH_SHORT).show()

            val intent = Intent(this@DepositoActivity, HomeActivity::class.java)
            with(intent) {
                putExtra("CONTA", conta)
                putExtra("SALDO", saldoNum.toString())
                putExtra("VIP", isVip)
                putExtra("EXTRATO", extrato)

                startActivity(this)
                finish()
            }
        }

        findViewById<Button>(R.id.btnDepositoSair).setOnClickListener {
            val intent = Intent(this@DepositoActivity, MainActivity::class.java)
            with(intent) {
                startActivity(this)
                finish()
            }
        }

        findViewById<Button>(R.id.btnDepositoVoltar).setOnClickListener {
            val intent = Intent(this@DepositoActivity, HomeActivity::class.java)
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
