import java.util.*;


public class Bombe {
	
	private ArrayList<String> commonWords;
	private EnigmaFile readUserInput;
	
	private String rotorsTypes[] = {"I","II","III","IV","V"};
	
	public int returnCommonWordFrequency(String inputMessage) {
		
		int score = 0;
		
		for(String word: commonWords) {
			
			if(inputMessage.contains(word.toUpperCase())) {
				
				score+=1;
				
			}
			
		}
		
		return score;
		
	}
	
	public int returnSearchCriteriaFrequency(String inputMessage, String[] searchCriteria) {
		
		if(searchCriteria==null) {
			
			return 1;
			
		}
		
		int score = 0;
		
		for(String word: searchCriteria) {
			
			if(inputMessage.contains(word.toUpperCase())) {
				
				score+=1;
				
			}
			
		}
		
		return score;
		
	}
	
	public Integer[][] returnCopy(Integer[][]a){
		
		int row = a.length;
		
		int col = 2;
		
		if (row > 0) col = a[0].length;
		
		Integer[][]c = new Integer[row][col];
		
		for(int i=0;i<row;i++) {
			
			for(int j=0;j<col;j++) {
				
				c[i][j] = a[i][j];
				
			}
			
		}
		
		return c;
		
	}
	
	public BasicRotor returnBasicRotorCopy(BasicRotor temp) {
		
		BasicRotor newRotor = new BasicRotor(temp.name);
		
		newRotor.setPosition(temp.getPosition());
		
		return newRotor;
		
	}
	
	public BasicRotor[] returnBasicRotorArrayCopy(BasicRotor[]a){
		
		int row = a.length;
		
		BasicRotor[]c = new BasicRotor[row];
		
		for(int i=0;i<row;i++) {
			
			c[i] = returnBasicRotorCopy( a[i] ) ;
			
		}
		
		return c;
		
	}
	
	public String addPlugsToInputMachine(EnigmaMachine inputMachine, ArrayList<Plug> foundPlugs ) throws Exception {
		
		String plugList = "";
		
		for(Plug plug: foundPlugs) {
			
			inputMachine.addPlug(plug.getEnd1(), plug.getEnd2());
			
			plugList+= plug.getEnd1()+"-"+plug.getEnd2()+ " ";
			
		}
		
		return plugList;
	}

	/*
	 * decryptInput(...):
	 * brute-force approach to this problem
	 */

	public BasicRotor[] addRotorsToMachine(int i, EnigmaMachine newMachine, String rotorSlots, String rotorType, Integer rotorPosition, BasicRotor[] currentRotors, Integer[] counters, RotorUnknowns foundRotors) {
		
		String type = null;
		
		Integer position = null;
		
		for(int k=i;k<i+rotorSlots.length();k++) {
			
			int rotorSlot = Character.getNumericValue(rotorSlots.charAt(k-i));
			
			if(rotorType==null && rotorPosition==null) {

				type = rotorsTypes[ counters[k] ];
				
				position = counters[ k+rotorSlots.length() ];
				
			}else if( rotorType==null | rotorPosition==null) {
				
				if(rotorPosition==null) {
					
					position =  counters[k] ;
					
				}else if(rotorPosition < 0) {
					
					position =  foundRotors.getInputRotorPosition( rotorSlot );
					
					
				}
				
				if(rotorType==null) {
					
					type = rotorsTypes[ counters[k] ];

					
				}else if(rotorType.contains("found")) {
					
					type = foundRotors.getInputRotorType(rotorSlot);

				}

			}	
	
			BasicRotor rotor = new BasicRotor( type  );
			
			rotor.setPosition( position );
			
			currentRotors[rotorSlot] = rotor;
			
			newMachine.addRotor(rotor,rotorSlot);

		} 
		
		return currentRotors;
		
	}

