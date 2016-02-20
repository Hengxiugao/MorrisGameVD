/***
 * Author: Hengxiu Gao, Author: Hengxiu Gao, Email:Hengxiugao@yahoo.com
 *
 * CS 6343 AI Project, Morris Game Variant-D
 *
 * Class: OutputObject, return a string which is required output format
 *
 */
public class OutputObject
{
	public int estimate, numNodes;
	public BoardPosition b;
	public String toString()
	{
		return 	"BoardPosition:\t\t\t" + b + "\n" +
				"Positions Evaluated:\t" + numNodes + "\n" +
				"MINIMAX estimate:\t\t" + estimate;
	}
}
