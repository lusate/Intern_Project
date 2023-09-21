<%--
  Created by IntelliJ IDEA.
  User: milvus
  Date: 2023-07-20
  Time: 오전 9:02
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title>나의 개인정보</title>
</head>
<body>
<div class="row justify-content-center">
    <div class="container col-5" style="text-align:center">
        <h1>나의 개인정보</h1>
        <br/>

        <hr/>


        <form:form action="/member/edit" name="frm" method="post">

            <div class="form-floating mb-3">
                <p>회원 아이디 : <c:out value="${memberForm.loginId}"/> </p>
            </div>


            <div class="form-floating mb-3">
                <input type="password" name="password" class="form-control">

                <label>비밀번호</label>
            </div>

<%--         비번 확인   --%>
            <div class="form-floating mb-3">
                <input type="password" name="passwordConfirm" class="form-control">

                <label>비밀번호 확인</label>
            </div>

            <div class="form-floating mb-3">
                <input type="text" name="username" class="form-control" value="${memberForm.username}">

                <label>Name</label>
            </div>

            <div class="form-floating mb-3">
                <input type="text" name="city" class="form-control" value="${memberForm.city}">
                <label>City</label>
            </div>

            <div class="form-floating mb-3">
                <input type="text" name="street" class="form-control" value="${memberForm.street}">
                <label>Street</label>
            </div>

            <div class="form-floating mb-3">
                <input type="text" name="detail" class="form-control" value="${memberForm.detail}">
                <label>상세 주소</label>
            </div>

            <div class="d-grid gap-2">
                <input type="button" class="btn btn-outline-secondary" name="checkForm" value="회원 수정" />
            </div>
        </form:form>

    </div> <!-- /container -->
</div>
</body>

<script type="text/javascript">
    let token = $("meta[name='_csrf']").attr("content");
    let header = $("meta[name='_csrf_header']").attr("content");
    $(function() {
        $(document).ajaxSend(function(e, xhr) {
            xhr.setRequestHeader(header, token);
        });
    });

    $(function (){
        $('input[name=checkForm]').click(function(){
            if ($('input[name=loginId]').val() == '') {
                alert("ID를 입력해주세요.");
                $('input[name=loginId]').focus();
                // focus() : 입력창에 입력을 할 수 있도록 커서를 이동해주는 편리함을 주는 기능
            }

            else if ($('input[name=password]').val() == '') {
                alert("비밀번호를 입력해주세요.");
                $('input[name=password]').focus();
            }

            else if($('input[name=passwordConfirm]').val() == ''){
                alert("비밀번호 확인을 위해 한 번 더 입력해주세요");
                $('input[name=passwordConfirm]').focus();
            }

            else if($('input[name=password]').val() !== $('input[name=passwordConfirm]').val()){
                alert("비밀번호가 맞지 않습니다.");
                $('input[name=passwordConfirm]').focus();
            }

            else if($('input[name=username]').val() == ''){
                alert("이름을 입력해주세요");
                $('input[name=username]').focus();
            }

            else if($('input[name=city]').val() == ''){
                alert("도시명을 입력해주세요");
                $('input[name=city]').focus();
            }

            else if($('input[name=street]').val() == ''){
                alert("거리명을 입력해주세요");
                $('input[name=street]').focus();
            }

            else if($('input[name=detail]').val() == ''){
                alert("상세 주소를 입력해주세요");
                $('input[name=detail]').focus();
            }
            else{
                // 여기서 submit을 안 해줘서 위에 <button>을 해줘도 아무 반응이 없었던 것
                alert("수정되었습니다");
                $('form[name=frm]').submit();
            }
        });
    });

</script>
</html>
