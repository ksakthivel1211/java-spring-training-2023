package cdw.springTraining.gatekeeper.aspect;

import cdw.springTraining.gatekeeper.customException.GateKeepingCustomException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author sakthivel
 * GateKeeperLoggingAspect class file logs the return or error response through around advice
 */
@Aspect
@Component
public class GateKeeperLoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(GateKeeperLoggingAspect.class);

    @Around("execution(* cdw.springTraining.gatekeeper.controller.*.*(..))")
    public Object controllerLogging(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{

        String method = proceedingJoinPoint.getSignature().toShortString();
        Object[] args = proceedingJoinPoint.getArgs();
        String val = args.getClass().getName();

        SimpleDateFormat dateformat = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss aa");
        String datetime = dateformat.format(new Date().getTime());
        Object result = null;
        logger.info("\nMethod : "+method+"\nArguments : "+args+"\nTime : "+datetime);

        System.out.println("\nMethod : "+method+"\nArguments : "+val+"\nTime : "+datetime);

        long begin = System.currentTimeMillis();
        try
        {
            result = proceedingJoinPoint.proceed();
        }
        catch (GateKeepingCustomException gateKeepingCustomException) {
            logger.error(gateKeepingCustomException.getMessage());
            throw gateKeepingCustomException;
        }
        long end = System.currentTimeMillis();

        System.out.println("\nReturn value : "+result+"\nTime taken to complete the process : "+(end-begin)/1000);
        return result;
    }
}
