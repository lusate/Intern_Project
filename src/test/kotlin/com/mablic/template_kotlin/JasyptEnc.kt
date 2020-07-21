package com.mablic.template_kotlin

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

class JasyptEnc {
}

fun main(args: Array<String>) {
	
    val mpCryptoPassword = "milvussteve"
    val value = "jdbc:sqlserver://121.78.116.235:1433;DatabaseName=adi_dwn_test;integratedSecurity=false;"

    println("Original Value : $value")
    val encryptor = StandardPBEStringEncryptor()
    encryptor.setAlgorithm("PBEWithMD5AndDES")

    encryptor.setPassword(mpCryptoPassword)
    val encryptedPassword = encryptor.encrypt(value)
    println(encryptedPassword)

    val decryptor = StandardPBEStringEncryptor()
    decryptor.setPassword(mpCryptoPassword)
    System.out.println(decryptor.decrypt(encryptedPassword))
 	
}