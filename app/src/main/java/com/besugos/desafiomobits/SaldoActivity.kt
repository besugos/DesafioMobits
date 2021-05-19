package com.besugos.desafiomobits

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.core.view.isVisible

class SaldoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saldo)

        val conta = intent.getStringExtra("CONTA")
        val saldo = intent.getStringExtra("SALDO")
        val isVip = intent.getBooleanExtra("VIP", false)

        val header = findViewById<TextView>(R.id.saldoContaHeader)
        header.text = "Conta: $conta"
        findViewById<TextView>(R.id.saldoHeaderVip).isVisible = isVip

        val txtSaldo = findViewById<TextView>(R.id.txtSaldo)

        val saldoNum = saldo!!.toDouble()

        var saldoFormatado = ""

        if (saldoNum >= 0) {
            saldoFormatado = String.format("%.2f", saldoNum)
        } else {
            saldoFormatado = "("+ String.format("%.2f", (saldoNum * (-1))) + ")"
        }


        txtSaldo.text = "R$ ${saldoFormatado}"

        findViewById<Button>(R.id.btnSaldoSair).setOnClickListener {
            val intent = Intent(this@SaldoActivity, MainActivity::class.java)
            with(intent) {
                startActivity(this)
                finish()
            }
        }

        findViewById<Button>(R.id.btnSaldoVoltar).setOnClickListener {
            val intent = Intent(this@SaldoActivity, HomeActivity::class.java)
            with(intent) {
                putExtra("CONTA", conta)
                putExtra("SALDO", saldo)
                putExtra("VIP", isVip)

                startActivity(this)
                finish()
            }
        }

    }
}