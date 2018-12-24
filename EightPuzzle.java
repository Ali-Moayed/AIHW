import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EightPuzzle {
	
	public static void main(String[] args){
		int solved = 0;
		MultiSimulatedAnnealingSearchAgent[] agents = new MultiSimulatedAnnealingSearchAgent[12];
		//p1 = Goal
		Problem p1 = new Problem(new int[][]{
			  { 1, 2, 3 },
			  { 4, 0, 6 },
			  { 7, 8, 9 }
			});
		
		Problem p2 = new Problem(new int[][]{
			  { 1, 0, 2 },
			  { 4, 8, 3 },
			  { 7, 9, 6 }
			});
		
		Problem p3 = new Problem(new int[][]{
			  { 0, 7, 9 },
			  { 6, 4, 3 },
			  { 8, 1, 2 }
			});

		Problem p4 = new Problem(new int[][]{
			  { 0, 9, 4 },
			  { 8, 1, 6 },
			  { 3, 2, 7 }
			});
		
		Problem p5 = new Problem(new int[][]{
			  { 3, 2, 9 },
			  { 0, 6, 1 },
			  { 7, 8, 4 }
			});
		
		Problem p6 = new Problem(new int[][]{
			  { 7, 8, 6 },
			  { 0, 2, 4 },
			  { 3, 1, 9 }
			});
		
		Problem p7 = new Problem(new int[][]{
			  { 7, 8, 0 },
			  { 4, 2, 1 },
			  { 9, 3, 6 }
			});
		
		Problem p8 = new Problem(new int[][]{
			  { 2, 4, 6 },
			  { 0, 1, 9 },
			  { 3, 7, 8 }
			});
		
		Problem p9 = new Problem(new int[][]{
			  { 4, 9, 1 },
			  { 6, 2, 7 },
			  { 3, 0, 8 }
			});
		
		Problem p10 = new Problem(new int[][]{
			  { 6, 7, 9 },
			  { 3, 0, 8 },
			  { 1, 2, 4 }
			});
		
		Problem p11 = new Problem(new int[][]{
			  { 8, 9, 7 },
			  { 3, 4, 2 },
			  { 1, 0, 6 }
			});
		
		Problem p12 = new Problem(new int[][]{
			  { 8, 3, 1 },
			  { 4, 9, 0 },
			  { 6, 7, 2 }
			});
		
		agents[0] = new MultiSimulatedAnnealingSearchAgent(p1, 10);
		agents[1] = new MultiSimulatedAnnealingSearchAgent(p2, 10);
		agents[2] = new MultiSimulatedAnnealingSearchAgent(p3, 10);
		agents[3] = new MultiSimulatedAnnealingSearchAgent(p4, 10);
		agents[4] = new MultiSimulatedAnnealingSearchAgent(p5, 10);
		agents[5] = new MultiSimulatedAnnealingSearchAgent(p6, 10);
		agents[6] = new MultiSimulatedAnnealingSearchAgent(p7, 10);
		agents[7] = new MultiSimulatedAnnealingSearchAgent(p8, 10);
		agents[8] = new MultiSimulatedAnnealingSearchAgent(p9, 10);
		agents[9] = new MultiSimulatedAnnealingSearchAgent(p10, 10);
		agents[10] = new MultiSimulatedAnnealingSearchAgent(p11, 10);
		agents[11] = new MultiSimulatedAnnealingSearchAgent(p12, 10);
		
//		for (int i = 0; i < 20; i++) {
//			agents[i] = new SimulatedAnnealingSearchAgent(new Problem(randomData()));
//		}
		
		for (MultiSimulatedAnnealingSearchAgent agent : agents) {
			long time = System.currentTimeMillis();
			SearchNode sn = agent.solve();
			time = System.currentTimeMillis() - time;
			sn.time = time;
			if(sn.isGoal())
				solved++;
			
			PrintResult(sn);
		}

		System.out.print("Success rate:\t" + (float)solved/agents.length + "\t(" + solved + "/" + agents.length + ")");
	}
	
	public static void PrintResult(SearchNode sn){
		System.out.println("Time spent in milliseconds:\t" + sn.time);
		
//		Removing returns
		sn.moves.poll();
		List<Problem.move> movesList = new ArrayList<>(sn.moves);
		movesList.remove(Problem.move.Stay);
		boolean changed = true;
		while (changed) {
			movesList.removeAll(Collections.singleton(Problem.move.Stay));
			changed = false;
			for (int j = 0; j < movesList.size() - 2; j++) {
				switch (movesList.get(j)) {
				case Left:
					if(movesList.get(j+1) == Problem.move.Right){
						movesList.set(j, Problem.move.Stay);
						movesList.set(j+1, Problem.move.Stay);
						changed = true;
					}
					break;
				case Right:
					if(movesList.get(j+1) == Problem.move.Left){
						movesList.set(j, Problem.move.Stay);
						movesList.set(j+1, Problem.move.Stay);
						changed = true;
					}
					break;
				case Up:
					if(movesList.get(j+1) == Problem.move.Down){
						movesList.set(j, Problem.move.Stay);
						movesList.set(j+1, Problem.move.Stay);
						changed = true;
					}
					break;
				case Down:
					if(movesList.get(j+1) == Problem.move.Up){
						movesList.set(j, Problem.move.Stay);
						movesList.set(j+1, Problem.move.Stay);
						changed = true;
					}
					break;
				default:
					break;
				}
			}
		}
		
		System.out.println("Moves:\t" + movesList.toString());
		System.out.println("MovesCount(estimated number of states):\t" + movesList.size() + " / 181,440");
		System.out.println("cost:\t" + sn.cost);
		
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
