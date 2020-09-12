package com.jhjz.emr.uirepo

import org.junit.Test

/**
 * @describe :
 *
 * @author zwq 2020/9/8
 */
class LinkedListAlgo {
    @Test
    fun main() {

    }

    /**
     *请判断一个链表是否为回文链表。
     * 先把链表转为数组 然后双指针判断是否回文
     */
    fun isPalindrome(head: ListNode?): Boolean {
        val intArr = mutableListOf<Int>()

        var preHead = head
        while (preHead != null) {
            intArr.add(preHead.`val`)
            preHead = preHead.next
        }

        var start = 0
        var end = intArr.size-1
        while (start < end) {
            if (intArr[start] != intArr[end]) {
                return false
            }
            start++
            end--
        }
        return true
    }

    /**
     * 将两个升序链表合并为一个新的 升序 链表并返回。新链表是通过拼接给定的两个链表的所有节点组成的。 
    示例：
    输入：1->2->4, 1->3->4
    输出：1->1->2->3->4->4
     */
    fun mergeTwoLists(l1: ListNode?, l2: ListNode?): ListNode? {
        var l1 = l1
        var l2 = l2
        val preNode = ListNode(-1)
        var preHead = preNode

        while (l1 != null && l2 != null) {
            if (l1.`val` <= l2.`val`) {
                preHead.next = l1
                l1 = l1.next
            } else {
                preHead.next = l2
                l2 = l2.next
            }
            preHead = preHead.next!!
        }

        preHead.next = l1 ?: l2
        return preNode.next
    }

    /**
     * 删除链表的倒数第N个节点
    给定一个链表，删除链表的倒数第 n 个节点，并且返回链表的头结点。

    示例：
    给定一个链表: 1->2->3->4->5, 和 n = 2.
    当删除了倒数第二个节点后，链表变为 1->2->3->5.
     */
    fun removeNthFromEnd(head: ListNode?, n: Int): ListNode? {
        //设置预先节点 防止头指针丢失 无法返回 且防止链表初始化时无头部节点错误
        val dummyNode = ListNode(0)
        dummyNode.next = head

        //确定链表长度
        var length = 0
        var first = dummyNode.next
        while (first != null) {
            length++
            first = first.next
        }

        //将第n-1位节点指向替换
        first = dummyNode
        for (i in 0 until n){
            first = first!!.next
        }
        first!!.next = first.next!!.next
        return dummyNode.next
    }
    fun removeNthFromEnd2(head: ListNode, n: Int): ListNode? {
        //设置预先节点
        val dummy = ListNode(0)
        dummy.next = head

        var first: ListNode? = dummy
        var second: ListNode? = dummy

        //找到n的后一位
        for (i in 1..n + 1) {
            first = first!!.next
        }
        // 第二和第一个节点的距离相同 同时移动 结束时 后一位就是n的前一位
        while (first != null) {
            first = first.next
            second = second!!.next
        }
        second!!.next = second.next!!.next
        return dummy.next
    }

    // 1->2->3->4->null
    /**
     * 反转链表
     */
    fun reverseList(head: ListNode?): ListNode? {
        var preNode: ListNode? = null//前一个节点 头节点的前一个节点为空
        var curNode = head//当前节点
        while (curNode != null) {
            val tempNode = curNode.next//记录下一位节点

            curNode.next = preNode//将当前节点的下一位指向前一位 即1指向空 2指向1 3指向2
            preNode = curNode//替换前置节点

            curNode = tempNode//替换当前节点为下一个节点
        }
        return preNode
    }

    class ListNode(var `val`: Int) {
        var next: ListNode? = null
    }
}