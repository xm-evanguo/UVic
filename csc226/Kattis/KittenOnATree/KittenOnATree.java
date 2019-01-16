import java.util.*;
public class KittenOnATree {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int[] parent = new int[101];
		for(int i = 0; i < parent.length; i++) {
			parent[i] = i;
		}
		String[] line;
		int tmp;
		int Kitten = sc.nextInt();
		sc.nextLine();
		while(sc.hasNext()) {
			line = sc.nextLine().split(" ");
			tmp = Integer.parseInt(line[0]);
			if(tmp == -1) {
				break;
			}
			for(int i = 1; i < line.length; i++) {
				parent[Integer.parseInt(line[i])] = tmp;
			}
		}
		sc.close();
		System.out.print(Kitten);	
		while(Kitten != parent[Kitten]) {
			Kitten = parent[Kitten];
			System.out.print(" " + Kitten);
		}
	}

}
