import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class CSPBike extends CSP {

    static Set<Object> varBik = new HashSet<Object>(Arrays.asList(new String[] {"black", "blue", "green", "red", "white"}));
    static Set<Object> varNam = new HashSet<Object>(Arrays.asList(new String[] {"adrian", "charles", "henry", "joel", "richard"}));
    static Set<Object> varSan = new HashSet<Object>(Arrays.asList(new String[] {"bacon", "chicken", "cheese", "pepperoni", "tuna"}));
    static Set<Object> varJui = new HashSet<Object>(Arrays.asList(new String[] {"apple", "cranberry", "grapefruit", "orange", "pineapple"}));
    static Set<Object> varAge = new HashSet<Object>(Arrays.asList(new String[] {"12", "13", "14", "15", "16"}));
    static Set<Object> varSpo = new HashSet<Object>(Arrays.asList(new String[] {"baseball", "basketball", "hockey", "soccer", "swimming"}));

    public boolean isGood(Object X, Object Y, Object x, Object y) {
        //if X is not even mentioned in by the constraints, just return true
        //as nothing can be violated
        if(!C.containsKey(X))
            return true;

        //check to see if there is an arc between X and Y
        //if there isn't an arc, then no constraint, i.e. it is good
        if(!C.get(X).contains(Y))
            return true;

        //The owner of the White bike is somewhere between the 15-year-old boy and the youngest boy, in that order.
        if(X.equals("white") && Y.equals("15") && (Integer)x - (Integer)y < 1)
            return false;
        if(X.equals("white") && Y.equals("12") && (Integer)y - (Integer)x < 1)
            return false;

        //Henry is exactly to the left of the Soccer fan.
        if(X.equals("henry") && Y.equals("soccer") && (Integer)y - (Integer)x != 1)
            return false;

        //The boy who is going to drink Grapefruit juice is somewhere between who brought Tuna sandwich and who brought Pineapple juice, in that order.
        if(X.equals("grapefruit") && Y.equals("tuna") && (Integer)x - (Integer)y < 1)
            return false;
        if(X.equals("grapefruit") && Y.equals("pineapple") && (Integer)y - (Integer)x < 1)
            return false;

        //The one who likes Swimming is next to the friend who likes Baseball.
        if(X.equals("swimming") && Y.equals("baseball") && Math.abs((Integer)x-(Integer)y) != 1)
            return false;

        //The cyclist that brought Pineapple juice is somewhere between the 14-year-old and the boy that brought Orange juice, in that order.
        if(X.equals("pineapple") && Y.equals("14") && (Integer)x - (Integer)y < 1)
            return false;
        if(X.equals("pineapple") && Y.equals("orange") && (Integer)y - (Integer)x < 1)
            return false;

        //The boy riding the White bike is somewhere between the boys riding the blue and the black bicycles, in that order.
        if(X.equals("white") && Y.equals("blue") && (Integer)x - (Integer)y < 1)
            return false;
        if(X.equals("white") && Y.equals("black") && (Integer)y - (Integer)x < 1)
            return false;

        //Joel is next to the 16-year-old cyclist.
        if(X.equals("joel") && Y.equals("16") && Math.abs((Integer)x-(Integer)y) != 1)
            return false;

        //Adrian is exactly to the left of the boy who is going to eat Pepperoni sandwich.
        if(X.equals("adrian") && Y.equals("pepperoni") && (Integer)y - (Integer)x != 1)
            return false;

        //The 12-year-old is somewhere between the 14-year-old and the oldest boy, in that order.
        if(X.equals("12") && Y.equals("14") && (Integer)x - (Integer)y < 1)
            return false;
        if(X.equals("12") && Y.equals("16") && (Integer)y - (Integer)x < 1)
            return false;

        //The boy who is going to eat Bacon sandwich is somewhere to the right of the owner of the White bicycle.
        if(X.equals("bacon") && Y.equals("white") && (Integer)x - (Integer)y < 1)
            return false;

        //The cyclist riding the White bike is somewhere between Richard and the boy riding the Red bike, in that order.
        if(X.equals("white") && Y.equals("richard") && (Integer)x - (Integer)y < 1)
            return false;
        if(X.equals("white") && Y.equals("red") && (Integer)y - (Integer)x < 1)
            return false;

        //The Baseball fan is next to the boy who is going to drink Apple juice.
        if(X.equals("baseball") && Y.equals("apple") && Math.abs((Integer)x-(Integer)y) != 1)
            return false;

        //Charles is somewhere between Richard and Adrian, in that order.
        if(X.equals("charles") && Y.equals("richard") && (Integer)x - (Integer)y < 1)
            return false;
        if(X.equals("charles") && Y.equals("adrian") && (Integer)y - (Integer)x < 1)
            return false;

        //The boy who likes the sport played on ice is going to eat Pepperoni sandwich.
        if(X.equals("hockey") && Y.equals("pepperoni") && !x.equals(y))
            return false;

        //The 16-year-old brought Cheese sandwich.
        if(X.equals("16") && Y.equals("cheese") && !x.equals(y))
            return false;

        //Uniqueness constraints

        if(varBik.contains(X) && varBik.contains(Y) && !X.equals(Y) && x.equals(y))
            return false;

        if(varNam.contains(X) && varNam.contains(Y) && !X.equals(Y) && x.equals(y))
            return false;

        if(varSan.contains(X) && varSan.contains(Y) && !X.equals(Y) && x.equals(y))
            return false;

        if(varJui.contains(X) && varJui.contains(Y) && !X.equals(Y) && x.equals(y))
            return false;

        if(varAge.contains(X) && varAge.contains(Y) && !X.equals(Y) && x.equals(y))
            return false;

        if(varSpo.contains(X) && varSpo.contains(Y) && !X.equals(Y) && x.equals(y))
            return false;

        return true;
    }

    public static void main(String[] args) throws Exception {
        CSPBike csp = new CSPBike();

        Integer[] dom = {1,2,3,4,5};

        for(Object X : varBik)
            csp.addDomain(X, dom);

        for(Object X : varNam)
            csp.addDomain(X, dom);

        for(Object X : varSan)
            csp.addDomain(X, dom);

        for(Object X : varJui)
            csp.addDomain(X, dom);

        for(Object X : varAge)
            csp.addDomain(X, dom);

        for(Object X : varSpo)
            csp.addDomain(X, dom);


        //unary constraints: just remove values from domains

        //In the fifth position is the 13-year-old boy.
        for(int i=1; i<=5; i++)
            if(i != 5)
                csp.D.get("13").remove(i);

        //In the middle is the boy that likes Baseball
        for(int i=1; i<=5; i++)
            if(i != 3)
                csp.D.get("baseball").remove(i);

        //The boy who likes Hockey is at the fifth position
        for(int i=1; i<=5; i++)
            if(i != 5)
                csp.D.get("hockey").remove(i);

        //In one of the ends is the boy riding the Green bicycle.
        for(int i=1; i<=5; i++)
            if(i != 1 && i != 5)
                csp.D.get("green").remove(i);

        //The boy riding the Black bike is at the third position.
        for(int i=1; i<=5; i++)
            if(i != 3)
                csp.D.get("black").remove(i);

        //The boy that is going to drink Pineapple juice is at the fourth position.
        for(int i=1; i<=5; i++)
            if(i != 4)
                csp.D.get("pineapple").remove(i);

        //The cyclist who is going to eat Tuna sandwich is at one of the ends.
        for(int i=1; i<=5; i++)
            if(i != 1 && i != 5)
                csp.D.get("tuna").remove(i);

        //binary constraints: add constraint arcs

        //The boy who likes the sport played on ice is going to eat Pepperoni sandwich.
        csp.addBidirectionalArc("hockey", "pepperoni");

        //The 16-year-old brought Cheese sandwich.
        csp.addBidirectionalArc("16", "cheese");

        //The owner of the White bike is somewhere between the 15-year-old boy and the youngest boy, in that order.
        csp.addBidirectionalArc("white", "15");
        csp.addBidirectionalArc("white", "12");

        //Henry is exactly to the left of the Soccer fan.
        csp.addBidirectionalArc("henry", "soccer");

        //The boy who is going to drink Grapefruit juice is somewhere between who brought Tuna sandwich and who brought Pineapple juice, in that order.
        csp.addBidirectionalArc("grapefruit", "tuna");
        csp.addBidirectionalArc("grapefruit", "pineapple");

        //The one who likes Swimming is next to the friend who likes Baseball.
        csp.addBidirectionalArc("swimming", "baseball");

        //The cyclist that brought Pineapple juice is somewhere between the 14-year-old and the boy that brought Orange juice, in that order.
        csp.addBidirectionalArc("pineapple", "14");
        csp.addBidirectionalArc("pineapple", "orange");

        //The boy riding the White bike is somewhere between the boys riding the blue and the black bicycles, in that order.
        csp.addBidirectionalArc("white", "blue");
        csp.addBidirectionalArc("white", "black");

        //Adrian is exactly to the left of the boy who is going to eat Pepperoni sandwich.
        csp.addBidirectionalArc("adrian", "pepperoni");

        //The 12-year-old is somewhere between the 14-year-old and the oldest boy, in that order.
        csp.addBidirectionalArc("12", "14");
        csp.addBidirectionalArc("12", "16");

        //The boy who is going to eat Bacon sandwich is somewhere to the right of the owner of the White bicycle.
        csp.addBidirectionalArc("bacon", "white");

        //The cyclist riding the White bike is somewhere between Richard and the boy riding the Red bike, in that order.
        csp.addBidirectionalArc("white", "richard");
        csp.addBidirectionalArc("white", "red");

        //The Baseball fan is next to the boy who is going to drink Apple juice.
        csp.addBidirectionalArc("baseball", "apple");

        //Charles is somewhere between Richard and Adrian, in that order.
        csp.addBidirectionalArc("charles", "richard");
        csp.addBidirectionalArc("charles", "adrian");

        //Uniqueness constraints

        for(Object X : varAge)
            for(Object Y : varAge)
                csp.addBidirectionalArc(X,Y);

        for(Object X : varBik)
            for(Object Y : varBik)
                csp.addBidirectionalArc(X,Y);

        for(Object X : varJui)
            for(Object Y : varJui)
                csp.addBidirectionalArc(X,Y);

        for(Object X : varNam)
            for(Object Y : varNam)
                csp.addBidirectionalArc(X,Y);

        for(Object X : varSan)
            for(Object Y : varSan)
                csp.addBidirectionalArc(X,Y);

        for(Object X : varSpo)
            for(Object Y : varSpo)
                csp.addBidirectionalArc(X,Y);


        //Now let's search for solution

        Search search = new Search(csp);
        System.out.println(search.BacktrackingSearch());
    }
}