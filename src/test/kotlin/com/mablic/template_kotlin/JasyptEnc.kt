package com.mablic.template_kotlin

import org.jasypt.encryption.pbe.PooledPBEStringEncryptor
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class JasyptEnc {

}

private val log: Logger = LoggerFactory.getLogger("JasyptEnc")

fun main(args: Array<String>) {
    val enc = PooledPBEStringEncryptor()
    val config = SimpleStringPBEConfig()
    config.password = "APP_ENCTYPTION_PASSWORD"
    config.algorithm = "PBEWITHHMACSHA512ANDAES_256"
    config.setKeyObtentionIterations("1000")
    config.poolSize = 1
    config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator")
    config.setIvGeneratorClassName("org.jasypt.iv.RandomIvGenerator")
    config.stringOutputType = "base64"
    enc.setConfig(config)

    log.error("=========================")
    log.error(enc.encrypt("qewqeqweqwe"))
    log.error("=========================")

}