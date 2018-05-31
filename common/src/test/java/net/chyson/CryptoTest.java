package net.chyson;

import net.chyson.crypto.AESCipher;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * michael.chyson
 * 5/31/2018
 */
public class CryptoTest {
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/ECB/PKCS5Padding";


    public static void main(String[] args) throws Exception {
        String KEY = "oKHzBrHOUzUh2sziSAtOvg==";
        String WRONG_KEY = "Michael Ming Chyson";
        System.out.println(KEY.length());
        System.out.println(WRONG_KEY.length());
        byte[] decode = new Base64().decode(KEY);
        SecretKeySpec key = new SecretKeySpec(decode, ALGORITHM);
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] hello_worlds = cipher.doFinal("hello world".getBytes());
        String encryptedString = new Base64().encodeToString(hello_worlds);
        System.out.println(encryptedString);


        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decode1 = new Base64().decode(encryptedString);
        byte[] bytes = cipher.doFinal(decode1);
        String s = new String(bytes);
        String s1 = new Base64().encodeToString(bytes);

        System.out.println("s:" + s);
        System.out.println("s1:" + s1);


        AESCipher aesCipher = new AESCipher(KEY);
        String michael_chyson = aesCipher.encryt("michael chyson");
        System.out.println(michael_chyson);

        String decrypt = aesCipher.decrypt(michael_chyson);
        System.out.println(decrypt);

    }
}
