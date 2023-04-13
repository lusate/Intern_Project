package com.omnilab.templatekotlin.common;

public class RegexPatternGroup {

	private static final String regExClass;
	private static final String regExStyle;
	private static final String regExScript;
	private static final String regExOnAttr;

	static {
		regExClass = "( )(class)( )?=( )?(\"|')[^<]+(\"|')";
		regExStyle = "<(style)([^<]+)(</style>| +/>)";
		regExScript = "<(script)([^<]+)(</script>| +/>)";
		regExOnAttr = "( )(on)[a-z]+( )?=( )?(\"|')[^/>]+(\"|')";
	}

	private RegexPatternGroup() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * @return the dateRegex
	 * @Method Description : 
	 * 		^[0-9]{4}-((0[0-9]{1})|(1[0-2]{1}))-((0[0-9]{1})|(1[0-9]{1})|(2[0-9]{1})|(3[0-1]{1}))$
	 */
	public static String getDateRegex() {
		return "^[0-9]{4}-((0[0-9]{1})|(1[0-2]{1}))-((0[0-9]{1})|(1[0-9]{1})|(2[0-9]{1})|(3[0-1]{1}))$";
	}
	
	/**
	 * @return the getIntRegex
	 * @Method Description
	 * 		^[0-9]+$
	 */
	public static String getIntRegex() {
		return "^[0-9]+$";
	}

	/**
	 * @return the getSignedIntRegex
	 * @Method Description
	 * 		^[0-9\\-]+$
	 */
	public static String getSignedIntRegex() {
		return "^[0-9\\-]+$";
	}
	
	/**
	 * @return the getDecimalRegex
	 * @Method Description
	 * 		^[0-9\\.]+$
	 */
	public static String getDecimalRegex() {
		return "^[0-9\\.]+$";
	}
	
	/**
	 * @return the getIntAndNewRegex
	 * @Method Description
	 * 		^[0-9new]+$
apa	public static String getIntAndNewRegex() {
		return "^[0-9new]+$";
	}
	
	/**
	 * @return the getTagRegex
	 * @Method Description
	 * 		^[0-9A-Za-z가-힣\\_]+$
	 */
	public static String getTagRegex() {
		return "^[0-9A-Za-z가-힣\\_]+$";
	}
	
	/**
	 * @return the getKeywordRegex
	 * @Method Description
	 * 		^[a-zA-Z0-9ㄱ-ㅎ가-힣@\\.\\_\\-\\s]+$
	 */
	public static String getKeywordRegex() {
		return "^[a-zA-Z0-9ㄱ-ㅎ가-힣@\\.\\_\\-\\s]+$";
	}
	
	/**
	 * @return the getFreeNoteRegex
	 * @Method Description
	 * 		^[^;]+$
	 */
	public static String getFreeNoteRegex() {
		return "^[^;]+$";
	}

	/**
	 * @return the getColorCodeRegex
	 * @Method Description
	 * 		^(#)[0-9a-zA-Z]{6}$
	 */
	public static String getColorCodeRegex() {
		return "^(#)[0-9a-zA-Z]{6}$";
	}
	/**
	 * @return the getEnglishNumberRegex
	 * @Method Description
	 * 		^[a-zA-Z0-9]+$
	 */
	public static String getEnglishNumberRegex() {
		return "^[a-zA-Z0-9]+$";
	}
	
	/**
	 * @return the zipRegex
	 * @Method Description
	 * 		^[0-9\\-]+
	 */
	public static String getZipRegex() {
		return "^[0-9\\-]+";
	}

	/**
	 * @return the mobileRegex
	 * @Method Description
	 * 		^(0)(1([0|1|6|7|8|9]?)|505)-?([0-9]{3,4})-?([0-9]{4})$
	 */
	public static String getMobileRegex() {
		return "^(0)(1([0|1|6|7|8|9]?)|505)-?([0-9]{3,4})-?([0-9]{4})$";
	}
	
	/**
	 * @return the koreanTelRegex
	 * @Method Description
	 * 		^\\d{2,4}-?\\d{3,4}-?\\d{4}$
	 */
	public static String getKoreanTelRegex() {
		return "^\\d{2,4}-?\\d{3,4}-?\\d{4}$";
	}
	
	/**
	 * @return the emailRegex
	 * @Method Description
	 * 		^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$
	 */
	public static String getEmailRegex() {
		return "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$";
	}
	
