package com.wlkmultimedia.utils

import org.rabbitconverter.rabbit.Rabbit

/**
 * Created by Aung Thuya on 1/7/2017.
 */
class FontUtil {
    companion object{
        var isValid: Boolean = false

        fun convertNumberToMMCharacter(number: Int): String {
            var myString = number.toString()
            myString = myString.replace("1".toRegex(), "၁")
            myString = myString.replace("2".toRegex(), "၂")
            myString = myString.replace("3".toRegex(), "၃")
            myString = myString.replace("4".toRegex(), "၄")
            myString = myString.replace("5".toRegex(), "၅")
            myString = myString.replace("6".toRegex(), "၆")
            myString = myString.replace("7".toRegex(), "၇")
            myString = myString.replace("8".toRegex(), "၈")
            myString = myString.replace("9".toRegex(), "၉")
            myString = myString.replace("0".toRegex(), "၀")

            return myString
        }

        fun vo(text: String?): String? {
            return if (isValid) {
                text.toString()
            } else {
                Rabbit.uni2zg(text)
            }
        }

        fun vi(text: String?): String {
            return if (isValid) {
                text.toString()
            } else {
                Rabbit.zg2uni(text)
            }
        }
    }


}
