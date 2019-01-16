import java.text.DecimalFormat;

/**
 * Evan Guo
 * CS @Uvic
 */

public class HR {

	public static void main(String[] args) {
		Kattio io = new Kattio(System.in);
		DecimalFormat df = new DecimalFormat("#.0000");
		int nCase = io.getInt();
		int beats;
		double times;
		double minABPM;
		double maxABPM;
		double BPM;
		for(int i = 0; i < nCase; i++) {
			beats = io.getInt();
			times = io.getDouble();
			BPM = 60 * beats / times;
			minABPM = 60 / (times / (beats-1));
			maxABPM = 60 / (times/ (beats+1));
			io.println(df.format(minABPM) + " " + df.format(BPM) + " " + df.format(maxABPM));
		}
		io.close();
	}

}
