
public class Bombe {
	//find Plugs
	public void findPlugs() {
		BasicRotor firstRotor = new BasicRotor("IV");
		firstRotor.setPosition(8);
		BasicRotor secondRotor = new BasicRotor("III");
		secondRotor.setPosition(4);
		BasicRotor thirdRotor = new BasicRotor("II");
		thirdRotor.setPosition(21);
		Reflector reflector = new Reflector("I");
		String message = "JBZAQVEBRPEVPUOBXFLCPJQSYFJI";
		String encryptedMessage;
		for(int i=0;i<26;i++) {
			for(int j=0;j<26;j++) {
				EnigmaMachine newMachine = new EnigmaMachine();
				newMachine.addReflector(reflector);
				newMachine.addRotor(firstRotor, 0);
				newMachine.addRotor(secondRotor, 1);
				newMachine.addRotor(thirdRotor, 2);
				newMachine.addPlug('D', (char) (i+ 65) );
				newMachine.addPlug('S', (char) (j+ 65) );
				encryptedMessage = newMachine.encodeMessage(message);
				if(encryptedMessage.contains("ANSWER")) {
					System.out.println("Encrypted Message: " +encryptedMessage);
					System.out.println("Plug: [D-" + ((char) (i+ 65)) + "]");
					System.out.println("Plug: [S-" + ((char) (j+ 65)) + "]");
				}
			}
		}
			
	}
	
	public void findRotorPositions() {
		BasicRotor firstRotor = new BasicRotor("V");
		BasicRotor secondRotor = new BasicRotor("III");
		BasicRotor thirdRotor = new BasicRotor("II");
		Reflector reflector = new Reflector("I");
		Plug plugOne = new Plug('H','L');
		Plug plugTwo = new Plug('G','P');
		
				
		String message = "AVPBLOGHFRLTFELQEZQINUAXHTJMXDWERTTCHLZTGBFUPORNHZSLGZMJNEINTBSTBPPQFPMLSVKPETWFD";
		String encryptedMessage;
		for(int i=0;i<26;i++) {
			for(int j=0;j<26;j++) {
				for(int k=0;k<26;k++) {
					firstRotor.setPosition(i);
					secondRotor.setPosition(j);
					thirdRotor.setPosition(k);
					EnigmaMachine newMachine = new EnigmaMachine();
					newMachine.addReflector(reflector);
					newMachine.addRotor(firstRotor, 0);
					newMachine.addRotor(secondRotor, 1);
					newMachine.addRotor(thirdRotor, 2);
					newMachine.addPlug('H','L');
					newMachine.addPlug('G','P');
					encryptedMessage = newMachine.encodeMessage(message);
					if(encryptedMessage.contains("ELECTRIC")) {
						System.out.println("Encrypted Message: " +encryptedMessage);
						System.out.println("First Rotor position: " + i);
						System.out.println("Second Rotor position: " + j);
						System.out.println("Third Rotor position: " + k);
					}
					
				}
			}
		}
		
		
			
	}
	
	public void findRotorsUsed() {
		
		String rotors[] = {"I","II","III","IV","V"};
		

		Reflector reflector = new Reflector("I");
				
		String message = "WMTIOMNXDKUCQCGLNOIBUYLHSFQSVIWYQCLRAAKZNJBOYWW";
		String encryptedMessage;
		
		for(int i=0;i<5;i++) {
			for(int j=0;j<5;j++) {
				for(int k=0;k<5;k++) {
					BasicRotor firstRotor = new BasicRotor(rotors[i]);
					BasicRotor secondRotor = new BasicRotor(rotors[j]);
					BasicRotor thirdRotor = new BasicRotor(rotors[k]);
					firstRotor.setPosition(22);
					secondRotor.setPosition(24);
					thirdRotor.setPosition(23);
					EnigmaMachine newMachine = new EnigmaMachine();
					newMachine.addReflector(reflector);
					newMachine.addRotor(firstRotor, 0);
					newMachine.addRotor(secondRotor, 1);
					newMachine.addRotor(thirdRotor, 2);
					newMachine.addPlug('M','F');
					newMachine.addPlug('O','I');
					encryptedMessage = newMachine.encodeMessage(message);
					if(encryptedMessage.contains("JAVA")) {
						System.out.println("Encrypted Message: " +encryptedMessage);
						System.out.println("First Rotor: " + rotors[i]);
						System.out.println("Second Rotor: " + rotors[j]);
						System.out.println("Third Rotor: " + rotors[k]);
					}
				}
			}
		}
		
					
					
					
				
		
		
		
			
	}
	
	
	public static void main(String[] args) {
		Bombe breaker = new Bombe();
		breaker.findPlugs();
		breaker.findRotorPositions();
		breaker.findRotorsUsed();
	}
	
}
