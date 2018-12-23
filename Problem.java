public class Problem {
	public enum move{
		Left, Right, Up, Down, Stay;
	}
	private move[][][] nexts = new move[][][]{
		{{move.Right,move.Down,move.Stay},{move.Left,move.Right,move.Down,move.Stay},{move.Left,move.Down,move.Stay}},
		{{move.Right,move.Up,move.Down,move.Stay},{move.Left,move.Right,move.Up,move.Down,move.Stay},{move.Left,move.Up,move.Down,move.Stay}},
		{{move.Right,move.Up,move.Stay},{move.Left,move.Right,move.Up,move.Stay},{move.Left,move.Up,move.Stay}}
	};
	
	int[][] initial = new int[3][3];
	int[][] data = new int[3][3];
	
	public Problem(int[][] init) {
		initial = init;
		data = initial;
	}
	
	public int[] getZIndex(){

		for (int i = 0 ; i < 3; i++)
		    for(int j = 0 ; j < 3 ; j++){
		         if ( data[i][j] == 0){
		             return new int[]{i,j};
		         }
		    }
		return null;
	}
		
	public move[] getNextMoves(){
		int[] pos = getZIndex();
		return nexts[pos[0]][pos[1]];
	}
}
