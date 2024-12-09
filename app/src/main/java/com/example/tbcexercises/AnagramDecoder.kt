package com.example.tbcexercises


class AnagramDecoder {

    fun groupAnagrams(input: MutableList<String>): MutableList<MutableList<String>> {
        val finalResult: MutableList<MutableList<String>> = mutableListOf()
        val alreadyCheckedString = mutableListOf<String>()

        for (element in input) {
            if (alreadyCheckedString.contains(element)) {
                continue
            }

            val result = mutableListOf<String>()
            for (otherElement in input) {
                if (otherElement.toCharArray().sorted().toString() == element.toCharArray().sorted()
                        .toString()
                ) {
                    result.add(otherElement)
                    alreadyCheckedString.add(otherElement)
                }
            }

            finalResult.add(result)
        }

        return finalResult
    }
}