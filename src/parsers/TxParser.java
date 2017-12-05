package parsers;
import javax.xml.bind.DatatypeConverter;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class TxParser {

	
	public static String parse(String tx_addr) {
		
		System.out.println("On parse " + tx_addr);
		
		JsonParser jp = new JsonParser();
		JsonObject o = jp.parse(tx_addr).getAsJsonObject();
	
		String string_txrefs = o.get("outputs").toString();
		JsonArray json_txrefs =  jp.parse(string_txrefs).getAsJsonArray();
		
		String string_first_txref = json_txrefs.get(0).toString();
		JsonObject json_first_txrefs =  jp.parse(string_first_txref).getAsJsonObject();
		
		String script = json_first_txrefs.get("script").toString();
		
		System.out.println(jsonify(script));
		
		return jsonify(script);

		    
	}
	
	
	private static String jsonify(String str) {
	    return str.substring(1, str.length() - 1);
	}
}
