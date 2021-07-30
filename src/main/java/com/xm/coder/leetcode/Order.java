package com.xm.coder.leetcode;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * @auther xings
 * @email mynamecoder@163.com
 * @description
 * @date 2021/7/30 11:00 上午
 */

public class Order {
    /**
     * 冒泡排序
     */
    @Test
    public void bub() {
        int[] nums = {2, 3, 4, 5, 1};
        int len = nums.length;
        for (int count = 1; count < len - 1; count++) {
            for (int i = 0; i < len - count; i++) {
                if (nums[i] < nums[i + 1]) {
                    int temp = nums[i];
                    nums[i] = nums[i + 1];
                    nums[i + 1] = temp;
                }
            }
        }

    }

    /**
     * 选择排序
     */
    @Test
    public void sel() {
        int[] nums = {3, 44, 38, 5, 47, 15, 36, 26, 27, 2, 46, 4, 19, 50, 48};
        int len = nums.length;
        for (int i = 0; i < len; i++) {
            for (int j = i + 1; j < len; j++) {
                if (nums[i] < nums[j]) {
                    int temp = nums[i];
                    nums[i] = nums[j];
                    nums[j] = temp;
                }
            }
        }
    }

}
