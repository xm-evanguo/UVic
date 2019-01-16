import java.util.HashSet;
import java.util.Scanner;
import java.util.TreeSet;

/**
 * Name: Evan Guo
 * Student Number: V00866199
 * Uvic CSC 226 Spring 2018
 */

public class Boggle {
	
	static int point;
	static String longWord;
	static char[][] boggle;
	static boolean[][] check;
	static String[] words;
	static HashSet<String> hs = new HashSet<String>();
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int nWords = sc.nextInt();
		words = new String[nWords];
		for(int i = 0; i < nWords; i++) {
			words[i] = sc.next();
		}
		int nBoggle = sc.nextInt();
		for(int i = 0; i < nBoggle; i++) {
			point = 0;
			longWord = "";
			hs.clear();
			boggle = new char[4][4];
			//build the 2-d char array
			for(int j = 0; j < 4; j++) {
				String tmp = sc.next();
				for(int k = 0; k < 4; k++) {
					boggle[j][k] = tmp.charAt(k);
				}
			}
			
			for(int j = 0; j < nWords; j++) {
				boolean toBreak = false;
				check = new boolean[4][4];
				for(int k = 0; k < 4; k++) {
					if(toBreak) break;
					for(int s = 0; s < 4; s++) {
						if(words[j].charAt(0) == boggle[k][s]) {
							if(hs.contains(words[j])) {
								toBreak = true;
								break;
							}
							if(find(k, s, j, 1)) {
								hs.add(words[j]);
								if(words[j].length() > longWord.length()) {
									longWord = words[j];
								}else if(words[j].length() == longWord.length()) {
									TreeSet<String> ts = new TreeSet<String>();
									ts.add(words[j]);
									ts.add(longWord);
									longWord = ts.pollFirst();
								}
								if(words[j].length() == 3 || words[j].length() == 4) {
									point = point + 1;
								}else if(words[j].length() == 5) {
									point = point + 2;
								}else if(words[j].length() == 6) {
									point = point + 3;
								}else if(words[j].length() == 7) {
									point = point + 5;
								}else if(words[j].length() >= 8) {
									point = point + 11;
								}
								toBreak = true;
								break;
							}
						}
					}
				}
			}
			System.out.println(point + " " + longWord + " " + hs.size());
		}
		
	}
	
	private static boolean find(int x, int y, int noWord, int nChar) {
		check[x][y] = true;
		
		if(nChar == words[noWord].length()) {
			return true;
		}
		
		//check x, y+1
		if(y+1 < 4) {
			if(check[x][y+1] == false) {
				if(boggle[x][y+1] == words[noWord].charAt(nChar)) {
					if(find(x, y+1, noWord, nChar+1)) {
						return true;
					}
				}
			}
		}
		
		//check x, y-1
		if(y-1 >= 0) {
			if(check[x][y-1] == false) {
				if(boggle[x][y-1] == words[noWord].charAt(nChar)) {
					if(find(x, y-1, noWord, nChar+1)) {
						return true;
					}
				}
			}
		}
		
		
		//check x+1
		if(x+1 < 4) {
			//check x+1, y
			if(check[x+1][y] == false) {
				if(boggle[x+1][y] == words[noWord].charAt(nChar)) {
					if(find(x+1, y, noWord, nChar+1)) {
						return true;
					}
				}
			}
			
			//check x+1, y+1
			if(y+1 < 4) {
				if(check[x+1][y+1] == false) {
					if(boggle[x+1][y+1] == words[noWord].charAt(nChar)) {
						if(find(x+1, y+1, noWord, nChar+1)) {
							return true;
						}
					}
				}
			}
			
			//check x+1, y-1
			if(y-1 >= 0) {
				if(check[x+1][y-1] == false) {
					if(boggle[x+1][y-1] == words[noWord].charAt(nChar)) {
						if(find(x+1, y-1, noWord, nChar+1)) {
							return true;
						}
					}
				}
			}
			
		}
		
		//check x-1
		if(x-1 >= 0) {
			//System.out.println("x-1 >= 0");
			//check x-1, y
			if(check[x-1][y] == false) {
				if(boggle[x-1][y] == words[noWord].charAt(nChar)) {
					if(find(x-1, y, noWord, nChar+1)) {
						return true;
					}
				}
			}
			
			//check x-1, y+1
			if(y+1 < 4) {
				if(check[x-1][y+1] == false) {
					if(boggle[x-1][y+1] == words[noWord].charAt(nChar)) {
						if(find(x-1, y+1, noWord, nChar+1)) {
							return true;
						}
					}
				}
			}
			
			//check x-1, y-1
			if(y-1 >= 0) {
				//System.out.println("x-1 >= 0 && y-1 >=0");
				if(check[x-1][y-1] == false) {
					if(boggle[x-1][y-1] == words[noWord].charAt(nChar)) {
						if(find(x-1, y-1, noWord, nChar+1)) {
							return true;
						}
					}
				}
			}
		}
		
		check[x][y] = false;
		return false;
		
	}
	
	

}
