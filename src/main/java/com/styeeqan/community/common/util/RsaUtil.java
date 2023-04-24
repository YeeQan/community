package com.styeeqan.community.common.util;

import com.styeeqan.community.pojo.dto.KeyPairDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Optional;

/**
 * 非对称加密工具类
 */
@Slf4j
@Component
public class RsaUtil {

    private static final String ALGORITHM_NAME = "RSA";

    /**
     * 生成密钥对
     */
    public Optional<KeyPairDto> generateKeyPair() {
        KeyPairGenerator keyPairGen;
        try {
            keyPairGen = KeyPairGenerator.getInstance(ALGORITHM_NAME);
        } catch (NoSuchAlgorithmException e) {
            log.error("生成RSA密钥对失败", e);
            return Optional.empty();
        }
        KeyPairDto keyPairDto = new KeyPairDto();
        keyPairGen.initialize(1024, new SecureRandom());
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();
        RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
        String publicKey = Base64.getEncoder().encodeToString(rsaPublicKey.getEncoded());
        String privateKey = Base64.getEncoder().encodeToString(rsaPrivateKey.getEncoded());
        keyPairDto.setPublicKey(publicKey);
        keyPairDto.setPrivateKey(privateKey);
        return Optional.of(keyPairDto);
    }

    /**
     * RSA加密
     */
    public Optional<String> encrypt(String content, String publicKey) {
        byte[] decoded = Base64.getDecoder().decode(publicKey);
        RSAPublicKey pubKey;
        try {
            pubKey = (RSAPublicKey) KeyFactory.getInstance(ALGORITHM_NAME).generatePublic(new X509EncodedKeySpec(decoded));
            Cipher cipher;
            cipher = Cipher.getInstance(ALGORITHM_NAME);
            cipher.init(Cipher.ENCRYPT_MODE, pubKey);
            String encode = Base64.getEncoder().encodeToString(cipher.doFinal(content.getBytes(StandardCharsets.UTF_8)));
            return Optional.ofNullable(encode);
        } catch (Exception e) {
            log.error("RSA加密失败", e);
            return Optional.empty();
        }
    }

    /**
     * 解密
     */
    public Optional<String> decrypt(String content, String privateKey) {
        try {
            RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) KeyFactory
                    .getInstance(ALGORITHM_NAME)
                    .generatePrivate(new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKey)));
            Cipher cipher = Cipher.getInstance(ALGORITHM_NAME);
            cipher.init(Cipher.DECRYPT_MODE, rsaPrivateKey);
            String decode = new String(cipher.doFinal(Base64.getDecoder().decode(content)));
            return Optional.ofNullable(decode);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
}
