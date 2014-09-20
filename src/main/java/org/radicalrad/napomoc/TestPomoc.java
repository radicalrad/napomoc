package org.radicalrad.napomoc;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.logging.Level;

import javax.json.Json;
import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.DeploymentException;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.RemoteEndpoint.Basic;
import javax.websocket.Session;

import org.glassfish.tyrus.client.ClientManager;

import com.google.gson.Gson;



@ClientEndpoint
public class TestPomoc {
	class ZdarzeniePomocyMessage {

		private String uzytkownikWzywajacy;
		private String status;
		private String uzytkownikRatujacy;
		private String region;
		private String lokX;
		private String lokY;
		private Date dataOdebrania;
		private Date dataZgloszenia;
		
		public String getUzytkownikWzywajacy() {
			return uzytkownikWzywajacy;
		}
		public void setUzytkownikWzywajacy(String uzytkownikWzywajacy) {
			this.uzytkownikWzywajacy = uzytkownikWzywajacy;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public String getUzytkownikRatujacy() {
			return uzytkownikRatujacy;
		}
		public void setUzytkownikRatujacy(String uzytkownikRatujacy) {
			this.uzytkownikRatujacy = uzytkownikRatujacy;
		}
		public String getRegion() {
			return region;
		}
		public void setRegion(String region) {
			this.region = region;
		}
		public String getLokX() {
			return lokX;
		}
		public void setLokX(String lokX) {
			this.lokX = lokX;
		}
		public String getLokY() {
			return lokY;
		}
		public void setLokY(String lokY) {
			this.lokY = lokY;
		}
		public Date getDataOdebrania() {
			return dataOdebrania;
		}
		public void setDataOdebrania(Date dataOdebrania) {
			this.dataOdebrania = dataOdebrania;
		}
		public Date getDataZgloszenia() {
			return dataZgloszenia;
		}
		public void setDataZgloszenia(Date dataZgloszenia) {
			this.dataZgloszenia = dataZgloszenia;
		}
		
		@Override
		public String toString() {
			return "ZdarzeniePomocyMessage [uzytkownikWzywajacy="
					+ uzytkownikWzywajacy + ", status=" + status
					+ ", uzytkownikRatujacy=" + uzytkownikRatujacy + ", region="
					+ region + ", lokX=" + lokX + ", lokY=" + lokY
					+ ", dataOdebrania=" + dataOdebrania + ", dataZgloszenia="
					+ dataZgloszenia + "]";
		}
	}

	@OnOpen
	public void onOpen(Session session) {
		ZdarzeniePomocyMessage msg = new ZdarzeniePomocyMessage();
		msg.setLokX("123");
		msg.setLokY("1234");
		msg.setRegion("warszawa");
		msg.setStatus("status");
		msg.setDataOdebrania(new Date());
		msg.setDataZgloszenia(new Date());
		msg.setUzytkownikRatujacy("");
		msg.setUzytkownikWzywajacy("");
		System.out.println(this.getClass().getName()+" tworzy zdarzenie");
		try {
			//session.getUserProperties().put("region", "test");
			System.out.println(this.getClass().getName()+" tworzy aktualizuje sesje");
			session.getBasicRemote().sendObject(msg);
			
			session.getUserProperties().put("lokX", "12,33");
			session.getUserProperties().put("lok&", "52,33");
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
	
	        client.connectToServer(TestPomoc.class, new URI("ws://localhost:8080/hascode/zdarzenie/warszawa"));
	        
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
