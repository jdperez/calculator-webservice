package com.foo;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: jose5124
 * Date: 7/3/12
 * Time: 2:20 PM
 * To change this template use File | Settings | File Templates.
 */
@Component
@Aspect
public class AuditAspect {

    @Before("execution(* divide(..)) || execution(* enumCalculate(..))")
    public void doAuditing() {
        System.out.println("doAuditing is called");
    }

}
