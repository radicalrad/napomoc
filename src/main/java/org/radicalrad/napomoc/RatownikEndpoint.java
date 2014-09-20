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

@ServerEndpoint(value = "/zdarzenie/{region}", encoders = ZdarzeniePomocyMessageEncoder.class, decoders =ZdarzeniePomocyMessageDecoder.class)
public class RatownikEndpoint {
	private final Logger log = Logger.getLogger(getClass().getName());

	@OnOpen
	public void open(final Session session, @PathParam("region") final String region) {
		log.info("session openend and bound to region: " + region);
		session.getUserProperties().put("region", region);
	}

	@OnMessage
	public void onMessage(final Session session, final ZdarzeniePomocyMessage msg) {
		String room = (String) session.getUserProperties().get("region");
		log.info("onMessage:"+ msg.toString());
		try {
			for (Session s : session.getOpenSessions()) {
				if (s.isOpen()
						&& room.equals(s.getUserProperties().get("region"))) {
					s.getBasicRemote().sendObject(msg);
				}
			}
		} catch (IOException | EncodeException e) {
			log.log(Level.WARNING, "onMessage failed", e);
		}
	}
}
