package com.besugos.desafiomobits

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.besugos.desafiomobits.model.UsuarioModel
import com.besugos.desafiomobits.utils.IMudarTab
import com.google.android.material.tabs.TabLayout

const val LOGIN_FRAGMENT = 0
const val SIGN_UP_FRAGMENT = 1

class MainActivity : AppCompatActivity(), IMudarTab {

    private val tab by lazy { findViewById<TabLayout>(R.id.layoutLogin) }

    private lateinit var loginFragment: LoginFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inicializaDB()

        val pager = findViewById<ViewPager>(R.id.viewPagerLogin)

        tab.setupWithViewPager(pager)

        loginFragment = LoginFragment()

        pager.adapter = LoginAdapter(
            listOf(loginFragment, SignUpFragment()),
            listOf("Login", "Cadastrar"),
            supportFragmentManager
        )
    }

    override fun mudarTab(posicaoAtual: Int) {
        val novaPosicao = if (posicaoAtual == LOGIN_FRAGMENT) {
            SIGN_UP_FRAGMENT
        } else {
            LOGIN_FRAGMENT
        }

        val tabNova = tab.getTabAt(novaPosicao)
        tabNova?.select()
    }

    override fun userNameAlterado(conta: String) {
        loginFragment.userNameAlterado(conta)
    }

    fun inicializaDB() {
        val usuarioComum = UsuarioModel("12345", "1234", "100.00", false, "")
        val usuarioVip = UsuarioModel("11111", "1234", "10000.00", true, "")

        val keySaldo = usuarioComum.conta + "SALDO"
        val keySenha = usuarioComum.conta + "SENHA"
        val keyVIP = usuarioComum.conta + "VIP"
        val keyExtrato = usuarioComum.conta + "EXTRATO"

        val keySaldoVip = usuarioVip.conta + "SALDO"
        val keySenhaVip = usuarioVip.conta + "SENHA"
        val keyVIPVip = usuarioVip.conta + "VIP"
        val keyExtratoVip = usuarioVip.conta + "EXTRATO"

        val sharedPreferences = getSharedPreferences("banco_mobits_usuarios", Context.MODE_PRIVATE)

        if (sharedPreferences.contains(keySenha)) {
            return
        } else {
            val editor = sharedPreferences.edit()
            editor.apply {
                putString(keySaldo, usuarioComum.saldo)
                putString(keySenha, usuarioComum.password)
                putString(keyExtrato, usuarioComum.extrato)
                putBoolean(keyVIP, usuarioComum.vip)

                putString(keySaldoVip, usuarioVip.saldo)
                putString(keySenhaVip, usuarioVip.password)
                putString(keyExtratoVip, usuarioVip.extrato)
                putBoolean(keyVIPVip, usuarioVip.vip)
            }.apply()
        }
    }
}