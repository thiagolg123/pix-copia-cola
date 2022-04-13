package br.com.bondevendas.pixcopypaste.service

import br.com.bondevendas.pixcopypaste.dto.Payload
import br.com.bondevendas.pixcopypaste.service.impl.GeneratorCRC16Impl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import java.util.*


@Autowired
fun main(args: Array<String>) {
    var generatorCRC16 =  GeneratorCRC16Impl()

    //00020126450014br.gov.bcb.pix0112+119861003840207AIE AIE5204000053039865802BR5916Thiago Gonçalves6006Cidade62070503*** test hexa gerado com 3 digitos

    val payload =
        Payload("+5511986663235", "descricao", "Thiago", "Santandre", "120.00", UUID.randomUUID().toString())
    println("DADOS: valor: ${payload.amount}, pixKey: ${payload.pixKey}, txId: ${payload.txId} ")

    println("Payload montado:\n ${payload.constructPayload()}")
    println(generatorCRC16.generate("00020126430014br.gov.bcb.pix0114+55119861003840203OPA5204000053039865802BR5916Thiago Goncalves6006Cidade62070503***"))

}
