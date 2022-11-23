package com.goalist.blockchainserver.model;

import com.goalist.blockchainserver.util.Sha256Algorithm;

import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.security.*;

public class Wallet {
    private PrivateKey privateKey;
    private PublicKey publicKey;
    private String blockchainAddress;

    public Wallet() throws NoSuchAlgorithmException {
        initialize();
    }

    public void initialize() throws NoSuchAlgorithmException {
        initializeKeyPair();
        initializeBlockchainAddress();
    }

    private void initializeKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("EC");
        // 乱数生成器
        SecureRandom randomGen = SecureRandom.getInstance("SHA1PRNG");
        // 鍵サイズと乱数生成器を指定して鍵ペア生成器を初期化
        int keySize = 256;
        keyGen.initialize(keySize, randomGen);
        // 鍵ペア生成
        KeyPair keyPair = keyGen.generateKeyPair();
        // 秘密鍵
        this.privateKey = keyPair.getPrivate();
        // 公開鍵
        this.publicKey = keyPair.getPublic();
    }

    private void initializeBlockchainAddress() {
//        TODO ハッシュ化しただけ。本来もっと複雑。
        this.blockchainAddress = Sha256Algorithm.sha256(publicKey.toString());
    }

    public String generateSignature(Transaction transaction) throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException, SignatureException {
        String transactionHash = Sha256Algorithm.sha256(transaction.toString());
        // 署名生成アルゴリズムを指定する
        Signature dsa = Signature.getInstance("SHA1withECDSA");
        // 初期化
        dsa.initSign(this.privateKey);
        // 署名生成
        dsa.update(transactionHash.getBytes("UTF-8"));
        // 生成した署名を取り出す
        byte[] signature = dsa.sign();
        return DatatypeConverter.printHexBinary(signature);
    }

    public String getBlockchainAddress() {
        return this.blockchainAddress;
    }

    public PublicKey getPublicKey() {
        return this.publicKey;
    }
}