package com.example.tbcexercises

import android.content.Context
import com.example.tbcexercises.Util.isNumber

class EnTranslate(private val context: Context) : Translator {
    private val resources = context.resources
    private val mapErteulebi: Map<Int, String> =
        resources.getStringArray(R.array.ErteulebiEN).mapIndexed() { idx, el -> (idx + 1 to el) }
            .toMap()

    private val mapAseulebi: Map<Int, String> =
        resources.getStringArray(R.array.AseulebiEn).mapIndexed() { idx, el -> (idx + 1 to el) }
            .toMap()

    private val mapTwoDigits: Map<Int, String> =
        resources.getStringArray(R.array.TwoDigitsEN).mapIndexed() { idx, el -> (idx + 11 to el) }
            .toMap()

    private val mapAteulebi: Map<Int, String> = resources.getStringArray(R.array.AteulebiEn)
        .mapIndexed() { idx, el -> ((idx + 1) to el) }.toMap()


    override fun translateNum(text: String): String {
        return when {
            text.length == 1 && text.isNumber() -> translateOneDigit(text)
            text.length == 2 && text.isNumber()  -> translateTwoDigit(text)
            text.length == 3 && text.isNumber() -> translateThreeDigit(text)
            text == "1000" -> "a thousand"

            else -> "Enter the Number correctly"
        }

    }

    private fun translateOneDigit(text: String): String {
        return mapErteulebi.get(text.toInt()) ?: ""
    }

    private fun translateTwoDigit(text: String): String {
        var answer = mapAteulebi.get(text.toInt())
            ?: mapTwoDigits.get(text.toInt())
        if (answer == null) {
            answer = mapAteulebi.get(
                text[0].toString().toInt()
            ) + "-" + translateOneDigit(text[1].toString())

        }
        return answer
    }

    private fun translateThreeDigit(text: String): String {

        var answer: String = mapAseulebi.get(text.toInt()) ?: ""
        if (answer == "") {
            answer = mapAseulebi.get(
                text[0].toString().toInt()
            ) + " " + translateTwoDigit(text.substring(1, 3))
        }
        return answer
    }

}