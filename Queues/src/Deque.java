import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> 
{
   public Deque() 
   {
	   // construct an empty deque
   }
   
   public boolean isEmpty() 
   {
	   // is the deque empty?
	   return false;
   }

   public int size()  
   {
	   // return the number of items on the deque
	   return 0;
   }
   
   public void addFirst(Item item)          
   {
	   // insert the item at the front
   }
   
   public void addLast(Item item) 
   {
	   // insert the item at the end
   }
   
   public Item removeFirst() 
   {
	   // delete and return the item at the front
	   return null;
   }
   
   public Item removeLast() 
   {
	   // delete and return the item at the end
	   return null;
   }
   
   public Iterator<Item> iterator() 
   {
	   // return an iterator over items in order from front to end
	   return null;
   }
   
   public static void main(String[] args) 
   {
	   // unit testing
   }
}