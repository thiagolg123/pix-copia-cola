package br.com.bondevendas.pixcopypaste.dto

import br.com.bondevendas.pixcopypaste.constants.IDPixPayloadQrCode.ID_ADDITIONAL_DATA_FIELD_TEMPLATE
import br.com.bondevendas.pixcopypaste.constants.IDPixPayloadQrCode.ID_ADDITIONAL_DATA_FIELD_TEMPLATE_TXID
import br.com.bondevendas.pixcopypaste.constants.IDPixPayloadQrCode.ID_COUNTRY_CODE
import br.com.bondevendas.pixcopypaste.constants.IDPixPayloadQrCode.ID_CRC_16
import br.com.bondevendas.pixcopypaste.constants.IDPixPayloadQrCode.ID_MERCHANT_ACCOUNT_INFORMATION
import br.com.bondevendas.pixcopypaste.constants.IDPixPayloadQrCode.ID_MERCHANT_ACCOUNT_INFORMATION_DESCRIPTION
import br.com.bondevendas.pixcopypaste.constants.IDPixPayloadQrCode.ID_MERCHANT_ACCOUNT_INFORMATION_GUI
import br.com.bondevendas.pixcopypaste.constants.IDPixPayloadQrCode.ID_MERCHANT_ACCOUNT_INFORMATION_KEY
import br.com.bondevendas.pixcopypaste.constants.IDPixPayloadQrCode.ID_MERCHANT_CATEGORY_CODE
import br.com.bondevendas.pixcopypaste.constants.IDPixPayloadQrCode.ID_MERCHANT_CITY
import br.com.bondevendas.pixcopypaste.constants.IDPixPayloadQrCode.ID_MERCHANT_NAME
import br.com.bondevendas.pixcopypaste.constants.IDPixPayloadQrCode.ID_PAYLOAD_FORMAT_INDICATOR
import br.com.bondevendas.pixcopypaste.constants.IDPixPayloadQrCode.ID_TRANSACTION_AMOUNT
import br.com.bondevendas.pixcopypaste.constants.IDPixPayloadQrCode.ID_TRANSACTION_CURRENCY
import br.com.bondevendas.pixcopypaste.service.impl.GeneratorCRC16Impl

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
    private val FIX_VALUE_MERCHANT_CATEGORY_CODE = "0000"
    private val FIX_VALUE_TRANSACTION_CURRENCY = "986"
    private val FIX_VALUE_COUNTRY_CODE = "BR"

    var generatorCRC16 =  GeneratorCRC16Impl()

    /*
     * building linha a linha
     *   https://www.bcb.gov.br/content/estabilidadefinanceira/pix/Regulamento_Pix/II_ManualdePadroesparaIniciacaodoPix.pdf pag 14
     */

    private fun constructPayloadInformations(id: String, valueString: String): String {
        val valueLength = valueString.length.toString()
        val valueLengthWithPad = valueLength.padStart(2, '0')

        return id.plus(valueLengthWithPad).plus(valueString)
    }

    fun getPayload () : String{
        var payload = StringBuilder()
        payload.append(constructPayloadInformations(ID_PAYLOAD_FORMAT_INDICATOR, FIX_VALUE_FORMAT_INDICATOR))
        payload.append(buildMerchantAccountInformation())
        payload.append(constructPayloadInformations(ID_MERCHANT_CATEGORY_CODE, FIX_VALUE_MERCHANT_CATEGORY_CODE))
        payload.append(constructPayloadInformations(ID_TRANSACTION_CURRENCY, FIX_VALUE_TRANSACTION_CURRENCY))
        payload.append(constructPayloadInformations(ID_TRANSACTION_AMOUNT, this.amount))
        payload.append(constructPayloadInformations(ID_COUNTRY_CODE, FIX_VALUE_COUNTRY_CODE))
        payload.append(constructPayloadInformations(ID_MERCHANT_NAME, this.merchantName))
        payload.append(constructPayloadInformations(ID_MERCHANT_CITY, this.merchantCity))
        payload.append(buildAdditionalDataFieldTemplate())



        return generatorCRC16.generate(payload.toString())
    }


    private fun buildMerchantAccountInformation(): String{
        val dominioBC = constructPayloadInformations(ID_MERCHANT_ACCOUNT_INFORMATION_GUI, FIX_VALUE_ACCOUNT_GUI)
        val pixKeyFormated = constructPayloadInformations(ID_MERCHANT_ACCOUNT_INFORMATION_KEY, this.pixKey)
        val description =
            when(this.description.isBlank()) {
                true  -> ""
                false -> constructPayloadInformations(ID_MERCHANT_ACCOUNT_INFORMATION_DESCRIPTION, this.description)
            }

        return constructPayloadInformations(ID_MERCHANT_ACCOUNT_INFORMATION, dominioBC.plus(pixKeyFormated).plus(description))
    }

    private fun buildAdditionalDataFieldTemplate() : String{
        val txIdFormated = constructPayloadInformations(ID_ADDITIONAL_DATA_FIELD_TEMPLATE_TXID, txId)

        return constructPayloadInformations(ID_ADDITIONAL_DATA_FIELD_TEMPLATE, txIdFormated)
    }

}