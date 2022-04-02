/*
Base code taken from https://algs4.cs.princeton.edu/21elementary/InsertionX.java.html
 */

import java.util.ArrayList;

public class InsertionSort {
    /**
     * Rearranges the array in ascending order, using the natural order.
     * @param a the array to be sorted
     */
    public static <T extends Comparable<T>> ArrayList<T> sort(ArrayList<T> a) {
        int n = a.size();

        // put smallest element in position to serve as sentinel
        int exchanges = 0;
        for (int i = n-1; i > 0; i--) {
            if (less(a.get(i), a.get(i-1))) {
                exch(a, i, i-1);
                exchanges++;
            }
        }
        if (exchanges == 0) return null;


        // insertion sort with half-exchanges
        for (int i = 2; i < n; i++) {
            T v = a.get(i);
            int j = i;
            while (less(v, a.get(j-1))) {
                a.set(j, a.get(j-1));
                j--;
            }
            a.set(j,v);
        }
        return a;

    }

    // is v < w ?
    private static <T extends Comparable<T>> boolean less(T v, T w) {
        return v.compareTo(w) < 0;
    }

    // exchange a[i] and a[j]
    private static <T extends Comparable<T>> void exch(ArrayList<T> a, int i, int j) {
        T swap = a.get(i);
        a.set(i, a.get(j));
        a.set(j, swap);
    }

}
