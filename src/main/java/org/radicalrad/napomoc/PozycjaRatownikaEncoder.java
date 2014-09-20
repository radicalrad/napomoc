package org.radicalrad.napomoc;

import javax.json.Json;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class PozycjaRatownikaEncoder implements Encoder.Text<PozycjaRatownikaMessage> {
	@Override
	public void init(final EndpointConfig config) {
	}

	@Override
	public void destroy() {
	}

	@Override
	public String encode(final PozycjaRatownikaMessage msg) throws EncodeException {
		return Json.createObjectBuilder()
				.add("lokX", msg.getLokX())
				.add("lokY", msg.getLokY())
				.add("uzytkownikRatujacy", msg.getUzytkownikRatujacy())
				.build()
				.toString();
	}
}
