
public class Subset {
	 public static void main(String[] args) 
	 {
		 if (args.length < 1) {
			 StdOut.printf("args: k number of inputs to read from stdin");
			 return;
		 }
			 
		 int k = Integer.parseInt(args[0]);
		 
		 RandomizedQueue<String> sQueue = new RandomizedQueue<String>();
		 
		 while (!StdIn.isEmpty())
		 {
			 String s = StdIn.readString();
			 sQueue.enqueue(s);
		 }
		 
		 for (int i = 0; i < k; i++) {
			 StdOut.println(sQueue.dequeue());
		 }
		 
	 }
}
