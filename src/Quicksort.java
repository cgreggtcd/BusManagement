import java.util.Random;

public class Quicksort {
    /**
     * Sorts an array of doubles using Quick Sort.
     * This method is static, thus it can be called as SortComparison.sort(a)
     * @param a: An unsorted array of doubles.
     * @return array sorted in ascending order
     *
     */
    public static int[] quickSort (int[] a){
        shuffle(a);

        quickSort(a, 0, a.length-1);
        return a;
    }//end quicksort

    /**
     * Shuffles double array
     * @param a: double array to be shuffled.
     */
    private static void shuffle(int[] a){
        Random rand = new Random();
        for (int i = 0; i < a.length; i++){
            int swappingIndex = rand.nextInt(a.length);
            int temp = a[swappingIndex];
            a[swappingIndex] = a[i];
            a[i] = temp;
        }
    }

    /**
     * Recursive function for quickSort. Partitions array into two sections, and then recursively sorts those.
     * @param a : array being sorted
     * @param lo : start point of part of array being worked on
     * @param hi : end point of part of array being worked on
     */
    private static void quickSort(int[] a, int lo, int hi){
        if(hi <= lo){
            return;
        }
        int[] pivots = partition2Pivot(a, lo, hi);

        quickSort(a, lo, pivots[0]-1);
        quickSort(a, pivots[0]+1, pivots[1]-1);
        quickSort(a, pivots[1]+1, hi);

    }

    /**
     * Dual pivot partition
     * @param a: array of doubles to be partitioned
     * @param lo : start of where array will be worked on
     * @param hi : end of where array will be worked on
     * @return pivot indices
     */
    private static int[] partition2Pivot(int[] a, int lo, int hi){
        if(a[lo] > a[hi]){
            exchange(a, lo, hi);
        }
        int j = lo+1;
        int g = hi-1;
        int k = lo+1;
        int p1 = a[lo]; //pivot 1
        int p2 = a[hi]; //pivot 2

        while( k <= g ){

            // less than pivot 1
            if(a[k] < p1){
                exchange(a, k, j);
                j++;
            }

            //>= right pivot
            else if (a[k] >= p2){
                while(a[g] > p2 && k < g){
                    g--;
                }
                exchange(a,k, g);
                g--;
                if(a[k] < p1){
                    exchange(a,k,j);
                    j++;
                }
            }
            k++;
        }
        j--;
        g++;

        // Placing pivots
        exchange(a, lo, j);
        exchange(a, hi, g);
        return new int[] {j, g};
    }

    private static void exchange(int[] a, int i, int j){
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
}
