/*
 * Copyright 2022 Ivanti, Inc.
 * All rights reserved.
 */

package leetcode;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import leetcode.shared.TreeNode;

import static org.testng.Assert.assertEquals;

/**
 * Given two integer arrays preorder and inorder where preorder is the preorder traversal of a binary tree and inorder is the inorder traversal of the same tree, construct and return the binary tree.
 */
public class ConstructBinaryTreefromPreorderandInorderTraversal {

    private Map<Integer, Integer> inorderIndexMap = new HashMap<>();
    int[] preorder;
    int[] inorder;

    @DataProvider
    public static Object[][] testCases() {
        return new Object[][]{
                {new int[]{3,9,20,15,7}, new int[]{9,3,15,20,7}, TreeNode.createTreeFromPreOrder(3,9,20,null,null,15,7)},
        };
    }

    @Test(dataProvider = "testCases")
    public void test(int[] preorder, int[] inorder, TreeNode expected) {
        assertEquals(buildTree(preorder, inorder), expected);
    }


    public TreeNode buildTree(int[] preorder, int[] inorder) {
        for(int i = 0; i < inorder.length; i++) {
            inorderIndexMap.put(inorder[i], i);
        }
        this.inorder = inorder;
        this.preorder = preorder;
        return buildTree(0, 0, inorder.length);
    }

    private TreeNode buildTree(int ioStart, int poStart, int length) {
        if (length < 1) {
            return null;
        }
        TreeNode node = new TreeNode(this.preorder[poStart]);
        // at what position is the number in the inorderarray
        int inorderIndex = this.inorderIndexMap.get(this.preorder[poStart]);
        int leftTreeSize = inorderIndex - ioStart;
        int rightTreeSize = length - leftTreeSize - 1;
        node.left = buildTree(ioStart, poStart + 1, leftTreeSize);
        node.right = buildTree(inorderIndex + 1, poStart + leftTreeSize + 1, rightTreeSize);
        return node;
    }
}
