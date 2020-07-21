package com.mablic.template_kotlin;

import com.mablic.template_kotlin.common.CommonUtils;

public class Tests {
	public void tttt() {
		System.err.println("자바 클래스 테스트");
	}
	
	public static void main(String[] args) {
		Tests s = new Tests();
		s.tttt();

		System.err.println(CommonUtils.sha512("test"));
		
	}
}
