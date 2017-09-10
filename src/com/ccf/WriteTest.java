package com.ccf;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

/**
 * Created by root on 17-9-3.
 */
public class WriteTest {
    public static void main(String[] args) throws IOException {
        Writer pw = new PrintWriter("/home/chengchf/code_test/demo.txt");
        pw.write(97);
        pw.write('a');
        pw.close();
        System.out.println(Integer.toBinaryString(97));//1100001
        Character a = 'a';
        System.out.println(a.SIZE);//16
        System.out.println(charToByte2(a)[0]+" "+charToByte2(a)[1]);//0 97
    }

    public static byte[] charToByte2(char c) {
        byte[] arr = new byte[2];
        arr[0] = (byte) ((c >> 8)& 0xff);
        arr[1] = (byte) (c & 0xff);
        return arr;
    }
}
