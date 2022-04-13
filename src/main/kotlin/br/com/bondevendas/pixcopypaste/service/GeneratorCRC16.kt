package br.com.bondevendas.pixcopypaste.service

interface GeneratorCRC16 {
    fun generate(payload: String): String
}
