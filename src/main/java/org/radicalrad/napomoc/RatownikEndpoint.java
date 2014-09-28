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
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.glassfish.tyrus.client.ClientManager;

@ServerEndpoint(value = "/zdarzenie/{uzytkownik}", encoders = ZdarzeniePomocyMessageEncoder.class, decoders =ZdarzeniePomocyMessageDecoder.class)
public class RatownikEndpoint {
	private final Logger log = Logger.getLogger(getClass().getName());
	private Dane d = Dane.getInstance();
	private ClientManager client = ClientManager.createClient();
	@OnOpen
	public void open(final Session session, @PathParam("uzytkownik") final String uzytkownik) {
		log.info("session openend and bound to uzytkownik: " + uzytkownik);
		session.getUserProperties().put("uzytkownikRatujacy", uzytkownik);
	}

	@OnMessage
	public void onMessage(final Session session, final ZdarzeniePomocyMessage msg) {
		String uzytkownik = (String) session.getUserProperties().get("uzytkownik");
		log.info("onMessage:"+ msg.toString());
		try {
			for (Session s : session.getOpenSessions()) {
				/*
				if (s.isOpen()
						&& room.equals(s.getUserProperties().get("region"))) {
					s.getBasicRemote().sendObject(msg);
				}
				*/
				//powiadomienie powinien dostac tylko uzytkownik w URLu
				log.info("Session "+ s.toString());
				
				s.getBasicRemote().sendObject(msg);
				
				
			}
		} catch (Exception e) {
			log.log(Level.WARNING, "onMessage failed", e);
		}
	}
}
