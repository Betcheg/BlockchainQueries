package parsers;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import enumerations.ADDR_TYPE;
import exceptions.NotParsableException;

public class WebParser {

	public static String parse_url(String tx, ADDR_TYPE type) throws NotParsableException {
		
		String api_method = "";
		String url_content = "";
		
		if(type == ADDR_TYPE.ACCOUNT) {
			api_method = "addrs";
		}
		else if(type == ADDR_TYPE.TX) {
			api_method = "txs";
		}
		
		
		try {
			// get URL content
			String link ="https://api.blockcypher.com/v1/eth/main/"+api_method+"/0x"+tx;
			URL url = new URL(link);
			URLConnection conn = url.openConnection();

			// open the stream and put it into BufferedReader
			BufferedReader br = new BufferedReader(	new InputStreamReader(conn.getInputStream()));

			String inputLine;
			while ((inputLine = br.readLine()) != null) {
				url_content += inputLine;
			}
			br.close();

			System.out.println("Done");
			System.out.println(url_content);

		} catch (MalformedURLException e) {
			throw new NotParsableException("MalformedURLException");
		} catch (IOException e2) {
			throw new NotParsableException("IOException");
		}
		
		return url_content;
	}
}
