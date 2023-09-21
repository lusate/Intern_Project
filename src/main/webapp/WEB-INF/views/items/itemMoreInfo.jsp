<%--
  Created by IntelliJ IDEA.
  User: milvus
  Date: 2023-07-20
  Time: 오전 9:03
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link href="/css/star.css" rel="stylesheet"/>

<html>
<head>
    <title>물건 정보</title>
    <!-- default header name is X-CSRF-TOKEN -->


    <meta id="_csrf" name="_csrf" content="${_csrf.token}"/>
    <meta id="_csrf_header" name="_csrf_header" content="${_csrf.headerName}"/>


    <style>
        .ck-editor__editable { height: 380px; }
        .ck-content { font-size: 13px; }
        .ck-editor__editable p {margin: 0}
    </style>
</head>
<div class="container col-5" style="text-align:center">
    <H1>물건 정보</H1>
    <br/>

    <span class="d-block p-2 text-bg-dark fs-2" style="text-align:left"><c:out value="${item.dtype}"/></span>
    <br/>

    <div class="container text-center">
        <form method="post" sec:authorize="hasRole('ROLE_USER')">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <div class="row">
                <div class="col-6" style="display:flex">
                    <img src="/img/<c:out value="${item.saveName}" />" style="width: 100%; height: auto;">
                </div>

                <!-- 정보 -->
                <div class="col-6" style="text-align:left">
                    <b class="fs-5">상품 상세 정보</b>
                    <br/>
                    <br/>
                    <div class="row">
                        <div class="col-6">
                            <p>상품명</p>
                            <p>상품 가격</p>
                            <p>남은 수량</p>

                            <%-- c:if test="${조건식}" --%>
                            <c:if test="${item.dtype == 'BOOK'}">
                                <p>저자</p>
                                <p>ISBN</p>
                            </c:if>

                            <c:if test="${item.dtype == 'CLOTH'}">
                                <p>브랜드</p>
                                <p>사이즈</p>
                                <p>색상</p>
                            </c:if>
                            <p>주문 수량</p>

                        </div>
                        <div class="col-6">
                            <p class="fw-bold"><c:out value="${item.name}"/></p>
                            <p class="fw-bold"><c:out value="${item.price}"/></p>
                            <p class="fw-bold"><c:out value="${item.stockQuantity}"/></p>

                            <c:if test="${item.dtype == 'BOOK'}">
                                <p class="fw-bold"><c:out value="${item.author}"/></p>
                                <p class="fw-bold"><c:out value="${item.isbn}"/></p>
                            </c:if>

                            <c:if test="${item.dtype == 'CLOTH'}">
                                <p class="fw-bold"><c:out value="${item.brand}"/></p>
                                <p class="fw-bold"><c:out value="${item.size}"/></p>
                                <p class="fw-bold"><c:out value="${item.color}"/></p>
                            </c:if>

                            <input type="hidden" name="memberItemId" id="memberItemId" class="form-control"
                                   value="${item.member.id}"/>

                            <input type="hidden" name="itemId" id="itemId" class="form-control"
                                   value="${item.id}"/>


                            <div class="input-group input-group-sm mb-3">
                                <input type="number" name="count" id="count" min="1" class="form-control">
                            </div>
                        </div>
                    </div>

                </div>
            </div>
            <br/>


            <div class="row">
                <div class="col-6 d-grid gap-2">
                    <%--                    <input type="button" id="stockCheck" formaction="/addItem" value="장바구니에 담기"/>--%>
                    <button class="btn btn-outline-secondary" type="button" formaction="/addItem/${item.id}"
                            onclick="stockCheck()">장바구니에 담기
                    </button>
                </div>
            </div>

        </form>

    </div>

</div>


<%-- 리뷰 만들기 --%>
<h3 class="mt-4">상품 리뷰</h3>


<div class="form-group">
    <label> 리뷰 개수 </label>
    <label>
        <input type="text" class="form-control" name="cnt" value="${reviews.size()}" readonly>
    </label>
