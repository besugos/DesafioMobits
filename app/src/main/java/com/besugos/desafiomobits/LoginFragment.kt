package com.besugos.desafiomobits

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.besugos.desafiomobits.utils.IMudarTab
import com.besugos.desafiomobits.utils.UserService
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class LoginFragment : Fragment() {

    private lateinit var mudarTabListener: IMudarTab
    private lateinit var loginView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        loginView = inflater.inflate(R.layout.fragment_login, container, false)

        loginView.findViewById<MaterialButton>(R.id.btnLogin).setOnClickListener {
            if (validaEntradas(loginView)) {

                val conta =
                    loginView.findViewById<TextInputEditText>(R.id.etContaLogin).text.toString()
                val password =
                    loginView.findViewById<TextInputEditText>(R.id.editTextPasswordLogin).text.toString()

                try {
                    val keySaldo = conta + "SALDO"
                    val keySenha = conta + "SENHA"
                    val keyVIP = conta + "VIP"
                    val keyExtrato = conta + "EXTRATO"

                    val sharedPreferences = this.getActivity()!!
                        .getSharedPreferences("banco_mobits_usuarios", Context.MODE_PRIVATE)

                    if (sharedPreferences.contains(keySenha)) {
                        val senha = sharedPreferences.getString(keySenha, "")
                        val saldo = sharedPreferences.getString(keySaldo, "0.00")
                        val isVip = sharedPreferences.getBoolean(keyVIP, false)
                        val extrato = sharedPreferences.getString(keyExtrato, "")

                        if (password == senha) {
//                            val user = UserService.logIn(conta, senha)
//                            Toast.makeText(activity, "Login realizado com sucesso", Toast.LENGTH_LONG)
//                                .show()
                            Log.i("INFO", senha!!)
                            Log.i("INFO", saldo!!)

                            val intent = Intent(context, HomeActivity::class.java)
                            with(intent) {
                                putExtra("CONTA", conta)
                                putExtra("SALDO", saldo)
                                putExtra("VIP", isVip)
                                putExtra("EXTRATO", extrato)

                                startActivity(this)
                            }
                        } else {
                            Snackbar.make(loginView, "dados incorretos", Snackbar.LENGTH_LONG)
                                .setAction("Tentar novamente?") {
                                    // Responds to click on the action
                                }
                                .show()
                        }
                    }
                } catch (e: Exception) {
                    Snackbar.make(loginView, "dados incorretos", Snackbar.LENGTH_LONG)
                        .setAction("Tentar novamente?") {
                            // Responds to click on the action
                        }
                        .show()
                }
            }
        }

        loginView.findViewById<TextInputEditText>(R.id.etContaLogin).addTextChangedListener(object :
            TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                loginView.findViewById<TextInputLayout>(R.id.txtContaLogin).error = ""
            }
        })

        loginView.findViewById<TextInputEditText>(R.id.editTextPasswordLogin)
            .addTextChangedListener(object :
                TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    loginView.findViewById<TextInputLayout>(R.id.txtPasswordLogin).error = ""
                }
            })

        loginView.findViewById<Button>(R.id.btnSignUpLogin).setOnClickListener {
            mudarTabListener.mudarTab(LOGIN_FRAGMENT)
        }

        return loginView
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        mudarTabListener = context as IMudarTab
    }

    fun validaEntradas(view: View): Boolean {
        var resultado = true

        val conta = view.findViewById<TextInputEditText>(R.id.etContaLogin)
        val senha = view.findViewById<TextInputEditText>(R.id.editTextPasswordLogin)

        if (conta.text?.trim()!!.isBlank()) {
            view.findViewById<TextInputLayout>(R.id.txtContaLogin).error = "Campo Vazio"
            resultado = false
        }

        if (senha.text?.trim()!!.isBlank()) {
            view.findViewById<TextInputLayout>(R.id.txtPasswordLogin).error = "Campo Vazio"
            resultado = false
        }

        return resultado
    }

    fun userNameAlterado(conta: String) {

        val txtConta = loginView.findViewById<TextInputEditText>(R.id.etContaLogin)

        txtConta.setText(conta)

        val edtPassword = loginView.findViewById<TextInputEditText>(R.id.editTextPasswordLogin)
        edtPassword.text?.clear()
        edtPassword.requestFocus()
    }
}
