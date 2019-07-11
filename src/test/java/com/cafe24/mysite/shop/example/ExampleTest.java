package com.cafe24.mysite.shop.example;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.Arrays;

import org.hamcrest.core.StringStartsWith;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Matchers;

public class ExampleTest {
	
	// 테스트 케이스(메소드)끼리 공유해야 할 변수가 있으면
	// static!!
	private static StringBuilder output = new StringBuilder(); 
	
	
	@BeforeClass
	public static void setUpBefore() {
		System.out.println("@BeforeClass");
	}
	
	
	//작업을하고 난후의 변경사항을 다지워주는 역할
	@AfterClass
	public static void tearDownAfter() {
		System.out.println("@AfterClass");
		System.out.println(output.toString());
	}
	
	@Before
	public void setUp() {
		System.out.println("@Before");
	}

	// 메소드명의 문자열 순서에 따라 Test 메소드가 작동됨
	@Test
	public void myATest() {
		System.out.println("@Test:A");
		output.append("A");
	}
	
	@Test
	public void myBTest() {
		System.out.println("@Test:B");
		output.append("B");
	}
	
	@Test
	public void myCTest() {
		System.out.println("@Test:C");
		output.append("C");
	}
	
	
	// assertXYZ 테스트
	// 테스트에 포함시키지 않으려면 @Ignore 설정을해준다. 
	@Ignore
	@Test
	public void ingnoreTest() {
		assertTrue(false);
	}
	
	
	@Test
	public void testAssert1() {
		Object[] a = {"Java", "Junit", "Spring"};
		Object[] b = {"Java", "Junit", "Spring"};
		
		assertArrayEquals(a, b);
	}
	
	@Test
	public void testAssert() {
		
		assertTrue(true);
		assertFalse(false);
		
		assertNull(null);
		assertNotNull(new Object());
		
		assertEquals(1+2, 3);
		assertEquals(new String("hello"), "hello");
		assertNotEquals(true, false);
		
		assertSame("Hello", "Hello");
		assertNotSame(new Integer(1), new Integer(1));
		
//		assertThat : is
		assertThat(1+2, is(3));
		assertThat("this is never", is(not("that is never")));
		
//		assertThat : allOf
		assertThat("Hello World", allOf(startsWith("Hello"), containsString("or")));
		
//		assertThat : anyOf
		assertThat("Hello World", anyOf(startsWith("Heaven"), containsString("or")));
		
//		assertThat : both
		assertThat("ABC", both(containsString("A")).and(containsString("C")));
		
//		assertThat : either 
		assertThat("ABC", either(containsString("A")).or(containsString("C")));
		
//		assertThat : everyItem - 리스트의 모든 요소가 "Ap"로 시작해야 성공
		assertThat(Arrays.asList("Apple", "Application", "Applogize"), everyItem(startsWith("Ap")));
		
//		assertThat : hasItem -리스트중 하나라도 가지고있으면 성공
		assertThat(Arrays.asList("Red", "Banana", "Black"), hasItem("Banana"));
		
		//
//		fail("All Over!!!!");		
		
	}
	
	@After
	public void tearDown() {
		System.out.println("@After");
	}
	
}
