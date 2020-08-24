package com.jhjz.emr.uirepo

import org.junit.Test
import java.util.*

/**
 * @describe : 数组相关算法题
 *
 * @author zwq 2020/8/23
 */
class ArrayAlgo {

    @Test
    fun run() {
//        plusOne(intArrayOf(9, 9, 9, 9)).foreach()
//        moveZeroes(intArrayOf(0,1,0,3,12)).foreach()
//        twoSum(intArrayOf(2, 7, 11, 15), 26).foreach()
//        print(singleNum(intArrayOf(4, 1, 1, 2, 2)))
        intersect(intArrayOf(4, 9, 5, 6), intArrayOf(9, 9, 8, 4, 6)).foreach()
    }

    /**
     * 给定两个数组，编写一个函数来计算它们的交集。
     * map记录第一个数组中每个数字出现的次数
     * 遍历第二个数组 其中 获取值是否在map中存在 存在次数-1 记录这个数字
     * 时间复杂度O(n+m) 空间复杂度O(min(n,m)) m,n两个数组长度
     */
    fun intersect(nums1: IntArray, nums2: IntArray): IntArray {

        if (nums1.size > nums2.size)
            return intersect(nums2, nums1)

        val map = mutableMapOf<Int, Int>()
        nums1.forEach {
            map[it] = map.getOrDefault(it, 0) + 1
        }
        val result = IntArray(nums1.size)
        var index = 0
        nums2.forEach {
            var count = map.getOrDefault(it, 0)
            if (count > 0) {
                result[index++] = it
                count--
                if (count > 0) {
                    map[it] = count
                } else {
                    map.remove(it)
                }
            }
        }

        return result.copyOfRange(0, index)
    }

    /**
     * 给定一个非空整数数组，除了某个元素只出现一次以外，其余每个元素均出现两次。找出那个只出现了一次的元素。
     * 利用hash
     */
    fun singleNum(intArray: IntArray): Int? {
        val mutableMapOf = mutableMapOf<Int, Int>()
        for (i in intArray) {
            var count = mutableMapOf[i]
            if (count == null) {
                count = 1
            } else {
                count += 1
            }

            mutableMapOf[i] = count
        }
        mutableMapOf.forEach { (t, _) ->
            val i = mutableMapOf[t]
            if (i == 1) {
                return t
            }
        }
        return -1
    }

    /**
     * 给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那 两个 整数，并返回他们的数组下标。
     * 你可以假设每种输入只会对应一个答案。但是，数组中同一个元素不能使用两遍。
     *
     * 给定 nums = [2, 7, 11, 15], target = 9
     * 因为 nums[0] + nums[1] = 2 + 7 = 9
     * 所以返回 [0, 1]
     *
     * 思路：哈希表 key为值 value为下标
     * 如果 目标值 - 数组中的值 在 hash中的值存在 说明 相加可以为target
     * 则 输出两个下标
     *
     * 时间复杂度O(n) 空间复杂度O(1)
     */
    fun twoSum(nums: IntArray, target: Int): IntArray {
        val mapOf = mutableMapOf<Int, Int>()
        for (i in nums.indices) {
            val result = target - nums[i]
            if (mapOf.containsKey(result)) {
                return intArrayOf(mapOf[result]!!, i)
            }
            mapOf[nums[i]] = i
        }
        throw IllegalArgumentException("No two sum solution")
    }

    /**
     * T:给定一个数组 nums，编写一个函数将所有 0 移动到数组的末尾，同时保持非零元素的相对顺序。
     * [0,1,0,3,12]
     * 思路：交换元素  不为0 就交换nums[i]和nums[j]
     *
     * 时间复杂度：O(n) 空间复杂度:O(1)
     */
    public fun moveZeroes(nums: IntArray): IntArray? {
        var j = 0
        for (i in nums.indices) {
            if (nums[i] != 0) {
                val temp = nums[i]
                nums[i] = 0
                nums[j++] = temp
            }
        }
        return nums
    }

    /**
     * T:给定一个由整数组成的非空数组所表示的非负整数，在该数的基础上加一。
     * 思路：末位+1后取模 非0(即1-8 +1)返回数组
     *                  0的话 向前一位继续+1
     *                  999 情况 数组长度+1 首位为1
     * 时间复杂度:O(1)/O(n) 空间复杂度:O(1)/O(n)
     */
    public fun plusOne(digits: IntArray): IntArray? {
        var coypDigits = digits
        for (i in coypDigits.indices.reversed()) {
            coypDigits[i]++
            coypDigits[i] = coypDigits[i] % 10
            if (coypDigits[i] != 0) return coypDigits
        }
        coypDigits = IntArray(coypDigits.size + 1)
        coypDigits[0] = 1
        return coypDigits
    }

    private fun IntArray?.foreach() {
        println()
        this?.let {
            it.iterator().forEach {
                print(it)
            }
        }
    }
}