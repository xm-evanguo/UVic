import java.util.*;

public class a1 {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		HashSet<String> hs = new HashSet<String>();
		while(sc.hasNext()) {
			String tmp = sc.next();
			if(hs.contains(tmp)) {
				System.out.println("no");
				return;
			}
			hs.add(tmp);
		}

		System.out.print("yes");
		
		
		sc.close();
	}

}
