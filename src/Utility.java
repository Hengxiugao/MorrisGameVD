/***
 * Author: Hengxiu Gao, Author: Hengxiu Gao, Email:Hengxiugao@yahoo.com
 *
 * CS 6343 AI Project, Morris Game Variant-D
 *
 * Class: Utility, implement some utility functions.
 *
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class Utility {

	static FileWriter fileWriter = null;
	static BufferedWriter bufferedWriter = null;
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public static String ReadFile(String inputFile)
	{

		try
		{
			FileReader fileR = new FileReader(inputFile);
			BufferedReader buffR = new BufferedReader(fileR);
			String str = buffR.readLine();
			String str_temp = str;
			while(str!=null)
			{
				str_temp = str;
				str = buffR.readLine();
			}
			return str_temp;
		}
		catch(FileNotFoundException ex)
		{
			System.out.println( "Unable to open file '" + inputFile + "'");
		}
		catch(IOException ex)
		{
			System.out.println("Error reading file '" + inputFile + "'");
		}
		return null;

	}

	public static void initWrite() throws IOException
	{
		fileWriter = new FileWriter("Trace.txt");
		bufferedWriter = new BufferedWriter(fileWriter);
	}
	public static void WriteFileDuringRecu(String str) throws IOException
	{
		bufferedWriter.write(str);
	}
	public static void closeFile() throws IOException
	{
		bufferedWriter.close();
	}
	public static void WriteFile(String outputFile, String content)
	{
		try {
			FileWriter fileWriter = new FileWriter(outputFile);

			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			bufferedWriter.append(content.toString()+"\n");
			//System.out.print(content.toString());

			bufferedWriter.close();
		}
		catch(IOException ex) {
			System.out.println("Error writing to file '" + outputFile + "'");
		}

	}

	public static boolean isCloseMill(int currentMove, BoardPosition board) throws Exception
	{
		//System.out.println("    in is close, board="+board.toString());
		PositionType[] positions = board.getPositionArray();
		PositionType current = positions[currentMove];
		switch(currentMove)
		{
		case 0:
			if(positions[1]==current&&positions[2]==current)
				return true;
			if(positions[3]==current&&positions[6]==current)
				return true;
			if(positions[8]==current&&positions[20]==current)
				return true;
			return false;
		case 1:
			if(positions[0]==current&&positions[2]==current)
				return true;
			return false;
		case 2:
			if(positions[0]==current&&positions[1]==current)
				return true;
			if(positions[13]==current&&positions[22]==current)
				return true;
			if(positions[5]==current&&positions[7]==current)
				return true;
			return false;
		case 3:
			if(positions[0]==current&&positions[6]==current)
				return true;
			if(positions[4]==current&&positions[5]==current)
				return true;
			if(positions[9]==current&&positions[17]==current)
				return true;
			return false;
		case 4:
			if(positions[3]==current&&positions[5]==current)
				return true;
			return false;
		case 5:
			if(positions[3]==current&&positions[4]==current)
				return true;
			if(positions[12]==current&&positions[19]==current)
				return true;
			if(positions[2]==current&&positions[7]==current)
				return true;
			return false;
		case 6:
			if(positions[0]==current&&positions[3]==current)
				return true;
			if(positions[10]==current&&positions[14]==current)
				return true;
			return false;
		case 7:
			if(positions[2]==current&&positions[5]==current)
				return true;
			if(positions[11]==current&&positions[16]==current)
				return true;
			return false;
		case 8:
			if(positions[0]==current&&positions[20]==current)
				return true;
			if(positions[9]==current&&positions[10]==current)
				return true;
			return false;
		case 9:
			if(positions[8]==current&&positions[10]==current)
				return true;
			if(positions[3]==current&&positions[17]==current)
				return true;
			return false;
		case 10:
			if(positions[8]==current&&positions[9]==current)
				return true;
			if(positions[6]==current&&positions[14]==current)
				return true;
			return false;
		case 11:
			if(positions[7]==current&&positions[16]==current)
				return true;
			if(positions[12]==current&&positions[13]==current)
				return true;
			return false;
		case 12:
			if(positions[5]==current&&positions[19]==current)
				return true;
			if(positions[11]==current&&positions[13]==current)
				return true;
			return false;
		case 13:
			if(positions[11]==current&&positions[12]==current)
				return true;
			if(positions[2]==current&&positions[22]==current)
				return true;
			return false;
		case 14:
			if(positions[6]==current&&positions[10]==current)
				return true;
			if(positions[15]==current&&positions[16]==current)
				return true;
			if(positions[17]==current&&positions[20]==current)
				return true;
			return false;
		case 15:
			if(positions[14]==current&&positions[16]==current)
				return true;
			if(positions[18]==current&&positions[21]==current)
				return true;
			return false;
		case 16:
			if(positions[19]==current&&positions[22]==current)
				return true;
			if(positions[14]==current&&positions[15]==current)
				return true;
			if(positions[7]==current&&positions[11]==current)
				return true;
			return false;
		case 17:
			if(positions[3]==current&&positions[9]==current)
				return true;
			if(positions[18]==current&&positions[19]==current)
				return true;
			if(positions[14]==current&&positions[20]==current)
				return true;
			return false;
		case 18:
			if(positions[17]==current&&positions[19]==current)
				return true;
			if(positions[15]==current&&positions[21]==current)
				return true;
			return false;
		case 19:
			if(positions[5]==current&&positions[12]==current)
				return true;
			if(positions[17]==current&&positions[18]==current)
				return true;
			if(positions[16]==current&&positions[22]==current)
				return true;
			return false;
		case 20:
			if(positions[21]==current&&positions[22]==current)
				return true;
			if(positions[0]==current&&positions[8]==current)
				return true;
			if(positions[14]==current&&positions[17]==current)
				return true;
			return false;
		case 21:
			if(positions[20]==current&&positions[22]==current)
				return true;
			if(positions[15]==current&&positions[18]==current)
				return true;
			return false;
		case 22:
			if(positions[20]==current&&positions[21]==current)
				return true;
			if(positions[2]==current&&positions[13]==current)
				return true;
			if(positions[16]==current&&positions[19]==current)
				return true;
			return false;
		default:
			throw new Exception("No such position");
		}


	}

	public static ArrayList<Integer> isCloseMillReturnPos(int currentMove, BoardPosition board) throws Exception
	{
		//System.out.println("    in is close, board="+board.toString());
		PositionType[] positions = board.getPositionArray();
		PositionType current = board.getPosition(currentMove);
		ArrayList<Integer> result = new ArrayList<Integer>();
		switch(currentMove)
		{
		case 0:
			if(positions[1]==current&&positions[2]==current)
			{
				result.add(1);
				result.add(2);
			}
			if(positions[3]==current&&positions[6]==current)
			{
				result.add(3);
				result.add(6);
			}
			if(positions[8]==current&&positions[20]==current)
			{
				result.add(8);
				result.add(20);
			}
			return result;
		case 1:
			if(positions[0]==current&&positions[2]==current)
			{
				result.add(0);
				result.add(2);
			}
			return result;
		case 2:
			if(positions[0]==current&&positions[1]==current)
			{
				result.add(0);
				result.add(1);
			}
			if(positions[13]==current&&positions[22]==current)
			{
				result.add(13);
				result.add(22);
			}
			if(positions[5]==current&&positions[7]==current)
			{
				result.add(5);
				result.add(7);
			}
			return result;
		case 3:
			if(positions[0]==current&&positions[6]==current)
			{
				result.add(0);
				result.add(6);
			}
			if(positions[4]==current&&positions[5]==current)
			{
				result.add(4);
				result.add(5);
			}
			if(positions[9]==current&&positions[17]==current)
			{
				result.add(9);
				result.add(17);
			}
			return result;
		case 4:
			if(positions[3]==current&&positions[5]==current)
			{
				result.add(3);
				result.add(5);
			}
			return result;
		case 5:
			if(positions[3]==current&&positions[4]==current)
			{
				result.add(3);
				result.add(4);
			}
			if(positions[12]==current&&positions[19]==current)
			{
				result.add(12);
				result.add(19);
			}
			if(positions[2]==current&&positions[7]==current)
			{
				result.add(2);
				result.add(7);
			}
			return result;
		case 6:
			if(positions[0]==current&&positions[3]==current)
			{
				result.add(0);
				result.add(3);
			}
			if(positions[10]==current&&positions[14]==current)
			{
				result.add(10);
				result.add(14);
			}
			return result;
		case 7:
			if(positions[2]==current&&positions[5]==current)
			{
				result.add(2);
				result.add(5);
			}
			if(positions[11]==current&&positions[16]==current)
			{
				result.add(11);
				result.add(16);
			}
			return result;
		case 8:
			if(positions[0]==current&&positions[20]==current)
			{
				result.add(0);
				result.add(20);
			}
			if(positions[9]==current&&positions[10]==current)
			{
				result.add(9);
				result.add(10);
			}
			return result;
		case 9:
			if(positions[8]==current&&positions[10]==current)
			{
				result.add(8);
				result.add(10);
			}
			if(positions[3]==current&&positions[17]==current)
			{
				result.add(3);
				result.add(17);
			}
			return result;
		case 10:
			if(positions[8]==current&&positions[9]==current)
			{
				result.add(8);
				result.add(9);
			}
			if(positions[6]==current&&positions[14]==current)
			{
				result.add(6);
				result.add(14);
			}
			return result;
		case 11:
			if(positions[7]==current&&positions[16]==current)
			{
				result.add(7);
				result.add(16);
			}
			if(positions[12]==current&&positions[13]==current)
			{
				result.add(12);
				result.add(13);
			}
			return result;
		case 12:
			if(positions[5]==current&&positions[19]==current)
			{
				result.add(5);
				result.add(19);
			}
			if(positions[11]==current&&positions[13]==current)
			{
				result.add(11);
				result.add(13);
			}
			return result;
		case 13:
			if(positions[11]==current&&positions[12]==current)
			{
				result.add(11);
				result.add(12);
			}
			if(positions[2]==current&&positions[22]==current)
			{
				result.add(2);
				result.add(22);
			}
			return result;
		case 14:
			if(positions[6]==current&&positions[10]==current)
			{
				result.add(6);
				result.add(10);
			}
			if(positions[15]==current&&positions[16]==current)
			{
				result.add(15);
				result.add(16);
			}
			if(positions[17]==current&&positions[20]==current)
			{
				result.add(17);
				result.add(20);
			}
			return result;
		case 15:
			if(positions[14]==current&&positions[16]==current)
			{
				result.add(14);
				result.add(16);
			}
			if(positions[18]==current&&positions[21]==current)
			{
				result.add(18);
				result.add(21);
			}
			return result;
		case 16:
			if(positions[19]==current&&positions[22]==current)
			{
				result.add(19);
				result.add(22);
			}
			if(positions[14]==current&&positions[15]==current)
			{
				result.add(14);
				result.add(15);
			}
			if(positions[7]==current&&positions[11]==current)
			{
				result.add(7);
				result.add(11);
			}
			return result;
		case 17:
			if(positions[3]==current&&positions[9]==current)
			{
				result.add(3);
				result.add(9);
			}
			if(positions[18]==current&&positions[19]==current)
			{
				result.add(18);
				result.add(19);
			}
			if(positions[14]==current&&positions[20]==current)
			{
				result.add(14);
				result.add(20);
			}
			return result;
		case 18:
			if(positions[17]==current&&positions[19]==current)
			{
				result.add(17);
				result.add(19);
			}
			if(positions[15]==current&&positions[21]==current)
			{
				result.add(15);
				result.add(21);
			}
			return result;
		case 19:
			if(positions[5]==current&&positions[12]==current)
			{
				result.add(5);
				result.add(12);
			}
			if(positions[17]==current&&positions[18]==current)
			{
				result.add(17);
				result.add(18);
			}
			if(positions[16]==current&&positions[22]==current)
			{
				result.add(16);
				result.add(22);
			}
			return result;
		case 20:
			if(positions[21]==current&&positions[22]==current)
			{
				result.add(21);
				result.add(22);
			}
			if(positions[0]==current&&positions[8]==current)
			{
				result.add(0);
				result.add(8);
			}
			if(positions[14]==current&&positions[17]==current)
			{
				result.add(14);
				result.add(17);
			}
			return result;
		case 21:
			if(positions[20]==current&&positions[22]==current)
			{
				result.add(20);
				result.add(22);
			}
			if(positions[15]==current&&positions[18]==current)
			{
				result.add(15);
				result.add(18);
			}
			return result;
		case 22:
			if(positions[20]==current&&positions[21]==current)
			{
				result.add(20);
				result.add(21);
			}
			if(positions[2]==current&&positions[13]==current)
			{
				result.add(2);
				result.add(13);
			}
			if(positions[16]==current&&positions[19]==current)
			{
				result.add(16);
				result.add(19);
			}
			return result;
		default:
			throw new Exception("No such position");
		}


	}

	public static int[] getNeighbors(int currentMove) throws Exception
	{
		switch(currentMove)
		{
		case 0:
			return new int[]{1,3,8};
		case 1:
			return new int[]{0,2,4};
		case 2:
			return new int[]{1,5,13};
		case 3:
			return new int[]{0,4,6,9};
		case 4:
			return new int[]{1,3,5};
		case 5:
			return new int[]{2,4,7,12};
		case 6:
			return new int[]{3,7,10};
		case 7:
			return new int[]{5,6,11};
		case 8:
			return new int[]{0,9,20};
		case 9:
			return new int[]{3,8,10,17};
		case 10:
			return new int[]{6,9,14};
		case 11:
			return new int[]{7,12,16};
		case 12:
			return new int[]{5,11,13,19};
		case 13:
			return new int[]{2,12,22};
		case 14:
			return new int[]{10,15,17};
		case 15:
			return new int[]{14,16,18};
		case 16:
			return new int[]{11,15,19};
		case 17:
			return new int[]{9,14,18,20};
		case 18:
			return new int[]{15,17,19,21};
		case 19:
			return new int[]{12,16,18,22};
		case 20:
			return new int[]{8,17,21};
		case 21:
			return new int[]{18,20,22};
		case 22:
			return new int[]{13,19,21};
		default:
			throw new Exception("No such position");
		}
	}

	public static String ReadDirBoardFromFile(String inputFile)
	{
		String result = new String();
		try
		{
			FileReader fileR = new FileReader(inputFile);
			BufferedReader buffR = new BufferedReader(fileR);
			String temp[] = new String[7];
			for(int i=0;i<7;i++)
				temp[i] = buffR.readLine();
			for(int i=6;i>=0;i--)
				result+= temp[i];
			result = result.replaceAll(" ", "");

			return result;
		}
		catch(FileNotFoundException ex)
		{
			System.out.println( "Unable to open file '" + inputFile + "'");
		}
		catch(IOException ex)
		{
			System.out.println("Error reading file '" + inputFile + "'");
		}
		return null;
	}
	public static void printBoard(BoardPosition board)
	{
		System.out.println(board.getPosition(20)+"             "+board.getPosition(21)+"            "+board.getPosition(22));
		System.out.println("    "+board.getPosition(17)+"         "+board.getPosition(18)+"         "+board.getPosition(19));
		System.out.println("        "+board.getPosition(14)+"     "+board.getPosition(15)+"     "+board.getPosition(16));
		System.out.println(board.getPosition(8)+"   "+board.getPosition(9)+"   "+board.getPosition(10)+"           "+
		board.getPosition(11)+"   "+board.getPosition(12)+"   "+board.getPosition(13));
		System.out.println("        "+board.getPosition(6)+"           "+board.getPosition(7));
		System.out.println("    "+board.getPosition(3)+"         "+board.getPosition(4)+"         "+board.getPosition(5));
		System.out.println(board.getPosition(0)+"             "+board.getPosition(1)+"             "+board.getPosition(2));

	}
}
