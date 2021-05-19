package com.besugos.desafiomobits.utils

import android.content.Context
import com.besugos.desafiomobits.model.TransacaoModel
import com.besugos.desafiomobits.model.UsuarioModel
import java.lang.Exception

class UserService {

    companion object {

        val extratoVazio = arrayListOf<TransacaoModel>()

        val usuarioComum = UsuarioModel("12345", "1234", "100.00", false, "")
        val usuarioVip = UsuarioModel("11111", "1234", "10000.00", true, "")

        private val users = arrayListOf<UsuarioModel>(usuarioComum, usuarioVip)


        /**
         * Realiza login
         */
        fun logIn(conta: String, senha: String): UsuarioModel? {
            return users.find {
                it.conta == conta && it.password == senha
            }
        }

        /**
         * Registra um usuário
         */
        fun register(novoUsuario: UsuarioModel) {
            // Verifica se já existe usuário com email cadastrado
            val user = users.find {
                it.conta == novoUsuario.conta
            }

            if (user != null) {
                throw Exception("Usuário já cadastrado!")
            }

            users.add(novoUsuario)
        }
    }
}