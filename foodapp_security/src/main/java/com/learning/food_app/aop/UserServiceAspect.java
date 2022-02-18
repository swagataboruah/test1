package com.learning.food_app.aop;

import org.aspectj.lang.JoinPoint;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect //this container holds aop code
public class UserServiceAspect{

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Pointcut("within(@org.springframework.stereotype.Repository *) "+ 
			  " || within(@org.springframework.stereotype.Service *)" +
			  " || within(@org.springframework.web.bind.annotation.RestController *)" )	
	public void springPointCutExp() {
	
	}
	
	
	//is this applicable to packages/classes? yes but how to specify the package names
	@Pointcut("within(com.learning.food_app.controller..*) "+ 
				  " || within(com.learning.food_app.serviceImpl..*)" )	
	public void springPointCutExp2() {
		
	}
	
				
	@AfterThrowing(pointcut = "springPointCutExp() && springPointCutExp2()", throwing = "e") 
	public void logAfterThrowingExceotion(JoinPoint joinPoint, Throwable e) {
		
		log.error("exception {}.{}() with cause {}", joinPoint.getSignature().getDeclaringTypeName(),
				joinPoint.getSignature().getName(), e.getCause()!=null ? e.getCause():"NULL");
	}
	
	
	//assignment
//	@Around(value= "logDisplayingBalance()")  
//	public void aroundAdvice(ProceedingJoinPoint jp) throws Throwable {  
//	System.out.println("The method aroundAdvice() before invokation of the method " +
//	                    jp.getSignature().getName() +
//	                    " method");  
//	try {  
//	   jp.proceed();  
//	} finally {  
//		
//	}  
//	System.out.println("The method aroundAdvice() after invokation of the method " +
//	                    jp.getSignature().getName() +
//	                    " method");  
//	}  
  
	
	
	//if we add get like this it will only come for get methods
    @Before(value = "execution(* com.learning.food_app.serviceImpl.*.get*(..))") 	
	//Imply.* means any method from this package, with any number of arguments
	//before calling any method from service, it will call this below method
	public void beforeAllServiceMethods(JoinPoint joinPoint) {
		System.out.println("hello");
		System.err.println(joinPoint);		
	}
	
    
    
//	@After(value = "execution(* com.learning.food_app.serviceImpl.*.*(..))") 
//	//after calling any method from service, it will call this below method
//	public void beforeAllServiceMethods(JoinPoint joinPoint) {
//		System.out.println("hello");
//		System.err.println(joinPoint);
//		
//	}
	
	
	
	
	
	
	
}