</div>

<div class="form-group">
    <label> 리뷰 평점 </label>
    <c:forEach var="review" items="${reviews}">
        <c:set var="total" value="${total + review.grade}"/>
    </c:forEach>
    <label>
        <input type="text" class="form-control" name="avg" value=<fmt:formatNumber value="${total / reviews.size()}" pattern=".0" />  readonly />
    </label>
</div>



<button class="btn btn-info addReviewBtn">
    리뷰 등록하기
</button>

<%-- 리뷰 출력 --%>
<div class="list-group reviewList">
    <c:if test="${ fn:length(reviews) gt 0 }">
        <table class="table table-stripped">
            <thead>
            <tr>
                <th>User ID</th>
                <th>Text</th>
                <th>Star Rating</th>
                <th>Created Date</th>
            </tr>
            </thead>



            <tbody>
            <c:forEach var="review" items="${reviews}">
                <tr>
                    <input type="hidden" name="reviewId" class="form-control" value="${review.reviewId}"/>
                    <input type="hidden" name="reviewMemberId" class="form-control"
                           value="${review.memberId}"/>
                        <%-- ${member.loginId}로 하면 지금 로그인한 것으로 출력됨. --%>

                    <td>${review.loginId}</td>
                    <td class="text">${review.text}</td>

                    <c:choose>
                        <c:when test="${review.grade == 1}">
                            <td>★</td>
                        </c:when>
                        <c:when test="${review.grade == 2}">
                            <td>★★</td>
                        </c:when>
                        <c:when test="${review.grade == 3}">
                            <td>★★★</td>
                        </c:when>
                        <c:when test="${review.grade == 4}">
                            <td>★★★★</td>
                        </c:when>
                        <c:when test="${review.grade == 5}">
                            <td>★★★★★</td>
                        </c:when>
                    </c:choose>
                    <td>${review.createdDate}</td>
                    <td></td>

                    <td>
                            <%-- 댓글 버튼 누르면 새로 텍스트 창이 생성 / btn2를 클릭하면 text input이 안 보임--%>
                        <button class="btn btn-info commentBtn"> 댓글 </button>

                            <%-- editReviewBtn이 없기 때문에 reviewId를 console.log 찍어도 값이 계속 undefined 나온 것 --%>
                        <button class="btn btn-warning editReviewBtn"> 수정</button>

                        <button class="btn btn-danger removeBtn"> 삭제</button>
                    </td>
                </tr>

                <tr>
                    <th></th>
                    <th>User ID</th>
                    <th>Text</th>
                    <th>Created Date</th>
                </tr>


                <%--                <c:forEach var="comment" items="${commentList.get(review.reviewId)}">--%>
                <c:forEach var="comment" items="${commentList}">
                    <tr>
                        <c:if test="${comment.review.reviewId eq review.reviewId}">  <%-- 두 테이블에서 reviewId가 같아야 답글을 등록 --%>
                            <td></td>
                            <input type="hidden" name="commentReviewId" class="form-control" value="${comment.review.reviewId}"/>
                            <input type="hidden" name="commentId" class="form-control" value="${comment.commentId}"/>
                            <input type="hidden" name="commentMemberId" class="form-control"
                                   value="${comment.member.id}"/>

                            <%-- 댓글 수정할 때도 text를 가져옴 --%>
                            <td>${comment.member.loginId}</td>
                            <%--                            <td class="comment">${review.text}</td>--%>
                            <td class="commentContent">
                                    ${comment.content}
                            </td>
                            <td>${comment.createdDate}</td>

                            <%-- 수정이랑 삭제 버튼 생성 --%>
                            <td>
                                <button class="btn btn-primary editCommentBtn"> 수정</button>
                                <button class="btn btn-danger removeCommentBtn"> 삭제</button>
                            </td>
                        </c:if>
                    </tr>
                </c:forEach>
            </c:forEach>

            </tbody>
        </table>
    </c:if>
