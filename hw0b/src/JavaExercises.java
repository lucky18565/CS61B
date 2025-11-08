import java.util.ArrayList;
import java.util.List;

public class JavaExercises {

    /** Returns an array [1, 2, 3, 4, 5, 6] */
    public static int[] makeDice() {
        // TODO: Fill in this function.
        int[] aa = {1, 2, 3, 4, 5, 6};
        return aa;
    }

    /** Returns the order depending on the customer.
     *  If the customer is Ergun, return ["beyti", "pizza", "hamburger", "tea"].
     *  If the customer is Erik, return ["sushi", "pasta", "avocado", "coffee"].
     *  In any other case, return an empty String[] of size 3. */
    public static String[] takeOrder(String customer) {
        // TODO: Fill in this function.
        String[][] orders = new String[3][];
        orders[0] = new String[] {"beyti", "pizza", "hamburger", "tea"};
        orders[1] = new String[] {"sushi", "pasta", "avocado", "coffee"};
        orders[2] = new String[3]; // 空子数组,[]};
        if (customer.equals("Ergun")) {
            return orders[0];
        } else if (customer.equals("Erik")) {
            return orders[1];
        } else {
            return orders[2];
        }
    }

    /** Returns the positive difference between the maximum element and minimum element of the given array.
     *  Assumes array is nonempty. */
    public static int findMinMax(int[] array) {
        // TODO: Fill in this function.
        int min = array[0];
        int max = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] < min) {
                min = array[i];
            }
            if (array[i] > max) {
                max = array[i];
            }
        }
        int diff = max - min;
        return diff;
    }

    /**
      * Uses recursion to compute the hailstone sequence as a list of integers starting from an input number n.
      * Hailstone sequence is described as:
      *    - Pick a positive integer n as the start
      *        - If n is even, divide n by 2
      *        - If n is odd, multiply n by 3 and add 1
      *    - Continue this process until n is 1
      */
    public static List<Integer> hailstone(int n) {
        return hailstoneHelper(n, new ArrayList<>());
    }

    private static List<Integer> hailstoneHelper(int x, List<Integer> list) {
        // TODO: Fill in this function.
        if (x == 1) {
            list.add(x);
            return list;
        }
        if (x % 2 == 0) {
            list.add(x);
            return hailstoneHelper(x / 2, list);
        }  else {
            list.add(x);
            return hailstoneHelper(x * 3 + 1, list);
        }
    }

}
