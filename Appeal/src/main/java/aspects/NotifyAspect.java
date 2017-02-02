package aspects;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.util.SystemOutLogger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.ui.ModelMap;

import domain.MessageList;
import domain.Petit;

@Aspect
public class NotifyAspect {

	@Autowired
    private SimpMessagingTemplate template;

    private static final String WEBSOCKET_TOPIC = "/topic/notify";
    
    @Pointcut("execution(* web.PetitController.deletePetit(..))")
    private void anyOldTransfer2() {}
    
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
        template.convertAndSend(WEBSOCKET_TOPIC, new Date());
    }
    
    
    @After("anyOldTransfer2() || anyOldTransfer4() || anyOldTransfer5()") 
    public void notifyClients() throws Throwable {
        template.convertAndSend(WEBSOCKET_TOPIC, new Date());
    }
    
    
    
    
    
    
    @Pointcut("execution(* web.PetitController.close(..))")
    private void anyOldTransfer3() {}
    
    @Pointcut("execution(* web.PetitController.addPetit(..))")
    private void anyOldTransfer() {}
    
    
    @After(value = "anyOldTransfer3() && args(petitId,model,role,user)")
	public void beforeAccountMethodExecution(JoinPoint jp, Integer petitId,ModelMap model,String role,String user) {

	  MessageList ml = new MessageList(petitId,role,user,"closemes",new Date().toString());
	  template.convertAndSend(WEBSOCKET_TOPIC, ml);
	}
    
    
    @After(value = "anyOldTransfer() && args(petit,..)")
	public void after_add(JoinPoint jp, Petit petit) {

	  
	  MessageList ml = new MessageList(petit,"test1",petit.getBlockger2016().getRegname(),"add",new Date().toString());
	  template.convertAndSend(WEBSOCKET_TOPIC, ml);
	}

}
