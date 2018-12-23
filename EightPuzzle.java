import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EightPuzzle {
	
	public static void main(String[] args){
		int solved = 0;
		HillClimbingSearchAgent[] agents = new HillClimbingSearchAgent[100];
//		//p1 = Goal
//		Problem p1 = new Problem(new int[][]{
//			  { 1, 2, 3 },
//			  { 4, 0, 6 },
//			  { 7, 8, 9 }
//			});
//		agents[0] = new HillClimbingSearchAgent(p1);
//		
//		Problem p2 = new Problem(new int[][]{
//			  { 1, 0, 2 },
//			  { 4, 8, 3 },
//			  { 7, 9, 6 }
//			});
//		agents[1] = new HillClimbingSearchAgent(p2);

		for (int i = 0; i < 100; i++) {
			agents[i] = new HillClimbingSearchAgent(new Problem(randomData()));
		}
		
		for (HillClimbingSearchAgent agent : agents) {
			try{
				long time = System.currentTimeMillis();
				SearchNode sn = agent.solve();
				time = System.currentTimeMillis() - time;
				sn.time = time;
				if(sn.isGoal())
					solved++;
				
				PrintResult(sn);
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
//		System.out.print(isSolvable(p3.data));
		System.out.print("Success rate:\t" + (float)solved/100);
	}
	
	public static void PrintResult(SearchNode sn){
		System.out.println("Time spent in millis:\t" + sn.time);
		
		sn.moves.poll();
		System.out.println("Moves:\t" + sn.moves.toString());
		System.out.println("MovesCount(cost):\t" + sn.moves.size());
		
		System.out.println("Solution " + (sn.isGoal()?"":"not ") + "found!");

		System.out.println("\nStart state:");
		PrintData(sn.startData);
		
		System.out.println("\nFinal state:");
		PrintData(sn.data);
		
		System.out.println("\n----------------------------------------------------------------");
	}
	
	public static void PrintData(int[][] d){
		for (int i = 0; i < d.length; i++) {
			System.out.print("\t\t| ");
			for (int j = 0; j < d[i].length; j++) {
				System.out.print(d[i][j]);
				if(j<2){
					System.out.print(" , ");
				}
			}
			System.out.println(" |");
		}
	}
	
	public static int[][] randomData(){
		int[][] a = new int[3][3];
		List<Integer> nums = Arrays.asList(1,2,3,4,0,6,7,8,9);
		Collections.shuffle(nums);
		
		Integer[] snums = new Integer[9];
		nums.toArray(snums);
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				a[i][j] = snums[3*i + j];
			}
		}
		if(isSolvable(a)){
			return a;
		}
		return randomData();
	}
	
	public static boolean isSolvable(int[][] data){
		boolean b = true;
		int[] index = new int[2];
		int[][] d = new int[3][3];
		assignData(d, data);
		
		index[0] = getIndex(d, 1)[0];
		index[1] = getIndex(d, 1)[1];
		d[index[0]][index[1]] = d[0][0];
		d[0][0] = 1;
		if(d[index[0]][index[1]] != 0)
			b = !b;
		
		index[0] = getIndex(d, 2)[0];
		index[1] = getIndex(d, 2)[1];
		d[index[0]][index[1]] = d[0][1];
		d[0][1] = 2;
		if(d[index[0]][index[1]] != 0)
			b = !b;
		
		index[0] = getIndex(d, 3)[0];
		index[1] = getIndex(d, 3)[1];
		d[index[0]][index[1]] = d[0][2];
		d[0][2] = 3;
		if(d[index[0]][index[1]] != 0)
			b = !b;
		
		index[0] = getIndex(d, 4)[0];
		index[1] = getIndex(d, 4)[1];
		d[index[0]][index[1]] = d[1][0];
		d[1][0] = 4;
		if(d[index[0]][index[1]] != 0)
			b = !b;
		
		index[0] = getIndex(d, 6)[0];
		index[1] = getIndex(d, 6)[1];
		d[index[0]][index[1]] = d[1][2];
		d[1][2] = 6;
		if(d[index[0]][index[1]] != 0)
			b = !b;
		
		index[0] = getIndex(d, 7)[0];
		index[1] = getIndex(d, 7)[1];
		d[index[0]][index[1]] = d[2][0];
		d[2][0] = 7;
		if(d[index[0]][index[1]] != 0)
			b = !b;
		
		index[0] = getIndex(d, 8)[0];
		index[1] = getIndex(d, 8)[1];
		d[index[0]][index[1]] = d[2][1];
		d[2][1] = 8;
		if(d[index[0]][index[1]] != 0)
			b = !b;
		
		index[0] = getIndex(d, 9)[0];
		index[1] = getIndex(d, 9)[1];
		d[index[0]][index[1]] = d[2][2];
		d[2][2] = 9;
		if(d[index[0]][index[1]] != 0)
			b = !b;
		
		return b;
	}
	
	public static int[] getIndex(int[][] data, int t){
		for (int i = 0 ; i < 3; i++)
		    for(int j = 0 ; j < 3 ; j++){
		         if ( data[i][j] == t){
		             return new int[]{i,j};
		         }
		    }
		return null;
	}
	
	public static void assignData(int[][] a,int[][] b){
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				a[i][j] = b[i][j];
			}
		}
	}
}
