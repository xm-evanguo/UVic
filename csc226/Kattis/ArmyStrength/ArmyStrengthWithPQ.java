import java.util.PriorityQueue;
public class ArmyStrengthWithPQ {

	public static void main(String[] args) {
		Kattio io = new Kattio(System.in);
		int nCase = io.getInt();
		for(int i = 0; i < nCase; i++) {
			int nGodzilla = io.getInt();
			int nMechaGodzilla = io.getInt();
			PriorityQueue<Integer> Godzilla = new PriorityQueue<Integer>(nGodzilla);
			PriorityQueue<Integer> MechaGodzilla = new PriorityQueue<Integer>(nMechaGodzilla);
			for(int j = 0; j < nGodzilla; j++) {
				Godzilla.add(io.getInt());
			}
			for(int k = 0; k < nMechaGodzilla; k++) {
				MechaGodzilla.add(io.getInt());
			}
			while(Godzilla.peek() != null && MechaGodzilla.peek() != null) {
				if(Godzilla.peek() < MechaGodzilla.peek()) {					
					Godzilla.poll();
				}else {
					MechaGodzilla.poll();
				}
			}
			if(Godzilla.peek() == null) {
				System.out.println("MechaGodzilla");
			}else if(MechaGodzilla.peek() == null) {
				System.out.println("Godzilla");
			}else {
				System.out.println("uncertain");
			}
		}
		io.close();
	}

}
