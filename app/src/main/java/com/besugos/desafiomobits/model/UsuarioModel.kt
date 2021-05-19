package com.besugos.desafiomobits.model

//data class UserModel(val email: String, val password: String)

class UsuarioModel(
    val conta: String,
    val password: String,
    var saldo: String,
    val vip: Boolean,
    val extrato: String
)