
public class ProcessRecord {
	public int CPU;
	public int PID;
	public int startBT;
	public int endBT;
	public int completionT;
	
	public ProcessRecord() {
		CPU = 0;
		PID = 0;
		startBT = 0;
		endBT = 0;
		completionT = 0;
	}
	
	public ProcessRecord(int a, int b, int c, int d, int e){
		CPU = a;
		PID = b;
		startBT = c;
		endBT = d;
		completionT = e;
	}
	
	public String toString() {
		return (CPU + "," + PID + "," + startBT + "," + endBT + "," + completionT);
		
	}
}
