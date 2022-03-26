package ru.netology.nmedia.utils

object Utils {

    private const val THOUSANDS = 'K'
    private const val MILLIONS = 'M'


    fun getWordFromInt(inputNumber: Int): String {
        return if (inputNumber in 1000..1099) {
            (inputNumber / 1000).toString() + THOUSANDS
        } else if (inputNumber in 1100..9999) {
            (inputNumber / 1000).toString() + "." + (inputNumber / 100 % 10) + THOUSANDS
        } else if (inputNumber in 10000..999_999) {
            (inputNumber / 1000).toString() + THOUSANDS
        } else if (inputNumber > 999_999) {
            (inputNumber / 1_000_000).toString() + MILLIONS
        } else {
            inputNumber.toString()
        }
    }
}