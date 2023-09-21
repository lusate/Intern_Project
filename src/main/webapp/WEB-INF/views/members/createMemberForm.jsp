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
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>회원 가입</title>

    <meta id="_csrf" name="_csrf" content="${_csrf.token}"/>
    <!-- default header name is X-CSRF-TOKEN -->
    <meta id="_csrf_header" name="_csrf_header" content="${_csrf.headerName}"/>



</head>

<body>
<div class="row justify-content-center">
    <div class="container col-5" style="text-align:center">
        <h1>회원 가입</h1>

        <br/>


        <form:form name="frm" action="/members/new" method="post">

            <div class="form-floating mb-3">
                <input type="text" name="loginId" id="loginId" class="form-control">
                <label>로그인 ID</label>

            </div>

            <div class="form-floating mb-3">
                <%-- button 태그는 기본적으로 type이 submit이기 때문에 type을 button으로 설정해주어야 onclick이 제대로 작동함 --%>
                <button class="btn btn-outline-secondary" type="button" onclick="idCheck()"> 아이디 중복 확인 </button>
            </div>

            <div class="form-floating mb-3">
                <input type="password" name="password" id="password" class="form-control">
                <label>비밀번호</label>
            </div>


            <div class="form-floating mb-3">
                <input type="password" name="passwordConfirm" id="passwordConfirm" class="form-control">

                <label>비밀번호 확인</label>
            </div>


            <div class="form-floating mb-3">
                <input type="text" name="username" id="username" class="form-control">

                <label>이름</label>
            </div>


            <!-- 권한 넣어주기 -->
<%--            <input type="radio" name="role" value="ROLE_ADMIN, ROLE_USER"> 관리자--%>
            <input type="radio" name="role" value="ROLE_USER" checked="checked"> 사용자

            <br>

            <div class="form-floating mb-3">
                <input type="text" name="city" class="form-control">
                <label>도시</label>
            </div>

            <div class="form-floating mb-3">
                <input type="text" name="street" class="form-control">
                <label>도로명</label>
            </div>

            <div class="form-floating mb-3">
                <input type="text" name="detail" class="form-control">
                <label>상세 정보</label>
            </div>


            <div class="d-grid gap-2">
                <input type="button" class="btn btn-outline-secondary" name="registerCheck" value="회원가입" />
            </div>

        </form:form>
    </div>
</div>
</body>


<script type="text/javascript">
    // csrf 토큰
    let token = $("meta[name='_csrf']").attr("content");
    let header = $("meta[name='_csrf_header']").attr("content");
    $(function() {
        $(document).ajaxSend(function(e, xhr) {
            xhr.setRequestHeader(header, token);
        });
    });


    function idCheck(){
        // val() : 입력받은 value값을 가져오거나 원하는 value값으로 set이 가능하다.
        let loginId = $('#loginId').val();


        let params = {"loginId" : loginId}

        // alert(params);

        $.ajax({
            async: true,
            type: 'post',
            data: JSON.stringify(params),
            url: "/duplicate",
            dataType: "JSON", // 응답받을 데이터 타입
            contentType: "application/json; charset=UTF-8",
            success: function (result) {
                // result는 현재 [object Object]

                let num = result[Object.keys(result)[1]];
                // ([-_\.]?[0-9a-zA-Z])*`: `*` 메타 문자를 사용하여 앞에 있는 표현식이 0번 이상 반복될 수 있다고 지정
                // `@[0-9a-zA-Z]`: 이메일 주소의 `@` 기호와 그 뒤에 사용될 수 있는 문자열을 정의
                let email_format = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;

                // alert("성공 여부" + result);
                if (num !== 0) {
                    alert("이미 존재하는 아이디 입니다.");


                } else {
                    if ($('input[name=loginId]').val() == '') {
                        alert("ID를 입력해야 합니다.");
                    }

                    else if (!email_format.test($('input[name=loginId]').val())) {
                        alert("잘못된 이메일 형식입니다.");
                    }
                    else {
                        alert("가입할 수 있는 아이디입니다.");
                    }
                }
            },

            error: function () {
                alert("접근할 수 없습니다. 돌아가주세요.");
            }
        });
    };


    $(function () {
        $('input[name=registerCheck]').click(function () {

            if ($('input[name=loginId]').val() == '') {
                alert("ID를 입력해주세요.");
                $('input[name=loginId]').focus();
                // focus() : 입력창에 입력을 할 수 있도록 커서를 이동해주는 편리함을 주는 기능
            }
            else if ($('input[name=password]').val() == '') {
                alert("비밀번호를 입력해주세요.");
                $('input[name=password]').focus();
            }
            else if ($('input[name=passwordConfirm]').val() == '') {
                alert("비밀번호 확인을 위해 한 번 더 입력해주세요");
                $('input[name=passwordConfirm]').focus();
            }
            else if($('input[name=password]').val() !== $('input[name=passwordConfirm]').val()){
                alert("비밀번호가 맞지 않습니다.");
                $('input[name=passwordConfirm]').focus();
            }
            else if ($('input[name=username]').val() == '') {
                alert("이름을 입력해주세요");
                $('input[name=username]').focus();
            } else if ($('input[name=city]').val() == '') {
                alert("도시명을 입력해주세요");
                $('input[name=city]').focus();
            } else if ($('input[name=street]').val() == '') {
                alert("거리명을 입력해주세요");
                $('input[name=street]').focus();
            } else if ($('input[name=detail]').val() == '') {
                alert("상세 주소를 입력해주세요");
                $('input[name=detail]').focus();
            }
            else{
                $('form[name=frm]').submit();
                // idCheck();
            } // else 입력값 다 넣었을 때

        });

    });


</script>


</html>





<%--
function getInputValue(){
    var valueById = $('#inputId').val();
    var valueByClass = $('.inputClass').val();
    var valueByName = $('input[name=inputName]').val();
}

valueById : id 값을 기준으로 가져오기.  #은 아이디를 의미
valueByClass : class 값을 기준으로 가져오기
valueByName : name 값을 기준으로 가져오기



동기식일때 async를 해두면 전체적으로 데이터를 처리하고나서 success를 타고 데이터가 넘어감.

비동기식일때는 async false로 두면 전체적으로 데이터를 전부 다 체크하고 success까지 체크한 다음에
로직으로 넘어감.


css -> # . 차이
class 이름을 선택자로 지정하려면 .을 사용. id를 선택자로 지정시에는 #
--%>