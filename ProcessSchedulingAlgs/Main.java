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
		
//		testFCFS
//		FCFS alg = new FCFS(processStorage.get(0), "testdata1");
//		FCFS.run();
		
//		testSJF
//		ShortestJobFirst s = new ShortestJobFirst(processStorage.get(0), "testdata1");
//		s.run();
		
//		testRR
//		RoundRobin r = new RoundRobin(processStorage.get(0), "kevin");
//		r.run();
	}
	
//	public static void testFCFS(ArrayList<Process> list) {
//		FCFS alg = new FCFS(list, "testdata1");
//		FCFS.run();
//	}
}
