import com.skype.Skype;
import com.skype.SkypeException;

public class SendChatMessage {

    public static void main(String[] args) {

	try {
	    String message = "Hello, This is test message";

	    Skype.chat(Constants.SKYPE_FRIEND_NICKNAME).send(message);

	    System.out.println("Message sent!");
	    
	    
	    
	} catch (SkypeException e) {
	    e.printStackTrace();
	}

    }
}