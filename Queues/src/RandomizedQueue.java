/**********************************************
Randomized queue. A randomized queue is similar to a stack or queue, except that the item removed is chosen 
uniformly at random from items in the data structure. Create a generic data type RandomizedQueue that implements the following API:

public class RandomizedQueue<Item> implements Iterable<Item> {
   public RandomizedQueue()                 // construct an empty randomized queue
   public boolean isEmpty()                 // is the queue empty?
   public int size()                        // return the number of items on the queue
   public void enqueue(Item item)           // add the item
   public Item dequeue()                    // delete and return a random item
   public Item sample()                     // return (but do not delete) a random item
   public Iterator<Item> iterator()         // return an independent iterator over items in random order
   public static void main(String[] args)   // unit testing
}

Throw a NullPointerException if the client attempts to add a null item; throw a java.util.NoSuchElementException 
if the client attempts to sample or dequeue an item from an empty randomized queue; throw an UnsupportedOperationException 
if the client calls the remove() method in the iterator; throw a java.util.NoSuchElementException if the client calls 
the next() method in the iterator and there are no more items to return.

Your randomized queue implementation must support each randomized queue operation (besides creating an iterator) in 
constant amortized time and use space proportional to the number of items currently in the queue. That is, any 
sequence of M randomized queue operations (starting from an empty queue) should take at most cM steps in the worst 
case, for some constant c. Additionally, your iterator implementation must support construction in time linear in 
the number of items and it must support the operations next() and hasNext() in constant worst-case time; you may 
use a linear amount of extra memory per iterator. The order of two or more iterators to the same randomized queue 
should be mutually independent; each iterator must maintain its own random order. 
**********************************************/

import java.util.Iterator;
import java.util.NoSuchElementException;

@SuppressWarnings("unchecked")
public class RandomizedQueue<Item> implements Iterable<Item> 
{
	private Item[] items;
	private int size;
	
	public RandomizedQueue() 
	{
		// construct an empty randomized queue
		items = (Item[]) new Object[1];
	}
   
	public boolean isEmpty() 
	{
	   // is the queue empty?
	   return size == 0;
	} 
   
	public int size() 
	{
		// return the number of items on the queue
		return size;
	}
   
	public void enqueue(Item item)
	{
		// add the item
		if (item == null)
			throw new NullPointerException();

		if (size >= items.length)
			resize(items.length * 2);
		
		items[size++] = item;

	}
   
	public Item dequeue() 
	{
		// delete and return a random item
		if (isEmpty())
			throw new NoSuchElementException();
		
		int x = StdRandom.uniform(size);
		
		size--;
		Item item = items[x];
		items[x] = items[size];
		items[size] = null;
		
		if (size > 0 && size <= items.length / 4)
			resize(items.length / 2);
		
		return item;
	}
  
	public Item sample() 
	{
		// return (but do not delete) a random item
		if (isEmpty())
			throw new NoSuchElementException();

		int x = StdRandom.uniform(size);
		return items[x];
	}
   
	public Iterator<Item> iterator() 
	{
		// return an independent iterator over items in random order
		return new QueueIterator();
	}
	

	private class QueueIterator implements Iterator<Item>
	{

		public QueueIterator()
		{
		}
		   
		public boolean hasNext() 
		{
			return size > 0; 
		}	
		   
		public void remove() 
		{
			throw new UnsupportedOperationException();
		}
		   
		public Item next()
		{
			if (size == 0)
				throw new NoSuchElementException();
			
			return dequeue();
		}
	}
	   
	
	private void resize(int capacity)
	{
		Item[] newItems = (Item[]) new Object[capacity];
		for (int i = 0; i < size; i++)
			newItems[i] = items[i];
		
		items = newItems;
	}

	
   public static void main(String[] args) 
   {
	   RandomizedQueue<Integer> iQueue = new RandomizedQueue<Integer>();
	   
	   for (int i = 0; i < 1000; i++)
	   {
		   iQueue.enqueue(i);
		   StdOut.printf("enqueued %d\n", i);
	   }
		 
	   for (int i = 0; i < 1000; i++)
		   StdOut.printf("dequeued %d\n", iQueue.dequeue());

	   
	   for (int i = 0; i < 500; i++)
	   {
		   iQueue.enqueue(i);
		   StdOut.printf("enqueued %d\n", i);
	   }

	   for (int i = 0; i < 500; i++)
		   StdOut.printf("dequeued %d\n", iQueue.dequeue());

	   StdOut.printf("\n\n");

	   RandomizedQueue<String> sQueue = new RandomizedQueue<String>();
	   String s = "apple";
	   sQueue.enqueue(s);
	   StdOut.printf("enqueued %s\n", s);

	   s = "banana";
	   sQueue.enqueue(s);
	   StdOut.printf("enqueued %s\n", s);

	   s = "cherry";
	   sQueue.enqueue(s);
	   StdOut.printf("enqueued %s\n", s);
   
	   s = "grape";
	   sQueue.enqueue(s);
	   StdOut.printf("enqueued %s\n", s);

	   s = "peach";
	   sQueue.enqueue(s);
	   StdOut.printf("enqueued %s\n", s);

	   s = "orange";
	   sQueue.enqueue(s);
	   StdOut.printf("enqueued %s\n", s);

	   printQueue(sQueue);

	   s = "grape";
	   sQueue.enqueue(s);
	   StdOut.printf("enqueued %s\n", s);

	   s = "peach";
	   sQueue.enqueue(s);
	   StdOut.printf("enqueued %s\n", s);

	   s = "orange";
	   sQueue.enqueue(s);
	   StdOut.printf("enqueued %s\n", s);
   
	   StdOut.printf("dequeued %s\n", sQueue.dequeue());
	   StdOut.printf("dequeued %s\n", sQueue.dequeue());
	   StdOut.printf("dequeued %s\n", sQueue.dequeue());

	   try {
		   sQueue.dequeue();
	   } catch (NoSuchElementException e)
	   {
		   StdOut.printf("\nException caught when caling dequeue() on empty queue");
	   }
   }
   
   @SuppressWarnings("rawtypes")
   private static void printQueue(RandomizedQueue q)
   {
	   StdOut.printf("Queue contains: ");
	   for (Object o : q)
		   StdOut.printf("%s, ", o);
	   StdOut.printf("\n\n");
   }
}