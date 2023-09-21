<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="strping" %>
<link rel="stylesheet" type="text/css" href="/css/index.css?v=0.12">
<link rel="stylesheet" type="text/css" href="/css/milse-common-ui.css?v=0.12">
<!-- ${baseURL} -->
<div class="wrap">
	<div id="mainFrm" class="login_form">
		<div class="container col-5" style="text-align:center">

		<form:form action="/home" method="post" name="form">

			<%-- form:form 하면 csrf 토큰이 이미 생성되어
			 <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" /> 를 사용하지 않아도 됨.
			 --%>

			<div class="login" id="login">
<%--				<img src="/img/Milvus_CI.png" alt="Milvus_CI"/>--%>
				<div class="form-floating mb-3">
					<input class="form-control" type="text" name="id" maxlength="30" placeholder="ID" />
					<label>아이디 입력하세요</label>
				</div>

				<!-- 비번 입력 -->
				<div class="form-floating">
					<input class="form-control" type="password" name="password" maxlength="25" placeholder="Password" autocomplete="off"/>
					<label>비밀번호 입력하세요</label>
				</div>

				<!-- remember -->
				<div class="form_div">
					<input id="rm_ck" name="remember-me" type="checkbox" value="true"/>
					<label for="rm_ck">Remember me</label>
					<input type="hidden" name="remember_me" id="remember_me"/>
				</div>

				<!-- 로그인, 회원가입 버튼 부분-->
				<div class="d-grid gap-2">
					<input class="form-control btn btn-primary bi bi-lock-fill" type="button" name="move" value=" Sign-In" />

					<a href="/members/new" class="btn btn-outline-secondary">SIGN UP</a>
				</div>
			</div>

		</form:form>
		</div>
	</div>
</div>

<script type="text/javascript" src="/js/jquery-2.1.3.min.js"></script>
<script>
	$(function(){
		$('input[name=id]').keydown(function(e) {
			if(e.keyCode==13) {
				$('input[name=move]').click();
			} else{
				return true;
			}
		});

		$('input[name=password]').keydown(function(e){
			if(e.keyCode==13){
				$('input[name=move]').click();
			} else {
				return true;
			}
		});

		$('input[name=move]').click(function(){
			if($('input[name=id]').val()==''){
				alert("ID를 입력해주세요.");
				$('input[name=id]').focus();
			}else if($('input[name=password]').val() == ''){
				alert("비밀번호를 입력해주세요.");
				$('input[name=password]').focus();
			}else{
				var remmCbox = $('#rm_ck');
				var cc = remmCbox[0].checked;
				if(cc) {
					$('#remember_me').val("remember_me");
				} else {
					$('#remember_me').val("forget_me");
				}

				$('form[name=form]').submit();
			}
		});
	});
</script>