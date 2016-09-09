package aspects;

import java.util.Date;

import org.apache.poi.util.SystemOutLogger;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@Aspect
public class NotifyAspect {

	@Autowired
    private SimpMessagingTemplate template;

    private static final String WEBSOCKET_TOPIC = "/topic/notify";
    
    @Pointcut("execution(* web.PetitController.addPetit(..))")
    private void anyOldTransfer() {}
    
    @Pointcut("execution(* web.PetitController.deletePetit(..))")
    private void anyOldTransfer2() {}
    
    @Pointcut("execution(* web.PetitController.close(..))")
    private void anyOldTransfer3() {}
    
    @Pointcut("execution(* web.PetitController.refreshAddPetit(..))")
    private void anyOldTransfer4() {}
    
    @Pointcut("execution(* web.PetitController.open(..))")
    private void anyOldTransfer5() {}
    
    @Pointcut("execution(* web.Basic.refreshAddPetit(..))")
    private void anyOldTransfer6() {
    }
    
    
  /*  
    @Pointcut("execution(* web.PetitController.refreshAddPetit(..))")
    private void anyOldTransfer2() {}
    
  */
    @After("anyOldTransfer6()") 
    public void notifyCents() throws Throwable {
    	System.out.println("@@ TEST2 @@");
        template.convertAndSend(WEBSOCKET_TOPIC, new Date());
    }
    
    
    @After("anyOldTransfer() || anyOldTransfer2() || anyOldTransfer3() || anyOldTransfer4() || anyOldTransfer5()") 
    public void notifyClients() throws Throwable {
        template.convertAndSend(WEBSOCKET_TOPIC, new Date());
    }
    

}
