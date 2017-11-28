import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;

public class EnigmaFile {
	EnigmaMachine machine;
	public String readInputFile(String fileName){ // returns contents of file if file exists or null if file doesn't exist
		StringBuilder fileSource = new StringBuilder();
		try{
			BufferedReader currentFileBuffer = new BufferedReader(new FileReader(fileName));
			String line = currentFileBuffer.readLine();
			while (line != null) {
				fileSource.append(line);
				line = currentFileBuffer.readLine();
			}
			currentFileBuffer.close();
		}catch (Exception e){
			System.err.format("Exception occurred trying to read '%s'.", fileName);
			e.printStackTrace();
			return null;
		}
		return fileSource.toString();
	}
	
	public EnigmaFile() {
		machine = new EnigmaMachine();
		Scanner userInput = new Scanner(System.in);
		
		System.out.println("Enter enigma machine settings (seperated as lines): \n "
				+ "* number of plugs (n) \n "
				+ "* all n plugs e.g. A-F (seperated as lines) \n "
				+ "* all rotors in form B/T(basic/turnover)-rotorType(I,II,..)-intitialPosition(integer) \n"
				+ "   e.g. B-II-21 or T-I-9  (rotors must be seperated as lines) \n "
				+ "* reflector type e.g. I or II \n * file name for input file");
		
		
		int numOfPlugs = Integer.parseInt(userInput.nextLine());
		
		/*
		 * Iterate through each of the plug input lines
		 * ...access sockets for each line and add plug to machine (enigma)
		 */
		
		String plug;
		for(int i=0;i<numOfPlugs;i++) {
			plug = userInput.nextLine().toUpperCase();
			machine.addPlug(plug.charAt(0),plug.charAt(2));
		}
		
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
		
		/*
		 * use rotorInput to create necessary TurnoverRotor or BasicRotor objects 
		 * ..then add them to appropriate slot positions within the (enigma) machine
		 * iterate from 2 ==> 0 in order to easily instantiate TurnoverRotor using valid nextRotor
		 */
		BasicRotor nextRotor = null; 
		
		for(int i=2;i>-1;i--) {
			if(rotorInput[i][0].equals("T")) {
				TurnoverRotor newRotor = new TurnoverRotor(rotorInput[i][1],nextRotor);
				newRotor.setPosition(Integer.parseInt(rotorInput[i][2]));
				nextRotor = newRotor;
				machine.addRotor(newRotor, i);
			}else {
				BasicRotor newRotor = new BasicRotor(rotorInput[i][1]);
				newRotor.setPosition(Integer.parseInt(rotorInput[i][2]));
				nextRotor = newRotor;
				machine.addRotor(newRotor, i);
			}
		}
		
		
		/*
		 * Read type of reflector:
		 * Instantiate a new reflector
		 * Add above reflector to machine
		 */
		type = userInput.nextLine();
		Reflector reflector = new Reflector(type);
		machine.addReflector(reflector);
		
		/*
		 * Read fileName and create full file extension
		 * Set fileSource = contents of input file
		 */
		String fileName = userInput.nextLine()+".txt";
		String fileSource = readInputFile(fileName);
	
		System.out.println("You inputted: " + fileSource);
		System.out.println("En(/De)crypted message: " + machine.encodeMessage(fileSource));

	}
	/*
	public static void main(String[] args) {
		EnigmaFile myEnigmaFile = new EnigmaFile();
	}
	*/
}
