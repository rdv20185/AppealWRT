package aspects;

import java.util.Date;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@Aspect
public class NotifyAspect {

	@Autowired
    private SimpMessagingTemplate template;

    private static final String WEBSOCKET_TOPIC = "/topic/notify";
    
    @Pointcut("execution(* web.PetitControllerN.addPetit(..))")
    private void anyOldTransfer() {}
  /*  
    @Pointcut("execution(* web.PetitController.refreshAddPetit(..))")
    private void anyOldTransfer2() {}
    
  
    @After("execution(* web.PetitController.addPetit(..))") 
    public void notifyClients() throws Throwable {
    	System.out.println("@@ TEST2 @@");
        template.convertAndSend(WEBSOCKET_TOPIC, new Date());
    }
    */
    
    @After("anyOldTransfer()") 
    public void notifyClients() throws Throwable {
    	System.out.println("@@ TEST2 @@");
        template.convertAndSend(WEBSOCKET_TOPIC, new Date());
    }
    

}
