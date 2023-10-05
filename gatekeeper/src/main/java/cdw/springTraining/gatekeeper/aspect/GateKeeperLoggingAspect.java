package cdw.springTraining.gatekeeper.aspect;

import cdw.springTraining.gatekeeper.customException.GateKeepingCustomException;
import org.apache.juli.logging.LogFactory;
import org.apache.logging.log4j.LogManager;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class GateKeeperLoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(GateKeeperLoggingAspect.class);

    @Around("execution(* cdw.springTraining.gatekeeper.controller.*.*(..))")
    public Object controllerLogging(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{

        String method = proceedingJoinPoint.getSignature().toShortString();
        Object[] args = proceedingJoinPoint.getArgs();

        Object result = null;
        logger.info("\nMethod : "+method+"\nArguments : "+args+"\nTime : "+System.currentTimeMillis());

        System.out.println("\nMethod : "+method+"\nArguments : "+args+"\nTime : "+System.currentTimeMillis());

        long begin = System.currentTimeMillis();
        try
        {
            result = proceedingJoinPoint.proceed();
        }
        catch (Exception exception)
        {
            GateKeepingCustomException customException = new GateKeepingCustomException(exception.getMessage());
            logger.error(String.valueOf(customException));
//            throw customException;
            result = customException;
        }
        long end = System.currentTimeMillis();

        System.out.println("\nReturn value : "+result+"\nTime taken to complete the process : "+(end-begin)/1000);
        return result;
    }
}
