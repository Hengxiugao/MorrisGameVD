/***
 * Author: Hengxiu Gao, UTD ID: 2021229554
 * 
 * CS 6343 AI Project, Morris Game Variant-D
 * 
 * Class:MoveGenerator, Implement each kind of generating move in the game, including generate add, generate move, generate remove and generate hopping.
 * 
 */

import java.util.ArrayList;



public class MoveGenerator {

	public static void main(String[] args) throws Exception {
		/*
		StaticEstimation estimate = new StaticEstimation();
		BoardPosition b1 = new BoardPosition("WWWWWxxxxxxxxxxxBxBBxxx");
		MoveGenerator moveg = new MoveGenerator();
		//System.out.println(moveg.isCloseMill(2,b1));
		
		ArrayList<BoardPosition> L = moveg.GenerateMovesOpening(b1);
		
		for(BoardPosition i : L)
		{
			System.out.println(i.toString()+", est="+estimate.StaticEstimateOpening(i));
		}
		*/
		Utility.printBoard(new BoardPosition("WWxxxBxBBWBxxWWWBxxBxBW"));
	}
	
	
	ArrayList<BoardPosition> GenerateMovesOpening(BoardPosition current) throws Exception
	{
		
		return GenerateAdd(current, true);
		
	}
	
	ArrayList<BoardPosition> GenerateMovesOpeningBlack(BoardPosition current) throws Exception
	{
		BoardPosition temp = new BoardPosition(current.toString());
		temp.swapColor();
		ArrayList<BoardPosition> L = GenerateAdd(temp,false);
		for(BoardPosition l : L)
		{
			l.swapColor();
		}
		return L;
	}
	
	
	ArrayList<BoardPosition> GenerateMovesMidgameEndgame(BoardPosition current) throws Exception
	{
		if(current.getNumOfWhite()==3)
			return GenerateHopping(current,true);
		else
			return GenerateMove(current,true);
		
	}
	
	ArrayList<BoardPosition> GenerateMovesMidgameEndgameBlack(BoardPosition current) throws Exception
	{
		current.setIsWhite(false);
		BoardPosition temp = new BoardPosition(current.toString());
		temp.swapColor();
		ArrayList<BoardPosition> L = new ArrayList<BoardPosition>();
		
		if(current.getNumOfWhite()==3)
			L = GenerateHopping(temp,false);
		else
			L = GenerateMove(temp,false);
		
		for(BoardPosition l : L)
		{
			l.swapColor();
		}
		
		return L;
	}
	
	
	ArrayList<BoardPosition> GenerateAdd(BoardPosition board, boolean isWhite) throws Exception
	{
		ArrayList<BoardPosition> L = new ArrayList<BoardPosition>();
		for(int i=0;i<23;i++)
		{
			if(board.isEmpty(i))
			{
				BoardPosition board_temp = new BoardPosition(board.toString());
				board_temp.isClose = board.isClose;
				board_temp.putPiece(i, PositionType.W);
				//System.out.println("--DEBUG--"+board_temp.toString());
				if(Utility.isCloseMill(i,board_temp)) //TODO
				{
					board_temp.isClose = true;
					//System.out.println("ccc");
					L = GenerateRemove(board_temp,L,isWhite);
				}
				else
				{
					L.add(board_temp);
					board_temp.setIsWhite(isWhite);
				}
			}
		}
		return L;
		
	}
	
	ArrayList<BoardPosition> GenerateHopping(BoardPosition board, boolean isWhite) throws Exception
	{
		ArrayList<BoardPosition> L = new ArrayList<BoardPosition>();
		PositionType[] positions = board.getPositionArray();
		//System.out.println("-DEBUG--"+board);
		for(int i=0;i<23;i++)
		{
			if(positions[i]==PositionType.W)
				for(int j=0;j<23;j++)
					if(board.isEmpty(j))
					{
						BoardPosition board_temp = new BoardPosition(board.toString());
						board_temp.movePiece(i, j);
						if(Utility.isCloseMill(j,board_temp))
							L = GenerateRemove(board_temp,L,isWhite);
						else
						{
							board_temp.setIsWhite(isWhite);
							L.add(board_temp);
						}
					}
		}
		return L;
		
	}
	
	ArrayList<BoardPosition> GenerateMove(BoardPosition board, boolean isWhite) throws Exception
	{
		ArrayList<BoardPosition> L = new ArrayList<BoardPosition>();
		PositionType[] positions = board.getPositionArray();
		for(int i=0;i<23;i++)
		{
			if(positions[i]==PositionType.W)
			{
				int[] neighbors = Utility.getNeighbors(i);
				for(int j:neighbors)
				{
					if(board.isEmpty(j))
					{
						BoardPosition board_temp = new BoardPosition(board.toString());
						board_temp.movePiece(i, j);
						if(Utility.isCloseMill(j,board_temp))
							L = GenerateRemove(board_temp,L,isWhite);
						else
						{
							board_temp.setIsWhite(isWhite);
							L.add(board_temp);
						}
					}
				}
			}
		}
		return L;
		
	}
	
	ArrayList<BoardPosition> GenerateRemove(BoardPosition board, ArrayList<BoardPosition> L, boolean isWhite) throws Exception
	{
		for(int i=0;i<23;i++)
			if(board.getPosition(i)==PositionType.B)
			{
				//if(!closeMill(i,board))//TODO
				if(!Utility.isCloseMill(i,board)) 
				{
					
					BoardPosition board_temp = new BoardPosition(board.toString());
					board_temp.isClose = board.isClose;
					board_temp.removePiece(i);
					//System.out.println("---DEBUG---- REMOVE"+i+", board="+board_temp.toString());
					board_temp.setIsWhite(isWhite);
					L.add(board_temp);
				}
				
			}
		//if(L.isEmpty()) TODO bug here
		//	L.add(board);
		return L;
		
	}

}
