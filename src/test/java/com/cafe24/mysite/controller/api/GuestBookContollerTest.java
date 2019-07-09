package com.cafe24.mysite.controller.api;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.cafe24.config.web.TestWebConfig;
import com.cafe24.mysite.config.AppConfig;
import com.cafe24.mysite.service.GuestbookService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= {AppConfig.class, TestWebConfig.class})
@WebAppConfiguration
public class GuestBookContollerTest {
	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext webApplicationContext;
	
	@Autowired
	private GuestbookService guestbookService;
	
	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
//	@Test
//	public void testDIGuestbookService() {
//		assertNotNull(guestbookService);
//	}
	
	@Test
	public void testFetchGuestbookList() throws Exception {
		// url로 찌를때 mockMvc를 사용한다.
		mockMvc.perform(get("/api/guestbook/list/{no}", 1L))
		.andExpect(status().isOk()).andDo(print());
		
		// andDo 메소드는 응답내용을 확인하는 메소드
	}
}
