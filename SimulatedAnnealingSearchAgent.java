import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;

public class SimulatedAnnealingSearchAgent {
	int count;
	int depthLimit = 1000;
	
	Problem problem;
	SearchNode firstNode;
	SearchNode finalNode;
	SearchNode peekNode;
	Queue<SearchNode> nextNodes = new PriorityQueue<>(new NodeComparator());

	public SimulatedAnnealingSearchAgent(Problem problem){
		firstNode = new SearchNode(problem.data, problem.data, new ArrayDeque<>() , 0,false);
		firstNode.moves.add(Problem.move.Stay);
		finalNode = firstNode;
		peekNode = firstNode;
		this.problem = problem;
		
		count = 0;
	}
	
	public SearchNode solve(){
		nextNodes.clear();
		count++;
		if (finalNode.isGoal() || count > depthLimit){
			return (finalNode.getH() < peekNode.getH())? finalNode:peekNode;
		}else{
			if(finalNode.isPeek && (finalNode.getH() < peekNode.getH())){
				peekNode = new SearchNode(finalNode);
			}
			Problem.move[] nextMoves = problem.getNextMoves();
			int[] Zpos = problem.getZIndex();
			int[][] cData = new int[3][3];
			
			assignData(cData, problem.data);
			
			Queue<Problem.move> moves = new ArrayDeque<>(finalNode.moves);
			for (Problem.move move : nextMoves) {
				moves = new ArrayDeque<>(finalNode.moves);
				switch (move) {
				case Left:
				{
					cData[Zpos[0]][Zpos[1]] = problem.data[Zpos[0]][Zpos[1]-1];
					cData[Zpos[0]][Zpos[1]-1] = 0;
					
					moves.add(Problem.move.Left);
					nextNodes.add(new SearchNode(firstNode.startData, cData,moves, finalNode.cost + 1, false));
					moves = new ArrayDeque<>(finalNode.moves);
					
					assignData(cData, problem.data);
					break;
				}
				case Right:
				{
					cData[Zpos[0]][Zpos[1]] = problem.data[Zpos[0]][Zpos[1]+1];
					cData[Zpos[0]][Zpos[1]+1] = 0;

					moves.add(Problem.move.Right);
					nextNodes.add(new SearchNode(firstNode.startData, cData,moves, finalNode.cost + 1, false));
					
					assignData(cData, problem.data);
					break;
				}	
				case Up:
				{
					cData[Zpos[0]][Zpos[1]] = problem.data[Zpos[0]-1][Zpos[1]];
					cData[Zpos[0]-1][Zpos[1]] = 0;
					
					moves.add(Problem.move.Up);
					nextNodes.add(new SearchNode(firstNode.startData, cData,moves, finalNode.cost + 1, false));
					
					assignData(cData, problem.data);
					break;
				}	
				case Down:
				{
					cData[Zpos[0]][Zpos[1]] = problem.data[Zpos[0]+1][Zpos[1]];
					cData[Zpos[0]+1][Zpos[1]] = 0;
					
					moves.add(Problem.move.Down);
					nextNodes.add(new SearchNode(firstNode.startData, cData,moves, finalNode.cost + 1, false));
					
					assignData(cData, problem.data);
					break;
				}
				case Stay:
				{
					moves.add(Problem.move.Stay);
					nextNodes.add(new SearchNode(firstNode.startData, cData,moves, finalNode.cost + 1, true));
					
					assignData(cData, problem.data);
					break;
				}	
				default:
					break;
				}
			}

			if(nextNodes.peek().getH() >= peekNode.getH()){
				int sumOfH = 5;
				int[] chances = new int[nextNodes.size()];
				int x = 0;
				for (SearchNode n : nextNodes) {
					sumOfH += n.getH();
					chances[x++] = n.getH();
				}
				for (int i = 0; i < chances.length; i++) {
					chances[i] = sumOfH - chances[i];
				}
				
				int min = chances[chances.length - 1];
				for (int i = 0; i < chances.length; i++) {
					int dif = chances[i] - min;
					chances[i] += 10*dif*dif;
					if(i>0)
						chances[i] += chances[i-1];
				}
				
				Random r = new Random();
				int rc = r.nextInt(chances[chances.length - 1]);
				x = 0;
				
				for (int i = 0; i < chances.length; i++) {
					if(chances[i] > rc){
						x = i;
						break;
					}
				}
				
				for (int i = 0; i < x; i++) {
					nextNodes.poll();
				}
			}
			finalNode = nextNodes.poll();
			assignData(problem.data, finalNode.data);
			return solve();
		}
	}
	
	class NodeComparator implements Comparator<SearchNode>{ 
        public int compare(SearchNode s1, SearchNode s2) { 
            if (s1.getH() > s2.getH()) 
                return 1; 
            else if (s1.getH() < s2.getH()) 
                return -1; 
            return 0; 
        } 
    } 
	
	public void assignData(int[][] a,int[][] b){
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				a[i][j] = b[i][j];
			}
		}
	}
}
