package com.xm.coder.leetcode;

import org.junit.jupiter.api.Test;

/**
 * @auther xings
 * @email mynamecoder@163.com
 * @description 674. 最长连续递增序列
 * @date 2021/7/21 5:01 下午
 */

public class LongestContinuousIncreasingSubsequence {
    public static void main(String[] args) {
        int[] nums = {1,2,3,2};
        int violence = new LongestContinuousIncreasingSubsequence().violence(nums);
        System.out.println(violence);
    }

    /**
     * 使用动态规划
     * 时间复杂度：O(n^2)，其中 nn 是字符串的长度。动态规划的状态总数为 O(n^2)
     * 对于每个状态，我们需要转移的时间为 O(1)。
     * 空间复杂度：O(n^2)，即存储动态规划状态需要的空间。
     * 空间超出，舍弃
     */
    @Test
    public void test() {
        Integer[] nums = {1, 1, 1, 1};
        int len = nums.length;
        Boolean[][] booleans = new Boolean[len][len];
        int l = 0;
        int maxLen = 1;
        for (int i = 0; i < len; i++) {
            booleans[i][i] = true;
        }
        for (int L = 2; L <= len; L++) {
            for (int i = 0; i < len; i++) {
                int j = L + i - 1;
                if (j + 1 > len) {
                    break;
                }
                if (nums[j - 1] >= nums[j]) {
                    booleans[i][j] = false;
                } else {
                    if (j - i < 2) {
                        booleans[i][j] = true;
                    } else {
                        booleans[i][j] = booleans[i][j - 1];
                    }
                }
                if (booleans[i][j] && maxLen < j - i + 1) {
                    maxLen = j - i + 1;
                    l = i;
                }
            }
        }
        for (int i = l; i < l + maxLen; i++) {
            System.out.println(nums[i]);
        }
        System.out.println("maxLength" + maxLen);
    }

    /**
     * 时间复杂度：O(n)，其中 nn 是数组 nums 的长度。需要遍历数组一次。
     *
     * 空间复杂度：O(1)。额外使用的空间为常数。
     *
     * @param nums
     * @return
     */
    @Test
    public int violence(int[] nums) {
        int l = 0;
        int len = nums.length;
        int maxLen = 1;
        for (int i = 0; i < len - 1; i++) {
            if (nums[i + 1] <= nums[i]) {
                l = i + 1;
            }
            maxLen = Math.max(maxLen, i - l + 2);
        }
        return maxLen;
    }
}
