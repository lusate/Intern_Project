package com.omnilab.templatekotlin

import org.jasypt.encryption.pbe.PooledPBEStringEncryptor
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.security.SecureRandom

class JasyptEnc

private val log: Logger = LoggerFactory.getLogger("JasyptEnc")

fun main() {
    val enc = PooledPBEStringEncryptor()
    val config = SimpleStringPBEConfig()
    config.password = "milvussteve"
    config.algorithm = "PBEWITHHMACSHA512ANDAES_256"
    config.setKeyObtentionIterations("1000")
    config.poolSize = 1
    config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator")
    config.setIvGeneratorClassName("org.jasypt.iv.RandomIvGenerator")
    config.stringOutputType = "base64"
    enc.setConfig(config)

}