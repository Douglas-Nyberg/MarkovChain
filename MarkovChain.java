import java.util.ArrayList;
import java.util.List;

public class MarkovChain {

    // symbol table: key = string vertex, value = set of neighboring vertices
    private ST<String, List<String>> st;

    // number of edges
    private int E;

    /**
     * Initializes an empty graph with no vertices or edges.
     */
    public MarkovChain() {
        st = new ST<String, List<String>>();
    }

    /**
     * Initializes a graph from the specified file, using the specified delimiter.
     *
     * @param delimiter the delimiter
     */


    public MarkovChain(String textIn, String delimiter, int k) { //takes in some data(String or words), a delimiter and an int for what order markov model
        st = new ST<String, List<String>>();

        //figuring out if the data is words or just a string
        boolean isWords = false;
        if (textIn.contains(" ") || textIn.contains(":") || textIn.contains(",") || textIn.contains("."))
            isWords = true;
        textIn = textIn.replace("\n", "").replace("\r", "");

        //creating and filling an array list with the data if its a word
        ArrayList TextInAL = new ArrayList<String>();
        if (isWords) {
            for (String word : textIn.split(delimiter)) {
                TextInAL.add(word);
            }
        } else { //filling an array kust with data if its a string
            for (int i = 0; i < textIn.length(); i++) {
                TextInAL.add(textIn.charAt(i));
            }


        }
        //creating a "lineExtended" for boundary conditions, its basicaly our data + k more data on the end to account for wrap around
        ArrayList LineExtended = new ArrayList<String>();
        LineExtended = TextInAL;
        for (int i = 0; i < k; i++) {
            LineExtended.add(TextInAL.get(i));
        }

        //assigning Strings v and w based on a sublist of the arraylist we created above then we call addtransition
        for (int i = 0; i < LineExtended.size() - k; i++) {

            String v = LineExtended.subList(i, i + k).toString();

            String w = LineExtended.subList(i + 1, i + k + 1).toString();
            addTransition(v, w);
        }
    }


    private int N;

    // looks to see if v has a vertex, if not it adds a vertex then it adds w as a value attached to v the key
    public void addTransition(String v, String w) {
        if (!hasVertex(v)) addVertex(v);
        st.get(v).add(w);
        N++;
    }

    // randonly selects a value based on the amount of values associated with the key
    public String next(String v) {
        int num = (int) (Math.random() * st.get(v).size());
        return st.get(v).get(num);
    }

    // this just prints the keys attached to values attached to sizes and how many transitions occured overall for the Markov Chain
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (String v : st) {
            s.append(" Key: " + v + " Value/s: ");
            for (String w : st.get(v)) {
                s.append(w + " : ");
            }
            int num = (int) (Math.random() * st.get(v).size());
            s.append("Size = " + st.get(v).size() + " ");
            s.append('\n');

        }
        s.append("N Transitions = " + N);
        s.append('\n');
        return s.toString();
    }


    /**
     * Returns the number of vertices in this graph.
     *
     * @return the number of vertices in this graph
     */
    public int V() {
        return st.size();
    }

    /**
     * Returns the number of edges in this graph.
     *
     * @return the number of edges in this graph
     */
    public int E() {
        return E;
    }

    // throw an exception if v is not a vertex
    private void validateVertex(String v) {
        if (!hasVertex(v)) throw new IllegalArgumentException(v + " is not a vertex");
    }

    /**
     * Returns the degree of vertex v in this graph.
     *
     * @param v the vertex
     * @return the degree of {@code v} in this graph
     * @throws IllegalArgumentException if {@code v} is not a vertex in this graph
     */
    public int degree(String v) {
        validateVertex(v);
        return st.get(v).size();
    }

    /**
     * Adds the edge v-w to this graph (if it is not already an edge).
     *
     * @param v one vertex in the edge
     * @param w the other vertex in the edge
     */
    public void addEdge(String v, String w) {
        if (!hasVertex(v)) addVertex(v);
        if (!hasVertex(w)) addVertex(w);
        if (!hasEdge(v, w)) E++;
        st.get(v).add(w);
        st.get(w).add(v);
    }

    /**
     * Adds vertex v to this graph (if it is not already a vertex).
     *
     * @param v the vertex
     */
    public void addVertex(String v) {
        if (!hasVertex(v)) st.put(v, new ArrayList<String>());
    }


    /**
     * Returns the vertices in this graph.
     *
     * @return the set of vertices in this graph
     */
    public Iterable<String> vertices() {
        return st.keys();
    }

    /**
     * Returns the set of vertices adjacent to v in this graph.
     *
     * @param v the vertex
     * @return the set of vertices adjacent to vertex {@code v} in this graph
     * @throws IllegalArgumentException if {@code v} is not a vertex in this graph
     */
    public Iterable<String> adjacentTo(String v) {
        validateVertex(v);
        return st.get(v);
    }

    /**
     * Returns true if v is a vertex in this graph.
     *
     * @param v the vertex
     * @return {@code true} if {@code v} is a vertex in this graph,
     * {@code false} otherwise
     */
    public boolean hasVertex(String v) {
        return st.contains(v);
    }

    /**
     * Returns true if v-w is an edge in this graph.
     *
     * @param v one vertex in the edge
     * @param w the other vertex in the edge
     * @return {@code true} if {@code v-w} is a vertex in this graph,
     * {@code false} otherwise
     * @throws IllegalArgumentException if either {@code v} or {@code w}
     *                                  is not a vertex in this graph
     */
    public boolean hasEdge(String v, String w) {
        validateVertex(v);
        validateVertex(w);
        return st.get(v).contains(w);
    }


    /**
     * Unit tests the {@code Graph} data type.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {

        MarkovChain markov = new MarkovChain();


    }

}
