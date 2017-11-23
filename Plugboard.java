import java.util.ArrayList;

/*
 * Plugboard sits at front of the machine
 * Jt contains 26 sockets (one for each letter of the alphabet)
 * A maximum of 13 plugs can be inserted to the plugboard given no clashes
 * Plugboard provides first encoding mechanism
 */
public class Plugboard {
	
	private ArrayList<Plug> plugs;
	/*
	 * addPlug(char firstEnd, char secondEnd) method
	 * creates Plug given input
	 * adds created Plug to plugs given there are no clashes
	 * returns true if plug can be added
	 */
	public boolean addPlug(char firstEnd, char secondEnd) {
		
		Plug newPlug = new Plug(firstEnd,secondEnd);
		/*
		 * iterate through ArrayList plugs to check if input plug clashes with any plug
		 * size of plugs can't be > 12
		 */
		for(Plug currentPlug : plugs) {
			
			if(currentPlug.clashesWith(newPlug) | plugs.size() > 12 ) {
				return false;
			}
			
		}
		
		plugs.add(newPlug);
		
		return true;
	}
	
	public int getNumPlugs() {
		
		return plugs.size();
		
	}
	
	public void clear() {
		
		plugs.clear();
		
	}
	
	/*
	 * substitute(char letterIn)
	 * returns encoded letterIn if any plug is connected to current letterIn
	 * else returns letterIn
	 */
	
	public char substitute(char letterIn) {
		
		char encodedLetter;
		
		for(Plug currentPlug : plugs) {
			
			encodedLetter = currentPlug.encode(letterIn);
			
			if(encodedLetter!=letterIn) {
				return encodedLetter;
			}
			
		}
		
		return letterIn;
	}
	
	public Plugboard() {
		
		plugs = new ArrayList<Plug>();
		
	}
	
}
