import java.util.Scanner;
public class ArmyStrength {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		if(n == 0) {
			System.out.println("uncertain");
		}else {
			for(int i = 0; i < n; i++) {
				System.out.println(compare(sc));
			}
		}
	}	
	
	public static String compare(Scanner sc) {
		int Godzilla = sc.nextInt();
		int MechaGodzilla = sc.nextInt();
		LinkedList GodzillaArmy = new LinkedList();
		LinkedList MechaGodzillaArmy = new LinkedList();
		ListNode cur;
		
		for(int i = 0; i < Godzilla; i++) {
			cur = new ListNode(sc.nextInt(), null);
			if(i == 0) {
				GodzillaArmy.start = cur;
			}else {
				GodzillaArmy.rear.next = cur;
			}
			GodzillaArmy.rear = cur;
			GodzillaArmy.n++;
		}
		

		for(int i = 0; i < MechaGodzilla; i++) {
			cur = new ListNode(sc.nextInt(), null);
			if(i == 0) {
				MechaGodzillaArmy.start = cur;
			}else {
				MechaGodzillaArmy.rear.next = cur;
			}
			MechaGodzillaArmy.rear = cur;
			MechaGodzillaArmy.n++;
		}
		
		GodzillaArmy.sort();
		MechaGodzillaArmy.sort();
		
		while(GodzillaArmy.start != null && MechaGodzillaArmy.start != null) {
			if(GodzillaArmy.start.data >= MechaGodzillaArmy.start.data) {
				MechaGodzillaArmy.start = MechaGodzillaArmy.start.next;
			}else {
				GodzillaArmy.start = GodzillaArmy.start.next;
			}
		}
		
		if(GodzillaArmy.start == null && MechaGodzillaArmy.start == null) {
			return "uncertain";
		}else if(GodzillaArmy.start == null) {
			return "MechaGodzilla";
		}else {
			return "Godzilla";
		}
	}


}
