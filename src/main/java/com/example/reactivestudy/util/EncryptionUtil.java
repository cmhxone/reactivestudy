package com.example.reactivestudy.util;

import java.security.MessageDigest;
import java.util.Base64;
import java.util.Optional;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EncryptionUtil {
    
    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
    private static final String KEY = "Need$32Byt3L3ngth0f3ncryptionK3y";
    private static final String IV = KEY.substring(0, 16);

    /**
     * AES256 암호화 메소드
     * @param plainText
     * @return
     */
    public static Optional<String> encryptAES(String plainText) {

        Optional<String> result = null;

        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            SecretKey keySpec = new SecretKeySpec(KEY.getBytes(), "AES");
            IvParameterSpec ivParamSpec  = new IvParameterSpec(IV.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParamSpec);

            byte[] encrypted = cipher.doFinal(plainText.getBytes("UTF-8"));
            result = Optional.of(Base64.getEncoder().encodeToString(encrypted));
        } catch (Exception e) {
            log.error("encryptAES(): {}", e.getMessage());
        }

        return result;
    }

    /**
     * AES256 복호화 메소드
     * @param encryptedText
     * @return
     */
    public static Optional<String> decryptAES(String encryptedText) {

        Optional<String> result = null;

        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            SecretKey keySpec = new SecretKeySpec(KEY.getBytes(), "AES");
            IvParameterSpec ivParamSpec = new IvParameterSpec(IV.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParamSpec);

            byte[] decodedBytes = Base64.getDecoder().decode(encryptedText);
            
            result = Optional.of( new String( cipher.doFinal(decodedBytes), "UTF-8") );
        } catch (Exception e) {
            log.error("decryptAES(): {}", e.getMessage());
        }

        return result;
    }

    /**
     * 단방향 해싱 메소드
     * @param plaintext
     * @param algorithm
     * @return
     */
    public static Optional<String> hashing(String plaintext, String algorithm) {

        Optional<String> result = null;

        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            md.update(plaintext.getBytes());

            // bytes to hex
            StringBuilder sb = new StringBuilder();
            for (byte b : md.digest()) {
                sb.append(String.format("%02x", b));
            }

            result = Optional.of(sb.toString());
        } catch (Exception e) {
            log.error("hashing(): {}", e.getMessage());
        }

        return result;
    }

    /**
     * Runner for getting an encrypt/decrypt text
     * @param args (type, text)
     */
    public static void main(String args[]) {

        if (args.length < 2 || (
            !"E".equals(args[0]) && !"D".equals(args[0])
        )) {
            log.info("main(): run as 'java -cp reactivestudy.jar {} <ENCRYPT_TYPE(E or D)> <TEXT>'", EncryptionUtil.class.toString());
            return;
        }

        if ("E".equals(args[0])) {
            System.out.println(EncryptionUtil.encryptAES(args[1]).get());
        } else if ("D".equals(args[0])) {
            System.out.println(EncryptionUtil.decryptAES(args[1]).get());
        }

        return;
    }
}
