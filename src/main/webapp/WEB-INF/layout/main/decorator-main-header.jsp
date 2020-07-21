<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<c:set var="url">${pageContext.request.requestURL}</c:set>
<c:set var="baseURL" value="${fn:replace(url, pageContext.request.requestURI, pageContext.request.contextPath)}" />
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
		<title>miCRM::<sitemesh:write property='title' /></title>
		<link rel="stylesheet" href="/css/micrm-font-ui.css?v=0.01">
		<link rel="stylesheet" href="/css/micrm-common-style.css?v=0.01">
		<link rel="stylesheet" href="/css/micrm-custom-dialog.css?v=0.01">
		<link rel="stylesheet" href="/css/micrm-custom-select.css?v=0.01">
		<link rel="stylesheet" href="/css/jquery-ui.css?v=0.01">
		<script type="text/javascript" src="/js/jquery-2.1.3.min.js"></script>
		<script type="text/javascript" src="/js/customDialog.js"></script>
		<script type="text/javascript" src="/js/customSelect.js"></script>
		<script type="text/javascript" src="/js/common.js"></script>
		<script type="text/javascript" src="/js/commonAjax.js"></script>
		<script type="text/javascript" src="/js/jquery.validate.min.js"></script>
		<script type="text/javascript" src="/js/additional-methods.min.js"></script>
		<script type="text/javascript" src="/js/html5shiv.js"></script>
		<script type="text/javascript" src="/js/jquery-ui.js"></script>
		<sitemesh:write property='head' />
		<script type="text/javascript">
			var rep;
			
			$(document).ready(function(){
				var url = document.URL;
				$('nav').children('a:last-child').attr('href', url);
				$('aside.main-menu ul.menu-list li.menu').each(function(){
					var id = $(this).attr('id');
					if (url.indexOf(id) > 0){
						$(this).addClass('now');
					}
				})
				var usrAgent = navigator.userAgent;
				$('html').attr('user-agent', usrAgent.toLowerCase());
				
				rep = function(response){
					var newsList = '';
					if (response.notice_list.length > 0){
						for (var i = 0; i < response.notice_list.length; i ++ ){
							newsList += '<li class="note-list">'
								+ '<p class="top">' + response.notice_list[i].list_title + '</p>'
								+ '<p class="row">' + response.notice_list[i].reg_dt + '</p>'
								+ '<button type=\"button\" class=\"notice-check\" onclick=\"popup(\'/pop/newsDetail.mi?n=' + response.notice_list[i].list_seq + '\', \'news\', \'800\', \'600\');\">+</button>'
								+ '</li>';
						}
						$('#newsList').html(newsList);
					} 
					if (response.recent_list.length > 0){
						var recentList = '';
						for (var i = 0; i < response.recent_list.length; i ++ ){
							var key = response.recent_list[i].TY;
							var href;
							switch (key) {
								case 'email':
									href = '${baseURL }/m02/edmDet.mi?cid=' + response.recent_list[i].ID;
									recentList += '<li class="quick-list">'
												+ '<a href="' + href + '" class="title EM">Recent Email</a>'
												+ '</li>';
									break;
								case 'mobile':
									href = '${baseURL }/m02/smsDet.mi?cid=' + response.recent_list[i].ID;
									recentList += '<li class="quick-list">'
												+ '<a href="' + href + '" class="title MO">Recent Mobile</a>'
												+ '</li>';
									break;
								case 'kakao':
									href = '${baseURL }/m02/kakaoDet.mi?cid=' + response.recent_list[i].ID;
									recentList += '<li class="quick-list">'
												+ '<a href="' + href + '" class="title KK">Recent Kakao</a>'
												+ '</li>';
									break;
								case 'sendtarget':
									href = '${baseURL }/m01/sendGroupDet.mi?id=' + response.recent_list[i].ID;
									recentList += '<li class="quick-list">'
												+ '<a href="' + href + '" class="title SD">Recent Send Group</a>'
												+ '</li>';
									break;
								case 'sending':
									href = '${baseURL }/m03/sendHistoryDet.mi?program_id=' + response.recent_list[i].ID;
									recentList += '<li class="quick-list">'
												+ '<a href="' + href + '" class="title CP">Recent Campaign</a>'
												+ '</li>';
									break;
								case 'report':
									href = '${baseURL }/m04/campReportDet.mi?pid=' + response.recent_list[i].ID + "&type=single";
									recentList += '<li class="quick-list">'
												+ '<a href="' + href + '" class="title RP">Recent Report</a>'
												+ '</li>';
									break;
								}
						}
						$('#quickCover').html(recentList);
					}
					if(response.work_list.length > 0){
						var temp_html = "";
						for(var j = 0; j < response.work_list.length; j++){
							var dd, time;
							if(response.work_list[j].work_stat == '1'){
								dd = "이 진행 중입니다.";
								time = response.work_list[j].work_st_dt;
							}else if(response.work_list[j].work_stat == '2'){
								dd = "이 완료 되었습니다.";
								time = response.work_list[j].work_end_dt;
							}else{
								dd = "이 에러가 발생 하였습니다.";
								time = response.work_list[j].work_end_dt;
							}
							
							temp_html += '<li class="note-list">'
								+ '<p class="top"><a href="${baseURL }/m05/workDet.mi?id='+ response.work_list[j].seq +'">'+ response.work_list[j].work_type +' '+ dd +'</a></p>'
								+ '<p class="row">'+ time +'</p>'
								+ '<button class="notice-delete" type="button" onclick="notidel(\''+ response.work_list[j].seq +'\')"></button>'
								+ '</li>';
						}
						$("#note-list-cover").html(temp_html);
						$("#notice-box").html("<span class='notice-count'>"+ response.work_cnt +"</span>");
					}else{
						$("#note-list-cover").html("");
						$("#notice-box").html("");
					}
				}
				com_ajax.ajax('post', '${baseURL}/common/sideMenu.ajax', null, rep);
			});
			function logout(){
				$('#logoutForm').submit();
			}
			function notidel(n){
				var param = {sid : n};
				com_ajax.ajax('post', '${baseURL}/common/noti_del.ajax', param, function(){});
				com_ajax.ajax('post', '${baseURL}/common/sideMenu.ajax', null, rep);
			}
		</script>
		<style>html,body,header{min-width: 1680px;}</style>
	</head>
	<body class="colored">
		<input type="hidden" name="_csrfparameterName" value="${_csrf.parameterName}">
		<input type="hidden" name="_csrfHeaderName" value="${_csrf.headerName }">
		<input type="hidden" name="_csrfToken" value="${_csrf.token }">
		<header>
			<h1 onclick="location.href='/common/home.mi';">miCRM</h1>
			<div class="function-box">
				<sitemesh:write property='page.nav' />
				<div class="user-info">					
					<span class="user-account"><a href="${baseURL }/m05/mypage.mi">${sessionScope.SE_USR_NAME}(${sessionScope.SE_USR_EMAIL})</a></span>
					<span class="user-group">${sessionScope.SE_USR_GRP }</span>
					<input type="button" class="logout-button" value="Logout" onclick="customDialog.confirm('시스템에서 로그아웃 됩니다. 계속 하시겠습니까?', '사용자 로그아웃', 'logout();')"/>
					<form:form action="${baseURL }/logout.service" method="post" id="logoutForm"></form:form>
				</div>
			</div>
			<div class="notice-box" id="notice-box" onclick="$('.note-pannel').addClass('on');">
				
			</div>
		</header>
		<aside class="main-menu">
			<ul class="menu-list">
				<li id="common" class="menu">
					<a href="${baseURL }/common/home.mi">HOME</a>
				</li>
				<li id="m01" class="menu">
					<a href="${baseURL }/m01/contacts.mi">Address</a>
				</li>
				<li id="m02" class="menu">
					<a href="${baseURL }/m02/edmLst.mi">Contents</a>
				</li>
				<li id="m03" class="menu">
					<a href="${baseURL }/m03/sendType.mi">Sending</a>
				</li>
				<li id="m04" class="menu">
					<a href="${baseURL }/m04/campReportLst.mi">Report</a>
				</li>
				<li id="m05" class="menu">
					<a href="${baseURL }/m05/workLst.mi">Settings</a>
				</li>
			</ul>
		</aside>
		<aside class="note-pannel">
			<div class="note-back"></div>
			<div class="note-title">
				<span class="title-name">Notice</span>
				<button class="notice-close" onclick="$(this).parent().parent().removeClass('on');"></button>
			</div>
			<div class="note-body">
				<div class="note-area">
					<div class="note-grp-name">
						<span class="title-name">System Messages</span>
						<button type="button" class="more-view-btn" onclick="location.href = '${baseURL}/m05/workHst.mi'" type="button">더보기</button>
					</div>
					<ul class="note-list-cover" id="note-list-cover"></ul>
				</div>
				<div class="note-area">
					<div class="note-grp-name">
						<span class="title-name">Notice</span>
						<button type="button" class="more-view-btn" onclick="popup('/pop/news.mi', 'news', '800', '600');">더보기</button>
					</div>
					<ul class="note-list-cover" id="newsList"></ul>
				</div>
				
			</div>
			<div class="note-bottom">
				<button class="big-title-btn" onclick="popup('/pop/mailtoSupport.mi', 'news', '1024', '768');" type="">support@micrm.co.kr</button>
				<div class="note-grp-name">
					<span class="title-name">Quick Menu</span>
				</div>
				<ul class="note-quick-cover" id="quickCover"></ul>
			</div>
		</aside>
		<sitemesh:write property='body' />
		<div id="loadingCover" class="layer-lv3">
			<div class="spinner">
				<i></i><i></i><i></i><i></i><i></i><i></i><i></i><i></i><i></i><i></i><i></i><i></i>
			</div>
			<div class="loading">NOW LOADING...</div>
		</div>
	</body>
	<sitemesh:write property='page.script' />
</html>