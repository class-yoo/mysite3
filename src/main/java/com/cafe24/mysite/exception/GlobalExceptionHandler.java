package com.cafe24.mysite.exception;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.cafe24.mysite.dto.JSONResult;
import com.fasterxml.jackson.databind.ObjectMapper;

@ControllerAdvice
// Component는 Controller Advice의 부모클래스이다
// 에러가 났을경우 spring-servlet.xml에 설정된 base-package의 경로를 찾아  @ControllerAdvice가 설정된 컨트롤러의 
// @ExceptionHandler(Exception.class)가 설정된 메소드를 실행시킨다.
public class GlobalExceptionHandler {
	
	public static final Log LOGGER = LogFactory.getLog(GlobalExceptionHandler.class);
	
//	mysite2의 예외는 다여기로 들어와서 처리됨
	@ExceptionHandler(Exception.class)
	public String handleUserDaoException(HttpServletRequest request, HttpServletResponse response, Exception e)
			throws ServletException, IOException {

		// 1. 로깅
		/*
		 * 오버로드가 되어 있기 때문에 FileWriter 를 이용해서 오류메세지를 파일에 작성해둘 수 있음.
		 * e.printStackTrace(FileWriter);
		 */
		e.printStackTrace();
		StringWriter errors = new StringWriter();
		e.printStackTrace(new PrintWriter(errors));
		LOGGER.error(errors.toString()); // 로거는 화면에도 뿌리고 파일에도 남길 수 있는 기능
		System.out.println(errors.toString());
		
		String accept = request.getHeader("accept");

		if (accept.matches(".*application/json.*")) { // "application/json"을 포함한 모든 문자(.*)일 경우 true
			// JSON 응답
			response.setStatus(HttpServletResponse.SC_OK);
			JSONResult jsonResult = JSONResult.fail(errors.toString());
			String result = new ObjectMapper().writeValueAsString(jsonResult);

			System.out.println(result);
			OutputStream os = response.getOutputStream();
			os.write(result.getBytes("utf-8"));
			os.close();

		} else {
			// 2. 안내페이지 가기 + 정상종료(response)
			request.setAttribute("uri", request.getRequestURI());
			request.setAttribute("exception", errors.toString());
			request.getRequestDispatcher("/WEB-INF/views/error/exception.jsp").forward(request, response);

		}
		return "error/exception";
	}

}