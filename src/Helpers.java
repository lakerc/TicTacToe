import java.util.ArrayList;
public class Helpers {
		//Check the arraylist for x to see if it contains a winning combo
		public static boolean checkWin(ArrayList<Integer> arr){
			//check columns for a win
			for (int i = 0; i < 3; i++){
				if (arr.contains(i) && arr.contains(i + 3) && arr.contains(i + 6))
					return true;
			}

			//check rows for a win
			for (int i = 0; i < 7; i++){
				if (arr.contains(i) && arr.contains(i + 1) && arr.contains(i + 2))
					return true;
				i += 2;
			}

			//check diagonals for a win
			if (arr.contains(0) && arr.contains(4) && arr.contains(8))
				return true;
			else if (arr.contains(2) && arr.contains(4) && arr.contains(6))
				return true;

			return false;
		}

		//Find the proper coordinate for the area clicked
		public static int pickPos(double num){
			if (num >= 0 && num < 200)
				return 0;
			else if (num >= 200 && num < 400)
				return 200;
			else if (num >= 400 && num < 600)
				return 400;
			return -1;
		}

		//Gets a cell number that corresponds to the area clicked
		public static int getCell(int x, int y){
			if (x == 0 && y == 0)
				return 0;
			else if (x == 200 && y == 0)
				return 1;
			else if (x == 400 && y == 0)
				return 2;
			else if (x == 0 && y == 200)
				return 3;
			else if (x == 200 && y == 200)
				return 4;
			else if (x == 400 && y == 200)
				return 5;
			else if (x == 0 && y == 400)
				return 6;
			else if (x == 200 && y == 400)
				return 7;
			else if (x == 400 && y == 400)
				return 8;
			return -1;
		}

		//Checks to see if a "cell" is in use
		public static boolean cellInUse(int x, int y, ArrayList<Integer> arrX, ArrayList<Integer> arrO){
			int cellNum = getCell(x, y);
			if (arrX.contains(cellNum) || arrO.contains(cellNum))
					return true;
			return false;
		}
}