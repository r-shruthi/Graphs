import java.util.ArrayList;
import java.util.List;

/**Stack Implementation using List
 * Operations:
 * Push(Integer element)
 * Pop()
 * Peek()
 ** 
 */

/**
 * @author shrut
 *
 */
public class Stack {

	List<Integer> stackList;
	int top = -1;
	Integer topObj;
	public Stack()
	{
		stackList = new ArrayList<Integer>();
	}
	
	public void push(Integer pushElement)
	{
		stackList.add(pushElement);
		top++;		
	}
	
	public Integer pop()
	{
		Integer topElement;
		if(top >= 0){
			topElement = stackList.remove(top);
			top--;
		}
		else
		{
			topElement = null;
		}
		return topElement;			
	}
	
	public Integer peek()
	{
		Integer topElement;
		if(top >= 0){
			topElement = stackList.get(top);
		}
		else
		{
			topElement = null;
		}
		return topElement;		
	}
	
	public Boolean isEmpty()
	{
		if(stackList.isEmpty())
			return true;
		else
			return false;
	}
	
}
