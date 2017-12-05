package parsers;
import enumerations.OPCODE;

public class OpcodeParser {

	public static OPCODE parse(String data_hex) {
		
		String op_hex= data_hex.substring(0, 2);
		int op_int = Integer.parseInt(op_hex, 16); 
		
		switch(op_int) {
		case 0:
			return OPCODE.PING;
		case 1: 
			return OPCODE.DELETE;
		case 2: 
			return OPCODE.HTTP;
		case 3: 
			return OPCODE.REG;
		case 4: 
			return OPCODE.EXEC;
		default:
			return OPCODE.UNKNOWN;
		}
		
	}
}
