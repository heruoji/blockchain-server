package com.goalist.blockchainserver.util;

import java.security.MessageDigest;

public class Sha256Algorithm {
    public static String sha256(String input) {
        try { //メッセージ・ダイジェストは、任意サイズのデータを取得して固定長のハッシュ値を出力する安全な一方向のハッシュ機能です。
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes("UTF-8"));
            StringBuffer hexStr = new StringBuffer(); // 16進数としてハッシュ値を持つ
            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1) hexStr.append('0');
                hexStr.append(hex);
            }
            return hexStr.toString();
        }
        catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        System.out.println(Sha256Algorithm.sha256("test"));
    }
}
