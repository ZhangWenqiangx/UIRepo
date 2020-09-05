package com.jhjz.emr.uirepo

import org.junit.Test

/**
 * @describe :
 *
 * @author zwq 2020/9/3
 */
class StringAlgo {

    @Test
    fun run() {

    }

    /**
     * 编写一个函数，其作用是将输入的字符串反转过来。输入字符串以字符数组 char[] 的形式给出。
    不要给另外的数组分配额外的空间，你必须原地修改输入数组、使用 O(1) 的额外空间解决这一问题。
    你可以假设数组中的所有字符都是 ASCII 码表中的可打印字符。

    示例 1：

    输入：["h","e","l","l","o"]
    输出：["o","l","l","e","h"]
    示例 2：

    输入：["H","a","n","n","a","h"]
    输出：["h","a","n","n","a","H"]

     利用双指针 替换左一位 和 右一位
     */
    fun reverseString(charArray: CharArray): CharArray {
        var start = 0
        var end = charArray.size - 1
        var temp: Char
        while (start < end) {
            temp = charArray[start]
            charArray[start++] = charArray[end]
            charArray[end--] = temp
        }
        return charArray
    }

    /**
     *
    给定一个字符串，验证它是否是回文串，只考虑字母和数字字符，可以忽略字母的大小写。
    说明：本题中，我们将空字符串定义为有效的回文串。

    示例 1:
    输入: "A man, a plan, a canal: Panama"
    输出: true

    示例 2:
    输入: "race a car"
    输出: false

    1.筛选 把字符串中的数字和字符取出
    2.双指针判断 是否 相同
    O(n)
    O(n)
     */
    fun isPalindrome(s: String): Boolean {
        val strBuf = StringBuffer()
        for (i in s.indices) {
            val ch = s[i]
            if (Character.isLetterOrDigit(ch)) {
                strBuf.append(ch)
            }
        }

        var start = 0
        var end = strBuf.length - 1
        while (start < end) {
            if (Character.toLowerCase(strBuf[start++]) != Character.toLowerCase(strBuf[end--])) {
                return false
            }
        }
        return true
    }


}