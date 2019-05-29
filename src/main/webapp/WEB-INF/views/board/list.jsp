<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	pageContext.setAttribute("showBoardNum", 5);
%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.servletContext.contextPath}/assets/css/board.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp"/>
		<div id="content">
			<div id="board">
				<form id="search_form" action="${pageContext.servletContext.contextPath}/board/list" method="get">
					<input type="text" id="keyword" name="keyword" value="${keyword}">
					<input type="hidden" id="showBoardNum" name="showBoardNum" value="${showBoardNum}">
					<input type="submit" value="찾기">
				</form>
				<table class="tbl-ex">
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>&nbsp;</th>
					</tr>
					
					<c:forEach items='${list}' var ='vo' varStatus="status">
					<tr>
						<td>${list.size() - status.count+1}</td>
						<td style="text-align:left; padding-left: ${20*vo.depth}px">
						<c:choose>
							<c:when test="${vo.depth >  0}">
							<img src="${pageContext.servletContext.contextPath}/assets/images/reply.png">
							</c:when>
						</c:choose>
							<a href="${pageContext.servletContext.contextPath}/board/view?no=${vo.no}">${vo.title}</a>
						</td>
						<td>${vo.userName}</td>
						<td>${vo.hit}</td>
						<td>${vo.regDate}</td>
						<c:choose>
							<c:when test="${vo.userNo == authUser.no}">
							<td><a href="${pageContext.servletContext.contextPath}/board/remove?no=${vo.no}" class="del">삭제</a></td>
							</c:when>
						</c:choose>
					</tr>
					</c:forEach>
					
				</table>
				
				<!-- pager 추가 -->
				<div class="pager">
					<ul>
					<c:if test="${paging.blockStartNum>5}">
						<li><a href="${pageContext.servletContext.contextPath}/board/list?keyword=${keyword}&curPageNum=${paging.blockStartNum-1}&showBoardNum=${showBoardNum}">◀</a></li>
					</c:if>
							<c:forEach begin="${paging.blockStartNum}" end="${paging.blockLastNum}" varStatus="status">
								<c:choose>
									<c:when test="${paging.curPageNum == paging.blockStartNum+status.count-1}">
										<li class="selected">
											<a href="${pageContext.servletContext.contextPath}/board/list?keyword=${keyword}&curPageNum=${paging.blockStartNum + status.count-1}&showBoardNum=${showBoardNum}">${paging.blockStartNum+status.count-1}</a>
										</li>
									</c:when>
									<c:otherwise>
										<li>
											<a href="${pageContext.servletContext.contextPath}/board/list?keyword=${keyword}&curPageNum=${paging.blockStartNum+status.count-1}&showBoardNum=${showBoardNum}">${paging.blockStartNum+status.count-1}</a>
										</li>
									</c:otherwise>
								</c:choose>
							</c:forEach>
					<c:if test="${paging.blockLastNum<paging.lastPageNum}">
						<li><a href="${pageContext.servletContext.contextPath}/board/list?keyword=${keyword}&curPageNum=${paging.blockLastNum+1}&showBoardNum=${showBoardNum}">▶</a></li>
					</c:if>
					</ul>
				</div>					
				<!-- pager 추가 -->
				
				<div class="bottom">
					<a href="${pageContext.servletContext.contextPath}/board/write" id="new-book">글쓰기</a>
				</div>				
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp"/>
		<c:import url="/WEB-INF/views/includes/footer.jsp"/>
	</div>
</body>
</html>