	/**
	 * @return the script
	 * @Method Description
	 * 		<(script)([^<]+)(</script>| +/>)
	 */
	public static String getScriptRegex() {
		return "<(script)([^<]+)(</script>| +/>)";
	}
	/**
	 * @return the class
	 * @Method Description
	 * 		( )(class)( )?=( )?(\"|')[^<]+(\"|')
	 */
	public static String getClassRegex() {
		return "( )(class)( )?=( )?(\"|')[^<]+(\"|')";
	}
	/**
	 * @return the style
	 * @Method Description
	 * 		<(style)([^<]+)(</style>| +/>)
	 */
	public static String getStyleRegex() {
		return "<(style)([^<]+)(</style>| +/>)";
	}
	/**
	 * @return the onAttribute
	 * @Method Description
	 * 		( )(on)[a-z]+( )?=( )?(\"|')[^/>]+(\"|')
	 */
	public static String getOnAttributeRegex() {
		return "( )(on)[a-z]+( )?=( )?(\"|')[^/>]+(\"|')";
	}
	/**
	 * @return the getPwdCheckRegex
	 * @Method Description
	 * 		^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#[$]%()_[+][?]\\/[*]])[A-Za-z\\d!@#[$]%()_[+][?]\\/[*]]{9,}$
	 */
	public static String getPwdCheckRegex(){
		return "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#[$]%()_[+][?]\\/[*]])[A-Za-z\\d!@#[$]%()_[+][?]\\/[*]]{9,}$";
	}
	
	/**
	 * @return the getIPV4Regex
	 * @Method Description
	 * 		^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$
	 */
	public static String getIPV4Regex(){
		return "^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";
	}
	/**
	 * @return the getBlacklist
	 * @Method Description
	 * 		((?i)javascript)|((?i)script)|((?i)iframe)|((?i)document)|((?i)vbscript)|((?i)applet)|((?i)embed)|((?i)object)|((?i)frame)|((?i)grameset)|((?i)layer)|((?i)bgsound)|((?i)alert)|((?i)onblur)|((?i)onchange)|((?i)onclick)|((?i)ondblclick)|((?i)enerror)|((?i)onfocus)|((?i)onload)|((?i)onmouse)|((?i)onscroll)|((?i)onsubmit)|((?i)onunload)|((?i)union)|((?i)insert)|((?i)drop)|((?i)update)|((?i)delete)|((?i)substr)|((?i)declare)|((?i)openrowset)|((?i)user_tables)|((?i)user_tab_columns)|((?i)table_name)|((?i)column_name)|((?i)row_num)|((?i);)
	 * 		CommonUtils.blacklistReplace 의 정규표현식
	 */
	public static String getBlacklist() {
		return "((?i)javascript)|((?i)script)|((?i)iframe)|((?i)document)|((?i)vbscript)|((?i)applet)|((?i)embed)|((?i)object)|((?i)frame)|((?i)grameset)|((?i)layer)|((?i)bgsound)|((?i)alert)|((?i)onblur)|((?i)onchange)|((?i)onclick)|((?i)ondblclick)|((?i)enerror)|((?i)onfocus)|((?i)onload)|((?i)onmouse)|((?i)onscroll)|((?i)onsubmit)|((?i)onunload)|((?i)union)|((?i)insert)|((?i)drop)|((?i)update)|((?i)delete)|((?i)substr)|((?i)declare)|((?i)openrowset)|((?i)user_tables)|((?i)user_tab_columns)|((?i)table_name)|((?i)column_name)|((?i)row_num)|((?i);)";
	}
	
	/**
	 * @return String 
	 * @author OMNILAB
	 * @description 입력 받은 문자열에서 스크립트와, 콜론, On 속성을 제거한 후 반환한다 
	 */
	public static String removeBlackList(String string){
		String response = string.replaceAll(regExScript, "")
								.replaceAll(regExOnAttr, "")
								.replaceAll(";", "");
		return response;
	}
	
	/**
	 * @return String 
	 * @author OMNILAB
	 * @description 입력 받은 문자열에서 스크립트태그와 스타일 태그, 클래스와, ON 속성을 제거한 후 반환합니다. 
	 */
	public static String removeHTMLTag(String string){
		String response = string.replaceAll(regExScript, "")
								.replaceAll(regExOnAttr, "")
								.replaceAll(regExStyle, "")
								.replaceAll(regExClass, "");
		return response;
	}
	
	/**
	 * @return String 
	 * @author OMNILAB
	 * @description 입력 받은 문자열에서 스크립트(on 속성 포함)를 제거한 후 반환한다 
	 */
	public static String removeScriptRemove(String string){
		String response = string.replaceAll(regExScript, "")
								.replaceAll(regExOnAttr, "");
		return response;
	}
}
