
public class Factovisors {

	public static void main(String[] args) {
		Kattio io = new Kattio(System.in);
		while(io.hasMoreTokens()) {
			int n = io.getInt();
			int m = io.getInt();
			if(check(n,m)) {
				System.out.println(m + " divides " + n + "!");
			}else {
				System.out.println(m + " does not divide " + n + "!");
			}
		}
		io.close();
	}
	
	private static boolean check(int n, int m) {
		if(n == m || m == 1) {
			return true;
		}else if(m%2==0 && n%2==0) {
			m = m / 2;
			return(check(n, m));
		}else if(m%3==0 && n%3==0) {
			m = m/3;
			return(check(n,m));
		}else if(m%5 == 0 && n%5==0) {
			m = m/5;
			return(check(n,m));
		}else if(m%7==0 && n%7==0) {
			m = m / 7;
			return(check(n,m));
		}else {
			return false;
		}
	}

}
