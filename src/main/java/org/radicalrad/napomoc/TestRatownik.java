package org.radicalrad.napomoc;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;

import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.DeploymentException;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.RemoteEndpoint.Basic;

import org.glassfish.tyrus.client.ClientManager;
import org.radicalrad.napomoc.TestPomoc.ZdarzeniePomocyMessage;

import com.google.gson.Gson;
@ClientEndpoint
public class TestRatownik {

	@OnOpen
	public void onOpen(Session session) {
		PozycjaRatownikaMessage msg = new PozycjaRatownikaMessage();
		msg.setLokX("123");
		msg.setLokY("1234");		
		msg.setUzytkownikRatujacy("rad@test.pl");
		System.out.println(this.getClass().getName()+" tworzy zdarzenie");
		try {
			//session.getUserProperties().put("region", "test");
			System.out.println(this.getClass().getName()+" tworzy aktualizuje sesje");
			session.getBasicRemote().sendObject(msg);
			
			try {
				for (Session s : session.getOpenSessions()) {
						System.out.println("Sesja: "+s.toString());
						Basic b = s.getBasicRemote();
						System.out.println("Props: "+s.getUserProperties().toString());
						Gson gson = new Gson();
						String json = gson.toJson(msg); 
						System.out.println("json: "+json);
						b.sendText(json);
							
					
				}
			} catch (IOException  e) {
				e.printStackTrace();
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EncodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@OnMessage
	public String onMessage(String message, Session session) {
		 System.out.println("Received response in client from endpoint: " + message);
    
		return null;
	}

	@OnClose
	public void onClose(Session session, CloseReason closeReason) {
	 
	}

	public static void main(String[] args) {
		ClientManager client = ClientManager.createClient();
	    try {
	///pozycja/{uzytkownik}
	    	String uzytkownik = "rad@test.pl";
	        client.connectToServer(TestRatownik.class, new URI("ws://localhost:8080/hascode/pozycja/"+uzytkownik));
	        
	     Thread.sleep(50);

	    } catch (DeploymentException | URISyntaxException  e) {
	        throw new RuntimeException(e);
	    } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
}
