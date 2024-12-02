package com.example.tbcexercises

import android.content.Context
import android.content.Intent.ShortcutIconResource
import android.content.res.Resources
import android.util.Log
import com.example.tbcexercises.Util.isNumber

class GeoTranslate(private val context: Context) : Translator {
    private val resources = context.resources
    private val mapErteulebi: Map<Int, String> =
        resources.getStringArray(R.array.ErteulebiGe).mapIndexed() { idx, el -> (idx + 1 to el) }
            .toMap()
    private val mapAseulebi: Map<Int, String> =
        resources.getStringArray(R.array.AseulebiGe)
            .mapIndexed() { idx, el -> ((idx + 1) * 100 to el) }
            .toMap()
    private val mapTwoDigits: Map<Int, String> =
        resources.getStringArray(R.array.TwoDigitsGe).mapIndexed() { idx, el -> (idx + 11 to el) }
            .toMap()

    private val mapAteulebi: Map<Int, String> =
        resources.getStringArray(R.array.AteulebiGe)
            .mapIndexed() { idx, el -> ((idx + 1) * 10 to el) }
            .toMap()


    override fun translateNum(text: String): String {
        return when {
            text.length == 1 && text.isNumber() -> translateOneDigit(text)
            text.length == 2 && text.isNumber() -> translateTwoDigit(text)
            text.length == 3 && text.isNumber() -> translateThreeDigit(text)
            text == "1000" -> "ათასი"

            else -> "რიცხვი სწორად შეიყვანეთ"
        }

    }

    private fun translateOneDigit(text: String): String {
        return mapErteulebi.get(text.toInt()) ?: ""
    }

    private fun translateTwoDigit(text: String): String {
        Log.d("TwoDigits", mapTwoDigits.toString())
        Log.d("Ateulebi", mapAteulebi.toString())


        var answer = mapAteulebi.get(text.toInt())
            ?: mapTwoDigits.get(text.toInt()) //Base casebistvis tavidan

        val mapAteulebiFiltered: Map<Int, String> = mapAteulebi.filter { it.key % 20 == 0 }
            .map { (key, value) -> key to value.substring(0, value.length - 1) + "და" }.toMap()

        Log.d("Ateulebi2", mapAteulebiFiltered.toString())

        //tu ar daemtxva Base caseebs
        if (answer == null && text[0].toString().toInt() * 10 in mapAteulebiFiltered) {
            answer = mapAteulebiFiltered.get(
                text[0].toString().toInt() * 10
            ) + translateNum(text[1].toString())
        } else if (answer == null && text[0].toString().toInt() * 10 !in mapAteulebiFiltered) {
            answer =
                mapAteulebiFiltered.get((text[0].toString().toInt() - 1) * 10) + mapTwoDigits.get(
                    text[1].toString().toInt() + 10
                )
        }
        return answer ?: ""
    }

    fun translateThreeDigit(text: String): String {
        var answer: String? = mapAseulebi.get(text.toInt())

        val mapAseulebiFiltered: Map<Int, String> = mapAseulebi
            .map { (key, value) -> key / 100 to value.substring(0, value.length - 1) }.toMap()

        if (answer == null && text[1].toString() != "0") {
            answer = mapAseulebiFiltered.get(
                text[0].toString().toInt()
            ) + translateTwoDigit(text.substring(1, 3))
        } else if (answer == null && text[1].toString() == "0") {
            answer = mapAseulebiFiltered.get(text[0].toString().toInt()) + translateOneDigit(
                text.last().toString()
            )

        }
        return answer ?: ""
    }


}