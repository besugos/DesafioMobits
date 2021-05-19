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
import android.widget.CheckBox
import androidx.fragment.app.Fragment
import com.besugos.desafiomobits.model.TransacaoModel
import com.besugos.desafiomobits.model.UsuarioModel
import com.besugos.desafiomobits.utils.IMudarTab
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class SignUpFragment : Fragment() {

    private lateinit var mudarTabListener: IMudarTab

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sign_up, container, false)
        val txtConta = view.findViewById<TextInputEditText>(R.id.etContaSignup)
        val txtPassword = view.findViewById<TextInputEditText>(R.id.etPassSignup)
        val cbVIP = view.findViewById<CheckBox>(R.id.cbVIP)

        view.findViewById<MaterialButton>(R.id.btnSignUp).setOnClickListener {

            if (validaEntradas(view)) {

                val conta = view.findViewById<TextInputEditText>(R.id.etContaSignup).text.toString()
                val password = view.findViewById<TextInputEditText>(R.id.etPassSignup).text.toString()

                try {
                    val keySaldo = conta + "SALDO"
                    val keySenha = conta + "SENHA"
                    val keyVIP = conta + "VIP"
                    val keyExtrato = conta + "EXTRATO"

                    val sharedPreferences = this.getActivity()!!
                        .getSharedPreferences("banco_mobits_usuarios", Context.MODE_PRIVATE)

                    if (sharedPreferences.contains(keySenha)) {
                        Snackbar.make(view, "Usuário já cadastrado", Snackbar.LENGTH_LONG)

                    } else {

                        val editor = sharedPreferences.edit()
                        editor.apply {
                            putString(keySaldo, "0.0")
                            putString(keySenha, password)
                            putString(keyExtrato, "")
                            putBoolean(keyVIP, cbVIP.isChecked)
                        }.apply()

                        Snackbar.make(view, "Usuário criado com sucesso", Snackbar.LENGTH_SHORT)
                            .show()

                        val contaRegistrada = txtConta.text.toString()

                        view.findViewById<TextInputEditText>(R.id.etContaSignup).text?.clear()
                        view.findViewById<TextInputEditText>(R.id.etPassSignup).text?.clear()
                        view.findViewById<TextInputEditText>(R.id.etRptPassSignup).text?.clear()

                        mudarTabListener.mudarTab(SIGN_UP_FRAGMENT)
                        mudarTabListener.userNameAlterado(contaRegistrada)
                    }
                } catch (e: Exception) {
                    Snackbar.make(view, "Erro ao criar o usuário", Snackbar.LENGTH_LONG)
                        .setAction("Tentar novamente?") {
                            // Responds to click on the action
                        }
                        .show()
                }
            }
        }

        view.findViewById<TextInputEditText>(R.id.etContaSignup).addTextChangedListener(object :
            TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                view.findViewById<TextInputLayout>(R.id.txtContaSignup).error = ""
            }
        })

        view.findViewById<TextInputEditText>(R.id.etPassSignup).addTextChangedListener(object :
            TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                view.findViewById<TextInputLayout>(R.id.txtPasswordSignup).error = ""
            }
        })

        view.findViewById<TextInputEditText>(R.id.etRptPassSignup).addTextChangedListener(object :
            TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                view.findViewById<TextInputLayout>(R.id.txtRepeatPasswordSignup).error = ""
            }
        })

        return view
    }

    fun validaEntradas(view: View): Boolean {
        var resultado = true

        val edtConta = view.findViewById<TextInputEditText>(R.id.etContaSignup)
        val edtPassword = view.findViewById<TextInputEditText>(R.id.etPassSignup)
        val edtRptPassword = view.findViewById<TextInputEditText>(R.id.etRptPassSignup)

        if (edtConta.text?.trim()!!.isBlank()) {
            view.findViewById<TextInputLayout>(R.id.txtContaSignup).error = "Campo Conta Vazio"
            resultado = false
        }

        if (edtConta.text?.trim()!!.length != 5) {
            view.findViewById<TextInputLayout>(R.id.txtContaSignup).error =
                "Conta deve possuir 5 dígitos"
            resultado = false
        }

        if (edtPassword.text?.trim()!!.length != 4) {
            view.findViewById<TextInputLayout>(R.id.txtPasswordSignup).error =
                "Senha deve possuir 4 dígitos"
            resultado = false
        }

        if (edtPassword.text.toString() != edtRptPassword.text.toString()) {
            view.findViewById<TextInputLayout>(R.id.txtRepeatPasswordSignup).error =
                "Senhas não coincidem"
            resultado = false
        }

        return resultado
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)

        mudarTabListener = context as IMudarTab
    }
}