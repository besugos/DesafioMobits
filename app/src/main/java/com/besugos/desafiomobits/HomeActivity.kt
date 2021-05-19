package com.besugos.desafiomobits

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.besugos.desafiomobits.model.UsuarioModel

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val conta = intent.getStringExtra("CONTA")
        val saldo = intent.getStringExtra("SALDO")
        val isVip = intent.getBooleanExtra("VIP", false)
        val extrato = intent.getStringExtra("EXTRATO")

        val header = findViewById<TextView>(R.id.contaHeader)
        header.text = "Conta: ${conta}"
        findViewById<TextView>(R.id.headerVip).isVisible = isVip
        findViewById<Button>(R.id.btnGerente).isVisible = isVip

        findViewById<Button>(R.id.btnSaldo).setOnClickListener {
            val intent = Intent(this@HomeActivity, SaldoActivity::class.java)
            with(intent) {
                putExtra("CONTA", conta)
                putExtra("SALDO", saldo)
                putExtra("VIP", isVip)
                putExtra("EXTRATO", extrato)
                startActivity(this)
                finish()
            }
        }

        findViewById<Button>(R.id.btnSair).setOnClickListener {
            val intent = Intent(this@HomeActivity, MainActivity::class.java)
            with(intent) {
                startActivity(this)
                finish()
            }
        }

        findViewById<Button>(R.id.btnExtrato).setOnClickListener {

        }

        findViewById<Button>(R.id.btnDeposito).setOnClickListener {
            val intent = Intent(this@HomeActivity, DepositoActivity::class.java)
            with(intent) {
                putExtra("CONTA", conta)
                putExtra("SALDO", saldo)
                putExtra("VIP", isVip)
                putExtra("EXTRATO", extrato)
                startActivity(this)
                finish()
            }
        }

        findViewById<Button>(R.id.btnSaque).setOnClickListener {
            val intent = Intent(this@HomeActivity, SaqueActivity::class.java)
            with(intent) {
                putExtra("CONTA", conta)
                putExtra("SALDO", saldo)
                putExtra("VIP", isVip)
                putExtra("EXTRATO", extrato)
                startActivity(this)
                finish()
            }
        }

        findViewById<Button>(R.id.btnTransferencia).setOnClickListener {

        }

        findViewById<Button>(R.id.btnGerente).setOnClickListener {

        }

    }

    private fun saveData(novoUsuario: UsuarioModel) {
        val keySaldo = novoUsuario.conta + "SALDO"
        val keySenha = novoUsuario.conta + "SENHA"
        val keyVIP = novoUsuario.conta + "VIP"
        val keyExtrato = novoUsuario.conta + "EXTRATO"

        val sharedPreferences = getSharedPreferences("banco_mobits_usuarios", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.apply {
            putString(keySaldo, novoUsuario.saldo.toString())
            putString(keySenha, novoUsuario.password)
            putString(keyExtrato, novoUsuario.extrato)
            putBoolean(keyVIP, novoUsuario.vip)
        }.apply()
    }

    private fun loadData(conta: String): UsuarioModel {
        val keySaldo = conta + "SALDO"
        val keySenha = conta + "SENHA"
        val keyVIP = conta + "VIP"
        val keyExtrato = conta + "EXTRATO"

        val sharedPreferences = getSharedPreferences("banco_mobits_usuarios", Context.MODE_PRIVATE)
        val senha = sharedPreferences.getString(keySenha, "")
        val saldo = sharedPreferences.getString(keySaldo, "0,00")
        val isVip = sharedPreferences.getBoolean(keyVIP, false)
        val extrato = sharedPreferences.getString(keyExtrato, "")

        return UsuarioModel(conta, senha!!, saldo!!, isVip, extrato!!)
    }
}
