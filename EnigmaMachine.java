/*
 * class models the Enigma Machine
 */
public class EnigmaMachine {
	private Plugboard plugboard;
	private BasicRotor rotors[];
	private Reflector reflector;
	
	public void addPlug(char firstEndSocket, char secondEndSocket) {
		plugboard.addPlug(firstEndSocket, secondEndSocket);
	}
	
	public void clearPlugboard() {
		plugboard.clear();
	}
	
	public void addRotor(BasicRotor rotor, int slot) {
		rotors[slot%3] = rotor;
	}
	
	public BasicRotor getRotor(int slot) {
		return rotors[slot%3];
	}
	
	public void addReflector(Reflector reflector) {
		this.reflector = reflector;
	}
	
	public Reflector getReflector() {
		return reflector;
	}
	
	public void setPosition(int slot, int position) {
		rotors[slot%3].setPosition(position);
	}
	
	
	public char encodeLetter(char letterIn) {
		/*
		 * Pass letterIn to plugboard
		 */
		letterIn = plugboard.substitute(letterIn);
		
		/*
		 * convert letterIn to an integer using ASCII
		 * subtract by 65 so values in range of 0 - 25
		 */
		
		int inputLetter = ( (int) letterIn ) - 65;
		

		/*
		 * pass inputLetter to first rotor
		 * pass result through to second rotor
		 * pass result through to third rotor
		 */
		for(int i=0;i<3;i++) {
			inputLetter = rotors[i].substitute(inputLetter);
		}
		
		/*
		 * pass inputLetter to reflector
		 */
		
		inputLetter = reflector.substitute(inputLetter);

		
		/*
		 * pass inputLetter to third rotor using inverse mapping
		 * pass result through to second rotor using inverse mapping
		 * pass result through to first rotor using inverse mapping
		 */
		
		for(int i=2;i>-1;i--) {
			inputLetter = rotors[i].substituteBack(inputLetter);
		}
		
		/*
		 * convert (inputLetter + 65) to a char using ASCII
		 */
		letterIn = (char) (inputLetter + 65);
		
		
		/*
		 * pass letterIn back through to plugboard to get final encoded letter
		 * rotate first rotor
		 */
		
		letterIn = plugboard.substitute(letterIn);
		rotors[0].rotate();

		
		return letterIn;
	}
	
	public String encodeMessage(String inputMessage) {
		String encodedMessage = "";
		for(char currentLetter: inputMessage.toCharArray()) {
			encodedMessage += this.encodeLetter(currentLetter);
		}
		return encodedMessage;
	}
	
	
	
	public EnigmaMachine() {
		plugboard = new Plugboard();
		rotors = new BasicRotor[3];
	}
	
	
	
}
