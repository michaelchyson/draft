package net.chyson.string;

public class StringUtils {

    private static int nth(String string, String separator, int num) {
        int i = 0;
        while (num > 0) {
            i = string.indexOf(separator, i + 1);
            num -= 1;
        }
        return i;
    }

    private static String nthString(String string, String separator, int num) {
        int i = nth(string, separator, num - 1);
        int j = nth(string, separator, num);
        return string.substring(i + 1, j);
    }

    private static String nthLeft(String string, String separator, int num) {
        int i = nth(string, separator, num - 1);
        return string.substring(0, i);
    }


    private static String nthRight(String string, String separator, int num) {
        int i = nth(string, separator, num);
        return string.substring(i + 1, string.length());
    }
}
