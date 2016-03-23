import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import autoitx4java.AutoItX;

import com.skype.ChatMessage;
import com.skype.ChatMessageAdapter;
import com.skype.Skype;
import com.skype.SkypeException;
import com.skype.User;

public class SkypeAutoTask {

    public static void main(String[] args) throws Exception {

	System.out.println("Start Auto Answering ...");

	// To prevent exiting from this program
	Skype.setDaemon(false);

	Skype.addChatMessageListener(new ChatMessageAdapter() {
	    public void chatMessageReceived(ChatMessage received)
		    throws SkypeException {
		if (received.getType().equals(ChatMessage.Type.SAID)) {

		    // Sender
		    User sender = received.getSender();

		    System.out.println(sender.getId() + " say: "
			    + received.getContent());

		    String command = received.getContent();
		    String passcode = "";

		    switch (command) {
		    case "callme":
			Skype.call(sender.getId()).answer();
			break;

		    case "videocall":
			skypeVideoCall(received);
			break;
		    case "callend":
			skypeVideoCallEnd(received);
			break;
		    case "screenshare":
			skypeShareScreen(received);
			break;
		    case "punchin":

			timesheet(received, command);

			break;

		    case "punchout":
			timesheet(received, command);

			break;
		    case "lockscreen":
			try {
			    Process p = Runtime
				    .getRuntime()
				    .exec("cmd /c rundll32.exe user32.dll, LockWorkStation");

			} catch (IOException e) {
			    // TODO Auto-generated catch block
			    e.printStackTrace();
			}
			break;

		    case "shutdown":

			if (sender.getId().equals(
				Constants.SKYPE_FRIEND_NICKNAME)) {
			    try {
				Process p = Runtime.getRuntime().exec(
					"shutdown /s /f /t 6000");

			    } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			    }
			} else {
			    received.getSender()
				    .send("You dont have access to this command. Thanks.");
			}
			break;

		    case "restart":
			if (sender.getId().equals(
				Constants.SKYPE_FRIEND_NICKNAME)) {
			    try {
				Process p = Runtime
					.getRuntime()
					.exec("shutdown /r /f /t 6000 /c 'Restarting OS'");

			    } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			    }
			} else {
			    received.getSender()
				    .send("You dont have access to this command. Thanks.");
			}
			break;

		    case "abort":
			received.getSender()
				.send("You dont have access to this command. Thanks.");
			try {
			    Process p = Runtime.getRuntime().exec(
				    "shutdown /a ");

			} catch (IOException e) {
			    // TODO Auto-generated catch block
			    e.printStackTrace();
			}
			break;

		    default:

			if (sender.getId().equals(
				Constants.SKYPE_FRIEND_NICKNAME)) {
			} else {

			    String question = command;
			    String URL = "http://www.google.com/search?q="
				    + question;

			    HtmlUnitDriver driver2 = new HtmlUnitDriver();

			    driver2.get(URL);
			    WebElement submitelement = driver2.findElement(By
				    .id("center_col"));
			    received.getSender()
				    .send("Auto Reply:: I am currenty not in my desk. I found some of the stuffs in google that might intrest you. Thanks.");
			    received.getSender().send(submitelement.getText());

			}
			break;
		    }

		    System.out.println(" - Auto answered!");
		}
	    }

	    private void skypeShareScreen(ChatMessage received) {
		// TODO Auto-generated method stub
		AutoItX x = new AutoItX();
		x.mouseClick("left", 807, 681, 1, 10);
		x.mouseClick("left", 790, 515, 1, 10);
		x.controlSend("Skype™ - sudghimire", "", "", "{Enter}");
	    }

	    private void skypeVideoCallEnd(ChatMessage received) {
		// TODO Auto-generated method stub
		AutoItX x = new AutoItX();
		x.mouseClick("left", 878, 680, 1, 10);

	    }

	    private void skypeVideoCall(ChatMessage received)
		    throws SkypeException {
		received.getSender().send("Video Calling ... ");
		AutoItX x = new AutoItX();
		x.mouseClick("left", 505, 750, 1, 10);
		x.mouseClick("left", 60, 145, 1, 10);
		x.send(received.getSender().getDisplayName());
		x.mouseClick("left", 80, 190, 1, 10);
		x.mouseClick("left", 250, 106, 1, 10);

	    }

	    /**
	     * @param received
	     * @throws SkypeException
	     */
	    private void timesheet(ChatMessage received, String command)
		    throws SkypeException {
		received.getSender().send(
			"Work in progress ... Please wait ... ");
		FirefoxDriver driver = new FirefoxDriver();

		driver.get("https://vitpayroll/Employee/Default.aspx");

		driver.findElement(By.id("txtUserName")).sendKeys("i80681");
		driver.findElement(By.id("txtPassword")).sendKeys("nepal111!1");
		driver.findElement(By.id("btnLogin")).click();

		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		String totalTime = driver
			.findElement(
				By.xpath("//*[@id='ctl01_Div2']/div/table/tbody/tr[2]/td[8]/div/div"))
			.getText();
		totalTime = totalTime.replaceAll("\\s", "");

		if (command.equals("punchout")) {
		    received.getSender().send(
			    "Punchout successful. Your total time is - "
				    + totalTime);
		}
		if (command.equals("punchin")) {
		    received.getSender().send("PunchIn successful.");
		}
		driver.findElementByLinkText("Logout").click();
		driver.quit();
	    }
	});

	System.out.println("Auto Answering started!");
    }
}
