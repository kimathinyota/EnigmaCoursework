import java.util.ArrayList;
import java.util.Arrays;

public class Counters {
	
	private Integer counters[];
	private ArrayList<Counter> counterTypes;
		
	void increment(int multiple, int i) {
		
		if(counters[i-1]%multiple==0 && counters[i-1]!=0 ) {
			
			counters[i]+=1;
			
			counters[i-1]=0;
			
		}
	}
	
	public Counter returnCounterForCurrentPosition(int currentPosition) {
		for(Counter temp: counterTypes) {
			if(temp.isCorrectCounter(currentPosition)) {
				return temp;
			}
		}
		return null;
	}
	
	public Counter returnCounterByLabel(String label) {
		for(Counter temp: counterTypes) {
			if(temp.getLabel().equals(label)) {
				return temp;
			}
		}
		return null;
	}
	
	
	public void incrementCounters() {
		counters[0]+=1;
		Counter relevantCounter;
		for(int i=1; i<counters.length;i++) {
			relevantCounter = this.returnCounterForCurrentPosition(i-1);
			this.increment( relevantCounter.getMultiple() , i);
		}
		
	}
	
	public void addCounter(String label, int startPointer, int endPointer, int multiple) {
		
		Counter newCounter = new Counter(label, startPointer, endPointer, multiple);
		counterTypes.add(newCounter);
	}
	
	public boolean isFinishedIterating() {

		return counters[ counters.length - 1 ] % returnCounterForCurrentPosition( counters.length - 1).getMultiple() == 0 
			   && counters[ counters.length - 1 ]!=0;
	}
	
	public Integer[] returnCounters() {
		return counters;
	}
	
	
	
	public boolean isPositionWithinCounterRange(int currentPosition, Counter inputCounter) {
		Counter temp = this.returnCounterForCurrentPosition(currentPosition);
		return inputCounter.isEqual(temp);
	}
	
	public boolean isPositionWithinCounterRange(int currentPosition, String counterLabel) {
		Counter inputCounter = returnCounterByLabel(counterLabel);
		return this.isPositionWithinCounterRange(currentPosition, inputCounter);
	}
	
	
	public void resetCounters() {
		for(int i=0; i<counters.length; i++) {
			counters[i] = 0;
		}
	}
	
	public void addCounters(Counter[] counters) {
		counterTypes.addAll( new ArrayList<Counter>( Arrays.asList( counters ) ) );
	}
	
	public Counters(int totalNumberOfCounters) {
		counters = new Integer[totalNumberOfCounters];
		counterTypes = new ArrayList<Counter>();
		this.resetCounters();
	}
}
