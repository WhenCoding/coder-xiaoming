package com.xm.coder.leetcode;

/**
 * @auther xings
 * @email mynamecoder@163.com
 * @description 5. 最长回文字符串
 * @date 2021/7/17 10:33 上午
 */

public class LongestPalindromicSubstring {
    public static void main(String[] args) {
        // System.out.println(violence("abcd"));
        // System.out.println(isPalindromic("aba"));
        System.out.println(longestPalindrome("aacabdkacaa"));
    }

    /**
     * 解法一：暴力枚举
     */

    public static String violence(String str) {
        char[] chars = str.toCharArray();
        int maxLength = 1;
        int begin = 0;
        // 枚举出所有的字符串
        for (int left = 0; left < chars.length; left++) {
            for (int right = left + 1; right <= chars.length - 1; right++) {
                if (right - left + 1 > maxLength && isPalindromic(chars, left, right)) {
                    maxLength = right - left + 1;
                    begin = left;
                }
            }
        }
        return str.substring(begin, begin + maxLength);
    }

    /**
     * 是否是回文串
     */
    public static Boolean isPalindromic(char[] chars, int left, int right) {
        while (left < right) {
            System.out.println(chars[left] + ":" + chars[right]);
            if (chars[left] != chars[right]) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

    /**
     * 解法二：动态规划
     */

    public static String longestPalindrome(String s) {

        int len = s.length();
        if (len < 2) {
            return s;
        }
        char[] chars = s.toCharArray();
        int left = 0;
        int maxLength = 1;
        Boolean[][] booleans = new Boolean[len][len];
        for (int i = 0; i < len; i++) {
            booleans[i][i] = true;
        }

        for (int L = 2; L <= len; L++) {
            for (int i = 0; i < len; i++) {
                // j - i + 1 = L
                int j = L + i - 1;
                if (j >= len) {
                    break;
                }
                if (chars[i] != chars[j]) {
                    booleans[i][j] = false;
                } else {
                    if (j - i + 1 < 4) {
                        booleans[i][j] = true;
                    } else {
                        booleans[i][j] = booleans[i + 1][j - 1];
                    }
                }
                if (j - i + 1 > maxLength && booleans[i][j]) {
                    maxLength = j - i + 1;
                    left = i;
                }
            }
        }
        return s.substring(left, maxLength + left );
    }
}
