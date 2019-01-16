import java.util.*;
import java.util.Map.Entry;
public class SC {

	public static void main(String[] args) {
		TreeMap<Integer,String> tm = new TreeMap<Integer,String>();
		Scanner sc = new Scanner(System.in);
		int nCase = sc.nextInt();
		int r = 0;
		String s;
		for(int i = 0; i < nCase; i++) {
			if(sc.hasNextInt()) {
				r = sc.nextInt()/2;
				s = sc.next();
			}else {
				s = sc.next();
				r = sc.nextInt();
			}
			tm.put(r, s);
		}
		
		for(Entry<Integer, String> entry: tm.entrySet()) {
		    	 System.out.println(entry.getValue());
		}
		/*
		 * ---------------------------------------------------------------------------------------------------------
		 *  Out print MapTree with Map.Entry:
		 *  
		 *  for(Map.Entry<K, V> entry : tm.entrySet()) {
     	 *		System.out.println("Key: " + entry.getKey() + ". Value: " + entry.getValue());
		 *	}
		 *		
		 *  --------------------------------------------------------------------------------------------------------
		 *  Out print MapTree with Iterator and Set:
		 *  
		 *  Set set = tm.entrySet();
		 *  Iterator iterator = set.iterator();
		 *  while(iterator.hasNext()){
		 *  	Map.Entry entry = (Map.Entry)iterator.next();
		 *  	System.out.println("Key is " + entry.getKey() + " & Value is " + entry.getValue());
		 *  }
         *
         *  --------------------------------------------------------------------------------------------------------
         *  Out print HashSet with Iterator:
         *  	
         *  HashSet<String> hs = new HashSet<String>();
         *  Iterator<String> it = hs.Iterator();
         *  while(it.hasNext()){
         *  	System.out.println(it.next());
         *  }
         *  
		 */
		sc.close();
	}

}
