import java.util.ArrayDeque;
import java.util.Queue;

public class SearchNode {
	boolean isPeek;
	int cost;
	int[][] data = new int[3][3];
	int[][] startData = new int[3][3];
	Queue<Problem.move> moves = new ArrayDeque<>();
	
	long time;
	
	int heuristic;
	
	public SearchNode(int[][] startData, int[][] data, Queue<Problem.move> moves, int cost, boolean isPeek){
		assignData(this.data, data);
		assignData(this.startData, startData);
		for (Problem.move move : moves) {
			this.moves.add(move);
		}
		this.isPeek = isPeek;
		this.cost = cost;
		
		heuristic = 0;
		heuristic += Math.abs(getIndex(1)[0] - 0) + Math.abs(getIndex(1)[1] - 0);
		heuristic += Math.abs(getIndex(2)[0] - 0) + Math.abs(getIndex(2)[1] - 1);
		heuristic += Math.abs(getIndex(3)[0] - 0) + Math.abs(getIndex(3)[1] - 2);
		heuristic += Math.abs(getIndex(4)[0] - 1) + Math.abs(getIndex(4)[1] - 0);
		heuristic += Math.abs(getIndex(6)[0] - 1) + Math.abs(getIndex(6)[1] - 2);
		heuristic += Math.abs(getIndex(7)[0] - 2) + Math.abs(getIndex(7)[1] - 0);
		heuristic += Math.abs(getIndex(8)[0] - 2) + Math.abs(getIndex(8)[1] - 1);
		heuristic += Math.abs(getIndex(9)[0] - 2) + Math.abs(getIndex(9)[1] - 2);
	}
	public SearchNode(SearchNode sn){
		assignData(this.data, sn.data);
		assignData(this.startData, sn.startData);
		for (Problem.move move : sn.moves) {
			this.moves.add(move);
		}
		this.isPeek = sn.isPeek;
		this.heuristic = sn.heuristic;
		this.cost = sn.cost;
	}
	
	public int getH(){
		return heuristic;
	}
	
	public boolean isGoal(){
		return getH() == 0;
	}
	
	public int[] getIndex(int t){
		for (int i = 0 ; i < 3; i++)
		    for(int j = 0 ; j < 3 ; j++){
		         if ( data[i][j] == t){
		             return new int[]{i,j};
		         }
		    }
		return null;
	}
	
	public void assignData(int[][] a,int[][] b){
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				a[i][j] = b[i][j];
			}
		}
	}
}
