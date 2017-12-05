import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

public class EnigmaFile {
	
	public String mostRecentReflectorTypeRead;
	
	public String readInputFile(String fileName){ // returns contents of file if file exists or null if file doesn't exist
		
		StringBuilder fileSource = new StringBuilder();
		
		try{
			
			BufferedReader currentFileBuffer = new BufferedReader(new FileReader(fileName));
			
			String line = currentFileBuffer.readLine();
			
			while (line != null) {
			
				fileSource.append(line+" ");
				
				line = currentFileBuffer.readLine();
			}
			
			currentFileBuffer.close();
			
		}catch (Exception e){
			
			System.err.format("Exception occurred trying to read '%s'.", fileName);
			
			e.printStackTrace();
			
			return null;
		}
		
		return fileSource.toString().substring(0, fileSource.length() - 1 );
	
	}
	
	public boolean isCapitalLetter(char input) {
		
		int asciiLetter = (int) input;
		
		if( (asciiLetter - 65) >= 0 && (asciiLetter - 65) < 26 ) {
			
			return true;
		
		}
	
		return false;
		
	}
	
	public String formatInputFile(String fileName) {
		
		String fileSource = readInputFile(fileName);
		String formatFile = "";
		
		if(fileSource!=null) {
			for(int i=0; i<fileSource.length(); i++) {
				if(isCapitalLetter( fileSource.charAt(i) )  ) {
					formatFile += fileSource.charAt(i);
				}
			}
		}
		
		return formatFile;
		
	}
	
	public String[] readPlugs(Scanner userInput)  {
		
		if(userInput==null) {
			
			userInput = new Scanner(System.in);
			
		}
		
		System.out.println("Enter the following (seperated as lines): \n "
				+ "* number of plugs (n) \n "
				+ "* all n plugs e.g. A-F (seperated as lines) \n "
				+ "* use ? to indicate unknown plug (only for Bombe)" );
		
		int numOfPlugs = Integer.parseInt(userInput.nextLine());
		
		if(numOfPlugs>13) {
			numOfPlugs = 13;
		}
		
		/*
		 * Iterate through each of the plug input lines
		 * ...access sockets for each line and add plug to machine (enigma)
		 */
		
		String plug;
		
		String plugs[] = new String[numOfPlugs];
		
		for(int i=0;i<numOfPlugs;i++) {
			
			plug = userInput.nextLine().toUpperCase();
			
			plugs[i] = plug;
			
		}
		
		return plugs;
		
	}
	
	public String[][] readRotors(Scanner userInput){
		
		if(userInput==null) {
			userInput = new Scanner(System.in);
		}
		
		System.out.println("Enter all rotors in slot order (seperated as lines): \n"
				+ "...in form B/T(basic/turnover)-rotorType(I,II,..)-intitialPosition(integer) \n"
				+ "...e.g. B-II-21 or T-I-9 \n" 
				+ "...use ? if position or type is unknown (only for Bombe) ");
		
		String type;
		String classType;
		String position;
		String currentRotor;
		
		/*
		 * rotorInput array
		 * stores {classType(B/T),type(I,II..),position} 
		 * ... for the 3 rotors
		 */
		
		String[][] rotorInput = new String[3][3]; 
		
		/*
		 * iterate through input rotor lines:
		 * ... find classType, type, and position for each line
		 * ... set rotorInput[x] = {classType,type,position} for each line (x = 0 to x = 2)
		 */
		
		for(int i=0;i<3;i++) {
			currentRotor = userInput.nextLine();
			type = currentRotor.split("-")[1];
			classType = currentRotor.split("-")[0];
			position = currentRotor.split("-")[2];
			String rotor[] = {classType,type,position};
			rotorInput[i] = rotor;
		}
		
		return rotorInput;
		
	}
		
	public String returnReflectorType(Scanner userInput) {
	
		System.out.println("Enter reflector type e.g. I or II");
		
		String type = userInput.nextLine();
		
		if(!type.equals("I") && !type.equals("II")) {
			
			type = "?";
			
		}
		
		this.mostRecentReflectorTypeRead = type;
		
		return type;
	}
	
	public void setEqual(String[] input, String[] other) {

		for(int i=0;i<other.length;i++) {
			
			input[i] = other[i];
			
		}
	}
	
