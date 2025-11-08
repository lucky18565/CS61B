import java.util.ArrayList;
import java.util.List;

public class ListExercises {

    /** Returns the total sum in a list of integers */
    public static int sum(List<Integer> L) {
        // TODO: Fill in this function.
        if (L == null) {
            return 0;
        }
        int sum = 0;
        for (int i : L) {
            sum += i;
        }
        return sum;
    }

    /** Returns a list containing the even numbers of the given list */
    public static List<Integer> evens(List<Integer> L) {
        // TODO: Fill in this function.
        List<Integer> aa = new ArrayList<>();
        for (int i : L) {
            if (i % 2 == 0) {
                aa.add(i);
            }
        }
        return aa;
    }

    /** Returns a list containing the common item of the two given lists */
    public static List<Integer> common(List<Integer> L1, List<Integer> L2) {
        // TODO: Fill in this function.
        List<Integer> bb = new ArrayList<>();
        for (int i : L1) {
            if (L2.contains(i) && !bb.contains(i)) {
                bb.add(i);
            }
        }
        return bb;
    }


    /** Returns the number of occurrences of the given character in a list of strings. */
    public static int countOccurrencesOfC(List<String> words, char c) {
        // TODO: Fill in this function.
        int count = 0;
        for (String i : words) {
            for (int j = 0; j < i.length(); j++) {
                if (i.charAt(j) == c) {
                    count += 1;
                }
            }
        }
        return count;
    }
}
