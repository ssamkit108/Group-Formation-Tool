package com.dal.catmeclone.encrypt;

public class EncryptAbstractFactoryImpl implements EncryptAbstractFactory {
    @Override
    public BCryptPasswordEncryption createBCryptPasswordEncryption() {
        return new BCryptPasswordEncryptionImpl();
    }
}
