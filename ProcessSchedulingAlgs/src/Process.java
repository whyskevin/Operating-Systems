/**
 * Class: Process
 * Purpose: Emulates a process with a PID, burst time, and priority value.
 * */
public class Process implements Comparable{
	
	public static final int CONTEXT_SWITCHING_COST = 3;
	public int PID;
	public int burstTime;
	public int priority;
	public int rangeStart;
	public int rangeEnd;

	public Process(int x, int y, int z) {
		PID = x;
		burstTime = y;
		priority = z;
		rangeEnd = 0;
		rangeStart = 0;
	}
	
	//Copy Constructor
	public Process( Process copyMe) {
		this.PID = copyMe.PID;
		this.burstTime = copyMe.burstTime;
		this.priority = copyMe.priority;
		this.rangeStart = copyMe.rangeStart;
		this.rangeEnd = copyMe.rangeEnd;
	}
	
	/**
	 * @param Object o - most likely a Process
	 * @return The process' PID with higher priority.
	 * */
	@Override
	public int compareTo(Object o) {
		Process c = (Process) o;
//		System.out.println("Comparing" + this.toString() + " to " + c);
		if(this.burstTime > c.burstTime) {
//			System.out.println("greater");
			return 1;
		}else if (this.burstTime == c.burstTime){
//			System.out.println("equal");
			return 0;
		}
//		System.out.println("less");
		return -1;
	}
	
	//Creates an interval to be used in lottery scheduling.
	public void setRange(int startVal) {
		rangeStart = startVal;
		rangeEnd = startVal + priority;
		System.out.println(PID + " range is " + rangeStart + " - " + rangeEnd);
	}
	
	//Checks if a process is the lottery ticket.
	public boolean isTicket(int randomNumber) {
		if( randomNumber <= rangeEnd && randomNumber >= rangeStart) return true;
		return false;
	}
	
	public String toString() {
		return (PID + " , " + burstTime + " , " + priority);
	}
	
}
