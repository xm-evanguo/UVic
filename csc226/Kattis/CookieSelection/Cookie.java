public class Cookie {

    public static void main(String[] args) {
    	Kattio io = new Kattio(System.in);
        BinaryHeap bh = new BinaryHeap();
        BinaryHeap bh2 = new BinaryHeap();
        int send;
        int tmp;
        while(io.hasMoreTokens()) {
        	String st = io.getWord();
        	if(st.equals("#")){
        		if(bh.count != 0) {
        			if(bh.count % 2 == 0) {
        				send = bh.count/2+1;
        			}else {
        				send = (bh.count+1)/2;
        			}
        			for(int i = 1; i < send; i++) {
        				bh2.insert(bh.delete(1));
        			}
        			tmp = bh.delete(1);
        			while(bh.count != 0) {
        				bh2.insert(bh.delete(1));
        			}
        		}else {
        			if(bh2.count % 2 == 0) {
        				send = bh2.count/2+1;
        			}else {
        				send = (bh2.count+1)/2;
        			}
        			for(int i = 1; i < send; i++) {
        				bh.insert(bh2.delete(1));
        			}
        			tmp = bh2.delete(1);
        			while(bh2.count != 0) {
        				bh.insert(bh2.delete(1));
        			}
        		}
    			System.out.println(tmp);
        	}else {
            	if(bh2.count != 0) {
            		bh2.insert(Integer.parseInt(st));
            	}else {
                	bh.insert(Integer.parseInt(st));
            	}
        	}
        }
        io.close();
    }

}