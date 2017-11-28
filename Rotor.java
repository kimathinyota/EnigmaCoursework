public abstract class Rotor {
	
	protected String name;
	private int position;
	protected Integer mapping[];
	protected static final int ROTORSIZE = 26;
	
	public void setPosition(int position) {
		
		this.position = position;
		
	}
	
	public int getPosition() {
		
		return this.position;
		
	}
	
	/*
	 * initialise(String input) method:
	 */
	public abstract void initialise(String input);
	
	/*
	 * substitute(int) method:
	 */
	public abstract int substitute(int input);

		
}
