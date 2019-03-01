import java.io.*;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Author: Kevin Yao
 * Class: FCFS - First Come First Serve
 * Purpose: Implementation of a FCFS scheduling algorithm used in operating systems.
 * */
public class FCFS {
	static final int processSwitch = 3;
	public static ArrayList<Process> processes;
	public static ArrayList<ProcessRecord> CSV;
	public static String file;
	public static String CSVHeader = "CpuTime,PID,StartingBurstTime,EndingBurstTime,CompletionTime\n";
	
	public FCFS(ArrayList<Process> list, String fileName) {
		processes = list;
		CSV = new ArrayList<ProcessRecord>();
		file = fileName;
	}
	
	/**
	 * Processes the list of processes using a FCFS scheduling algorithm.
	 * Outputs four CSV files with values
	 * */
	public static void run() throws IOException{
		int CPU = 0;
		int startingBT = 0;
		int endingBT = 0;
		int completionT = 0;
		String fileName = "firstcomefirstserve-"+file+".csv";
		
		Process p = null;
		for(int i = 0; i < processes.size(); i++) {
			ProcessRecord instance = new ProcessRecord();
			p = processes.get(i);
			instance.CPU = CPU; //Store CPU time
			instance.PID = p.PID; //Store process PID
			instance.startBT = p.burstTime; //Store process start burst time
			instance.endBT = 0; //Store process ending burst time. Always 0
			instance.completionT = CPU + p.burstTime; //Stores completion time to this process
			System.out.println(instance);
			CSV.add(instance);
			CPU += p.burstTime + 3;
		}
//		System.out.println("End calculation");
		int total = 0;
		int numberNonZero = 0;
		for(ProcessRecord rec: CSV) {
			if(rec.completionT > 0) {
				total += rec.completionT;
				numberNonZero++;
			}
		}
		int averageTurnAround = total/numberNonZero;
		
		//Output to CSV file
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
