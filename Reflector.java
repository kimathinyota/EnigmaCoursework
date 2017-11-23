
public class Reflector extends Rotor{
	
	/*
	 * initialise(String input) method
	 * input is the type of Reflector e.g. ReflectorI or ReflectorII
	 * name of Reflector will be set to input
	 * mapping will be set to correct array based on type of reflector
	 */
	public void initialise(String reflectorType) {
		this.name = reflectorType;
		Integer[] mapOne = { 24, 17, 20, 7, 16, 18, 11, 3, 15, 23, 13, 6, 14, 10, 12, 8, 4, 1, 5, 25, 2, 22, 21, 9, 0, 19 };
		Integer[] mapTwo = { 5, 21, 15, 9, 8, 0, 14, 24, 4, 3, 17, 25, 23, 22, 6, 2, 19, 10, 20, 16, 18, 1, 13, 12, 7, 11 };
		if(reflectorType.equals("ReflectorI")) {
			this.mapping = mapOne;
		}else {
			this.mapping = mapTwo;
		}
	}
	
	public int substitute(int arrayPos) {
		return this.mapping[arrayPos];
	}
}
