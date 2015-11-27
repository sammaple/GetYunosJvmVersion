package com.jhy.getyunosjvmversion;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @Author: Shper
 * @Description: TODO
 * @Since: JDK 8.0
 * @Version: 0.1 2015��1��13�� C ����<br>
 */
public class Util {

    /*
     * ro.product.model
     */
    public static String getModel() {
        return cutString(exec_grep("getprop | grep ro.product.model"));
    }

    /*
     * ro.build.version.release
     */
    public static String getVersion() {
        return cutString(exec_grep("getprop | grep ro.build.version.release"));
    }

    /*
     * ro.aliyun.clouduuid
     */
    public static String getUUID() {
        return cutString(exec_grep("getprop | grep ro.aliyun.clouduuid"));
    }

    /*
     * dhcp.eth0.ipaddress
     */
    public static String getIP() {
        return cutString(exec_grep("getprop | grep dhcp.eth0.ipaddress"));
    }

    /*
     * ִ�� linux ����
     */
    public static String exec(String cmd) {
        String rst = "";
        try {
            Process p = Runtime.getRuntime().exec(cmd);
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((rst = br.readLine()) != null) {
                return rst;
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return checkNULL(rst);
    }

    /*
     * ִ�� linux ����ɴ���������
     */
    public static String exec_grep(String cmd) {
        String rst = "";
        try {
            ProcessBuilder pb = new ProcessBuilder("sh", "-c", cmd);
            Process p = pb.start();
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((rst = br.readLine()) != null) {
                return rst;
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return checkNULL(rst);
    }

    /**
     * ��� Ŀ¼�Ƿ����
     */
    public static void checkDir() {
        File file = new File("/sdcard/feedback/");
        if (file.exists()) {
            Util.exec_grep("mkdir /sdcard/feedback");
        }
    }


    /**
     * �ü� ��ȡ getprop ����
     * 
     * @param str
     * @return
     */
    public static String cutString(String str) {
        if (null == str || "" == str) {
            return "";
        }

        String tmp[] = str.split(":");
        tmp[1] = tmp[1].trim();
        return tmp[1].substring(1, tmp[1].length() - 1);

    }

    /**
     * ��� �ַ����Ƿ�Ϊ��
     * 
     * @param str
     * @return
     */
    public static String checkNULL(String str) {
        if (null == str) {
            return "";
        } else {
            return str;
        }
    }

}
