
public class TurnoverRotor extends BasicRotor {
	
	private int turnoverPosition;
	BasicRotor nextRotor;
	
	public void setNextRotor(BasicRotor nextRotor) {
		this.nextRotor = nextRotor;
	}
	
	public TurnoverRotor(String type,BasicRotor nextRotor) {
		super(type);	
		switch(type) {
			case "I":
				turnoverPosition = 24;
				break;
			case "II":
				turnoverPosition = 12;
				break;
			case "III":
				turnoverPosition = 3;
				break;
			case "IV":
				turnoverPosition = 17;
				break;
			case "V":
				turnoverPosition = 7;
				break;	
		}
		this.nextRotor = nextRotor;
	}
	
	public void rotate() {
		
		this.setPosition(this.getPosition()+1);
		
		if(this.getPosition()==turnoverPosition && nextRotor!=null) {
			
			nextRotor.rotate();
			
		}
		
		if(this.getPosition()==this.ROTORSIZE) {
			
			this.setPosition(0);
			
		}
	}

}
