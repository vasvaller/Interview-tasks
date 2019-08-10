import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;

public class Substrings {
    static int wordsCount = 0;
    static LinkedList<String> words = new LinkedList<>();
    static HashMap<String, Integer> ribs = new HashMap<>();
    static HashMap<String, Integer> nodes = new HashMap<>();


    public static void main(String[] args) throws IOException {

        // raeding words count
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        // reading every word
        wordsCount = Integer.parseInt(reader.readLine());
        for (int i = 0; i < wordsCount; i++) {
            words.add(reader.readLine());
        }
        reader.close();

        // every word to ribs
        while (!words.isEmpty()) {
            String word = words.remove(0);

            // ribs with weights
            for (int i = 0; i < word.length() - 3; i++) {
                // creating substrings
                String subs1 = word.substring(i, i + 3);
                String subs2 = word.substring(i + 1, i + 4);

                // add substrings to nodes map
                if (nodes.containsKey(subs1)) {
                    nodes.put(subs1, nodes.get(subs1) + 1);
                } else nodes.put(subs1, 1);

                if (nodes.containsKey(subs2)) {
                    nodes.put(subs2, nodes.get(subs2) + 1);
                } else nodes.put(subs2, 1);

                // add concated substrings to ribs map
                if (ribs.containsKey(subs1 + subs2)) {
                    ribs.put(subs1 + subs2, ribs.get(subs1 + subs2) + 1);
                } else {
                    ribs.put(subs1 + subs2, 1);
                }
            }
        }

        System.out.println(nodes.size());
        System.out.println(ribs.size());
        ribs.forEach((k, v) -> System.out.println(k.substring(0, 3) + " " + k.substring(3, 6) + " " + v));
    }
}