</div>


<%-- 리뷰 작성하는 창 생성. --%>
<div class="reviewModal modal" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">상품 리뷰</h5>
            </div>
            <div class="modal-body">

                <p>이름 : <c:out value="${item.member.loginId}"/></p>

                <%-- 별점 기록 --%>
                <div class="form-group">
                    <%--                    여기--%>

                    <form class="mb-3" name="myform" id="myform" method="post">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <span class="grade">Grade</span>

                        <fieldset>
                            <span class="text-bold">별점을 선택해주세요</span>
                            <input type="radio" name="reviewStar" value="5" id="star1"><label for="star1">★</label>
                            <input type="radio" name="reviewStar" value="4" id="star2"><label for="star2">★</label>
                            <input type="radio" name="reviewStar" value="3" id="star3"><label for="star3">★</label>
                            <input type="radio" name="reviewStar" value="2" id="star4"><label for="star4">★</label>
                            <input type="radio" name="reviewStar" value="1" id="star5" checked="checked"><label for="star5">★</label>
                        </fieldset>

                    </form>
                </div>


                <div class="form-group">
                    <label>리뷰 작성하기</label>
                    <%--                    <textarea class="comment_area" style="width:450px;height:200px;font-size:20px;" name="text" placeholder="리뷰 작성"></textarea>--%>
                    <input type="text" class="form-control" name="text" id="text" style="width:400px;height:200px;font-size:20px;"
                           placeholder="리뷰 작성">
                </div>
            </div>
            <div class="modal-footer">
                <%--            reviewSaveBtn이 있는 곳으로 위치 선정--%>
                <input type="hidden" name="memberId" id="memberId" class="form-control"
                       value="${member.id}"/>
                <%--                창 닫기 저장이니 기록한 텍스트가 저장이 되야 함--%>
                <%--                <button type="button" class="close" data-dismiss="modal" aria-label="Close"> 창 닫기 </button>--%>
                <button class="btn btn-primary reviewSaveBtn"> 저장 </button>
                <button type="button" class="btn btn-secondary" data-dismiss="modal"> 저장 </button>
                <button type="button" class="close" data-dismiss="modal"> 창 닫기 </button>

            </div>
        </div>
    </div>
</div>




<%--댓글을 위한 모달 창 하나 더 생성 / 위 모달과 구분 짓기--%>
<div class="commentModal modal" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">댓글 작성</h5>
            </div>
            <div class="modal-body">

                <p>이름 : <c:out value="${item.member.loginId}"/></p>



                <div class="form-group">
                    <label>댓글 작성하기</label>
                    <textarea class="comment_area" style="width:450px;height:200px;font-size:20px;" id="commentText" placeholder="댓글 작성"></textarea>
                    <%--                    <input type="text" class="form-control" name="commentText" id="commentText" style="width:400px;height:200px;font-size:20px;"--%>
                    <%--                           placeholder="댓글 작성">--%>
                </div>
            </div>
            <div class="modal-footer">
                <button class="btn btn-primary commentSaveBtn" data-dismiss="modal"> 저장 </button>

                <%--                <button class="btn btn-secondary" data-dismiss="modal"> 저장 </button>--%>
                <button class="btn btn-outline-danger" data-dismiss="modal"> 댓글 수정 </button>
                <button class="close" data-dismiss="modal"> 창 닫기 </button>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript" src="/js/ckeditor5/build/ckeditor.js"></script>
<script type="text/javascript" src="/js/ckeditor5/build/uploadAdapter.js"></script>

<%--
<script>
ckeditor4일 때 CKEDITOR 사용하는데 난 안됨
    $(function () {
        CKEDITOR.replace('contents', {
            filebrowserUploadUrl : '${pageContext.request.contextPath}/item/info/${item.id}/all/' + reviewId + '/add'
        });
    });
</script>
--%>


