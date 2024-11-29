package com.example.tbcexercises


class Solution {
    fun greatestDivider(a: Int, b: Int): Int {
        if (a == 0 || b == 0) {
            return -1
        }
        val aPositive: Int = a.absoluteValue()
        val bPositive: Int = b.absoluteValue()
        val minNumber: Int = if (aPositive < bPositive) aPositive else bPositive
        var answer: Int = 1
        for (i in 1..minNumber) {
            if (aPositive % i == 0 && bPositive % i == 0) {
                answer = i
            }
        }
        return answer
    }

    fun lowestMultiple(a: Int, b: Int): Int {

        if (a == 0 || b == 0) {
            return -1
        }

        if (a == b) {
            return a
        }

        val aPositive: Int = a.absoluteValue()
        val bPositive: Int = b.absoluteValue()

        val aFactors: MutableList<Int> = aPositive.getFactors()
        val bFactors: MutableList<Int> = bPositive.getFactors()

        val bigFactors: MutableList<Int> = if (a < b) bFactors else aFactors

        val bigFactorsForTask: MutableList<Int> =
            bigFactors.toMutableList()//aq toMutableList imitom rom aqedan vshili tan
        val smallsFactors: MutableList<Int> = if (a < b) aFactors else bFactors

        val smallResult: MutableList<Int> = mutableListOf()
        for (i in smallsFactors) {
            if (i in bigFactorsForTask) {

                bigFactorsForTask.remove(i)
            } else {
                smallResult.add(i)
            }
        }

        bigFactors.addAll(smallResult)


        return bigFactors.reduce { acc, i -> acc * i }
    }

    fun containsDollar(s: String): Boolean {
        return s.contains("$")
    }

    fun recursiveSumToHundread(n:Int):Int {
        if (n==2){
            return n
        }

        return  n+ recursiveSumToHundread(n-2)
    }


    fun reverseInt(a: Int): Int {
        val aString: String = a.absoluteValue().toString()
        var i: Int = aString.length - 1
        while (aString[i] == '0') {
            i--
        }
        if (a < 0) {
            return ("-" + aString.substring(0, i+1).reversed()).toInt()
        }
        return aString.substring(0, i+1).reversed().toInt()


    }
    fun isPalindrome(a:String):Boolean{
        return a==a.reversed()
    }

}


fun Int.absoluteValue(): Int {
    val answer: Int = if (this < 0) -1 * this else this
    return answer
}

fun Int.getFactors(): MutableList<Int> {
    var number: Int = this
    val list: MutableList<Int> = mutableListOf()
    for (i in 2..number) {
        if (number == 1) break
        while (number % i == 0) {
            list.add(i)
            number /= i

        }
    }
    return list
}