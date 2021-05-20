package com.besugos.desafiomobits

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.core.view.isVisible
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ExtratoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_extrato)
        val conta = intent.getStringExtra("CONTA")
        val extrato = intent.getStringExtra("SALDO")
        val isVip = intent.getBooleanExtra("VIP", false)

        val header = findViewById<TextView>(R.id.extratoContaHeader)
        header.text = "Conta: $conta"
        findViewById<TextView>(R.id.extratoHeaderVip).isVisible = isVip

//        val extratoString = "12/12/21 14:30 | Depósito | 30,00#12/12/21 14:30 | Depósito | 30,00#12/12/21 14:30 | Depósito | 30,00#12/12/21 14:30 | Depósito | 30,00#12/12/21 14:30 | Depósito | 30,00#12/12/21 14:30 | Depósito | 30,00#12/12/21 14:30 | Depósito | 30,00#12/12/21 14:30 | Depósito | 30,00#12/12/21 14:30 | Depósito | 30,00#12/12/21 14:30 | Depósito | 30,00#12/12/21 14:30 | Depósito | 30,00#12/12/21 14:30 | Depósito | 30,00#12/12/21 14:30 | Depósito | 30,00#12/12/21 14:30 | Depósito | 30,00#12/12/21 14:30 | Depósito | 30,00#12/12/21 14:30 | Depósito | 30,00#12/12/21 14:30 | Depósito | 30,00#12/12/21 14:30 | Depósito | 30,00#"

        val keyExtrato = conta + "EXTRATO"
        val sharedPreferences = getSharedPreferences("banco_mobits_usuarios", Context.MODE_PRIVATE)
        val extratoConta = sharedPreferences.getString(keyExtrato, "")

        val extratoLista = extratoConta!!.split("#") as MutableList<String>
        if (extratoLista.size > 1) {
            extratoLista.removeLast()
        }


        val lista = findViewById<ListView>(R.id.listExtrato)

        lista.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, extratoLista)

        findViewById<Button>(R.id.btnExtratoSair).setOnClickListener {
            val intent = Intent(this@ExtratoActivity, MainActivity::class.java)
            with(intent) {
                startActivity(this)
                finish()
            }
        }

        findViewById<Button>(R.id.btnExtratoVoltar).setOnClickListener {
            val intent = Intent(this@ExtratoActivity, HomeActivity::class.java)
            with(intent) {
                putExtra("CONTA", conta)
                putExtra("SALDO", extrato)
                putExtra("VIP", isVip)

                startActivity(this)
                finish()
            }
        }

    }
}