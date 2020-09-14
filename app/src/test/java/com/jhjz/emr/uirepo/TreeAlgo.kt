package com.jhjz.emr.uirepo

/**
 * @describe :
 *
 * @author zwq 2020/9/14
 */
class TreeAlgo {

    /**
     * 给定一个二叉树，找出其最大深度。
     */
    fun maxDepth(root: TreeNode?): Int {
        return if (root == null) {
            0
        } else {
            val left = maxDepth(root.left)
            val right = maxDepth(root.right)
            return Math.max(left, right) + 1
        }
    }

    class TreeNode(var `val`: Int) {
        var left: TreeNode? = null
        var right: TreeNode? = null
    }
}