/***
 * Author: Hengxiu Gao, UTD ID: 2021229554
 * 
 * CS 6343 AI Project, Mills Game Variant-D
 * 
 * Class: StaticEstimation, implement static estimate function for the game
 * 
 */

import java.util.ArrayList;


public class StaticEstimation {

	MoveGenerator moveGen = new MoveGenerator();
	public static void main(String[] args) {
		

	}
	public int StaticEstimateMidgameEndgame(BoardPosition current) throws Exception
	{
		ArrayList<BoardPosition> L = new ArrayList<BoardPosition>();
		L = moveGen.GenerateMovesMidgameEndgame(current);
		int numWhitePieces = current.getNumOfWhite();
		int numBlackPieces = current.getNumOfBlack();
		int numBlackMoves = L.size();
		if(numBlackPieces<=2) return 10000;
			else if(numBlackPieces<=2) return -10000;
				else return (1000 * (numWhitePieces - numBlackPieces)- numBlackMoves);
		
	}
	
	public int StaticEstimateOpening(BoardPosition current) throws Exception
	{
		return current.getNumOfWhite() - current.getNumOfBlack();
	}
	
	/*
	 * Improved by considering Closed Mills, Difference of # Closed mills, Difference of # blocked opponent pieces, Difference of # pieces, Difference of # 2 piece configurations, 
	 * Difference of # 3-piece configurations, Difference of # Double mills, and check if it is won.
	 * */
	public int StaticEstimateMidgameEndgameImproved(BoardPosition current) throws Exception
	{
		
		int Closed_Mills = current.isClose&current.IsWhite() ? 1 : current.isClose&!current.IsWhite() ? -1 : 0;
		int DiffBlockedOpponentPieces = NumberofblockedOpponentPieces(current);
		int DiffNumOfPieces = current.getNumOfWhite() - current.getNumOfBlack();
		int PieceConfig[] = NumberofPieceConfigurations(current);
		int isWinning = current.getNumOfWhite()==2? 1 : current.getNumOfBlack()==2? -1 : 0;
		/*
		System.out.println("current="+current+",Mills="+Closed_Mills+",WhiteClose="+current.isClose+" BlockOppt="+DiffBlockedOpponentPieces+", 2 PieceConfig="
				+PieceConfig[0]+", 3 PieceConfig="
				+PieceConfig[1]);
		*/
		if(current.getNumOfWhite()>3&&current.getNumOfBlack()>3) // Mid game
		{
			return 34 * Closed_Mills + 10 *DiffBlockedOpponentPieces + 11 * DiffNumOfPieces + 8 * PieceConfig[2] + 10000 * isWinning;
		}
		else // End Game
		{
			return 26 * Closed_Mills + 10 * PieceConfig[0] + 1 * PieceConfig[1] + 10000 * isWinning;
		}
		
	
	}
	