<script type="text/javascript">
    // csrf 토큰
    let token = $("meta[name='_csrf']").attr("content");
    let header = $("meta[name='_csrf_header']").attr("content");
    $(function() {
        $(document).ajaxSend(function(e, xhr) {
            xhr.setRequestHeader(header,token);
        });
    });



    function stockCheck() {
        let memberItemId = $('#memberItemId').val();
        let itemId = $('#itemId').val();
        let count = $('#count').val();


        // 데이터 값 바꿔야 함. 값이 이 상태에서 되도록
        let params = {"memberId": memberItemId, "itemId": itemId, "count": count}

        if (memberItemId != null && itemId != null && count != null && memberItemId !== "" && itemId !== "" && count !== "") {
            $.ajax({
                async: true,
                type: 'POST',
                data: JSON.stringify(params),
                url: "/addItem/" + itemId,
                dataType: "JSON", // 응답받을 데이터 타입
                contentType: "application/json; charset=UTF-8",
                success: function (result) {
                    // result는 현재 [object Object]

                    let num = result[Object.keys(result)[3]];

                    if (num === 1) { // 0이면 불가능
                        alert("장바구니로 이동하였습니다");
                        // location.href

                    } else {
                        alert("재고가 부족합니다.");
                    }
                },

                error: function () {
                    alert("에러")
                }
            });
        } else {
            alert("값을 입력해주세요");
        }
    }



    $(function () {

        let reviewModal = $(".reviewModal");
        let commentModal = $(".commentModal");

        let memberId = $('#memberId').val();
        let grade = $('[name=reviewStar]:checked').val(); // 입력한 리뷰 별점

        let inputText = $('input[name="text"]'); // 입력한 리뷰 텍스트
        let reviewMemberId = 0;
        // let commentText = $('input[name="commentText"]'); // 입력한 댓글 텍스트
        let commentText = "";


        let itemId = $('#itemId').val();

        // 인덱스 0, 1, 2
        let reviewId = 0;
        let commentId = 0;
        let commentReviewId = 0;
        let commentMemberId = 0;




        // class가 addReviewBtn인 태그 (현재 button) 클릭하면 창이 생성
        $(".addReviewBtn").click(function () {

            $(".reviewSaveBtn").show(); // 리뷰 저장은 수정할 때도 보여줘야 함.


            $(".btn-secondary").hide();
            reviewModal.modal('show'); // 창 생성
            // console.log($(this).parent().parent().find("input[name='reviewId']").val());
        });


        // 리뷰 등록하기 위함
        $('.reviewSaveBtn').click(function () {
            grade = $('[name=reviewStar]:checked').val()


            let data = {itemId: itemId, grade: grade, text: inputText.val(), memberId: memberId};

            if ($('input[name=text]').val() !== '') {
                $.ajax({
                    url: "/item/info/${item.id}",
                    type: "POST",
                    data: JSON.stringify(data),
                    contentType: "application/json; charset=utf-8",
                    dataType: "text",

                    success: function (result) {
                        console.log("result: " + result);
                        self.location.reload();

                    }
                })
            }
            else alert("리뷰를 입력해주세요.");
            reviewModal.modal('hide');
        });


        // editReviewBtn은 수정 / 모달에서 버튼 1개로 / 일단 보류
        $(".editReviewBtn").on("click", function () {
            // editReviewBtn이 c:foreach 안에 있기 때문에 값들을 editReviewSaveBtn이 아닌 여기서 지정해줌.
            // $(this)는 class가 editReviewBtn인 태그
            reviewId = $(this).parent().parent().find("input[name='reviewId']").val();
            reviewMemberId = $(this).parent().parent().find("input[name='reviewMemberId']").val();



            // 내용 담기
            // 입력했던 내용 텍스트에 넣기
            $("#text").val($(this).parent().parent().find('.text').text())


            if (memberId === reviewMemberId) {
                $('.reviewModal').modal('show');
            }
            else {
                alert("타인의 리뷰는 수정이 불가능합니다")
                $('.reviewModal').modal('hide');
            }
            $(".reviewSaveBtn").hide();
            // $('.reviewModal').modal('show');


            // $('.reviewModal').modal('show');
            // 여기서 this는 현재 정해놓은 버튼이 c:foreach 안에 있어야 작동 가능
            // $(this).parent() 는 <td></td> 를 의미
            // $(this).parent().parent() 는 <tr></tr> 를 의미

        });


        // 수정한 것 저장
        $(".btn-secondary").on("click", function () {
            grade = $('[name=reviewStar]:checked').val();
            let data = {reviewId: reviewId, itemId: itemId, grade: grade, text: inputText.val(), memberId: memberId};

            if ($('input[name=text]').val() !== '') { // 리뷰를 입력해야 함
                $.ajax({
                    async: true,
                    url: '/item/info/${item.id}/' + reviewId + '/edit',
                    type: "PUT",
                    data: JSON.stringify(data),
                    contentType: "application/json; charset=utf-8",
                    dataType: "text",
                    success: function (result) {

                        // 현재 페이지 바로 새로고침
                        self.location.reload();

                    }
                })
            }
            else alert("리뷰를 입력해주세요.");


            $('.reviewModal').modal('hide');
        });


        // 리뷰 삭제
        $(".removeBtn").on("click", function () {
            reviewId = $(this).parent().parent().find("input[name='reviewId']").val();
            reviewMemberId = $(this).parent().parent().find("input[name='reviewMemberId']").val();


            if (memberId === reviewMemberId) {
                $.ajax({
                    url: '/item/info/' + itemId + "/" + reviewId + "/remove",
                    type: "DELETE",
                    contentType: "application/json; charset=utf-8",
                    dataType: "text",
                    success: function (result) {

                        console.log("result: " + result);

                        self.location.reload();

                    }
                })
            }
            else alert("타인의 리뷰는 삭제가 불가능합니다");


            reviewModal.modal('hide');
        });


        // 창 닫기
        $(".close").on("click", function () {
            reviewModal.modal('hide');
            commentModal.modal('hide');
        });


        // 답글 시작
        $(".commentBtn").on("click", function () {
            reviewId = $(this).parent().parent().find("input[name='reviewId']").val();

            $(".commentSaveBtn").show();
            $(".btn-outline-danger").hide();
            commentModal.modal('show'); // 창 생성
        });



        let com;
        com = ClassicEditor.create(document.querySelector('#commentText'), {
            language:'ko',
            extraPlugins: [MyCustomUploadAdapterPlugin]
        }).then(commentText => {
            com = commentText;
        }).catch( error => {
            console.error( error );
        });



        // 답글 등록하기 위함
        $('.commentSaveBtn').click(function () {
            let tmp = com.getData();
            // alert(tmp);

            let data = {reviewId: reviewId, content: tmp, memberId: memberId};

            if(tmp !== ''){
                $.ajax({
                    url: "/item/info/${item.id}/all/" + reviewId + "/add",
                    type: "POST",
                    data: JSON.stringify(data),
                    contentType: "application/json; charset=utf-8",
                    dataType: "text",
                    success: function (result) {
                        self.location.reload();

                    }
                })
            }
            else alert("댓글을 입력해주세요.");


            $('.commentModal').modal('hide');
        });



        // 답글 수정
        $(".editCommentBtn").on("click", function () {
            commentId = $(this).parent().parent().find("input[name='commentId']").val();
            // let commentText = $('input[name="commentText"]'); // 입력한 댓글 텍스트

            commentReviewId = $(this).parent().parent().find("input[name='commentReviewId']").val();
            commentMemberId = $(this).parent().parent().find("input[name='commentMemberId']").val();


            // 내용 담기
            // $("#commentText").val($(this).parent().parent().find("input[name='commentContent']").val());


            // $.trim()으로 문자열 양쪽의 공백을 제거해줌
            $("#commentText").val($.trim($(this).parent().parent().find('.commentContent').text()))

            if (memberId === commentMemberId) {
                $('.commentModal').modal('show');
            }
            else {
                alert("타인의 댓글은 수정이 불가능합니다")
                $('.commentModal').modal('hide');
            }

            $(".commentSaveBtn").hide();
            $(".btn-secondary").show();
            $(".btn-outline-danger").show();
            // 여기서 this는 현재 정해놓은 버튼이 c:foreach 안에 있어야 작동 가능
            // $(this).parent() 는 <td></td> 를 의미
            // $(this).parent().parent() 는 <tr></tr> 를 의미

        });



        // 답글 수정한 것 저장
        $(".btn-outline-danger").on("click", function () {
            let tmp = com.getData();
            let data = {commentId: commentId, reviewId: commentReviewId, content: tmp, memberId: memberId};

            if (tmp !== '') {
                $.ajax({
                    async: true,
                    url: '/item/info/${item.id}/all/' + commentReviewId + '/' + commentId + '/edit',
                    type: "PUT",
                    data: JSON.stringify(data),
                    contentType: "application/json; charset=utf-8",
                    dataType: "text",
                    success: function () {

                        // 현재 페이지 바로 새로고침
                        self.location.reload();

                    }
                })
            } else alert("댓글을 입력해주세요.");


            $('.commentModal').modal('hide');
            // 여기서 this는 현재 정해놓은 버튼이 c:foreach 안에 있어야 작동 가능
            // $(this).parent() 는 <td></td> 를 의미
            // $(this).parent().parent() 는 <tr></tr> 를 의미

        });




        $(".removeCommentBtn").on("click", function () {
            commentReviewId = $(this).parent().parent().find("input[name='commentReviewId']").val();
            commentId = $(this).parent().parent().find("input[name='commentId']").val();
            commentMemberId = $(this).parent().parent().find("input[name='commentMemberId']").val();


            let data = {commentId: commentId, reviewId: commentReviewId, content: commentText, memberId: memberId};

            if (memberId === commentMemberId) {
                $.ajax({
                    async: true,
                    url: '/item/info/${item.id}/all/' + commentReviewId + '/' + commentId + '/remove',
                    type: "DELETE",
                    data: JSON.stringify(data),
                    contentType: "application/json; charset=utf-8",
                    dataType: "text",
                    success: function (result) {

                        // 현재 페이지 바로 새로고침
                        self.location.reload();

                    }
                })
            }
            else alert("타인의 글을 삭제가 불가능합니다");

            $('.reviewModal').modal('hide');
        });
    });


    function MyCustomUploadAdapterPlugin(editor) {

        editor.plugins.get('FileRepository').createUploadAdapter = (loader) => {
            return new UploadAdapter(loader)
        }
    }
</script>
</body>
</html>


<%--
var는 변수를 한 번 더 선언했음에도 불구하고, 에러가 나오지 않고 각기 다른 값이 출력됨.

let 은 변수에 다른 값을 재할당할 수 있음. 변수 재할당 불가
let과 const는 immutable 여부의 차이가 있음.
let은 변수에 재할당이 가능
ex)
let name = 'abc'
let name = 'def' -> 에러
name = 'def' -> 재할당 가능

const 는 재할당 시 에러 메시지가 출력됨. 변수 재선언도 불가
ex)
let name = 'abc'
let name = 'def' -> 에러
name = 'def' -> 재할당 불가
--%>

<%--
formaction
폼 데이터(form data)가 서버로 제출될 때 입력 데이터를 처리할 파일의 URL을 명시
type 속성값이 submit 또는 image의 경우에만 사용 가능



// click과 on 차이
동적으로 생성된 태그에 클릭을 가능하게 이벤트를 바인딩 해준다
최초에 선언된 태그에만 동작한다. 동적으로 생성된 태그에는 안 먹힘
--%>




