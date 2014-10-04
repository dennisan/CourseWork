/**********************************************
Dequeue. A double-ended queue or deque (pronounced "deck") is a generalization of a stack and a queue 
that supports inserting and removing items from either the front or the back of the data structure. Create 
a generic data type Deque that implements the following API:

public class Deque<Item> implements Iterable<Item> {
   public Deque()                           // construct an empty deque
   public boolean isEmpty()                 // is the deque empty?
   public int size()                        // return the number of items on the deque
   public void addFirst(Item item)          // insert the item at the front
   public void addLast(Item item)           // insert the item at the end
   public Item removeFirst()                // delete and return the item at the front
   public Item removeLast()                 // delete and return the item at the end
   public Iterator<Item> iterator()         // return an iterator over items in order from front to end
   public static void main(String[] args)   // unit testing
}

Throw a NullPointerException if the client attempts to add a null item; throw a java.util.NoSuchElementException 
if the client attempts to remove an item from an empty deque; throw an UnsupportedOperationException if the 
client calls the remove() method in the iterator; throw a java.util.NoSuchElementException if the client calls 
the next() method in the iterator and there are no more items to return.

Your deque implementation must support each deque operation in constant worst-case time and use space proportional 
to the number of items currently in the deque. Additionally, your iterator implementation must support the 
operations next() and hasNext() (plus construction) in constant worst-case time and use a constant amount 
of extra space per iterator.
**********************************************/

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> 
{
	private Node first, last;
	private int size = 0;

	private class Node {
		public Node next;
		public Node prev;
		public Item item;
		public Node(Item Item) { item = Item; }
	}
	
	public Deque() {	}
   
	public boolean isEmpty() 
	{
		return first == null;
	}

	public int size()  
	{
		return size;
	}
   
	public void addFirst(Item item)          
	{
	   // insert the item at the front
	   if (item == null)
		   throw new NullPointerException(); 
	   
	   Node newNode = new Node(item);
	   
	   if (first != null)
	   {
		   newNode.next = first;
		   first.prev = newNode;
	   }
	   
	   first = newNode;
	   size++;

	   if (last == null)
		   last = first;
   }
   
   public void addLast(Item item) 
   {
	   if (item == null)
		   throw new NullPointerException(); 

	   // insert the item at the end
	   Node newNode = new Node(item);
	   
	   if (last != null)
	   {
		   newNode.prev = last;
		   last.next = newNode;
	   }
	   
	   last = newNode;
	   size++;

	   if (first == null)
		   first = last;
   }
   
   public Item removeFirst() 
   {
	   // delete and return the item at the front
	   
	   if (first == null)
		   throw new NoSuchElementException();
		   
	   Node oldFirst = first;

	   if (oldFirst.next == null)
	   {
		   first = null;
		   last = null;
	   }
	   else
	   {
		   first = oldFirst.next;
		   first.prev = null;
		   oldFirst.next = null;
	   }
	   
	   size--;
	   return oldFirst.item;
   }
   
   public Item removeLast() 
   {
	   if (last == null)
		   throw new NoSuchElementException();

	   Node oldLast = last;

	   if (oldLast.prev == null)
	   {
		   last = null;
		   first = null;
	   }
	   else
	   {
		   last = oldLast.prev;
		   last.next = null;
		   oldLast.prev = null;
	   }
	   
	   size--;
	   return oldLast.item;
   }
   
   public Iterator<Item> iterator() 
   {
	   // return an iterator over items in order from front to end
	   return new DequeIterator();
   }
   
   private class DequeIterator implements Iterator<Item>
   {
	   private Node current = first;
	   
	   public boolean hasNext() 
	   {
		   return current != null; 
	   }
	   
	   public void remove() 
	   {
		   throw new UnsupportedOperationException();
	   }
	   
	   public Item next()
	   {
		   if (current == null)
			   	throw new NoSuchElementException();
		   
		   Item item = current.item;
		   current = current.next;
		   return item;
	   }
   }
   
   public static void main(String[] args) 
   {
	   Deque<Integer> d = new Deque<Integer>();
	   
	   StdOut.printf("====== test 1 ======\n");

	   StdOut.printf("Adding 1000 integers to front\n");
	   for (int i = 0; i < 1000; i++)
		   d.addFirst(i);

	   StdOut.printf("deque has %d integers - ", d.size());
	   for (int i : d) StdOut.printf("%d ", i);
	   
	   StdOut.printf("\nRemoving 1000 integers from front\n");
	   for (int i = 0; i < 1000; i++)
		   d.removeFirst();

	   StdOut.printf("deque has %d integers - ", d.size());
	   for (int i : d) StdOut.printf("%d ", i);

	   StdOut.printf("\n\n====== test B ======\n");
	   StdOut.printf("Adding 1000 integers to back\n");

	   for (int i = 0; i < 1000; i++)
		   d.addFirst(i);

	   StdOut.printf("deque has %d integers - ", d.size());
	   for (int i : d) StdOut.printf("%d ", i);
	   
	   StdOut.printf("\nRemoving 1000 integers from back\n");
	   for (int i = 0; i < 1000; i++)
		   d.removeLast();
	   
	   StdOut.printf("deque has %d integers - ", d.size());
	   for (int i : d) StdOut.printf("%d ", i);

	   StdOut.printf("\n\n====== test 3 ======\n");
	   StdOut.printf("Adding 1000 integers to front\n");
	   
	   for (int i = 0; i < 1000; i++)
		   d.addFirst(i);

	   StdOut.printf("deque has %d integers - ", d.size());
	   for (int i : d) StdOut.printf("%d ", i);

	   StdOut.printf("\nRemoving 1000 integers from back\n");
	   for (int i = 0; i < 1000; i++)
		   d.removeLast();

	   StdOut.printf("deque has %d integers - ", d.size());
	   for (int i : d) StdOut.printf("%d ", i);
	   
	   StdOut.printf("\n\n====== test 4 ======\n");
	   StdOut.printf("Adding 1000 integers to back\n");
	   for (int i = 0; i < 1000; i++)
		   d.addFirst(i);

	   StdOut.printf("deque has %d integers - ", d.size());
	   for (int i : d) StdOut.printf("%d ", i);

	   StdOut.printf("\nRemoving 1000 integers from front\n");
	   for (int i = 0; i < 1000; i++)
		   d.removeFirst();

	   StdOut.printf("deque has %d integers\n\n", d.size());
	   for (int i : d) StdOut.printf("%d ", i);
	   
   }
}