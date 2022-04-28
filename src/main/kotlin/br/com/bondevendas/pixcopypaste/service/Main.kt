package br.com.bondevendas.pixcopypaste.service

import br.com.bondevendas.pixcopypaste.dto.Payload
import br.com.bondevendas.pixcopypaste.service.impl.GeneratorCRC16Impl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.core.ResolvableType
import java.util.*

fun main(args: Array<String>) {
    var generatorCRC16 =  GeneratorCRC16Impl()

    //00020126450014br.gov.bcb.pix0112+119861003840207AIE AIE5204000053039865802BR5916Thiago Gonçalves6006Cidade62070503*** test hexa gerado com 3 digitos
    //"00020126430014br.gov.bcb.pix0114+55119861003840203OPA5204000053039865802BR5916Thiago Goncalves6006Cidade62070503***"

    //PARA TESTAR CONFIGURE ESSES DADOS PARA O PIX RECEBEDOR:
    val payload =
        Payload("35873454817", "Bondezan viado", "Bonde Rosa", "Santandre", "10.50", "5649d5fs")

    // DEMO DOS DADOS:
    println("DADOS: valor: ${payload.amount}, pixKey: ${payload.pixKey}, txId: ${payload.txId} ")

    // TESTE DO HEXA COM 3 DIGITOS, concatena 0 a esquerda
    println(generatorCRC16.generate("00020126450014br.gov.bcb.pix0112+119861003840207AIE AIE5204000053039865802BR5916Thiago Gonçalves6006Cidade62070503***"))

    //PAYLOAD DE FATO
    println(payload.getPayload())
}
