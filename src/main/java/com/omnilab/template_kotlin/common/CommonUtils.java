package com.omnilab.template_kotlin.common;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.validation.DataBinder;


/**
 * @author : OMNILAB
 * @version : 1.0.0
 * @FileName : CommonUtils.java
 * @Project : TEMPLATE
 * @Description :
 * 공통 적으로 사용되는 함수 및 웹 프로젝트 사용시 자주 사용되는 함수 모음
 * @since : 2018. 12. 1.
 */

@SuppressWarnings("rawtypes")
public class CommonUtils {

    public static final Logger logger = LoggerFactory.getLogger(CommonUtils.class);

    /**
     * @param : HttpServletRequest
     * @return : String ip
     * @Method Name  : clientip
     * @author : OMNILAB
     * @Method Description : 클라이언트 아이피 체크
     */
    public static String clientip(HttpServletRequest req) {
        String ip = req.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = req.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = req.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = req.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = req.getHeader("HTTP_X_FORWARDED_FOR");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = req.getHeader("X-CLIENT-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = req.getRemoteAddr();
        }
        return ip;
    }

    public static String getScheme(HttpServletRequest req) {
        String scheme;
        String proto = req.getHeader("x-forwarded-proto");
        if (proto != null) {
            scheme = "https".equals(proto) ? "https" : "http";
        } else {
            scheme = req.isSecure() ? "https" : "http";
        }
        return scheme;
    }

    /**
     * @param : String
     * @return : String
     * @Method Name  : blacklistReplace
     * @Since : 2018. 12.
     * @version : 0.1
     * @Method Description :
     * 입력값 조작 방지 : 입력받은 String에서 Blacklist 를 검출 후 |허용하지 않는 문자 입니다.|로 Replace 한 후 리턴
     */
    public static String blacklistReplace(String value) {
        value = value.replaceAll("((?i)javascript)|((?i)script)|((?i)iframe)|((?i)document)|((?i)vbscript)|((?i)applet)|((?i)embed)|((?i)object)|((?i)frame)|((?i)grameset)|((?i)layer)|((?i)bgsound)|((?i)alert)|((?i)onblur)|((?i)onchange)|((?i)onclick)|((?i)ondblclick)|((?i)enerror)|((?i)onfocus)|((?i)onload)|((?i)onmouse)|((?i)onscroll)|((?i)onsubmit)|((?i)onunload)|((?i)union)|((?i)insert)|((?i)drop)|((?i)update)|((?i)delete)|((?i)substr)|((?i)declare)|((?i)openrowset)|((?i)user_tables)|((?i)user_tab_columns)|((?i)table_name)|((?i)column_name)|((?i)row_num)|((?i);)", "|허용하지 않는 문자 입니다.|");
        return value;
    }

    /**
     * @param : String
     * @return : String
     * @Method Name  : blacklistRemove
     * @Method Description :
     * 입력값 조작 방지 : 입력받은 String에서 Blacklist 를 검출 후 |허용하지 않는 문자 입니다.|로 Replace 한 후 리턴
     */
    public static String blacklistRemove(String value) {
        value = value.replaceAll("((?i)javascript)|((?i)script)|((?i)iframe)|((?i)document)|((?i)vbscript)|((?i)applet)|((?i)embed)|((?i)object)|((?i)frame)|((?i)grameset)|((?i)layer)|((?i)bgsound)|((?i)alert)|((?i)onblur)|((?i)onchange)|((?i)onclick)|((?i)ondblclick)|((?i)enerror)|((?i)onfocus)|((?i)onload)|((?i)onmouse)|((?i)onscroll)|((?i)onsubmit)|((?i)onunload)|((?i)union)|((?i)insert)|((?i)drop)|((?i)update)|((?i)delete)|((?i)substr)|((?i)declare)|((?i)openrowset)|((?i)user_tables)|((?i)user_tab_columns)|((?i)table_name)|((?i)column_name)|((?i)row_num)", "");
        return value;
    }

