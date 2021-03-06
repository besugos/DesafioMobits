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

class TransferenciaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transferencia)
        val conta = intent.getStringExtra("CONTA")
        val saldo = intent.getStringExtra("SALDO")
        val isVip = intent.getBooleanExtra("VIP", false)
        val extrato = intent.getStringExtra("EXTRATO")

        val header = findViewById<TextView>(R.id.transferenciaContaHeader)
        header.text = "Conta: $conta"
        findViewById<TextView>(R.id.transferenciaHeaderVip).isVisible = isVip

        findViewById<Button>(R.id.btnTransferencia).setOnClickListener {
            var saldoNum = saldo!!.toDouble()
            val transferencia = findViewById<TextInputEditText>(R.id.etTransferencia).text.toString()
            val transferenciaNum = transferencia!!.toDouble()
            val contaDestino = findViewById<TextInputEditText>(R.id.etContaTransferencia).text.toString()
            val keyContaDestino = contaDestino + "SALDO"
            val keyContaOrigem = conta + "SALDO"
            val keyExtratoOrigem = conta + "EXTRATO"
            val keyExtratoDestino = contaDestino + "EXTRATO"

            val sharedPreferences = getSharedPreferences("banco_mobits_usuarios", Context.MODE_PRIVATE)

            if (sharedPreferences.contains(keyContaDestino)) {
                var saldoDestino = sharedPreferences.getString(keyContaDestino, "0.00")!!.toDouble()
                var extratoDestino = sharedPreferences.getString(keyExtratoDestino, "")
                var extratoOrigem = sharedPreferences.getString(keyExtratoOrigem, "")
                val current = LocalDateTime.now()
                val formatter = DateTimeFormatter.ofPattern("dd/MM HH:mm")
                val formatted = current.format(formatter)

                if (isVip) {
                    saldoNum = saldoNum - transferenciaNum - (transferenciaNum * 0.008)
                    saldoDestino = saldoDestino + transferenciaNum

                    extratoOrigem = extratoOrigem + formatted + " | TRANS | -" + (String.format("%.2f", transferenciaNum)) + "#"
                    extratoOrigem = extratoOrigem + formatted + " | TARIF | -" + (String.format("%.2f", (0.08 * transferenciaNum))) + "#"
                    extratoDestino = extratoDestino + formatted + " | TRANS | +" + (String.format("%.2f", transferenciaNum)) + "#"

                    val editor = sharedPreferences.edit()
                    editor.apply {
                        putString(keyContaOrigem, saldoNum.toString())
                        putString(keyContaDestino, saldoDestino.toString())
                        putString(keyExtratoDestino, extratoDestino)
                        putString(keyExtratoOrigem, extratoOrigem)
                    }.apply()
                    Toast.makeText(this, "Transfer??ncia realizada com sucesso", Toast.LENGTH_SHORT).show()
                } else {
                    if (saldoNum >= (transferenciaNum + 8) && transferenciaNum <= 1000) {
                        saldoNum = saldoNum - (transferenciaNum + 8)
                        saldoDestino = saldoDestino + transferenciaNum

                        extratoOrigem = extratoOrigem + formatted + " | TRANS | -" + (String.format("%.2f", transferenciaNum)) + "#"
                        extratoOrigem = extratoOrigem + formatted + " | TARIF | -8.00#"
                        extratoDestino = extratoDestino + formatted + " | TRANS | +" + (String.format("%.2f", transferenciaNum)) + "#"

                        val editor = sharedPreferences.edit()
                        editor.apply {
                            putString(keyContaOrigem, saldoNum.toString())
                            putString(keyContaDestino, saldoDestino.toString())
                            putString(keyExtratoDestino, extratoDestino)
                            putString(keyExtratoOrigem, extratoOrigem)
                        }.apply()
                        Toast.makeText(this, "Transfer??ncia realizada com sucesso", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Valor n??o autorizado", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Conta destino inexistente", Toast.LENGTH_SHORT).show()
            }

            val intent = Intent(this@TransferenciaActivity, HomeActivity::class.java)
            with(intent) {
                putExtra("CONTA", conta)
                putExtra("SALDO", saldoNum.toString())
                putExtra("VIP", isVip)
                putExtra("EXTRATO", extrato)

                startActivity(this)
                finish()
            }
        }

        findViewById<Button>(R.id.btnTransferenciaSair).setOnClickListener {
            val intent = Intent(this@TransferenciaActivity, MainActivity::class.java)
            with(intent) {
                startActivity(this)
                finish()
            }
        }

        findViewById<Button>(R.id.btnTransferenciaVoltar).setOnClickListener {
            val intent = Intent(this@TransferenciaActivity, HomeActivity::class.java)
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
