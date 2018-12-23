import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

public class HillClimbingSearchAgent {
	int count;
	
	Problem problem;
	SearchNode firstNode;
	SearchNode finalNode;
	Queue<SearchNode> nextNodes = new PriorityQueue<>(new NodeComparator());

	public HillClimbingSearchAgent(Problem problem){
		firstNode = new SearchNode(problem.data, problem.data, new ArrayDeque<>() ,false);
		firstNode.moves.add(Problem.move.Stay);
		finalNode = firstNode;
		this.problem = problem;
		
		count = 0;
	}
	
	public SearchNode solve(){
		nextNodes.clear();
		count++;
		if (finalNode.isPeek || finalNode.isGoal() || count > 100){
			return finalNode;
		}else{
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
					nextNodes.add(new SearchNode(firstNode.startData, cData,moves, false));
					moves = new ArrayDeque<>(finalNode.moves);
					
					assignData(cData, problem.data);
					break;
				}
				case Right:
				{
					cData[Zpos[0]][Zpos[1]] = problem.data[Zpos[0]][Zpos[1]+1];
					cData[Zpos[0]][Zpos[1]+1] = 0;

					moves.add(Problem.move.Right);
					nextNodes.add(new SearchNode(firstNode.startData, cData,moves, false));
					
					assignData(cData, problem.data);
					break;
				}	
				case Up:
				{
					cData[Zpos[0]][Zpos[1]] = problem.data[Zpos[0]-1][Zpos[1]];
					cData[Zpos[0]-1][Zpos[1]] = 0;
					
					moves.add(Problem.move.Up);
					nextNodes.add(new SearchNode(firstNode.startData, cData,moves, false));
					
					assignData(cData, problem.data);
					break;
				}	
				case Down:
				{
					cData[Zpos[0]][Zpos[1]] = problem.data[Zpos[0]+1][Zpos[1]];
					cData[Zpos[0]+1][Zpos[1]] = 0;
					
					moves.add(Problem.move.Down);
					nextNodes.add(new SearchNode(firstNode.startData, cData,moves, false));
					
					assignData(cData, problem.data);
					break;
				}
				case Stay:
				{
					moves.add(Problem.move.Stay);
					nextNodes.add(new SearchNode(firstNode.startData, cData,moves, true));
					
					assignData(cData, problem.data);
					break;
				}	
				default:
					break;
				}
			}
//			finalNode = findBestNode(nextNodes);
			finalNode = nextNodes.poll();
			assignData(problem.data, finalNode.data);
			return solve();
		}	
	}
	
//	public SearchNode findBestNode(Set<SearchNode> nodes){
//		SearchNode BestNode = (SearchNode) nodes.toArray()[0];
//		for (SearchNode Node : nodes) {
//			if(Node.heuristic < BestNode.heuristic)
//				BestNode = Node;
//		}
//		return BestNode;
//	}
	
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
