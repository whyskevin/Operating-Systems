import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;

public class Lottery {
	static final int processSwitch = 3;
	public static ArrayList<Process> processes;
	public static ArrayList<ProcessRecord> CSV;
	public static String file;
	static final int timeQuanta = 40;
	public static String CSVHeader = "CpuTime,PID,StartingBurstTime,EndingBurstTime,CompletionTime\n";
	public static String fileName;

	
	public Lottery(ArrayList<Process> list, String name) {
		processes = list;
		file = name;
		CSV = new ArrayList<ProcessRecord> ();
		fileName = "lottery"+timeQuanta+"-"+file+".csv";
	}
	
	public void run() {	
		int CPU = 0;
		int maximumPriority = 0;
		//Calculate the maximum ticket value & the 
		for(Process x: processes) {
			x.setRange(maximumPriority);
			maximumPriority += x.priority;
		}
		
		Random r = new Random(); //Give random number [0,maximumPriority]
		int lotteryTicket = 0;
		
		do {
			lotteryTicket = r.nextInt(maximumPriority); //Gives us a random number
			System.out.println("Rand:" + lotteryTicket);
			for(Process p: processes) {
//				//Check if the given p is the process we're meant to run with
				if(p.isTicket(lotteryTicket)) {
					ProcessRecord instance = new ProcessRecord();
//					System.out.println(p);
					instance.CPU = CPU;
					instance.PID = p.PID;
					instance.startBT = p.burstTime;
					
					if(p.burstTime - timeQuanta < 0) { //Case: Process finishes this quanta. Has remaining time.
						CPU += p.burstTime;
						p.burstTime = 0;
						instance.completionT = CPU; //CT always 0 unless process finishes
						processes.remove(p);
						System.out.println("a");
					}else if(p.burstTime - timeQuanta == 0) { //Case: Process finishes perfectly
						CPU += p.burstTime;
						p.burstTime = 0;
						instance.completionT = CPU;
						processes.remove(p);
						System.out.println("b");
					}else if(p.burstTime - timeQuanta > 0) { //Case: Process doesn't finish in quanta time
						p.burstTime -= timeQuanta;
						CPU += timeQuanta;			
						System.out.println("c");
					}
					instance.endBT = p.burstTime;
					CPU += processSwitch;
					CSV.add(instance);
//					System.out.println(instance);
					break;
				}
			}
		}while (!processes.isEmpty());
		
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
