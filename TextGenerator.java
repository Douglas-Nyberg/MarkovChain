import java.util.ArrayList;


public class TextGenerator {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        int M = Integer.parseInt(args[1]);
        String textIn = StdIn.readAll();
        String delimiter = "[,|.| |\\n|\\s|\"|!|$|#|@|:|;|(|)|&|%|/]+";

        MarkovChain markov = new MarkovChain(textIn, delimiter, k);

        //creating an arraylist for k-gram called kgAL along with this we create a string version called kgS
        ArrayList kgAL = new ArrayList<String>();
        String kgS = "";
        textIn = textIn.replace("\n", "").replace("\r", "");
        Boolean isWords = false;

        if (textIn.contains(" ") || textIn.contains(":") || textIn.contains(",") || textIn.contains(".")) {
            isWords = true;
        }
        //if the data is words then it fills the kgAL with the words, then it assigns the string for kg to a sublist of our arraylist from 0 to k
        if (isWords) {
            for (String word : textIn.split(delimiter)) {
                kgAL.add(word);
            }
            kgS = kgAL.subList(0, k).toString();
        } // if the data is a string then we add the strings to our kgAL, then again assign the kgS the sublist of strings from 0 to k
        else {
            for (int i = 0; i < textIn.length(); i++) {
                kgAL.add(textIn.charAt(i));
            }
            kgS = kgAL.subList(0, k).toString();
        }


        System.out.println(markov);
        System.out.println();
        StdOut.print(kgS);


        // we then call next M - 1 times to produce partially random text
        int steps = M - k;
        for (int i = 0; i < M - 1; i++) {
            String next = markov.next(kgS);
            String textOut = "";

            // if its  a word then it prints a word
            if (isWords) {
                textOut = next.substring(next.lastIndexOf(" ") + 1);
                StdOut.print(textOut.replaceAll("]", " "));
            } else { //if its a string
                textOut = next;
                StdOut.print(textOut); // for easy reading string
                //StdOut.print(textOut.replaceAll("]", "").replaceAll(",", "")); // for hard reading string

            }

            kgS = next;


        }
        StdOut.println();

    }
}
