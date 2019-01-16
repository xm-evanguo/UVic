import java.util.*;
public class HP {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String password = sc.next();
		String fake = sc.next();
		boolean[] check = new boolean[fake.length()];
		sc.close();
		int count = 0;
		int tmp = 0;
		for(int i = 0; i < password.length(); i++) {
			tmp = fake.indexOf(password.charAt(i));
			
			if(tmp == -1) {
				System.out.println("FAIL");
				return;
			}
			if(check[tmp] == true) {
				while(check[tmp]) {
					tmp = fake.indexOf(password.charAt(i), tmp + 1);
					if(tmp == -1) {
						System.out.println("FAIL");
						return;
					}
				}
			}
			if(tmp < count) {
				System.out.println("FAIL");
				return;
			}else {
				count = tmp;
				check[tmp] = true;
			}
		}
		System.out.println("PASS");
	}

}
