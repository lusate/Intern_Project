package template_kotlin;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class test {
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		String a = "(광고) 아디다스 ADICLUB NIGHT 신청 안내";
		String b = "(광고)[Reebok] 연말 홈트 럭키박스 이벤트";
		String c = "(광고)[Reebok] 연말 홈트 럭키";
		String d = "(광고) 아디다스 ADICLUB NIGHT";

		System.err.println("=============================================================");
		System.err.println("a : " + a.getBytes().length);
		System.err.println("b : " + b.getBytes().length);
		System.err.println("c : " + c.getBytes().length);
		System.err.println("d : " + c.getBytes().length);
		System.err.println("=============================================================");
		System.err.println("a - euc-kr : " + a.getBytes("EUC-KR").length);
		System.err.println("b - euc-kr : " + b.getBytes("EUC-KR").length);
		System.err.println("c - euc-kr : " + c.getBytes("EUC-KR").length);
		System.err.println("d - euc-kr : " + d.getBytes("EUC-KR").length);
		System.err.println("=============================================================");
		
		
		String z = "%25E3%2584%25B1%25EC%2586%258C%25EA%25B3%25A1";
		
		System.err.print(URLDecoder.decode(z, StandardCharsets.UTF_8.toString()));
		
	}
	
}
