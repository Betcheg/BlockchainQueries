package parsers;

public class IpParser {

	public static String parse(String data_hex) {

		String ip ="";
		
		ip +=  Integer.toString(Integer.parseInt(data_hex.substring(2, 4), 16)) ;
		ip +=".";
		ip +=  Integer.toString(Integer.parseInt(data_hex.substring(4, 6), 16)) ;
		ip +=".";
		ip +=  Integer.toString(Integer.parseInt(data_hex.substring(6, 8), 16)) ;
		ip +=".";
		ip +=  Integer.toString(Integer.parseInt(data_hex.substring(8, 10), 16)) ; 
		
		return ip;
	}
}
