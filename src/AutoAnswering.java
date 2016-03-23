import com.skype.ChatMessage;
import com.skype.ChatMessageAdapter;
import com.skype.Skype;
import com.skype.SkypeException;
import com.skype.User;
 
public class AutoAnswering {
 
    public static void main(String[] args) throws Exception {
 
        System.out.println("Start Auto Answering ...");
         
        // To prevent exiting from this program
        Skype.setDaemon(false);
         if(Skype.isRunning()){
             
             System.out.println("Skype is running.");
         }
         
        Skype.addChatMessageListener(new ChatMessageAdapter() {
            public void chatMessageReceived(ChatMessage received)
                    throws SkypeException {
                if (received.getType().equals(ChatMessage.Type.SAID)) {
 
                    // Sender
                    User sender =received.getSender();    
                     
                    System.out.println(sender.getId() +" say:");
                    System.out.println(" "+received.getContent() );
                     
                    received.getSender().send(
                            "I'm working. Please, wait a moment.");
                     
                    System.out.println(" - Auto answered!");
                }
            }
        });
         
         
        System.out.println("Auto Answering started!");
    }
 
}
