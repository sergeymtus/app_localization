package ru.netology.nmedia.ui

class extraViewFunctions {
    fun stringNumberWithKInsteadOf1000(i: Int): String {
        if (i > 999_999_999_999) return ">1T!"

        var iTemp: Int = i / 1_000_000_000
        if (iTemp > 0) return "" + iTemp + "B"

        iTemp = i / 1_000_000
        if (iTemp > 0) return "" + iTemp + "M"

        iTemp = i / 1_000
        if (iTemp > 0) return "" + iTemp + "K"

        return "" + i
    }
}