	public String decryptInput(String message,String[]searchCriteria, boolean checkCommonWords, Scanner userInput) throws Exception {
		
		RotorUnknowns foundRotors = new RotorUnknowns(  );
		
		Reflector reflector = new Reflector("I");
		
		String[]plugs = new String[13];
	
		ArrayList<Plug> foundPlugs = new ArrayList<Plug>();		
			
		readUserInput.readAllUserInput(foundRotors, reflector, plugs, userInput);
		
		StringBuilder knownEnds = new StringBuilder( "" );
		
		int unknownCount = 0;
		
		unknownCount = readUserInput.determineSetOfPlugs(foundPlugs, knownEnds, plugs);
		
		/*
		 * Determine the slot numbers for rotors with only a single unknown position
		 * Determine the unknown types
		 * Determine the exact positions of unknown rotor types
		 */
		
		String unknownPositionsRotorSlots = foundRotors.getAllRotorSlotNumbersWithOnlyUnkownPositions();
		
		String unknownTypesRotorSlots = foundRotors.getAllRotorSlotNumbersWithOnlyUnkownTypes();
		
		String bothUnknownRotorSlots = foundRotors.getAllRotorSlotNumbersWithBothUnkowns();
		
		System.out.println("Unknown position slots: " + unknownPositionsRotorSlots);
		
		int reflectorCounter = 0;
				
		if( readUserInput.mostRecentReflectorTypeRead.equals("?") ) {
			reflectorCounter+=1;
		}

		int counterSize = (unknownCount * 2) + knownEnds.length() + unknownPositionsRotorSlots.length() 
							+ unknownTypesRotorSlots.length() + reflectorCounter 
							+ (2*bothUnknownRotorSlots.length() );	
		
		Counters currentCounterSet = new Counters( counterSize );
			
		int unknownRotorPositionsCounterStart = knownEnds.length();
		
		int unknownRotorTypesCounterStart = unknownRotorPositionsCounterStart + unknownPositionsRotorSlots.length();
		
		int reflectorCounterStart = unknownRotorTypesCounterStart + unknownTypesRotorSlots.length();
		
		int twoUnknownPlugsCounterStart = reflectorCounterStart + reflectorCounter;
		
		int typesForBothUnknownRotorsCounterStart = twoUnknownPlugsCounterStart + (unknownCount * 2);
		
		int positionsForBothUnknownRotorsCounterStart = typesForBothUnknownRotorsCounterStart + bothUnknownRotorSlots.length();
		
		Counter onlyEndsKnown = new Counter ("Known Ends", 0, unknownRotorPositionsCounterStart-1, 26 );
		Counter unknownRotorPositions = new Counter ("Unknown rotor positions", unknownRotorPositionsCounterStart, unknownRotorTypesCounterStart-1, 26 );
		Counter unknownRotorTypes = new Counter ("Unknown rotor types", unknownRotorTypesCounterStart, reflectorCounterStart-1, 5 );
		Counter counterForReflector = new Counter ("Reflector", reflectorCounterStart, twoUnknownPlugsCounterStart-1, 2 );
		Counter twoUnknownPlugs = new Counter ("Two unknown plugs", twoUnknownPlugsCounterStart, typesForBothUnknownRotorsCounterStart-1, 26 );
		Counter typesForBothUnknownRotors = new Counter ("Types both unknowns", typesForBothUnknownRotorsCounterStart, positionsForBothUnknownRotorsCounterStart - 1, 5 );
		Counter positionsForBothUnknownRotors = new Counter ("Positions both unknowns", positionsForBothUnknownRotorsCounterStart, counterSize - 1, 26 );

		Counter inputCounters[] = { 
				
				onlyEndsKnown, unknownRotorPositions,  unknownRotorTypes, counterForReflector, twoUnknownPlugs,
				typesForBothUnknownRotors, positionsForBothUnknownRotors
									
		};
		
		currentCounterSet.addCounters(inputCounters);
		
		

		
		int totalNumberOfIterations =  (int) ( Math.pow(26, ( unknownCount * 2 ) + unknownPositionsRotorSlots.length() + knownEnds.length() ) 
									 		* Math.pow(5, unknownTypesRotorSlots.length() )  * Math.pow(2, reflectorCounter ) 
									 		* Math.pow(26, bothUnknownRotorSlots.length() ) 
									 		* Math.pow(5, bothUnknownRotorSlots.length() )  
									 		);
		String userResponse = "YES";

		
		
		System.out.println("This program will check " + totalNumberOfIterations + " different arrangements" );
		
		
		String foundMessage = null;
		
		if(totalNumberOfIterations==1) {
			
			BasicRotor[] rotors = foundRotors.getRotors();
			
			EnigmaMachine newMachine = new EnigmaMachine();
				
			newMachine.addRotors(rotors);
			
			this.addPlugsToInputMachine(newMachine, foundPlugs);
			
			newMachine.addReflector(reflector);
			
			foundMessage = newMachine.encodeMessage(message);
			
			System.out.println("Decrypted Message: " +  foundMessage);
			
			System.out.println("System settings: Same as input \n");
			
			return foundMessage;
			
			
		}
		
		
		if( (totalNumberOfIterations >= Math.pow(26, 5) && !checkCommonWords) | (totalNumberOfIterations >= ( Math.pow(26, 5) / 1000 ) && checkCommonWords) ) {
			
			System.out.println("Are you sure you want to continue with calculation?:");
			
			userResponse = userInput.nextLine();
			
			
		}
		
		String plugList, savedPlugList = null;
		String displayRotors = null, finalReflector = null;
		int decryptedMessageScore = 0;
		String encryptedMessage;
		int highestScore = -1;
		
		while( !currentCounterSet.isFinishedIterating() && userResponse.toUpperCase().contains("Y") ) {
			
			BasicRotor[] rotors = foundRotors.getRotors();
			
			EnigmaMachine newMachine = new EnigmaMachine();
				
			newMachine.addRotors(rotors);
			
			plugList = this.addPlugsToInputMachine(newMachine, foundPlugs);
			
			Integer[] tempCounters = currentCounterSet.returnCounters();
			
			for(int i=0; i<tempCounters.length;i++) {

				if(currentCounterSet.isPositionWithinCounterRange(i, "Known Ends")) {
				
					newMachine.addPlug(knownEnds.charAt(i), (char) (tempCounters[i]+ 65) );
					plugList += knownEnds.charAt(i) +"-" + (char) (tempCounters[i]+ 65)+ " ";
					
				}else if(currentCounterSet.isPositionWithinCounterRange(i, "Unknown rotor positions")) {
	
					rotors = this.addRotorsToMachine(i, newMachine, unknownPositionsRotorSlots, "found" , null, rotors, tempCounters, foundRotors);
					i+=unknownPositionsRotorSlots.length()-1;

				}else if(currentCounterSet.isPositionWithinCounterRange(i, "Unknown rotor types")) {

					rotors = this.addRotorsToMachine(i, newMachine, unknownTypesRotorSlots, null , -1, rotors, tempCounters, foundRotors);

					i+=unknownTypesRotorSlots.length()-1;
					
				}else if(currentCounterSet.isPositionWithinCounterRange(i, "Reflector")) {
					
					reflector = new Reflector(rotorsTypes[tempCounters[i]]);
					
				}else if(currentCounterSet.isPositionWithinCounterRange(i, "Two unknown plugs")) {
					
					newMachine.addPlug((char) (tempCounters[i]+ 65), (char) (tempCounters[i+1]+ 65) );
					plugList += (char) (tempCounters[i]+ 65) +"-" + (char) (tempCounters[i+1]+ 65)+ " ";
					i+=1;
					
				}else if(currentCounterSet.isPositionWithinCounterRange(i, "Types both unknowns") | currentCounterSet.isPositionWithinCounterRange(i, "Positions both unknowns") ){

					rotors = this.addRotorsToMachine(i, newMachine, bothUnknownRotorSlots, null , null , rotors, tempCounters, foundRotors);
					i+=(bothUnknownRotorSlots.length()*2);

				}
			}
			
			rotors = this.returnBasicRotorArrayCopy(rotors);
			
			newMachine.addReflector(reflector);
			
			encryptedMessage = newMachine.encodeMessage(message);
			
			if(checkCommonWords) {
				
				decryptedMessageScore = (this.returnCommonWordFrequency(encryptedMessage) + 1) * returnSearchCriteriaFrequency(encryptedMessage,searchCriteria);
			
			}else {
				
				decryptedMessageScore = this.returnSearchCriteriaFrequency(encryptedMessage,searchCriteria);
			
			}
			
			if(decryptedMessageScore>highestScore) {

				highestScore = decryptedMessageScore;
				
				savedPlugList = plugList;
			
				foundMessage = encryptedMessage;
				
				displayRotors = "";
				
				for(int slot = 0; slot < 3; slot++) {
					displayRotors += (slot+1) + ": " + rotors[slot].name + " - " +  rotors[slot].getPosition() + " ";
				}
							
				finalReflector = reflector.name;
			}
			
			currentCounterSet.incrementCounters();
		
		}
		
		
		System.out.println("Decrypted Message: " + foundMessage);
			
		System.out.println("Final enigma machine settings: \nPlugs: "+savedPlugList + "\nRotors: " + displayRotors
							+"\nReflector: "+finalReflector +"\n");
		
	
		return foundMessage;
		
	}
	
	public Bombe () {
		
		readUserInput = new EnigmaFile();
		
		this.commonWords = new ArrayList<String>(   Arrays.asList(   readUserInput.readInputFile("common_words.txt").split(" ")   )    );
		
	}
	
	
}
