package com.dal.catmeclone.encrypt;

public interface EncryptAbstractFactory {
    public BCryptPasswordEncryption createBCryptPasswordEncryption();
}
