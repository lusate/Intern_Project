package com.mablic.template_kotlin.model

import org.apache.ibatis.type.Alias
import org.springframework.web.multipart.MultipartFile



@Alias("MailDto")
class MailDto {
    var name: String? = null
    var sender: String? = null
    var reciever: String? = null
    var subject: String? = null
    var message: String? = null
    var file: MultipartFile? = null
}