import java.text.DecimalFormat;

public class DB {
	
	public static void main(String[] args) {
		Kattio io = new Kattio(System.in);
		DecimalFormat df = new DecimalFormat("#.#########");
		double answer = 0;
		double[][] pList = new double[10001][502];
		int nThrows = io.getInt();
		int nSide = io.getInt();
		int nNum = io.getInt();
		pList[nThrows][0] = 1;
		for(int i = nThrows; i != 0; i--) {
			for(int j = 0; j <= nSide; j++) {
				double seen = j/(double)nSide;
				double unseen = (nSide - j)/(double)nSide;
				pList[i-1][j] += pList[i][j] * seen;
				pList[i-1][j+1] += pList[i][j] * unseen;
			}
		}
		for(int i = nNum; i <= nSide; i++) {
			answer += pList[0][i];
		}
		io.println(df.format(answer));
		io.close();
	}
	
}
