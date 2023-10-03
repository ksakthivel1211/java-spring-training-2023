package cdw.springTraining.gatekeeper.aspect;

import cdw.springTraining.gatekeeper.customException.GateKeepingCustomException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class GateKeeperLoggingAspect {

    @Around("execution(* cdw.springTraining.gatekeeper.controller.*.*(..))")
    public Object controllerLogging(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{

        String method = proceedingJoinPoint.getSignature().toShortString();
        Object[] args = proceedingJoinPoint.getArgs();

        Object result = null;

        System.out.println("\nMethod : "+method+"\nArguments : "+args+"\nTime : "+System.currentTimeMillis());

        long begin = System.currentTimeMillis();
        try
        {
            result = proceedingJoinPoint.proceed();
        }
        catch (Exception exception)
        {
            GateKeepingCustomException customException = new GateKeepingCustomException(exception.getMessage());
            System.out.println(customException);
//            throw customException;
            result = customException;
        }
        long end = System.currentTimeMillis();

        System.out.println("\nReturn value : "+result+"\nTime taken to complete the process : "+(end-begin)/1000);
        return result;
    }
}
