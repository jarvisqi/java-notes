package com.softmax.leet;

public class AddBinary {

    public static void main(String[] args) {
        String num1 = "11";
        String num2 = "1";
        System.out.println(solution(num1, num2));
    }

    private static String solution(String num1, String num2) {
        int m = num1.length() - 1, n = num2.length() - 1;
        int carry = 0; //表示进位
        StringBuilder sb = new StringBuilder();
        while (carry == 1 || m >= 0 || n >= 0) { //注意一定要判断 == 1,因为最高位可能会进1
            if (m >= 0 && num1.charAt(m--) == '1') {
                carry++;
            }
            if (n >= 0 && num2.charAt(n--) == '1') {
                carry++;
            }
            sb.append(carry % 2); //保留余数
            carry /= 2; //进位
        }
        return sb.reverse().toString();
    }

}

