package parsers;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import exceptions.NotParsableJSONException;

public class AddrParser {
	public static String parse(String tx_string) throws NotParsableJSONException {

		JsonParser jp = new JsonParser();
		JsonObject o = jp.parse(tx_string).getAsJsonObject();

		if(o.has("txrefs")) {

			String string_txrefs = o.get("txrefs").toString();
			JsonArray json_txrefs = jp.parse(string_txrefs).getAsJsonArray();

			String string_first_txref = json_txrefs.get(0).toString();
			JsonObject json_first_txrefs =  jp.parse(string_first_txref).getAsJsonObject();

			String tx_addr = json_first_txrefs.get("tx_hash").toString();

			return jsonify(tx_addr);
		}
		else {
			throw new NotParsableJSONException("Erreur json");
		}

	}

	private static String jsonify(String str) {
		return str.substring(1, str.length() - 1);
	}
}
