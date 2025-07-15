package com.hearts.schedule.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class MD5Util
{
    public static String encrypt(String strSrc)
    {
        try
        {
            // 定义十六进制字符数组，用于将字节转换为十六进制表示
            char hexChars[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

            // 将输入字符串转换为字节数组
            byte[] bytes = strSrc.getBytes();

            // 获取MD5消息摘要实例
            MessageDigest md = MessageDigest.getInstance("MD5");

            // 更新摘要内容
            md.update(bytes);

            // 计算哈希值(生成16字节的MD5哈希)
            bytes = md.digest();

            // 创建用于存储十六进制字符的数组(每个字节对应两个十六进制字符)
            int j = bytes.length;
            char[] chars = new char[j * 2];
            int k = 0;

            // 将每个字节转换为两个十六进制字符
            for (int i = 0; i < bytes.length; i++)
            {
                byte b = bytes[i];
                // 取高4位并转换为十六进制字符
                chars[k++] = hexChars[b >>> 4 & 0xf];
                // 取低4位并转换为十六进制字符
                chars[k++] = hexChars[b & 0xf];
            }

            // 返回十六进制字符串表示
            return new String(chars);
        }
        catch (NoSuchAlgorithmException e)
        {
            // 打印堆栈跟踪并抛出运行时异常
            e.printStackTrace();
            throw new RuntimeException("MD5加密出错!!!");
        }
    }
}
