import java.io.*;
import java.util.ArrayList;

public class Main {

	public static void main (String [] args) throws IOException {
		String fileNames [] = {
				"test_data/testdata1.txt",
				"test_data/testdata2.txt",
				"test_data/testdata3.txt",
				"test_data/testdata4.txt"};
		
		ArrayList<ArrayList<Process> > processStorage = new ArrayList < ArrayList<Process> >();
		FileReader fileRead = null;
		BufferedReader buffer = null;
		String PID;
		String burst_time;
		String priority;
		
		for(int i = 0; i < 4; i++) {
			fileRead = new FileReader(fileNames[i]);
			buffer = new BufferedReader(fileRead);
			ArrayList<Process> newList = new ArrayList<Process>();
			while( (PID = buffer.readLine())!= null) {
				 burst_time = buffer.readLine();
				 priority = buffer.readLine();
				Process ps = new Process(Integer.parseInt(PID), Integer.parseInt(burst_time), Integer.parseInt(priority));
				newList.add(ps);
//				System.out.println(ps);
			}
//			System.out.println();
			//Store the ArrayList of processes in a file in the storage array
			processStorage.add(i,newList);
			fileRead.close();
			buffer.close();
		}
		
		
		ArrayList<Process> RR1 = null;
		ArrayList<Process> RR2 = null;
		
		//Each algorithm was tested separately.
		for(int j = 0; j < 4; j++) {
//			testFCFS
			FCFS alg = new FCFS(processStorage.get(j), fileNames[j].substring(10,19));
			FCFS.run();
			
//			testSJF
			ShortestJobFirst s = new ShortestJobFirst(processStorage.get(j), fileNames[j].substring(10,19));
			s.run();
			
			//The current ArrayList is cloned here because the RoundRobin 			
			RR1 = new ArrayList<>();
			RR2 = new ArrayList<>();
			for(int e = 0; e < processStorage.get(j).size(); e++) {
				RR1.add(e, new Process(processStorage.get(j).get(e)));
				RR2.add(e, new Process(processStorage.get(j).get(e)));
			}
			
			
//			testRR20
			RoundRobin r1 = new RoundRobin(RR1, fileNames[j].substring(10,19), 20);
			r1.run();
//
//			testRR40
			RoundRobin r2 = new RoundRobin(RR2, fileNames[j].substring(10,19), 40);
			r2.run();


//			testLottery40
			Lottery l = new Lottery(processStorage.get(j),fileNames[j].substring(10,19));
			l.run();
		}
		

	}
}
