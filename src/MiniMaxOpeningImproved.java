/***
 * Author: Hengxiu Gao, UTD ID: 2021229554
 * 
 * CS 6343 AI Project, Morris Game Variant-D
 * 
 * Class: MiniMaxOpeningImproved, Using mini max algorithm to search the White players' moves for the stage 1 of Mills Game
 * 		  The static estimate function has been improved, it considers of Closed Mills, Difference of # Closed mills, Difference of # blocked opponent pieces, 
 * 		  Difference of # pieces, Difference of # 2 piece configurations, Difference of # 3-piece configurations.
 * 
 */

import java.util.ArrayList;

public class MiniMaxOpeningImproved {

	StaticEstimation estimate = new StaticEstimation();
	MoveGenerator moveGen = new MoveGenerator();
	public static void main(String[] args) throws Exception {
		MiniMaxOpeningImproved minimaximproved = new MiniMaxOpeningImproved();
		
		if(args.length>0)
		{
			if(args.length<4)
			{
				System.out.println("Arguments are not correct, \nThe Arguments shoule be: 1. InputFile name, 2. Output File name, 3. Maximum Depth, 4. the Phy of Input board \n   "
						+ "here the Maximum Depth means the maximum height of the search tree, the phy of input board means the rounds that has already been played in the input "
						+ "board, for example, the phy of board 'xxxxxxxxxxxxxxxxxxxxxx'=0, the phy of board 'xxxxxxxxxxxxxxxxxxxWxx'=1, the phy of board 'xxxxxxxxxxxxxxWxxBxxxx'=2,");
				System.exit(0);
			}
			System.out.println("Program running, Mini max for the opening stage with improved static estimate function");
			String InputFile = args[0];
			String OutputFile = args[1];
			int depth = Integer.parseInt(args[2]);
			int current_phy = Integer.parseInt(args[3]);
			BoardPosition InputPosition = new BoardPosition(Utility.ReadFile(InputFile));
			InputPosition.setPhy(current_phy);
			
			System.out.println("Input Board:");
			Utility.printBoard(InputPosition);
			
			OutputObject out = minimaximproved.MiniMaxOpeningImporved(depth,true,InputPosition);
			
			System.out.println("Output Board:");
			Utility.printBoard(out.b);
			Utility.WriteFile(OutputFile, out.toString());
			System.out.println("Program finishes.");
		}else
		{
			BoardPosition InputPosition = new BoardPosition("xxxxxxxxxWxxWxxxBxxxxxx");
			InputPosition.setPhy(4);
			
			OutputObject out = minimaximproved.MiniMaxOpeningImporved(5,true,InputPosition);
			
			System.out.println(out);
		}
		
	}
	/*
	void GenerateSearchTree(SearchTreeNode root, int phy) throws Exception
	{
		ArrayList<BoardPosition> BoardList;
		if(phy==4)
			return;
		
		if(phy%2==0)
			BoardList = moveGW.GenerateMovesOpening(root.board);
		else
			BoardList = moveGB.GenerateMovesOpening(root.board);
		for(BoardPosition board : BoardList)
		{
			SearchTreeNode child = new SearchTreeNode(board,root.board.getPhy()+1);
			root.children.add(child);
			GenerateSearchTree(child,root.board.getPhy()+1);
		}
		
	}*/
	
	OutputObject MiniMaxOpeningImporved(int depth, boolean isWhite, BoardPosition board) throws Exception
	{
		OutputObject out = new OutputObject();
		/* Means that we are at a terminal node */
		if (depth == 0)
		{
			out.estimate = estimate.StaticEstimateOpeningImproved(board);
			//System.out.println("estimate="+out.estimate);
			out.numNodes++;
			return out;
		}

		OutputObject in = new OutputObject();
		ArrayList<BoardPosition> nextMoves = (isWhite) ? moveGen.GenerateMovesOpening(board) : moveGen.GenerateMovesOpeningBlack(board);
		out.estimate = (isWhite) ? Integer.MIN_VALUE : Integer.MAX_VALUE;
		for (BoardPosition b : nextMoves)
		{
			b.setPhy(board.getPhy()+1);
			//System.out.println("level at"+depth+", board="+b+",phy="+b.getPhy()+",is White="+b.IsWhite());
			
			if (isWhite)
			{
				in = MiniMaxOpeningImporved(depth - 1, false, b);
				out.numNodes += in.numNodes;
				if (in.estimate > out.estimate)
				{
					out.estimate = in.estimate;
					out.b = b;
				}
			}
			else
			{
				in = MiniMaxOpeningImporved(depth - 1, true, b);
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
