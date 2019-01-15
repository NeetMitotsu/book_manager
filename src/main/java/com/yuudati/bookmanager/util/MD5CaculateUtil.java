package com.yuudati.bookmanager.util;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5计算
 * @Author Administrator李新栋 [lxd3808@163.com]
 * @Date 2019/1/15 11:38
 */
public class MD5CaculateUtil {

    /**
     * 获取文件md5
     * @param file
     * @return
     */
    public static String getMD5(File file) {
        FileInputStream inputStream = null;
        try {
            MessageDigest MD5 = MessageDigest.getInstance("MD5");
            inputStream = new FileInputStream(file);
            byte[] buffer = new byte[8192];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                MD5.update(buffer, 0, length);
            }
            return new String(Hex.encodeHex(MD5.digest()));
        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取字符串md5
     * @param target
     * @return
     */
    public static String getMD5(String target){
        return DigestUtils.md2Hex(target);
    }

}
