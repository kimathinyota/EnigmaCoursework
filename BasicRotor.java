
public class BasicRotor extends Rotor{

	public void initialise(String rotorType) {
		Integer mappingI[] = { 4, 10, 12, 5, 11, 6, 3, 16, 21, 25, 13, 19, 14, 22, 24, 7, 23, 20, 18, 15, 0, 8, 1, 17, 2, 9 };
		Integer mappingII[] = { 0, 9, 3, 10, 18, 8, 17, 20, 23, 1, 11, 7, 22, 19, 12, 2, 16, 6, 25, 13, 15, 24, 5, 21, 14, 4 };
		Integer mappingIII[] = { 1, 3, 5, 7, 9, 11, 2, 15, 17, 19, 23, 21, 25, 13, 24, 4, 8, 22, 6, 0, 10, 12, 20, 18, 16, 14 };
		Integer mappingIV[] = {4, 18, 14, 21, 15, 25, 9, 0, 24, 16, 20, 8, 17, 7, 23, 11, 13, 5, 19, 6, 10, 3, 2, 12, 22, 1 };
		Integer mappingV[] = { 21, 25, 1, 17, 6, 8, 19, 24, 20, 15, 18, 3, 13, 7, 11, 23, 0, 22, 12, 9, 16, 14, 5, 4, 2, 10 };
		
		switch(this.name) {
			case "I":
				this.mapping = mappingI;
				break;
			case "II":
				this.mapping = mappingII;
				break;
			case "III":
				this.mapping = mappingIII;
				break;
			case "IV":
				this.mapping = mappingIV;
				break;
			default:
				this.mapping = mappingV;
				break;
		}
		
	}

	public int substitute(int input) {
		if(this.getPosition()==0) {
			return mapping[input];
		}else if(this.getPosition()==10) {
			input-=10;
			
			
		}
	}
	
	public BasicRotor(String type) {
		super();
		this.name = type;
	}

}
