/***
 * Author: Hengxiu Gao, Email:Hengxiugao@yahoo.com
 *
 * CS 6343 AI Project, Morris Game Variant-D
 *
 * Class: ABOpening, Using alpha-beta pruning algorithm to search the white players' moves for the stage 1 of Morris Game
 *
 */


import java.util.List;


public class ABOpening {

	public static void main(String[] args) throws Exception {
		ABOpening ab = new ABOpening();

		if(args.length>0)
		{
			if(args.length<4)
			{
				System.out.println("Arguments are not correct, \nThe Arguments shoule be: 1. InputFile name, 2. Output File name, 3. Maximum Depth, 4. the Phy of Input board \n   "
						+ "here the Maximum Depth means the maximum height of the search tree, the phy of input board means the rounds that has already been played in the input "
						+ "board, for example, the phy of board 'xxxxxxxxxxxxxxxxxxxxxx'=0, the phy of board 'xxxxxxxxxxxxxxxxxxxWxx'=1, the phy of board 'xxxxxxxxxxxxxxWxxBxxxx'=2,");
				System.exit(0);
			}
			System.out.println("Program running, alpha-beta purning for the opening stage");
			String InputFile = args[0];
			String OutputFile = args[1];
			int depth = Integer.parseInt(args[2]);
			int current_phy = Integer.parseInt(args[3]);
			BoardPosition InputPosition = new BoardPosition(Utility.ReadFile(InputFile));
			InputPosition.setPhy(current_phy);

			System.out.println("Input Board:");
			Utility.printBoard(InputPosition);

			OutputObject out = ab.ABMiniMaxOpening(depth,true,InputPosition, Integer.MIN_VALUE, Integer.MAX_VALUE);

			System.out.println("Output Board:");
			Utility.printBoard(out.b);
			Utility.WriteFile(OutputFile, out.toString());
			System.out.println("Program finishes.");
		}else
		{
			BoardPosition InputPosition = new BoardPosition("WBWBBWxxxxxxxxxxxxxxxxx");
			InputPosition.setPhy(6);
			OutputObject out = ab.ABMiniMaxOpening(7,true,InputPosition,Integer.MIN_VALUE, Integer.MAX_VALUE);
			Utility.printBoard(out.b);
			System.out.println(out);
		}
	}

	StaticEstimation estimate = new StaticEstimation();
	MoveGenerator moveGen = new MoveGenerator();
	public OutputObject ABMiniMaxOpening(int depth, boolean isWhite, BoardPosition board, int alpha, int beta) throws Exception
	{
		OutputObject out = new OutputObject();

		/* Means that we are at a terminal node */
		if (depth == 0)
		{
			out.estimate = estimate.StaticEstimateOpening(board);
			out.numNodes++;
			return out;
		}

		OutputObject in = new OutputObject();
		List<BoardPosition> nextMoves = (isWhite) ? moveGen.GenerateMovesOpening(board) : moveGen.GenerateMovesOpeningBlack(board);
		for (BoardPosition b : nextMoves)
		{
			if (isWhite)
			{
				in = ABMiniMaxOpening(depth - 1, false, b, alpha, beta);
				out.numNodes += in.numNodes;
				if (in.estimate > alpha)
				{
					alpha = in.estimate;
					out.b = b;
				}
			}
			else
			{
				in = ABMiniMaxOpening(depth - 1, true, b, alpha, beta);
				out.numNodes += in.numNodes;
				out.numNodes++;
				if (in.estimate < beta)
				{
					beta = in.estimate;
					out.b = b;
				}
			}
			if (alpha >= beta)
			{
				break;
			}
		}

		out.estimate = (isWhite) ? alpha : beta;
		return out;
	}
}
