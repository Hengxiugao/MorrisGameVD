/***
 * Author: Hengxiu Gao, UTD ID: 2021229554
 * 
 * CS 6343 AI Project, Morris Game Variant-D
 * 
 * Class: MiniMaxGameBlack, Using mini max algorithm to search the Black players' moves for the stage 2 and stage 3 of Morris Game
 * 
 */

import java.util.ArrayList;


public class MiniMaxGameBlack {

	StaticEstimation estimate = new StaticEstimation();
	MoveGenerator moveGen = new MoveGenerator();
	public static void main(String[] args) throws Exception {
		MiniMaxGameBlack minimaxblack = new MiniMaxGameBlack();
		
		if(args.length>0)
		{
			if(args.length<4)
			{
				System.out.println("Arguments are not correct, \nThe Arguments shoule be: 1. InputFile name, 2. Output File name, 3. Maximum Depth, 4. the Phy of Input board \n   "
						+ "here the Maximum Depth means the maximum height of the search tree, the phy of input board means the rounds that has already been played in the input "
						+ "board, for example, the phy of board 'xxxxxxxxxxxxxxxxxxxxxx'=0, the phy of board 'xxxxxxxxxxxxxxxxxxxWxx'=1, the phy of board 'xxxxxxxxxxxxxxWxxBxxxx'=2,");
				System.exit(0);
			}
			System.out.println("Program running, Mini max Black for the mid/end stage");
			String InputFile = args[0];
			String OutputFile = args[1];
			int depth = Integer.parseInt(args[2]);
			int current_phy = Integer.parseInt(args[3]);
			BoardPosition InputPosition = new BoardPosition(Utility.ReadFile(InputFile));
			InputPosition.setPhy(current_phy);
			
			System.out.println("Input Board:");
			Utility.printBoard(InputPosition);
			
			OutputObject out = minimaxblack.MiniMax(depth,true,InputPosition);
			
			System.out.println("Output Board:");
			Utility.printBoard(out.b);
			Utility.WriteFile(OutputFile, out.toString());
			System.out.println("Program finishes.");
		}else
		{
			BoardPosition InputPosition = new BoardPosition("BBBBxxxxBxxBxxWxWxxWxxW");
			
			OutputObject out = minimaxblack.MiniMax(5,true,InputPosition);
			//out.b.swapColor();
			
			System.out.println(out);
		}
		
		
	}
	OutputObject MiniMax(int depth, boolean isBlack, BoardPosition board) throws Exception
	{
		OutputObject out = new OutputObject();
		if (depth == 0)
		{
			//BoardPosition board_white = new BoardPosition(board.toString());
			//board_white.swapColor();
			out.estimate = estimate.StaticEstimateMidgameEndgame(board);
			//System.out.println("estimate="+out.estimate);
			out.numNodes++;
			return out;
		}

		OutputObject in = new OutputObject();
		ArrayList<BoardPosition> nextMoves = (isBlack) ? moveGen.GenerateMovesMidgameEndgameBlack(board) : moveGen.GenerateMovesMidgameEndgame(board);
		out.estimate = (isBlack) ? Integer.MIN_VALUE : Integer.MAX_VALUE;
		for (BoardPosition b : nextMoves)
		{
			//System.out.println("level at"+depth+", board="+b);
			if (isBlack)
			{
				in = MiniMax(depth - 1, false, b);
				out.numNodes += in.numNodes;
				if (in.estimate > out.estimate)
				{
					out.estimate = in.estimate;
					out.b = b;
				}
			}
			else
			{
				in = MiniMax(depth - 1, true, b);
				out.numNodes += in.numNodes;
				out.numNodes++;
				if (in.estimate < out.estimate)
				{
					out.estimate = in.estimate;
					out.b = b;
				}
			}
		}
		return out;
	
	}

}
