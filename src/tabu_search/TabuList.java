package tabu_search;

import java.util.ArrayDeque;

public class TabuList {

    private final ArrayDeque<Pair> tabuList;
    private int max_size = 1;

    public TabuList() {
        tabuList = new ArrayDeque<>();
    }

    public void add(int f, int s) {
        System.out.println(tabuList);
        tabuList.add(new Pair(f, s));
        if (tabuList.size() > max_size) {
            tabuList.remove();
        }
    }

    public boolean contains(int f, int s) {
        return tabuList.contains(new Pair(f, s));
    }

    private class Pair {

        private final int f, s;

        public Pair(int f, int s) {
            this.f = f;
            this.s = s;
        }

        public int getF() {
            return f;
        }

        public int getS() {
            return s;
        }

        public boolean equals(Pair obj) {
            return (obj.getF() == f) && (obj.getS() == s);
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Pair) {
                return equals((Pair) obj);
            } else {
                return false;
            }
        }

        @Override
        public String toString() {
            return "(" + f + ", " + s + ")"; //To change body of generated methods, choose Tools | Templates.
        }

    }

    public void setMax_size(int max_size) {
        this.max_size = max_size;
    }

}
