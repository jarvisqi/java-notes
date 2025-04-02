package com.softmax.leet;

public class AddString {

    public static void main(String[] args) {
        String num1 = "111111";
        String num2 = "999999";
        System.out.println(solution(num1, num2));
    }

    private static String solution(String num1, String num2) {
        int m = num1.length() - 1;
        int n = num2.length() - 1;
        int carry = 0;//进位
        StringBuilder sb = new StringBuilder();
        while (carry >= 1 || m >= 0 || n >= 0) {
            int x = 0, y = 0;
            if (m >= 0) {
                x = num1.charAt(m--) - '0';
            }
            if (n >= 0) {
                y = num2.charAt(n--) - '0';
            }
            sb.append((x + y + carry) % 10);
            carry = (x + y + carry) / 10;
        }
        return sb.reverse().toString();
    }

}
