package org.radicalrad.napomoc;

import java.io.StringReader;
import java.util.Date;

import javax.json.Json;
import javax.json.JsonObject;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

public class PozycjaRatownikaDecoder implements Decoder.Text<PozycjaRatownikaMessage> {
	@Override
	public void init(final EndpointConfig config) {
	}

	@Override
	public void destroy() {
	}

	@SuppressWarnings("deprecation")
	@Override
	public PozycjaRatownikaMessage decode(final String textMessage) throws DecodeException {
		PozycjaRatownikaMessage msg = new PozycjaRatownikaMessage();
		JsonObject obj = Json.createReader(new StringReader(textMessage))
				.readObject();
		msg.setLokY(obj.getString("lokY"));
		msg.setLokX(obj.getString("lokX"));
		msg.setUzytkownikRatujacy(obj.getString("uzytkownikRatujacy"));
		
		return msg;
	}

	@Override
	public boolean willDecode(final String s) {
		return true;
	}
}