package com.omnilab.templateKotlin.config.view

import org.springframework.web.servlet.view.AbstractView
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import java.io.IOException
import java.io.PrintWriter
import org.apache.poi.openxml4j.exceptions.OpenXML4JRuntimeException
import java.text.SimpleDateFormat
import org.apache.poi.xssf.streaming.SXSSFWorkbook
import java.io.OutputStream
import java.net.URLEncoder
import java.util.*


class ExcelDownloadView: AbstractView() {

    override fun getContentType(): String? {
        return "application/octet-stream"
    }

    override fun renderMergedOutputModel(map: MutableMap<String, Any>, request: HttpServletRequest, response: HttpServletResponse) {
        val worker = map["book"] as SXSSFWorkbook
        val todate = SimpleDateFormat("YYYY-MM-dd HH_mm_ss").format(Date())
        val filename = map["name"].toString() + "_" + todate + ".xlsx"
        var os: OutputStream? = null

        try {
            response.contentType = this.contentType
            val header = request.getHeader("User-Agent")
            if (header.contains("Edge")) {
                response.setHeader("Content-Disposition", "attachment; fileName=" + URLEncoder.encode(filename, "UTF-8").replace("\\+", "%20") + ";")
            } else if (header.contains("Chrome") || header.contains("Opera") || header.contains("Firefox")) {
                response.setHeader("Content-Disposition", "attachment; fileName=" + String(filename.toByteArray(Charsets.UTF_8), Charsets.ISO_8859_1) + ";")
            } else {
                response.setHeader("Content-Disposition", "attachment; fileName=" + URLEncoder.encode(filename, "UTF-8").replace("\\+", "%20") + ";")
            }
            response.setHeader("Content-Transfer-encoding", "binary")

            os = response.outputStream
            worker.write(os)
            os.flush()
            os.close()
            worker.dispose()
            worker.close()
        } catch (e1: OpenXML4JRuntimeException) {
            logger.error("엑셀 다운로드 에러 팝업창 닫음 : " + filename.replace("[\r\n]".toRegex(), ""))
        } catch (e: Exception) {
            logger.error("엑셀 다운로드 에러")
            val out: PrintWriter
            try {
                out = response.writer
                out.print("<script type='text/javascript'>alert('파일 생성 도중 오류가 났습니다.\\n관리자에게 문의 바랍니다.'); window.open('about:blank', '_self').close();</script>")
                out.flush()
                out.close()
            } catch (e1: IOException) {
                logger.error("엑셀 다운로드 리스폰스 에러", e)
            }

        } finally {
            try {
                os?.close()
                worker.close()
            } catch (e: IOException) {

            }

        }
    }

}