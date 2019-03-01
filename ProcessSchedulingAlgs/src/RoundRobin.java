import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

public class RoundRobin {
	static final int processSwitch = 3;
	public static ArrayList<Process> processes;
	public static ArrayList<ProcessRecord> CSV;
	public static String file;
	public static int timeQuanta;
	public static String fileName;
	public static String CSVHeader = "CpuTime,PID,StartingBurstTime,EndingBurstTime,CompletionTime\n";

	
	public RoundRobin(ArrayList<Process> list, String name, int quanta) {
		processes = list;
		file = name;
		CSV = new ArrayList<ProcessRecord>();
		timeQuanta = quanta;
		fileName = "roundrobin"+timeQuanta+"-"+file+".csv";
	}
	
	/**
	 * Processes the list of processes using a RR scheduling algorithm w/ a time quanta
	 * Outputs four CSV files with values
	 * */
	public static void run() {
		Queue<Process> runningQueue = new LinkedList<Process>();
		Process p = null;
		int CPU = 0;	//Should we consider overflow?
		
		for(int i = 0; i < processes.size(); i++) {
			runningQueue.add(processes.get(i));
		}
		
//			System.out.println(runningQueue);
		while(!runningQueue.isEmpty()) { //If the queue isn't empty, we continue running processes
			
			ProcessRecord instance = new ProcessRecord();
			p = runningQueue.peek(); //Look at the new process
			
				instance.CPU = CPU;
				instance.PID = p.PID;
				instance.startBT = p.burstTime;
				
				if(p.burstTime - timeQuanta < 0) { //Case: Process finishes this quanta. Has remaining time.
					CPU += p.burstTime;
					p.burstTime = 0;
					instance.completionT = CPU; //CT always 0 unless process finishes
					runningQueue.remove();
				}else if(p.burstTime - timeQuanta == 0) { //Case: Process finishes perfectly
					CPU += p.burstTime;
					p.burstTime = 0;
					instance.completionT = CPU;
					runningQueue.remove();
				}else if(p.burstTime - timeQuanta > 0) { //Case: Process doesn't finish in quanta time
					p.burstTime -= timeQuanta;
					CPU += timeQuanta;
					runningQueue.remove();
					runningQueue.add(p);
				}
				instance.endBT = p.burstTime;
				CPU += processSwitch;
				CSV.add(instance);
		}
//		for(int d = 0; d < CSV.size(); d++) {
//			System.out.println(CSV.get(d));
//		}

		int total = 0;
		int numberNonZero = 0;
		for(ProcessRecord rec: CSV) {
			if(rec.completionT > 0) {
				total += rec.completionT;
				numberNonZero++;
			}
		}
		int averageTurnAround = total/numberNonZero;
		
//		Output to CSV file
		File f = new File("test_results/"+fileName);
		try {
			FileWriter fw = new FileWriter(f);
			fw.write(CSVHeader);
			for(int j = 0; j < CSV.size(); j++) {
				fw.write(CSV.get(j).toString()+"\n");
			}
			fw.write("Average Turnaround Time,"+averageTurnAround);;
			fw.flush();
			fw.close();
		}catch(IOException e) {
			System.out.println(e.getMessage());
		}
	
	}
	
}
