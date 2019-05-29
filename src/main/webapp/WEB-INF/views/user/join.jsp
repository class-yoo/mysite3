<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.servletContext.contextPath}/assets/css/user.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${pageContext.servletContext.contextPath}/assets/js/jquery/jquery-1.9.0.js"></script>
<script type="text/javascript">
$(function(){
	   $('#email').change(function(){
	      $('#check-button').show();
	      $('#check-img').hide();      
	   })
	   
	   $('#check-button').click(function(){
	      var email = $('#email').val();
	      console.log(email);
	      if(email==''){
	         return ;
	      }
	      
	      /*ajax 통신 */
	      $.ajax({ 
	         url:"${pageContext.servletContext.contextPath }/user/api/checkemail?checkemail=" + email,
	         type:"get", // get방식으로하고 url로 보내기 post일경우 data 항목에 넣어줘야함
	         dataType:"json",
	         data:"",
	         success:function(response){
	            if(response.result != "success"){
	               console.log(response.message);
	               return ;
	            }
	            
	            if(response.data == true){
	               alert('이미 존재하는 이메일입니다.\n다른 이메일을 입력해주세요')
	               $('#email').focus();
	               $("#email").val("");
	               return;
	            }
	            
	            $('#check-button').hide();
	            $('#check-image').show();
	            
	            
	         },
	         error:function(xhr, error){  // 요청에 대한 응답 실패 후  XmlHttpResponse ? 가 인자로 들어옴
	            console.error("error:" + error) 
	         }
	      });
	   })
	});

</script>

</head>
<body>
	<div id="container">
		<jsp:include page="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="user">

				<form:form 
				modelAttribute="userVo"
				id="join-form" 
				name="joinForm" 
				method="post" 
				action="${pageContext.servletContext.contextPath }/user/join" >
					<label class="block-label" for="name">이름</label>
					<input id="name" name="name" type="text" value="">
					<spring:hasBindErrors name="userVo">
					    <c:if test="${errors.hasFieldErrors('name') }">
							<p style="font-weight:bold; color:red; text-align:left; padding:0; margin: 0;">
					            <spring:message 
						     		code="${errors.getFieldError( 'name' ).codes[0] }" 				     
						     		text="${errors.getFieldError( 'name' ).defaultMessage }" />
					        </p> 
					   </c:if>
					</spring:hasBindErrors>

					<label class="block-label" for="email">이메일</label>
					<p style="font-weight: bold; color: #f00; text-align: left;">
					<form:input path="email"/>
					</p>
					<input type="button" id="check-button" value="체크">
					<img style="display:none" id="check-image" src="${pageContext.servletContext.contextPath }/assets/images/check.png" />
					<form:errors path="email"/>
					<label class="block-label">패스워드</label>
					<input name="password" type="password" value="">
					
					<fieldset>
						<legend>성별</legend>
						<label>여</label> <form:radiobutton path="gender" value="female" checked="checked"/>
						<label>남</label> <form:radiobutton path="gender" value="male"/>
					</fieldset>
					
					<fieldset>
						<legend>약관동의</legend>
						<input id="agree-prov" type="checkbox" name="agreeProv" value="y">
						<label>서비스 약관에 동의합니다.</label>
					</fieldset>
					
					<input type="submit" value="가입하기">
					
				</form:form>
			</div>
		</div>
		<jsp:include page="/WEB-INF/views/includes/navigation.jsp" />
		<jsp:include page="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>