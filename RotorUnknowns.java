import java.util.Arrays;
import java.util.HashSet;

public class RotorUnknowns {
	
	private String[][] rotorInput;
	
	public String getAllRotorSlotNumbersWithOnlyUnkownPositions(){
		
		String rotors = "";
		for(int i=0;i<3;i++) {
			if(this.rotorInput[i][2].equals("?") && !this.rotorInput[i][1].equals("?")) {
				rotors+=i;
			}
		}
		return rotors;
	}
	
	public String getAllRotorSlotNumbersWithOnlyUnkownTypes(){
		String rotors = "";
		for(int i=0;i<3;i++) {
			if(this.rotorInput[i][1].equals("?") && !this.rotorInput[i][2].equals("?") ) {
				rotors+=i;
			}
		}
		return rotors;
	}
	
	public String getAllRotorSlotNumbersWithBothUnkowns(){
		String rotors = "";
		for(int i=0;i<3;i++) {
			if(this.rotorInput[i][1].equals("?") && this.rotorInput[i][2].equals("?")) {
				rotors+=i;
			}
		}
		return rotors;
	}
		
	public int getInputRotorPosition(int slot) {
		return Integer.parseInt(rotorInput[slot][2]);
	}
	
	public String getInputRotorType(int slot) {
		return rotorInput[slot][1];
	}
	
	public boolean isNumeric(String input) {  
	    return input != null && input.matches("[-+]?\\d*\\.?\\d+");  
	} 
	
	public boolean isValidRotorType(String input) {
		String rotors[] = {"I","II","III","IV","V"};
		HashSet<String> possiblePositions = new HashSet<String>( Arrays.asList(rotors) );
		return possiblePositions.contains(input);
		
		
	}
	
	public void displayRotors() {
		for(int i=0; i<3; i++) {
			for(int j=0; j<3; j++) {
				System.out.println( rotorInput[i][j] );
			}
		}
	}
	
	public boolean isValidRotorInput(String type, String position) {
		return isNumeric(position) && isValidRotorType(type);
	}
	
	public BasicRotor[] getRotors() {
		
		BasicRotor nextRotor = null; 
		
		BasicRotor[] rotors = new BasicRotor[3];
		
		
		for(int i=2;i>-1;i--) {

			if( isValidRotorInput(rotorInput[i][1],rotorInput[i][2]) && rotorInput[i][0].equals("T") ) {
				TurnoverRotor newRotor = new TurnoverRotor(rotorInput[i][1],nextRotor);
				newRotor.setPosition(Integer.parseInt(rotorInput[i][2]));
				nextRotor = newRotor;
				rotors[i] = newRotor;
			}else if( isValidRotorInput(rotorInput[i][1],rotorInput[i][2]) && rotorInput[i][0].equals("B")   ){
				BasicRotor newRotor = new BasicRotor(rotorInput[i][1]);
				newRotor.setPosition(Integer.parseInt(rotorInput[i][2]));
				nextRotor = newRotor;
				rotors[i] = newRotor;
			}
		}
		
		return rotors;
		
	}
		
	public void intitialiseRotors(String[][] rotorInput) {
		
		for(int i=0;i<3;i++) {
			
			for(int j=0;j<3;j++) {
				
				this.rotorInput[i][j] = rotorInput[i][j];
				
			}
			
		}
		
	}
	
	public RotorUnknowns() {
		
		this.rotorInput = new String[3][3];
		
	}
	
}
