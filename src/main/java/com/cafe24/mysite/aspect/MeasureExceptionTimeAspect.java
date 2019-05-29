package com.cafe24.mysite.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class MeasureExceptionTimeAspect {
	
	// 모든 return 타입의 모든 패키지의 모든 repository 클래스의 모든메소드를 명시
	@Around("execution(* *..repository.*.*(..))") 
	public Object aroundAdvice(ProceedingJoinPoint pjp) throws Throwable {
		
		//before
		StopWatch sw = new StopWatch();
		sw.start();
		
		//method 실행
		Object result = pjp.proceed();
		
		//after
		sw.stop();
		
		String className  = pjp.getTarget().getClass().getName();
		String methodName = pjp.getSignature().getName();
		String taskName = className+"."+methodName;
		
		System.out.println("[Execution Time]["+taskName+"] "+sw.getTotalTimeMillis());
		
		return result;
	}
	
}
