/***
 * Author: Hengxiu Gao, Author: Hengxiu Gao, Email:Hengxiugao@yahoo.com
 *
 * CS 6343 AI Project, Morris Game Variant-D
 *
 * Class: Tournament
 *
 */
import java.util.List;


public class Tournament {




	//System.out.print(content.toString());


	public static void main(String[] args) throws Exception {
		Tournament tournament = new Tournament();


		if(args.length>0)
		{
			if(args.length<5)
			{
				System.out.println("Arguments are not correct, \nThe Arguments shoule be: 1. InputFile name, 2. Output File name, 3. Maximum Depth"
						+ "4. The game phase (1 or 2), 5. White=0 or Black=1");
				System.exit(0);
			}
			System.out.println("Program running, Tournament");
			String InputFile = args[0];
			String OutputFile = args[1];
			int depth = Integer.parseInt(args[2]);
			int phase = Integer.parseInt(args[3]);
			int WhiteorBlack = Integer.parseInt(args[4]);
			BoardPosition InputPosition = new BoardPosition(Utility.ReadFile(InputFile));

			System.out.println("Input Board:");
			Utility.printBoard(InputPosition);

			OutputObject out = null;
			if(WhiteorBlack==0) // For white
			{
				if(phase==1)
					out = tournament.ABMiniMaxOpening(depth,true,InputPosition, Integer.MIN_VALUE, Integer.MAX_VALUE);
				else
				{
					out = tournament.ABMiniMaxGame(depth,true,InputPosition, Integer.MIN_VALUE, Integer.MAX_VALUE);

					if(out.b.getNumOfBlack()==2)
						System.out.println("Black Lose");
					if(out.b.getNumOfWhite()==2)
						System.out.println("White Lose");
				}
			}else		// For black
			{
				ABGame abg = new ABGame();
				ABOpening abo = new ABOpening();
				InputPosition.swapColor();
				if(phase==1)
				{
					out = tournament.ABMiniMaxOpening(depth,true,InputPosition, Integer.MIN_VALUE, Integer.MAX_VALUE);
					out.b.swapColor();
				}
				else
				{
					out = tournament.ABMiniMaxGame(depth,true,InputPosition, Integer.MIN_VALUE, Integer.MAX_VALUE);
					out.b.swapColor();
					if(out.b.getNumOfBlack()==2)
						System.out.println("Black Lose");
					if(out.b.getNumOfWhite()==2)
						System.out.println("White Lose");
				}
			}

			Utility.WriteFile(OutputFile, out.b.toString());

			System.out.println("\nOutput Board:");
			Utility.printBoard(out.b);



			System.out.println("Program finishes.");
		}else
		{
			String InputFile = "in.txt";
			String BoardStr = Utility.ReadDirBoardFromFile(InputFile);
			Utility.initWrite();
			//Utility.WriteFileDuringRecu("123");
			//Utility.WriteFileDuringRecu("123");
			//BoardStr = "xBBBxBBxWWxWBWxWxxWxxxB";
			//System.out.println("Input Board:"+BoardStr);
			//Utility.printBoard(new BoardPosition(BoardStr));


			int depth = 7;
			int phase = 2;
			BoardPosition InputPosition = new BoardPosition(BoardStr);

			OutputObject out = null;
			if(phase==1)
				out = tournament.ABMiniMaxOpening(depth,true,InputPosition, Integer.MIN_VALUE, Integer.MAX_VALUE);
			else
				out = tournament.ABMiniMaxGame(depth,true,InputPosition, Integer.MIN_VALUE, Integer.MAX_VALUE);

			System.out.println("\nOutput Board:");
			Utility.printBoard(out.b);
			System.out.println(out);
			if(out.b.getNumOfBlack()==2)
				System.out.println("Black Lose");
			if(out.b.getNumOfWhite()==2)
				System.out.println("White Lose");
			Utility.closeFile();

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
			out.estimate = estimate.StaticEstimateOpeningImproved(board);
			out.numNodes++;
			//Utility.WriteFileDuringRecu("             l="+depth+" "+board.toString()+" estimate="+out.estimate+"\n");
			return out;
		}
		/*
		if(depth==6)
		{
			Utility.WriteFileDuringRecu(board.toString()+" alpha="+alpha+", beta="+beta+"\n");
		}

		if(depth==5)
		{
			Utility.WriteFileDuringRecu("  l="+depth+" "+board.toString()+" alpha="+alpha+", beta="+beta+"\n");
		}

		if(depth==4)
		{
			Utility.WriteFileDuringRecu("     l="+depth+" "+board.toString()+" alpha="+alpha+", beta="+beta+"\n");
		}

		if(depth==3)
		{
			Utility.WriteFileDuringRecu("       l="+depth+" "+board.toString()+" alpha="+alpha+", beta="+beta+"\n");
		}

		if(depth==2)
		{
			Utility.WriteFileDuringRecu("         l="+depth+" "+board.toString()+" alpha="+alpha+", beta="+beta+"\n");
		}

		if(depth==1)
		{
			Utility.WriteFileDuringRecu("           l="+depth+" "+board.toString()+" alpha="+alpha+", beta="+beta+"\n");
		}
		*/

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

	public OutputObject ABMiniMaxGame(int depth, boolean isWhite, BoardPosition board, int alpha, int beta) throws Exception
	{
		OutputObject out = new OutputObject();

		/* Means that we are at a terminal node */
		if (depth == 0)
		{
			out.estimate = estimate.StaticEstimateMidgameEndgameImproved(board);
			out.numNodes++;
			//Utility.WriteFileDuringRecu("             l="+depth+" "+board.toString()+" estimate="+out.estimate+"\n");
			return out;
		}
		/*
		if(depth==6)
		{
			Utility.WriteFileDuringRecu(board.toString()+" alpha="+alpha+", beta="+beta+"\n");
		}

		if(depth==5)
		{
			Utility.WriteFileDuringRecu("  l="+depth+" "+board.toString()+" alpha="+alpha+", beta="+beta+"\n");
		}

		if(depth==4)
		{
			Utility.WriteFileDuringRecu("     l="+depth+" "+board.toString()+" alpha="+alpha+", beta="+beta+"\n");
		}

		if(depth==3)
		{
			Utility.WriteFileDuringRecu("       l="+depth+" "+board.toString()+" alpha="+alpha+", beta="+beta+"\n");
		}

		if(depth==2)
		{
			Utility.WriteFileDuringRecu("         l="+depth+" "+board.toString()+" alpha="+alpha+", beta="+beta+"\n");
		}

		if(depth==1)
		{
			Utility.WriteFileDuringRecu("           l="+depth+" "+board.toString()+" alpha="+alpha+", beta="+beta+"\n");
		}
		*/
		OutputObject in = new OutputObject();
		List<BoardPosition> nextMoves = (isWhite) ? moveGen.GenerateMovesMidgameEndgame(board) : moveGen.GenerateMovesMidgameEndgameBlack(board);
		for (BoardPosition b : nextMoves)
		{
			if (isWhite)
			{
				in = ABMiniMaxGame(depth - 1, false, b, alpha, beta);
				out.numNodes += in.numNodes;
				//System.out.println("depth="+depth+", board="+b);
				if (in.estimate > alpha)
				{
					alpha = in.estimate;
					out.b = b;
				}
			}
			else
			{
				in = ABMiniMaxGame(depth - 1, true, b, alpha, beta);
				out.numNodes += in.numNodes;
				out.numNodes++;
				//System.out.println("depth="+depth+", board="+b);
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

	public OutputObject ABMiniMaxOpeningBlack(int depth, boolean isWhite, BoardPosition board, int alpha, int beta) throws Exception
	{
		OutputObject out = new OutputObject();

		/* Means that we are at a terminal node */
		if (depth == 0)
		{
			BoardPosition board_white = new BoardPosition(board.toString());
			board_white.swapColor();
			out.estimate = estimate.StaticEstimateOpeningImproved(board_white);
			out.numNodes++;
			//Utility.WriteFileDuringRecu("             l="+depth+" "+board.toString()+" estimate="+out.estimate+"\n");
			return out;
		}
		/*
		if(depth==6)
		{
			Utility.WriteFileDuringRecu(board.toString()+" alpha="+alpha+", beta="+beta+"\n");
		}

		if(depth==5)
		{
			Utility.WriteFileDuringRecu("  l="+depth+" "+board.toString()+" alpha="+alpha+", beta="+beta+"\n");
		}

		if(depth==4)
		{
			Utility.WriteFileDuringRecu("     l="+depth+" "+board.toString()+" alpha="+alpha+", beta="+beta+"\n");
		}

		if(depth==3)
		{
			Utility.WriteFileDuringRecu("       l="+depth+" "+board.toString()+" alpha="+alpha+", beta="+beta+"\n");
		}

		if(depth==2)
		{
			Utility.WriteFileDuringRecu("         l="+depth+" "+board.toString()+" alpha="+alpha+", beta="+beta+"\n");
		}

		if(depth==1)
		{
			Utility.WriteFileDuringRecu("           l="+depth+" "+board.toString()+" alpha="+alpha+", beta="+beta+"\n");
		}
		*/

		OutputObject in = new OutputObject();
		List<BoardPosition> nextMoves = (isWhite) ? moveGen.GenerateMovesOpeningBlack(board) : moveGen.GenerateMovesOpening(board);
		for (BoardPosition b : nextMoves)
		{
			if (isWhite)
			{
				in = ABMiniMaxOpeningBlack(depth - 1, false, b, alpha, beta);
				out.numNodes += in.numNodes;
				if (in.estimate > alpha)
				{
					alpha = in.estimate;
					out.b = b;
				}
			}
			else
			{
				in = ABMiniMaxOpeningBlack(depth - 1, true, b, alpha, beta);
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

	public OutputObject ABMiniMaxGameBlack(int depth, boolean isWhite, BoardPosition board, int alpha, int beta) throws Exception
	{
		OutputObject out = new OutputObject();

		/* Means that we are at a terminal node */
		if (depth == 0)
		{
			BoardPosition board_white = new BoardPosition(board.toString());
			board_white.swapColor();
			out.estimate = estimate.StaticEstimateOpeningImproved(board_white);
			out.numNodes++;
			//Utility.WriteFileDuringRecu("             l="+depth+" "+board.toString()+" estimate="+out.estimate+"\n");
			return out;
		}
		/*
		if(depth==6)
		{
			Utility.WriteFileDuringRecu(board.toString()+" alpha="+alpha+", beta="+beta+"\n");
		}

		if(depth==5)
		{
			Utility.WriteFileDuringRecu("  l="+depth+" "+board.toString()+" alpha="+alpha+", beta="+beta+"\n");
		}

		if(depth==4)
		{
			Utility.WriteFileDuringRecu("     l="+depth+" "+board.toString()+" alpha="+alpha+", beta="+beta+"\n");
		}

		if(depth==3)
		{
			Utility.WriteFileDuringRecu("       l="+depth+" "+board.toString()+" alpha="+alpha+", beta="+beta+"\n");
		}

		if(depth==2)
		{
			Utility.WriteFileDuringRecu("         l="+depth+" "+board.toString()+" alpha="+alpha+", beta="+beta+"\n");
		}

		if(depth==1)
		{
			Utility.WriteFileDuringRecu("           l="+depth+" "+board.toString()+" alpha="+alpha+", beta="+beta+"\n");
		}
		*/
		OutputObject in = new OutputObject();
		List<BoardPosition> nextMoves = (isWhite) ? moveGen.GenerateMovesMidgameEndgameBlack(board) : moveGen.GenerateMovesMidgameEndgame(board);
		for (BoardPosition b : nextMoves)
		{
			if (isWhite)
			{
				in = ABMiniMaxGameBlack(depth - 1, false, b, alpha, beta);
				out.numNodes += in.numNodes;
				//System.out.println("depth="+depth+", board="+b);
				if (in.estimate > alpha)
				{
					alpha = in.estimate;
					out.b = b;
				}
			}
			else
			{
				in = ABMiniMaxGameBlack(depth - 1, true, b, alpha, beta);
				out.numNodes += in.numNodes;
				out.numNodes++;
				//System.out.println("depth="+depth+", board="+b);
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
