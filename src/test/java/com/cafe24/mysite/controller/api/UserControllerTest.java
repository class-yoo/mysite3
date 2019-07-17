package com.cafe24.mysite.controller.api;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.cafe24.config.web.TestWebConfig;
import com.cafe24.mysite.config.AppConfig;
import com.cafe24.mysite.vo.UserVo;
import com.google.gson.Gson;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AppConfig.class, TestWebConfig.class })
@WebAppConfiguration
public class UserControllerTest {
	
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	
	@Test
	public void testUserJoin() throws Exception {
		
		UserVo userVo = new UserVo();
		
		//1. Normal User's Join Data
		userVo.setName("홍길동");
		userVo.setEmail("hgd888@naver.com");
		userVo.setPassword("12345678!");
		userVo.setGender("M");
		
		ResultActions resultActions = mockMvc
				.perform(post("/api/user/join")
						.contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(userVo)));
		
		resultActions
		.andExpect(status().isBadRequest())
		.andDo(print())
		.andExpect(jsonPath("$.result",is("fail")));
		
		//2. Invalidation in Name : 
		userVo.setName("홍");
		userVo.setEmail("hgd888@naver.com");
		userVo.setPassword("kickscar#2019");
		userVo.setGender("M");
		
		ResultActions resultActions2 = mockMvc
				.perform(post("/api/user/join")
						.contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(userVo)));
		
		resultActions2
		.andExpect(status().isBadRequest())
		.andDo(print())
		.andExpect(jsonPath("$.result",is("fail")));
		
		//3. Invalidation in gender : 
				userVo.setName("홍길동");
				userVo.setEmail("hgd888@naver.com");
				userVo.setPassword("kickscar#2019");
				userVo.setGender("MAN");
				
				ResultActions resultActions3 = mockMvc
						.perform(post("/api/user/join")
								.contentType(MediaType.APPLICATION_JSON)
								.content(new Gson().toJson(userVo)));
				
				resultActions3
				.andExpect(status().isBadRequest())
				.andDo(print())
				.andExpect(jsonPath("$.result",is("fail")));
		
	}
	
	@Test
	public void testUserLogin() throws Exception {
		
		UserVo userVo = new UserVo();
		
		//1. Normal User's Login Data
		userVo.setEmail("hgd888");
		userVo.setPassword( "kickscar#2019" );
		
		ResultActions resultActions = mockMvc
				.perform(post("/api/user/login")
						.contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(userVo)));
		
		resultActions
		.andDo(print())
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.result", is("fail")));
		
	}
	
}



