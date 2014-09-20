package org.radicalrad.napomoc;

import java.io.StringReader;
import java.util.Date;

import javax.json.Json;
import javax.json.JsonObject;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

import com.hascode.tutorial.ChatMessage;

public class ZdarzeniePomocyMessageDecoder  implements Decoder.Text<ZdarzeniePomocyMessage> {
	@Override
	public void init(final EndpointConfig config) {
	}

	@Override
	public void destroy() {
	}

	@SuppressWarnings("deprecation")
	@Override
	public ZdarzeniePomocyMessage decode(final String textMessage) throws DecodeException {
		ZdarzeniePomocyMessage msg = new ZdarzeniePomocyMessage();
		JsonObject obj = Json.createReader(new StringReader(textMessage))
				.readObject();
		msg.setDataOdebrania(new Date());
		msg.setDataZgloszenia(new Date(obj.getString("dataOdebrania")));
		msg.setRegion(obj.getString("region"));
		msg.setStatus(obj.getString("status"));
		msg.setLokY(obj.getString("lokY"));
		msg.setLokX(obj.getString("lokX"));
		msg.setUzytkownikRatujacy(obj.getString("uzytkownikRatujacy"));
		msg.setUzytkownikWzywajacy(obj.getString("uzytkownikWzywajacy"));
		
		return msg;
	}

	@Override
	public boolean willDecode(final String s) {
		return true;
	}
}
