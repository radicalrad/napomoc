package org.radicalrad.napomoc;



import java.io.IOException;
import java.net.URI;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.websocket.EncodeException;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.RemoteEndpoint.Basic;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.glassfish.tyrus.client.ClientManager;

import com.google.gson.Gson;

@ServerEndpoint(value = "/zgloszenie/{uzytkownik}", encoders = ZdarzeniePomocyMessageEncoder.class, decoders =ZdarzeniePomocyMessageDecoder.class)
public class PotrzebujacyPomocyEndpoint {
	private final Logger log = Logger.getLogger(getClass().getName());
	private Dane d = Dane.getInstance();
	private ClientManager client = ClientManager.createClient();
	@OnOpen
	public void open(final Session session, @PathParam("uzytkownik") final String uzytkownik) {
		log.info("session openend and bound to uzytkownik: " + uzytkownik);
		session.getUserProperties().put("uzytkownikWzywajacy", uzytkownik);
	}

	@OnMessage
	public void onMessage(final Session session, final ZdarzeniePomocyMessage msg) {
		String uzytkownik = (String) session.getUserProperties().get("uzytkownik");
		log.info("onMessage:"+ msg.toString());
		try {
			for (Session s : session.getOpenSessions()) {
				
				log.info("Session "+ s.toString());			
				log.info("Session "+ msg.toString());		
				//pobieranie lokalizacji zglaszajacego z komunikatu
				
				double xPotrzebujacego = new Double(msg.getLokX());
				double yPotrzebujacego = new Double(msg.getLokY());
				//pobranie ratownikow aktywnych w obszarze zgloszenia 
				
				List<PozycjaRatownikaMessage> pmrl = d.pobierzRatownikowWodlegosciKm(xPotrzebujacego, yPotrzebujacego, 3);
				Iterator<PozycjaRatownikaMessage> it = pmrl.iterator();
				while (it.hasNext()) {
					
					PozycjaRatownikaMessage prm = (PozycjaRatownikaMessage) it.next();
					Session ratownikSession =client.connectToServer(RatownikEndpointClient.class, new URI("ws://localhost:8080/hascode/zdarzenie/"+prm.getUzytkownikRatujacy()));
					Basic b =ratownikSession.getBasicRemote();
					Gson gson = new Gson();
					String json = gson.toJson(msg); 
					log.info("Wysylanie wiadomosci:"+json);
					b.sendText(json);
					ratownikSession.close();
				}
				
			}
		} catch (Exception e) {
			log.log(Level.WARNING, "onMessage failed", e);
		}
	}
}