	public void readAllUserInput(RotorUnknowns foundRotors, Reflector reflector, String plugs[], Scanner userInput) {
		
		foundRotors.intitialiseRotors(this.readRotors(userInput));
		
		String type = this.returnReflectorType(userInput);
		
		if( !type.equals("?") ) {
			
			reflector.initialise(type);
			
		}else {
			
			reflector = null;
			
		}
			

		setEqual(plugs, this.readPlugs(userInput) );
		
		
		
	}
		
	public int determineSetOfPlugs(ArrayList<Plug> foundPlugs, StringBuilder knownEnds, String[] plugs) throws Exception {	
		
		knownEnds.delete(0, knownEnds.length());
		
		int unknownCount = 0;
		
		char endOne,endTwo;
		/*
		 *  Determine how many unknown plug ends exists within input
		 *  Plugs can either have 0 , 1 or 2 unknowns
		 *  Code will split input plugs into these categories
		 *  0 unknown plugs ==> added to foundPlugs arraylist
		 *  1 unknown plugs ==> known plug end is appended to knownEnds string
		 *  2 unknown plugs ==> unknownCount is incremented by 1
		 */
		
		
		for(String plug: plugs) {
			
			if( plug != null ) {
				
				endOne = plug.charAt(0);
			
				endTwo = plug.charAt(2);
				
				if(endOne!='?' && endTwo!='?') {
					
					if( !( isCapitalLetter( endOne ) ) | !( isCapitalLetter( endTwo ) ) ) {
						throw new Exception("Error: Unrecognised plug end used ");
					}
					
					foundPlugs.add(new Plug(endOne,endTwo));
					
				}else if(endOne=='?' && endTwo!='?')  {
					
					if(knownEnds!=null) knownEnds.append( endTwo );
					
					
				}else if(endOne!='?' && endTwo=='?')  {
					
					if(knownEnds!=null) knownEnds.append( endOne );
			
					
				}else if(endOne=='?' && endTwo=='?')  {
					
					unknownCount+=1;
				}
				
			}
			
		}
		
		return unknownCount;
		
	}

	public int determineSetOfPlugs(String[] inputReadPlugs, ArrayList<Plug>returnedPlugs) throws Exception {
		
		StringBuilder knownEnds = new StringBuilder("");
		
		int unknownCount =  this.determineSetOfPlugs(returnedPlugs, knownEnds, inputReadPlugs);
		
		if(unknownCount > 0 | knownEnds.length()>0 ) {
			
			throw new Exception("Error: Plug ends must be in the form of A-Z for encryption");
		
		}
		
		return unknownCount;
		
		
	}
	
	public EnigmaMachine buildEnigmaMachineFromInput(Scanner userInput) throws Exception {
		
		EnigmaMachine machine = new EnigmaMachine();
		
		if(userInput==null) {
			userInput = new Scanner(System.in);
		}
		
		String[] plugs = new String[13];

		RotorUnknowns getRotors = new RotorUnknowns();

		Reflector reflector = new Reflector("I");
		
		ArrayList<Plug>foundPlugs = new ArrayList<Plug>();
		
		this.readAllUserInput(getRotors, reflector, plugs, userInput);
		
		this.determineSetOfPlugs(plugs, foundPlugs);
		
		BasicRotor[] rotors = getRotors.getRotors();
		
		for(Plug plug: foundPlugs) {
			
			machine.addPlug(plug.getEnd1(), plug.getEnd2());
			
		}
		
		for(int i=0;i<3;i++) {
			
			if(rotors[i]==null) {
				
				throw new Exception("Error: Rotors are missing or incorrect");
				
			}
			
			machine.addRotor(rotors[i], i);
		}
		
		machine.addReflector(reflector);
		
		return machine;
	}
	
	public void encryptMessage(Scanner userInput) throws Exception {
		
		
		if(userInput==null) {
			userInput = new Scanner(System.in);
		}
		
		 
		EnigmaMachine machine = this.buildEnigmaMachineFromInput(userInput);
		
		/*
		 * Read fileName and create full file extension
		 * Set fileSource = contents of input file
		 */
		
		System.out.println("Enter name of file containing de(en)crypted message");
		
		String fileName = userInput.nextLine()+".txt";
		
		String fileSource = readInputFile(fileName);
		
		if(fileSource!=null) {
			fileSource = fileSource.toUpperCase();
		}
	
		System.out.println("You inputted: " + fileSource);
		
		System.out.println("En(/De)crypted message: " + machine.encodeMessage(fileSource));
	
	}
	
}
