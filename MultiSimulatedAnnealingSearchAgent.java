import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class MultiSimulatedAnnealingSearchAgent {
	
	int cost;
	
	Problem problem;
	List<SimulatedAnnealingSearchAgent> agents = new ArrayList<>();
	int numberOfAgents;
	
	SearchNode firstNode;
	SearchNode finalNode;
	
	Queue<Problem.move> moves = new ArrayDeque<>();
	
	public MultiSimulatedAnnealingSearchAgent(Problem problem, int num){
		this.problem = problem;
		numberOfAgents = num;
		cost = 0;
		
		firstNode = new SearchNode(problem.data, problem.data, new ArrayDeque<>() , 0,false);
		finalNode = new SearchNode(firstNode);
	}
	
	public SearchNode solve(){
		for (int i = 0; i < 10 && !finalNode.isGoal(); i++) {
			agents.clear();
			for (int j = 0; j < numberOfAgents; j++) 
				agents.add(new SimulatedAnnealingSearchAgent(new Problem(finalNode.data), 100));
			for (SimulatedAnnealingSearchAgent agent : agents) {
				SearchNode sn = agent.solve();
				
				int nCost = sn.cost + finalNode.cost;
				if (isBetter(sn, finalNode))
					finalNode = sn;
				finalNode.cost = nCost;
			}

			for (Problem.move move : finalNode.moves) {
				moves.add(move);
			}
			cost += finalNode.cost;
		}
		finalNode.moves = this.moves;
		finalNode.startData = firstNode.data;
		finalNode.cost = cost;
		return finalNode;
	}
	
	public boolean isBetter(SearchNode s1, SearchNode s2) { 
        if (s1.getH() > s2.getH()) 
            return false; 
        else if (s1.getH() < s2.getH()) 
            return true; 
        else if (s1.moves.size() > s2.moves.size())
        	return false;
        else if (s1.moves.size() < s2.moves.size())
        	return true;
        return false; 
    } 
}
