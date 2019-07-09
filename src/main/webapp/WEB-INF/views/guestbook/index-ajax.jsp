<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link rel="stylesheet"
	href="${pageContext.request.contextPath }/assets/css/guestbook.css"
	rel="stylesheet" type="text/css">
<link rel="stylesheet"
	href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath }/assets/js/jquery/jquery-1.9.0.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<style type="text/css">

*{ margin: 0; padding: 0;}

#dialog-delete-form{
	position: fixed;
    margin-top: -30%;
    margin-left: 5%;
    width: 300px;
    height: 85px;
    border: 2px ridge #515151;
    border-radius: 5px;
    text-align: center;
    padding: 1%;
    background-color: white;
}

#delete-opr-btn{
	width:15%;
	height:25px;
	border-radius: 10%;
	margin-left:65%;
	border: none;
	background-color: #f8585b;
    color:#fff;
    text-align: center;
    text-decoration: none;
    float: left;
    cursor: pointer;
    

}
#delete-cancel-btn{
	width:15%;
	height:25px;
	border-radius: 10%;
	margin-left: 2%;
	border: none;
	background-color: #f8585b;
	color:#fff;
	text-align: center;
	text-decoration: none;
	display:block;
	float: left;
	cursor: pointer;
	
}

#password-delete{
	margin: 5%;
    text-align: center border: 1px solid #D0DFEF;
    width: 70%;
    height: 20px;
    font-size: 18px;
}

</style>


<script type="text/javascript">
	
	function showdeleteForm(btn) {
		$('#dialog-delete-form').show();
	};
	
	function cancelDelete() {
		$('#dialog-delete-form').hide();
	}
	
	function deleteGuestbook(form) {
		alert(form);
	}
</script>
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="guestbook">
				<h1>방명록</h1>
				<form id="add-form"
					action="${pageContext.request.contextPath}/guestbook/create"
					method="post">
					<input type="text" id="input-name" name="name" placeholder="이름">
					<input type="password" id="input-password" name="password"
						placeholder="비밀번호">
					<textarea id="tx-content" name="contents"
						placeholder="내용을 입력해 주세요."></textarea>
					<input type="submit" value="보내기" />
				</form>

				<ul id="list-guestbook">

					<li data-no=''><strong>지나가다가</strong>
						<p>
							별루입니다.<br> 비번:1234 -,.-
						</p> <strong></strong> <a class="delete-btn" id="123"
						onclick="javascript:showdeleteForm(this);">삭제</a></li>
					
					<li data-no=''><strong>둘리</strong>
						<p>
							안녕하세요<br> 홈페이지가 개 굿 입니다.
						</p> <strong></strong> <a href='' data-no=''>삭제</a></li>

					<li data-no=''><strong>주인</strong>
						<p>
							아작스 방명록 입니다.<br> 테스트~
						</p> <strong></strong> <a href='' data-no=''>삭제</a></li>
				</ul>
			</div>
			<div id="dialog-delete-form" title="메세지 삭제" style="display: none;">
				<p class="validateTips normal">작성시 입력했던 비밀번호를 입력하세요.</p>
				<p class="validateTips error" style="display: none;">비밀번호가 틀립니다.</p>
				<form action="javascript:deleteGuestbook(this);">
					<input type="password" id="password-delete" name='password' value=""
						class="text ui-widget-content ui-corner-all"> 
						<input type="hidden" id="hidden-no" name='guestbookId' value=""> 
						<input type="submit" id="delete-opr-btn" tabindex="-1" value="삭제" >
						<input type="button" id="delete-cancel-btn" onclick="javascript:cancelDelete();" value="취소">
						
				</form>
			</div>
			<div id="dialog-message" title="" style="display: none;">
				<p></p>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="guestbook-ajax" />
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>