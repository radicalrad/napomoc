package org.radicalrad.napomoc;

import javax.json.Json;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;


public class ZdarzeniePomocyMessageEncoder implements Encoder.Text<ZdarzeniePomocyMessage> {
	@Override
	public void init(final EndpointConfig config) {
	}

	@Override
	public void destroy() {
	}

	@Override
	public String encode(final ZdarzeniePomocyMessage msg) throws EncodeException {
		return Json.createObjectBuilder()
				.add("lokX", msg.getLokX())
				.add("lokY", msg.getLokY())
				.add("region", msg.getRegion())
				.add("status", msg.getStatus())
				.add("uzytkownikRatujacy", msg.getUzytkownikRatujacy())
				.add("uzytkownikWzywajacy", msg.getUzytkownikWzywajacy())
				.add("dataOdebrania", msg.getDataOdebrania().toString())
				.add("dataZgloszenia", msg.getDataZgloszenia().toString())
				.build()
				.toString();
	}
}
