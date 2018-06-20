package net.chyson.conversion;


public class Conversion {

//    public static String byte2HexComprehension(byte[] src) {
//        // byte: 8 bits
//        // short: 16 bits
//        // int: 32 bits
//        // long: 64 bits
//        // float: 32 bits
//        // double: 64 bits
//        StringBuilder stringBuilder = new StringBuilder();
//        if (src == null || src.length <= 0) {
//            return null;
//        }
//
//        for (int i = 0; i < src.length; i++) {
//            byte b = src[i];
//            //return int by default, no effort to convert it to other type
//            //besides this is toHexString method only in Integer.
//            //to use the toHexString method
//            int v = b & 0xFF; //return int be default
//            String hv = Integer.toHexString(v);
//
//            stringBuilder.append(i + ":"); //for better understanding view
//
//            if (hv.length() < 2) {
//                stringBuilder.append(0); //0x07 for example
//            }
//            stringBuilder.append(hv + ";");//for better understanding view
//        }
//        return stringBuilder.toString();
//    }

    public static String byte2Hex(byte[] src) {

        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }

        for (int i : src) {
            int v = src[i] & 0xFF;

            String hv = Integer.toHexString(v);

            if (hv.length() < 2) {
                stringBuilder.append(0); //0x07 for example
            }
            stringBuilder.append(hv);
        }

        return stringBuilder.toString();
    }

//    public static byte[] hexString2ByteComprehension(String hex) {
//        //hex string has 2 characters
//
//        hex = hex.toLowerCase();
//        int len = (hex.length() / 2);
//        System.out.println("length: " + hex.length());
//        System.out.println("len: " + len);
//
//        byte[] result = new byte[len];
//        System.out.println(hex);
//        char[] achar = hex.toCharArray();
//        for (char c : achar) {
//            System.out.print(c);
//            System.out.print("--");
//        }
//        System.out.println();
//        for (int i = 0; i < len; i++) {
//            int pos = i * 2;
//            result[i] = (byte) (char2Byte(achar[pos]) << 4 | char2Byte(achar[pos + 1]));
//        }
//        return result;
//    }

    public static byte[] hexString2Byte(String hex) {
        hex = hex.toLowerCase();
        int len = (hex.length() / 2);

        byte[] result = new byte[len];
        char[] chars = hex.toCharArray();

        for (int i = 0; i < len; i++) {
            int pos = i * 2;
            int high = char2Byte(chars[pos]) << 4;
            byte low = char2Byte(chars[pos + 1]);
            result[i] = (byte) (high | low);
        }
        return result;
    }

    private static byte char2Byte(char c) {
        return (byte) "01234567890abcdef".indexOf(c);//can not list 16*16 characters

    }

    public static void main(String[] args) {
        byte[] bytes = "hello!".getBytes();


        String s = byte2Hex(bytes);
        System.out.println(s);

        byte[] bytes1 = hexString2Byte(s);
        for (byte b : bytes1) {
            System.out.println(b);
        }
    }
}
