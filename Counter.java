
public class Counter {
	
	private String label;
	private int startPointer;
	private int endPointer;
	private int multiple;
	
	public String getLabel() {
		return this.label;
	}
	
	public int getStartPointer() {
		return this.startPointer;
	}
	
	public int getEndPointer() {
		return this.endPointer;
	}
	
	public int getMultiple() {
		return this.multiple;
	}
	
	public boolean isCorrectCounter(int currentCount) {
		return (currentCount >= this.startPointer && currentCount <= this.endPointer );
	}
	
	public boolean shouldIterateCounter(int currentCount) {
		
		return isCorrectCounter(currentCount) && (currentCount % multiple == 0);
		
	}
	
	public boolean isEqual(Counter b) {
		return ( this.startPointer == b.getStartPointer() && this.endPointer == b.getEndPointer()
				 && this.label==b.getLabel() && this.multiple == b.getMultiple());
	}
	
	public Counter(String label, int startPointer, int endPointer, int multiple) {
		this.label = label;
		this.startPointer = startPointer;
		this.endPointer = endPointer;
		this.multiple = multiple;
	}
	
}
