/*
 * Copyright 2022 Ivanti, Inc.
 * All rights reserved.
 */

package leetcode;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import leetcode.shared.TreeNode;

import static org.testng.Assert.assertEquals;

/**
 * TODO: Document this class.
 */
public class ConstructBinaryTreefromInorderandPostorderTraversal {

    private Map<Integer, Integer> inorderIndexMap = new HashMap<>();
    int[] inorder;
    int[] postorder;

    @DataProvider
    public static Object[][] testCases() {
        return new Object[][]{
                {new int[]{9,3,15,20,7}, new int[]{9,15,7,20,3}, TreeNode.createTreeFromPreOrder(3, 9, 20, null, null, 15, 7)},
        };
    }

    @Test(dataProvider = "testCases")
    public void test(int[] inorder, int[] postorder, TreeNode expected) {
        assertEquals(buildTree(inorder, postorder), expected);
    }


    public TreeNode buildTree(int[] inorder, int[] postorder) {
        for(int i = 0; i < inorder.length; i++) {
            inorderIndexMap.put(inorder[i], i);
        }
        this.inorder = inorder;
        this.postorder = postorder;
        return buildTree(0, 0, inorder.length);
    }

    private TreeNode buildTree(int ioStart, int poStart, int length) {
        if (length < 1) {
            return null;
        }
        TreeNode node = new TreeNode(postorder[poStart + length - 1]);
        // at what position is the number in the inorderarray
        int inorderIndex = inorderIndexMap.get(postorder[poStart + length - 1]);
        int leftTreeSize = inorderIndex - ioStart;
        int rightTreeSize = length - leftTreeSize - 1;
        node.left = buildTree(ioStart, poStart, leftTreeSize);
        node.right = buildTree(inorderIndex + 1, poStart + leftTreeSize, rightTreeSize);
        return node;
    }
}
