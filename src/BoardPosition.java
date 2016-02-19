/***
 * Author: Hengxiu Gao, UTD ID: 2021229554
 * 
 * CS 6343 AI Project, Morris Game Variant-D
 * 
 * Class: BoardPosition, Data structure for a board.
 * 
 */


public class BoardPosition {
	private String BoardString;
	private boolean isWhite;
	private int phy=0;
	private PositionType[] position;
	private int numOfWhite = 0;
	private int numOfBlack = 0;
	public boolean isClose = false;
	BoardPosition()
	{
		position = new PositionType[23];
		for(int i=0;i<23;i++)
		{
			position[i] = PositionType.x;
		}
	}
	
	BoardPosition(String BoardString) throws Exception
	{
		position = new PositionType[23];
		this.BoardString = BoardString;
		if(BoardString.length()!=23)
			throw new Exception("Board string is not supported, the length must be exactly 23");
		String2Position();
		countBlackWhite();
	}
	
	boolean isEmpty(int i)
	{
		return position[i]==PositionType.x;
	}
	void movePiece(int from, int to)throws Exception
	{
		if(!isEmpty(to))
			throw new Exception("Move ERROR");
		position[to] = position[from];
		position[from] = PositionType.x;
	}
	void putPiece(int i, PositionType type) throws Exception
	{
		if(type==PositionType.x)
			throw new Exception("Put ERROR");
		position[i] = type;
		if(type==PositionType.W)
			numOfWhite++;
		else numOfBlack++;
	}
	
	void removePiece(int i) throws Exception
	{
		if(position[i]==PositionType.x)
			throw new Exception("Remove ERROR");
		if(position[i]==PositionType.W)
			numOfWhite--;
		else
			numOfBlack--;
		position[i]=PositionType.x;
		
	}
	PositionType getPosition(int i)
	{
		return position[i];
	}
	PositionType[] getPositionArray()
	{
		return position;
	}
	public String toString()
	{
		Position2String();
		return BoardString;
	}
	
	void String2Position()
	{
		for(int i=0;i<23;i++)
		{
			switch(BoardString.charAt(i))
			{
			case 'x':
				position[i] = PositionType.x;
				break;
			case 'W':
				position[i] = PositionType.W;
				break;
			case 'B':
				position[i] = PositionType.B;
				break;
			}
			
		}
	}
	
	void Position2String()
	{
		char[] boardchar = new char[23];
		for(int i=0;i<23;i++)
		{
			switch(position[i])
			{
			case x:
				boardchar[i] = 'x';
				break;
			case W:
				boardchar[i] = 'W';
				break;
			case B:
				boardchar[i] = 'B';
				break;
			}
			
		}
		BoardString = new String(boardchar);
	}
	
	void swapColor()
	{
		for(int i=0;i<23;i++)
		{
			if(position[i]==PositionType.B)
				position[i] = PositionType.W;
			else if(position[i]==PositionType.W)
				position[i] = PositionType.B;
		}
		int temp = numOfWhite;
		numOfWhite = numOfBlack;
		numOfBlack = temp;
	}
	void countBlackWhite()
	{
		for(int i=0;i<23;i++)
			if(position[i]==PositionType.B)
				numOfBlack++;
			else if(position[i]==PositionType.W)
				numOfWhite++;
	}
	
	int getNumOfWhite()
	{
		return numOfWhite;
	}
	
	int getNumOfBlack()
	{
		return numOfBlack;
	}
	
	boolean IsWhite()
	{
		return isWhite;
	}
	
	void setIsWhite(boolean in)
	{
		isWhite = in;
	}
	
	int getPhy()
	{
		return phy;
	}
	
	void setPhy(int in)
	{
		phy = in;
	}
	
}
