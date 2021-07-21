package com.xm.coder.leetcode;

/**
 * @auther xings
 * @email mynamecoder@163.com
 * @description 1572. 矩阵对角线元素的和
 * @date 2021/7/21 2:18 下午
 */

public class MatrixDiagonalSum {
    public static void main(String[] args) {
        int[][] mat = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        int length = mat.length;
        int sum = 0;
        for (int i = 0; i < length; i++) {
            sum = sum + mat[i][i] + mat[i][length-1-i];
        }
        if(length % 2 != 0){
        }
        sum = sum -  mat[length/2][length/2] * (length & 1);
        System.out.println(sum);
    }
}
