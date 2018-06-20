package net.chyson.crypto;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import net.chyson.conversion.Conversion;
import org.apache.commons.codec.binary.Base64;

/**
 * michael.chyson
 * 5/30/2018
 */
public class AESCipher {

    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/ECB/PKCS5Padding";

    private SecretKeySpec key;

    private byte[] bytesKey;

    public AESCipher(String key) {
        byte[] decode = new Base64().decode(key);

        this.key = new SecretKeySpec(decode, ALGORITHM);
    }

    public AESCipher(byte[] bytesKey) {
        this.bytesKey = bytesKey;
        this.key = new SecretKeySpec(bytesKey, ALGORITHM);

    }

    public String encryt(String data) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException, NoSuchPaddingException, NoSuchAlgorithmException {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] bytes = cipher.doFinal(data.getBytes());
        //Encodes a byte[] containing binary data, into a String containing characters in the Base-N alphabet.
        //Uses UTF8 encoding.
        String s = new Base64().encodeToString(bytes);
        return s;
    }


    public String decrypt(String data) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException, NoSuchPaddingException, NoSuchAlgorithmException {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, key);
        // Decodes a String containing characters in the Base-N alphabet into binary data.
        byte[] decode = new Base64().decode(data);
        byte[] bytes = cipher.doFinal(decode);
        return new String(bytes);
    }

    public String encrytHex(String data) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException, NoSuchPaddingException, NoSuchAlgorithmException {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] bytes = cipher.doFinal(data.getBytes());
        //Encodes a byte[] containing binary data, into a String containing characters in the Base-N alphabet.
        //Uses UTF8 encoding.
        String s = Conversion.byte2Hex(bytes);
        return s;
    }

    public String decryptHex(String data) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException, NoSuchPaddingException, NoSuchAlgorithmException {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, key);
        // Decodes a String containing characters in the Base-N alphabet into binary data.
        byte[] decode = Conversion.hexString2Byte(data);
        byte[] bytes = cipher.doFinal(decode);
        return new String(bytes);
    }


    public static void main(String[] args) throws Exception {
        String KEY = "oKHzBrHOUzUh2sziSAtOvg==";//length:24

        byte[] decode = new Base64().decode(KEY);
        SecretKeySpec key = new SecretKeySpec(decode, ALGORITHM);
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] hello_worlds = cipher.doFinal("hello world".getBytes());


        String encryptedString = Conversion.byte2Hex(hello_worlds);
        System.out.println(encryptedString);


//        cipher.init(Cipher.DECRYPT_MODE, key);
//        byte[] decode1 = new Base64().decode(encryptedString);
//        byte[] bytes = cipher.doFinal(decode1);
//        String s = new String(bytes);
//        String s1 = new Base64().encodeToString(bytes);
//
//        System.out.println("s:" + s);
//        System.out.println("s1:" + s1);
//
//
//        AESCipher aesCipher = new AESCipher(KEY);
//        String michael_chyson = aesCipher.encryt("michael chyson");
//        System.out.println(michael_chyson);
//
//        String decrypt = aesCipher.decrypt(michael_chyson);
//        System.out.println(decrypt);

    }

//    public static void main(String[] args) throws Exception {
//        String KEY = "oKHzBrHOUzUh2sziSAtOvg==";
//        String WRONG_KEY = "Michael Ming Chyson";
//        System.out.println(KEY.length());
//        System.out.println(WRONG_KEY.length());
//        byte[] decode = new Base64().decode(KEY);
//        SecretKeySpec key = new SecretKeySpec(decode, ALGORITHM);
//        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
//        cipher.init(Cipher.ENCRYPT_MODE, key);
//        byte[] hello_worlds = cipher.doFinal("hello world".getBytes());
//        String encryptedString = new Base64().encodeToString(hello_worlds);
//        System.out.println(encryptedString);
//
//
//        cipher.init(Cipher.DECRYPT_MODE, key);
//        byte[] decode1 = new Base64().decode(encryptedString);
//        byte[] bytes = cipher.doFinal(decode1);
//        String s = new String(bytes);
//        String s1 = new Base64().encodeToString(bytes);
//
//        System.out.println("s:" + s);
//        System.out.println("s1:" + s1);
//
//
//        AESCipher aesCipher = new AESCipher(KEY);
//        String michael_chyson = aesCipher.encryt("michael chyson");
//        System.out.println(michael_chyson);
//
//        String decrypt = aesCipher.decrypt(michael_chyson);
//        System.out.println(decrypt);
//
//    }
}