    /**
     * @param : Object
     * @return : boolean
     * @Method Name  : isEmptyObject
     * @excetion :
     * @serial :
     * @version : 0.1
     * @Method Description :
     * Object를 입력받으면 Object의 instance를 체크한 후 해당 객체가 NULL 또는 공백인지 체크한후
     * 빈 값인 경우 true
     * 비지 않은 경우 false 를 리턴한다.
     * @see :
     */
    public static boolean isEmptyObject(Object obj) {
        if (obj == null) {
            return true;
        }
        if (obj instanceof String) {
            return ((String) obj).equals("");
        } else if (obj instanceof List) {
            return ((List) obj).isEmpty();
        } else if (obj instanceof Map) {
            return ((Map) obj).isEmpty();
        } else if (obj instanceof Object[]) {
            return Array.getLength(obj) == 0;
        } else {
            return obj == null;
        }
    }

    /**
     * @param : String
     * @return : boolean
     * @Method Name  : dateformat
     * @Method Description :
     * String 문자열 date type 유효성 체크 한 후 날짜형식이 맞으면 true, 아니면 false를 리턴한다.
     */
    public static boolean isDateFormat(String date) {
        date = date.replace(".", "").replace("-", "");

        if (date.length() > 8) {
            return false;
        } else if (date.length() == 7) {
            String m0 = date.substring(4, 5);
            if (m0.equals("0") || m0.equals("1")) {
                date = date.substring(0, 6) + "0" + date.substring(6);
            } else {
                date = date.substring(0, 4) + "0" + date.substring(4);
            }
        } else if (date.length() == 6) {
            date = date.substring(0, 4) + "0" + date.substring(4, 5) + "0" + date.substring(5, 6);
        }

        SimpleDateFormat dateFormatParser = new SimpleDateFormat("yyyyMMdd");
        dateFormatParser.setLenient(false);
        try {
            dateFormatParser.parse(date);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * @param : String
     * @return : boolean
     * @Method Name  : isDigit
     * @Method Description :
     * String 문자열을  Double로 Parse 하여 변환되면 true, 변환되지 않으면 false를 리턴한다.
     */
    public static boolean isDigit(String s) {
        try {
            Double.parseDouble(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * @param : String
     * @return : boolean
     * @Method Name  : isBit
     * @version : 0.1
     * @Method Description :
     * 입력받은 문자열이 0 또는 1인지 체크하여 true 및 false를 리턴한다.
     */
    public static boolean isBit(String s) {
        if (!s.equals("0") && !s.equals("1")) {
            return false;
        }
        return true;
    }

    /**
     * @param : String, int
     * @return : boolean
     * @Method Name  : checkByteLength
     * @version : 0.1
     * @Method Description :
     * 입력받은 문자열 str이 byte로 변환 했을 때 limitByte를 초과하는지 검증한 후
     * limitByte 이하 인경우 true, 초과인 경우 false 를 리턴한다.
     */
    public static boolean checkByteLength(String str, int limitByte) {
        int en = 0;
        int ko = 0;
        int etc = 0;

        char[] string = str.toCharArray();
        for (int j = 0; j < string.length; j++) {
            if (string[j] >= 'A' && string[j] <= 'z') {
                en++;
            } else if (string[j] >= '\u3131' && string[j] <= '\uD787') {
                ko += 2;
            } else {
                etc++;
            }
        }
        int sum = en + ko + etc;
        return (sum <= limitByte);
    }

    /**
     * @param : String value, int start, int end
     * @return : boolean
     * @Method Name  : stringBetween
     * @version : 0.1
     * @Method Description :
     * 입력받은 문자열 str이 int start와 int end 이상 이하인 경우 true를 리턴하며
     * String이 정수가 아니거나 start 보다 미만, end 를 초과할 경우 false를 리턴한다.
     */
    public static boolean stringBetween(String value, int start, int end) {
        try {
            int i = Integer.parseInt(value);
            if (i >= start && i <= end) {
                return true;
            } else return false;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * @param : String startDate, String endDate
     * @return : boolean
     * @Method Name  : compareDate
     * @version : 0.1
     * @Method Description :
     * 문자열  startDate와 endDate를 날짜형식으로 검토후 endDate가 startDate가 더 늦은 날짜면 true,
     * 그 외의 경우에는 false를 리턴한다.
     */
    public static boolean compareDate(String startDate, String endDate) {
        startDate = startDate.replace(".", "").replace("-", "");
        endDate = endDate.replace(".", "").replace("-", "");

        if (startDate.length() > 8 || endDate.length() > 8) {
            return false;
        }
        if (startDate.length() == 7) {
            String m0 = startDate.substring(4, 5);
            if (m0.equals("0") || m0.equals("1")) {
                startDate = startDate.substring(0, 6) + "0" + startDate.substring(6);
            } else {
                startDate = startDate.substring(0, 4) + "0" + startDate.substring(4);
            }
        } else if (startDate.length() == 6) {
            startDate = startDate.substring(0, 4) + "0" + startDate.substring(4, 5) + "0" + startDate.substring(5, 6);
        }
        if (endDate.length() == 7) {
            String m0 = endDate.substring(4, 5);
            if (m0.equals("0") || m0.equals("1")) {
                endDate = endDate.substring(0, 6) + "0" + endDate.substring(6);
            } else {
                endDate = endDate.substring(0, 4) + "0" + endDate.substring(4);
            }
        } else if (endDate.length() == 6) {
            endDate = endDate.substring(0, 4) + "0" + endDate.substring(4, 5) + "0" + endDate.substring(5, 6);
        }

        SimpleDateFormat dateFormatParser = new SimpleDateFormat("yyyyMMdd");
        dateFormatParser.setLenient(false);
        try {
            Date dt1 = dateFormatParser.parse(startDate);
            Date dt2 = dateFormatParser.parse(endDate);
            if (dt2.after(dt1)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * @param : String
     * @return : String
     * @Method Name  : sha512
     * @excetion : NoSuchAlgorithmException
     * @version : 0.1
     * @Method Description : Sha512 암호화
     */
    public static String sha512(String message) {
        MessageDigest md;
        StringBuilder mes1 = new StringBuilder();
        try {
            md = MessageDigest.getInstance("SHA-512");

            md.update(message.getBytes());
            byte[] mb = md.digest();
            for (byte temp : mb) {
                StringBuilder s = new StringBuilder(Integer.toHexString(temp));
                while (s.length() < 2) {
                    s.insert(0, "0");
                }
                s = new StringBuilder(s.substring(s.length() - 2));
                mes1.append(s);
            }
        } catch (NoSuchAlgorithmException e) {
            logger.error("|-------------- Sha512 Error : CommonUtils.java ------------------|", e);
        }
        return mes1.toString();
    }

    /**
     * @return : String
     * @Method Name  : getRandString16
     * @version : 0.1
     * @Method Description :
     * 16자리  숫자 + 알파벳 대소문자로 구성된 랜덤 스트링을 리턴한다.
     */
    public static String getRandString16() {
        StringBuffer temp = new StringBuffer();
        SecureRandom rnd = new SecureRandom();
        for (int i = 0; i < 16; i++) {
            int rIndex = rnd.nextInt(2);
            switch (rIndex) {
                case 0:
                    temp.append((char) ((int) (rnd.nextInt(26)) + 97));
                    break;
                case 1:
                    // 0-9
                    temp.append((rnd.nextInt(10)));
                    break;
            }
        }
        return temp.toString();
    }

    /**
     * @return : String
     * @Method Name  : getTimestampMS
     * @version : 0.1
     * @Method Description :
     * 시간의 밀리세컨드를 문자열로 리턴한다.
     */
    public static String getTimestamp() {
        Date now = new Date();
        return String.valueOf(now.getTime());
    }

    /**
     * @param : HttpServletRequest req
     * @return : String
     * @Method Name  : refererAnalytics
     * @version : 0.1
     * @Method Description :
     * HttpServletRequest의 User-Agent를 분석해서 웹로그를 구분한다.
     */
    public static String refererAnalytics(HttpServletRequest req) {
        String response = "";
        String usr = req.getHeader("user-agent").toLowerCase();
        if (req.getHeader("referer") == null) {
            if (usr.contains("kakao")) {
                response = "KAKAO TALK LINK";
            } else if (usr.contains("naver")) {
                response = "NAVER APP";
            } else if (usr.contains("nate")) {
                response = "NATE APP";
            } else if (usr.contains("instagram")) {
                response = "INSTAGRAM APP";
            } else if (usr.contains("face") || usr.contains("fbios")) {
                response = "FACEBOOK APP";
            } else {
                response = "DIRECT";
            }
        } else {
            String ref = req.getHeader("referer");
            if (ref.contains("facebook.com")
                    || ref.contains("instagram.com")
                    || ref.contains("story.kakao")
                    || ref.contains("cafe.naver")
                    || ref.contains("cafe.daum")
                    || ref.contains("blog.naver")
                    || ref.contains("blog.me")
                    || ref.contains("blog.daum")
                    || ref.contains("tistory.")
                    || ref.contains("me2.d")
                    || ref.contains("ruliweb")
                    || ref.contains("ppomppu")
                    || ref.contains("mamclub")
                    || ref.contains("82cook")
                    || ref.contains("youtube.com")
            ) {
                response = "SOCIAL";
            } else if (ref.contains("search.naver")
                    || ref.contains("search.daum.net")
                    || ref.contains("google.co.kr/search?q")
            ) {
                response = "ORGANIC SEARCH";
            } else if (ref.contains("searchad.nhncorp")
                    || ref.contains("searchad.naver")
            ) {
                response = "SEARCH AD";
            } else if (ref.contains("designerd.kr")
                    || ref.contains("work.OMNILAB.com")
            ) {
                response = "DESIGNERD";
            } else {
                response = "ETC WEB";
            }
        }
        return response;
    }

    /**
     * @param : Object ob
     * @Method Name  : ObjPrint
     * @version : 0.1
     * @Method Description :
     * 입력 받은 Object에 포함되어 있는 키값과 인자를 프린트한다.
     */
    public static void ObjPrint(Object ob) {
        try {
            StringBuilder sb = new StringBuilder();
            for (Field field : ob.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                Object value = field.get(ob);
                sb.append("|").append(field.getName()).append(" \t|").append("| ").append(value).append(" |\r\n");
            }
            logger.error("====================================== ObjPrint ======================================");
            logger.error(sb.toString());
            logger.error("====================================== ObjPrint ======================================");
        } catch (Exception e) {
            logger.error("객체 변수 확인 에러", e);
        }
    }

    /**
     * @param : String t
     * @return : String
     * @Method Name  : xmlEscapeText
     * @version : 0.1
     * @Method Description :
     * 입력받은 문자열 t를 분석해서 xml 작성시 삽입되면 안될 특수문자를 변환한다음 리턴한다.
     */
    public static String xmlEscapeText(String t) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < t.length(); i++) {
            char c = t.charAt(i);
            switch (c) {
                case '<':
                    sb.append("&lt;");
                    break;
                case '>':
                    sb.append("&gt;");
                    break;
                case '\"':
                    sb.append("&quot;");
                    break;
                case '&':
                    sb.append("&amp;");
                    break;
                case '\'':
                    sb.append("&apos;");
                    break;
                default:
                    if (c > 0x7e) {
                        sb.append("&#").append((int) c).append(";");
                    } else
                        sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * @param : Map<String, String[]> parameterMap, Class<T> dto
     * @return : <T> T
     * @throws : IllegalAccessException, InstantiationException
     * @Method Name  : getDTOFromParamMap
     * @version : 0.1
     * @Method Description :
     * 입력받은 ParameterMap을 함께 입력받은 Dto 객체로 치환한 다음 DTO 객체를 리턴한다.
     */
    public static <T> T getDTOFromParamMap(Map<String, String[]> parameterMap, Class<T> dto) throws IllegalAccessException, InstantiationException {
        final MutablePropertyValues sourceProps = getPropsFrom(parameterMap);
        T targetDTO = dto.newInstance();
        DataBinder binder = new DataBinder(targetDTO);
        binder.bind(sourceProps);

        return targetDTO;
    }

    /**
     * @param : Map<String, String[]> parameterMap
     * @return : MutablePropertyValues
     * @throws : IllegalAccessException, InstantiationException
     * @Method Name  : getPropsFrom
     * @version : 0.1
     * @Method Description :
     */
    private static MutablePropertyValues getPropsFrom(Map<String, String[]> parameterMap) {
        final MutablePropertyValues mpvs = new MutablePropertyValues();
        parameterMap.forEach(
                (k, v) -> {
                    String dotKey =
                            k.replaceAll("\\[]", "")
                                    .replaceAll("\\[(\\D+)", ".$1")
                                    .replaceAll("]\\[(\\D)", ".$1")
                                    .replaceAll("(\\.\\w+)]", "$1");
                    mpvs.addPropertyValue(dotKey, v);
                }
        );

        return mpvs;
    }

    /**
     * @param : String value
     * @return : String
     * @throws : UnsupportedEncodingException
     * @Method Name  : urlEncodeValue
     * @version : 0.1
     * @Method Description :
     * 입력받은 문자열을 UTF-8 문자셋으로 변환한 후 리턴한다.
     */
    public static String urlEncodeValue(String value) {
        try {
            return URLEncoder.encode(value, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return value;
        }
    }

    /**
     * @param : HttpServletRequest request, String name
     * @return : String
     * @throws : UnsupportedEncodingException
     * @Method Name  : getCookie
     * @version : 0.1
     * @Method Description :
     * request에 저장되어있는 Cookie 중에서 name 문자열과 일치하는 쿠키명의 값을 리턴한다. 없는 경우 null을 리턴한다.
     */
    public static String getCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    /**
     * @param : Date
     * @return : String
     * @Method Name : simpleDateToDatetime
     * @Method Description :
     * Date date 객체를 받아서 yyyy-MM-dd HH:mm:ss 형식의 String을 반환한다.
     */
    public static String simpleDateToDatetime(Date date) {
        SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strDate = form.format(date);
        return strDate;
    }

    /**
     * @param : Date date
     * @return : String
     * @Method Name : simpleDateToDate
     * @Method Description :
     * Date date 객체를 받아서 기본 날짜형 yyyy-MM-dd 형식 String을 반환한다.
     */
    public static String simpleDateToDate(Date date) {
        SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = form.format(date);
        return strDate;
    }

    /**
     * @param : String
     * @return : Date
     * @throws : ParseException
     * @Method Name : stringDatetimeParse
     * @version : 0.1
     * @Method Description :
     * yyyy-MM-dd HH:mm:ss 형식의 String을 받아서 Date 객체로 파싱한다.
     * 파싱에 실패한 경우에는 현재 시간을 반환한다.
     */
    public static Date stringDatetimeParse(String str) {
        SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        try {
            date = form.parse(str);
        } catch (ParseException e) {
            logger.error("|------------- ParseException : CommonUtiles.stringDatetimeParse ::: ", e);
            date = new Date();
        }
        return date;
    }

    /**
     * @param : String str
     * @return : Date
     * @throws : ParseException
     * @Method Name : StringDateParse
     * @Method Description :
     * yyyy-MM-dd 형식의 String을 받아서 Date 객체로 파싱한다.
     * 파싱에 실패한 경우에는 현재 시간을 반환한다.
     */
    public static Date stringDateParse(String str) {
        SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        try {
            date = form.parse(str);
        } catch (ParseException e) {
            logger.error("|------------- ParseException : CommonUtiles.stringDateParse ::: ", e);
            date = new Date();
        }
        return date;
    }

    /**
     * @param : String[] p, int i
     * @return : String
     * @Method Name : blankStringArr
     * @Method Description : 공백 리턴 배열용
     */
    public static String blankStringArr(String[] p, int i) {
        if (CommonUtils.isEmptyObject(p)) {
            return "";
        } else if (p.length <= i) {
            return "";
        } else {
            return p[i];
        }
    }


    public static synchronized String encodeURI(String s) {
        String[] findList = {"#", "+", "&", "%"};
        String[] replList = {"%23", "%2B", "%26", "%25"};
        return StringUtils.replaceEach(s, findList, replList);
    }
}