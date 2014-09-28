package org.radicalrad.napomoc;



import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.websocket.EncodeException;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/pozycja/{uzytkownik}", encoders = PozycjaRatownikaEncoder.class, decoders =PozycjaRatownikaDecoder.class)
public class PozycjaRatownikaEndpoint {
	private final Logger log = Logger.getLogger(getClass().getName());
	private Dane c = Dane.getInstance();
	@OnOpen
	public void open(final Session session, @PathParam("uzytkownik") final String uzytkownik) {
		log.info("session openend and bound to uzytkownik: " + uzytkownik);
		session.getUserProperties().put("uzytkownikRatujacy", uzytkownik);
	}

	@OnMessage
	public void onMessage(final Session session, final PozycjaRatownikaMessage msg) {
		String uzytkownik = (String) session.getUserProperties().get("uzytkownikRatujacy");
		log.info("onMessage:"+ msg.toString());
		for (Session s : session.getOpenSessions()) {
				
			if (s.isOpen()
					&& uzytkownik.equals(s.getUserProperties().get("uzytkownikRatujacy"))) {
				
				
				c.aktualizujPozycjeRatownika(msg);
				
			}
		}
	}
}