	/*
	 * Improved by considering Closed Mills, Difference of # Closed mills, Difference of # blocked opponent pieces, Difference of # pieces, Difference of # 2 piece configurations, 
	 * Difference of # 3-piece configurations
	 * */
	public int StaticEstimateOpeningImproved(BoardPosition current) throws Exception
	{
		int Closed_Mills = current.isClose&current.IsWhite() ? 1 : current.isClose&!current.IsWhite() ? -1 : 0;
		int DiffBlockedOpponentPieces = NumberofblockedOpponentPieces(current);
		int DiffNumOfPieces = current.getNumOfWhite() - current.getNumOfBlack();
		int PieceConfig[] = NumberofPieceConfigurations(current);
		
		/*
		System.out.println("current="+current+",Mills="+Closed_Mills+",WhiteClose="+current.isClose+" BlockOppt="+DiffBlockedOpponentPieces+", 2 PieceConfig="
				+PieceConfig[0]+", 3 PieceConfig="
				+PieceConfig[1]);
		*/
		return 28 * Closed_Mills + 1 *DiffBlockedOpponentPieces + 9 * DiffNumOfPieces + 10 * PieceConfig[0] + 7 * PieceConfig[1];
	}
	
	
	/*
	 * Difference between the number of yours opponent’s and yours blocked pieces 
	 * (pieces which don’t have an empty adjacent point)
	 * */
	private int NumberofblockedOpponentPieces(BoardPosition current) throws Exception
	{
		int countWhite = 0;
		int countBlack = 0;
		for(int i=0;i<23;i++)
		{
			
			if(current.getPosition(i)!=PositionType.x)
			{
				PositionType Opponent_pos = current.getPosition(i)==PositionType.B? PositionType.W:PositionType.B;
				
				int[] neighbors = Utility.getNeighbors(i);
				boolean isBlocked = true;
				for(int j=0;j<neighbors.length;j++)
					if(current.getPosition(neighbors[j])!=Opponent_pos)
					{
						isBlocked = false;
						break;
					}
				if(isBlocked)
					if(current.getPosition(i)==PositionType.W)
						countWhite++;
					else
						countWhite++;
			}
		}
		return countWhite - countBlack;
	}
	
	/*
	 * Return Number of 2 piece configurations, Number of 3-piece configurations and Double mills
	 * 
	 * Return Number of 2 piece configurations :
	 * Difference between the number of yours and yours opponent’s 2 piece configurations 
	 * (A 2-piece configuration is one to which adding one more piece would close a mills)
	 * 
	 * Number of 3-piece configurations
	 * Difference between the number of yours and yours opponent’s 3 piece configurations 
	 * (A 3-piece configuration is one to which a piece can be added in which one of two ways to close a mills)
	 * 
	 * Double mills
	 * Difference between number of yours and yours opponent’s double mills
	 *  (A double mills is one in which two mills share a common piece)
	 * */
	private int[] NumberofPieceConfigurations(BoardPosition current) throws Exception
	{
		int counterBlack2 = 0;
		int counterWhite2 = 0;
		int counterBlack3 = 0;
		int counterWhite3 = 0;
		int doubleWhite = 0;
		int doubleBlack = 0;
		int[] whitetable = new int[23];
		int[] blacktable = new int[23];
		
		for (int i = 0; i< 23; i++)
		{
			PositionType bPos = current.getPosition(i);
			if (bPos == PositionType.x)
			{
				BoardPosition board_temp = new BoardPosition(current.toString());
				board_temp.putPiece(i, PositionType.B);
				ArrayList<Integer> CloseMill = Utility.isCloseMillReturnPos(i, board_temp);
				if (CloseMill.size()>0)
				{
					
					counterBlack2 += CloseMill.size() / 2;
					doubleBlack += CloseMill.size() / 2 - 1;
					for(int j : CloseMill)
					{
						if(blacktable[j]==0)
							blacktable[j]=1;
						else
							counterBlack3++;
					}
					
					
					//System.out.println("\n ----debug------ i="+i+", Black2="+counterBlack2+",  Black3="+counterBlack3+", double black="+doubleBlack);
					
				}
				board_temp = new BoardPosition(current.toString());
				board_temp.putPiece(i, PositionType.W);
				CloseMill = Utility.isCloseMillReturnPos(i, board_temp);
				if (CloseMill.size()>0)
				{
					counterWhite2 += CloseMill.size() / 2;
					doubleWhite += CloseMill.size() / 2 - 1;
					for(int j : CloseMill)
					{
						if(whitetable[j]==0)
							whitetable[j]=1;
						else
							counterWhite3++;
					}
					//System.out.println("----debug------ i="+i+", White2="+counterWhite2+",  White3="+counterWhite3+", double white="+doubleWhite);
					
				}
			}
		}
		return new int[]{counterWhite2 - counterBlack2,counterWhite3 - counterBlack3, doubleWhite-doubleBlack};
	}
	
}
