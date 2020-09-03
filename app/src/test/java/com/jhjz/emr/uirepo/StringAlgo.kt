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


}