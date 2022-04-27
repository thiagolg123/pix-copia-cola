package br.com.bondevendas.pixcopypaste.dto

import br.com.bondevendas.pixcopypaste.constants.IDPixPayloadQrCode.ID_MERCHANT_ACCOUNT_INFORMATION
import br.com.bondevendas.pixcopypaste.constants.IDPixPayloadQrCode.ID_MERCHANT_ACCOUNT_INFORMATION_DESCRIPTION
import br.com.bondevendas.pixcopypaste.constants.IDPixPayloadQrCode.ID_MERCHANT_ACCOUNT_INFORMATION_GUI
import br.com.bondevendas.pixcopypaste.constants.IDPixPayloadQrCode.ID_MERCHANT_ACCOUNT_INFORMATION_KEY
import br.com.bondevendas.pixcopypaste.constants.IDPixPayloadQrCode.ID_PAYLOAD_FORMAT_INDICATOR

//constructor
class Payload(
    var pixKey: String,
    var description: String,
    var merchantName: String,
    var merchantCity: String,
    var amount: String,
    var txId: String
) {

    private val FIX_VALUE_FORMAT_INDICATOR = "01"
    private val FIX_VALUE_ACCOUNT_GUI = "br.gov.bcb.pix"

    /*
     * building linha a linha
     *   https://www.bcb.gov.br/content/estabilidadefinanceira/pix/Regulamento_Pix/II_ManualdePadroesparaIniciacaodoPix.pdf pag 14
     */

    private fun constructPayload(id: String, valueString: String): String {
        val valueLength = valueString.length.toString()
        val valueLengthWithPad = valueLength.padStart(2, '0')

        return id.plus(valueLengthWithPad).plus(valueString)
    }

    fun getPayload () : String{
        var payload = StringBuilder()
        payload.append(constructPayload(ID_PAYLOAD_FORMAT_INDICATOR, FIX_VALUE_FORMAT_INDICATOR))
        payload.append(constructMerchantAccountInformation())


        return payload.toString()
    }


    private fun constructMerchantAccountInformation(): String{
        val dominioBC = constructPayload(ID_MERCHANT_ACCOUNT_INFORMATION_GUI, FIX_VALUE_ACCOUNT_GUI)
        val pixKey = constructPayload(ID_MERCHANT_ACCOUNT_INFORMATION_KEY, this.pixKey)
        val description =
            when(this.description.isBlank()) {
                true  -> ""
                false -> constructPayload(ID_MERCHANT_ACCOUNT_INFORMATION_DESCRIPTION, this.description)
            }

        return constructPayload(ID_MERCHANT_ACCOUNT_INFORMATION, dominioBC.plus(pixKey).plus(description))
    }

}