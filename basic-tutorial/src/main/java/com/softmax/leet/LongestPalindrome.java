package com.softmax.leet;

/**
 * 最长回文子串
 * 回文串：正着读和反着读都一样的字符串
 */
public class LongestPalindrome {

    private static String solution(String str) {
        if (str == null || "".equals(str)) {
            return "";
        }
        String res = "";
        for (int i = 0; i < str.length(); i++) {
            //奇数
            String s1 = find(str, i, i);
            //偶数
            String s2 = find(str, i, i + 1);
            res = res.length() > s1.length() ? res : s1;
            res = res.length() > s2.length() ? res : s2;
        }
        return res;
    }

    /**
     * 查找回文串
     *
     * @param s
     * @param l
     * @param r
     * @return
     */
    private static String find(String s, int l, int r) {
        while (l >= 0 && r < s.length()) {
            if (s.charAt(l) == s.charAt(r)) {
                l--;
                r++;
            } else {
                break;
            }
        }
        //注意substring是小写
        return s.substring(l + 1, r);
    }

    public static void main(String[] args) {
        String str = "abbbaccabba";
        //bbaccabb
        System.out.println(solution(str));

    }
}
