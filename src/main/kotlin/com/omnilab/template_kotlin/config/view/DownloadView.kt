package com.omnilab.template_kotlin.config.view

import org.springframework.web.servlet.view.AbstractView
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.springframework.util.FileCopyUtils
import java.io.FileInputStream
import java.io.File
import java.io.OutputStream
import java.net.URLEncoder
import java.nio.charset.Charset


class DownloadView: AbstractView() {

    override fun getContentType(): String? {
        // 8비트로 된 일련의 데이터..application의 데이타 형식을 굳이 지정하지 않을떄 사용함.
        return "application/octet-stream";
    }

    override fun renderMergedOutputModel(map: MutableMap<String, Any>, request: HttpServletRequest, response: HttpServletResponse) {
        var os: OutputStream? = null
        var fis: FileInputStream? = null
        try {
            val path = map["path"] as String
            val file = File(path)

            if (!file.exists()) {
                val out = response.writer
                out.print("<script>alert('존재하지 않는 파일입니다.'); window.open('', '_self', ''); window.close();</script>")
                out.flush()
                out.close()
                return
            }
            response.contentType = this.contentType
            response.setContentLengthLong(file.length())//파일 크기 설정
            // 다운로드 파일에 대한 설정
            val header = request.getHeader("User-Agent")
            if (header.contains("Chrome") || header.contains("Opera") || header.contains("Firefox")) {
                response.setHeader("Content-Disposition", "attachment; fileName=" + String(file.getName().toByteArray(Charsets.UTF_8), Charsets.ISO_8859_1) + ";")
            } else {
                response.setHeader("Content-Disposition", "attachment; fileName=" + URLEncoder.encode(file.getName(), "UTF-8").replace("\\+", "%20") + ";")
            }
            //데이터 인코딩이 바이너리 파일임을 명시
            response.setHeader("Content-Transfer-encoding", "binary")
            // 실제 업로드 파일을 inputStream 으로 읽어서
            // response 에 연결된 OutputStream으로 전송하겠다.
            os = response.outputStream
            fis = FileInputStream(file)
            FileCopyUtils.copy(fis, os)
            fis.close()
            os.close()
        } catch (e: Exception) {
            val out = response.writer
            out.print("<script>alert('존재하지 않는 파일입니다.');</script>")
            out.flush()
            out.close()
        } finally {
            if (os != null) {
                os.close()
            }
            if(fis != null) {
                fis.close()
            }
        }
    }

}