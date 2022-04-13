package br.com.bondevendas.pixcopypaste.dto

import br.com.bondevendas.pixcopypaste.constants.IDPixPayloadQrCode.ID_MERCHANT_ACCOUNT_INFORMATION
import br.com.bondevendas.pixcopypaste.constants.IDPixPayloadQrCode.ID_MERCHANT_ACCOUNT_INFORMATION_GUI
import br.com.bondevendas.pixcopypaste.constants.IDPixPayloadQrCode.ID_MERCHANT_ACCOUNT_INFORMATION_KEY
import br.com.bondevendas.pixcopypaste.constants.IDPixPayloadQrCode.ID_PAYLOAD_FORMAT_INDICATOR
import br.com.bondevendas.pixcopypaste.constants.PayloadFieldSize.Merchant_Account_Information
import br.com.bondevendas.pixcopypaste.constants.PayloadFieldSize.Merchant_Account_Information_Chave
import br.com.bondevendas.pixcopypaste.constants.PayloadFieldSize.Merchant_Account_Information_GUI
import br.com.bondevendas.pixcopypaste.constants.PayloadFieldSize.Payload_Format_Indicator

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

    fun constructPayload(): String {
        val formactedPayload = StringBuilder()
        formactedPayload
            .append(ID_PAYLOAD_FORMAT_INDICATOR)
                .append(Payload_Format_Indicator)
                .append(FIX_VALUE_FORMAT_INDICATOR)

            .append(ID_MERCHANT_ACCOUNT_INFORMATION)
                .append(Merchant_Account_Information)
                    .append(ID_MERCHANT_ACCOUNT_INFORMATION_GUI)
                        .append(Merchant_Account_Information_GUI)
                            .append(FIX_VALUE_ACCOUNT_GUI)
                    .append(ID_MERCHANT_ACCOUNT_INFORMATION_KEY)
                        .append(Merchant_Account_Information_Chave)
                            .append(pixKey)
        return formactedPayload.toString()
    }
}