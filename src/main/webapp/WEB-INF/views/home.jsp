<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: milvus
  Date: 2023-07-20
  Time: 오후 6:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE HTML>
<title>Hello</title>

</head>
<body>

<div class="row justify-content-center">
    <div class="container col-5" style="text-align:center">
        <h1>HOME</h1>
        <div style="text-align:right">
            <form action="/logout" method="post">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

                <button type="submit" class="btn btn-outline-danger btn-sm">로그아웃</button>
            </form>
        </div>
        <hr/>

        <div class="row">
            <div class="col-sm-6">
                <div class="card" style="height:100%">
                    <div class="card-body">
                        <h5 class="card-title">회원 정보</h5>
                        <a href="/member/info" class="btn btn-secondary">회원 정보</a>
                    </div>
                </div>
            </div>

            <div class="col-sm-6">
                <div class="card" style="height:100%">
                    <div class="card-body">
                        <h5 class="card-title">상점</h5>
                        <a href="/items" class="btn btn-secondary">상점</a>
                    </div>
                </div>
            </div>
        </div>

        <br/>

        <div class="row">
            <div class="col-sm-6">
                <div class="card" style="height:100%">
                    <div class="card-body">
                        <h5 class="card-title">장바구니</h5>
                        <a href="/cartItems" class="btn btn-secondary">장바구니</a>
                    </div>
                </div>
            </div>


            <div class="col-sm-6">
                <div class="card" style="height:100%">
                    <div class="card-body">
                        <h5 class="card-title">주문 리스트</h5>
                        <a href="/orders" class="btn btn-secondary">주문 리스트</a>
                    </div>
                </div>
            </div>
        </div>
        <br/>
        <sec:authorize access="hasRole('ROLE_ADMIN')">
            <div class="row">

                <div class="col-sm-6">
                    <div class="card" style="height:100%">
                        <div class="card-body">
                            <h5 class="card-title">상품 관리</h5>
                            <a href="/items/new" class="btn btn-secondary">상품 관리</a>
                        </div>
                    </div>
                </div>


                <div class="col-sm-6">
                    <div class="card" style="height:100%">

                        <div class="card-body">
                            <h5 class="card-title">회원 목록</h5>
                            <a href="/members" class="btn btn-secondary">회원 목록</a>
                        </div>
                    </div>
                </div>
            </div>
        </sec:authorize>

        <br/>
    </div>

</div> <!-- /container -->
</div>
</body>
</html>


<%-- sec:authentication
<p>principal : <sec:authentication property="principal"/> </p>
<p>MemberVO : <sec:authentication property="principal.member"/></p>
<p>사용자 이름 : <sec:authentication property="principal.member.username"/></p>
<p>사용자 아이디: <sec:authentication property="principal.member.userid"/></p>
<p>사용자 권한 :<sec:authentication property="principal.member.authList"/></p>

일반 사용자로 들어갔을 때 보여지는 내용과 관리자로 들어갔을 때 보여지는 내용이 다르게 나오도록 한다.
--%>


<%-- sec:authorize
sec:authorize = "isAuthenticated()" << 권한이 있는 사용자만 보이는 블럭

sec:authorize= "isAnonymouse()" << 비로그인 사용자만 보이는 블럭

sec:authorize= "hasRole("USER")" << 권한이 USER인 사람만 보이는 블럭

sec:authorize= "hasRole("ADMIN")" << 권한이 ADMIN인 사람만 보이는 블럭
--%>