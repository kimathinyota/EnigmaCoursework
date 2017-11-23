/*
 * Plug connects two sockets
 * There are only 26 sockets so there can only be up to 13 plugs connected to a plugboard
 */
public class Plug {
	private char firstEndSocket;
	private char secondEndSocket;
	
	//Declaring getters() and setters() for plug
	public char getEnd1() {
		return firstEndSocket;
	}
	public char getEnd2() {
		return secondEndSocket;
	}
	public void setEnd1(char firstEndSocket ) {
		this.firstEndSocket = firstEndSocket;
	}
	public void setEnd2(char secondEndSocket) {
		this.secondEndSocket = secondEndSocket;
	}
	
	/*
	 * encode method will return letterIn if one end doesn't match letterIn
	 * it will return the letter at the end of the plug if one end does match
	 */
	public char encode(char letterIn) {
		if(firstEndSocket==letterIn) {
			return secondEndSocket;
		}else if(secondEndSocket==letterIn) {
			return firstEndSocket;
		}
		return letterIn;
	}
	
	/*
	 * clashesWith(Plug plugin) method
	 * returns true if any end of plugin matches with any end of this plug
	 */
	public Boolean clashesWith(Plug plugin) {
		
		return ( plugin.firstEndSocket==this.firstEndSocket | plugin.firstEndSocket==this.secondEndSocket
				| plugin.secondEndSocket==this.firstEndSocket | plugin.secondEndSocket==this.secondEndSocket );

	}
	
	public Plug(char firstEndSocket, char secondEndSocket) {
		this.firstEndSocket = firstEndSocket;
		this.secondEndSocket = secondEndSocket;
	}
	
}
