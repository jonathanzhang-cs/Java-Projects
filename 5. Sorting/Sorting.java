import java.text.DecimalFormat;
import java.util.Arrays;

public class Sorting {
    public static void BubbleSort(long[] a) {
        int out, in;
        for(out = a.length-1; out > 0; out--) {
            boolean swapped = false;
            for(in = 0; in < out; in++) {
                if(a[in] > a[in+1]) {
                    exch(a, in, in+1);
                    swapped = true;
                }
            }
            if(!swapped) break;
        }
    }

    public static void InsertionSort(long[] a) {
        int N = a.length;
        for (int i = 0; i < N; i++) {
            for (int j = i; j > 0 && a[j] < a[j-1]; j--) {
                exch(a, j, j-1);
            }
        }
    }

    public static void SelectionSort(long[] a) {
        int minIndex;
        for (int i = 0; i < a.length; i++){
            minIndex = i;
            for (int j = i; j < a.length; j++){
                if (a[j] < a[minIndex]){
                    minIndex = j;
                }
            }
            exch(a, i, minIndex);
        }
    }

    public static void MergeSort(long[] a) {
        long[] aux = new long[a.length];
        sort(a, aux, 0, a.length-1);
    }

    private static void sort(long[] a, long[] aux, int lo, int hi) {
        if (hi <= lo) return;
        int mid = lo + (hi - lo) / 2;
        sort(a, aux, lo, mid);
        sort(a, aux, mid + 1, hi);
        merge(a, aux, lo, mid, hi);
    }

    private static void merge(long[] a, long[] aux, int lo, int mid, int hi) {
        for (int k = lo; k <= hi; k++) {
            aux[k] = a[k];
        }
        
        int i = lo, j = mid+1;
        for (int k = lo; k <= hi; k++) {
            if      (i > mid)           a[k] = aux[j++];
            else if (j > hi)            a[k] = aux[i++];
            else if (aux[j] < aux[i])   a[k] = aux[j++];
            else                        a[k] = aux[i++];
        }
    }

    public static void QuickSort(long[] a) {
        QuickSort(a, 0, a.length - 1);
    }

    private static void QuickSort(long[] a, int left, int right) {
        if(right - left <= 0)
            return;
        int pIdx = partition(a, left, right, right); // always uses the right end element as pivot
        QuickSort(a, left, pIdx-1);
        QuickSort(a, pIdx+1, right);
    }

    private static int partition(long[] a, int left, int right, int pIdx) {
        long pivot = a[pIdx];
        exch(a, pIdx, right);
        int storeIndex = left;
        for(int i=left; i<right; i++) {
            if(a[i] <= pivot)
                exch(a, i, storeIndex++);
        }
        exch(a, right, storeIndex);
        return storeIndex;
    }

    public static void QuickSortOptimized(long[] a) {
        if (a.length <= 10){
            InsertionSort(a);
        }
        else{
            QuickSortShuffle(a);
            QuickSortOptimized(a, 0, a.length - 1);
        }
    }

    // Insertion sort that sorts in place given a range of values from an array
    private static void PartialInsertionSort(long [] a, int frontInd, int backInd) {
        for (int i = frontInd; i <= backInd; i++) {
            for (int j = i; j > 0 && a[j] < a[j-1]; j--) {
                exch(a, j, j-1);
            }
        }
    }
    
    private static void QuickSortOptimized(long[] a, int left, int right) {
        if (right - left <= 0){
            return;
        }
        else if (right - left <= 9){
            PartialInsertionSort(a, left, right);
        }
        else{
            int pIdx = partition(a, left, right, right);
            QuickSortOptimized(a, left, pIdx-1);
            QuickSortOptimized(a, pIdx+1, right);
        }
    }
    
    private static void QuickSortShuffle(long[] a){
        for (int i = 0; i < a.length; i++){
            exch(a, i, (int)(Math.random() * (a.length)));
        }
    }
    
    public static void MergeSortNonRec(long[] a) {
        int lo = 0, hi = 0, mid = 0;
        long [] aux = new long [a.length];
        for (int i = 2; i <= a.length; i = i * 2){
            for (int k = 0; k < a.length; k = k + i){
                lo = k;
                hi = k + (i - 1);
                mid = lo + (hi - lo) / 2;
                merge(a, aux, lo, mid, hi);
            }
        }
    }

    // ------------------------------------------------------
    // ------------------  helper methods -------------------
    // ------------------------------------------------------
    public static boolean testSort(long[] a) {
        long[] a2 = new long[a.length];
        System.arraycopy(a, 0, a2, 0, a.length);
        Arrays.sort(a);
        for(int i = 0; i < a.length; i++)
            if(a2[i] != a[i])
                return false;
        return true;
    }

    private static long[] randArray(int n) {
        long[] rand = new long[n];
        for(int i=0; i<n; i++)
            rand[i] = (int) (Math.random() * n * 10);
        return rand;
    }

    private static long[] orderedArray(int n) {
        long[] arr = new long[n];
        for(int i=0; i<n; i++)
            arr[i] = i+1;
        return arr;
    }

    private static void startTimer() {
        timestamp = System.nanoTime();
    }

    private static double endTimer() {
        return (System.nanoTime() - timestamp)/1000000.0;
    }

    private static void exch(long[] a, int i, int j) {
        long swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }

    private static long timestamp;

    public static void main(String[] args) {
        // set this flag to true when testing sorting with an ordered array (corner case for quick sort)
        boolean useOrderedArray = false;

        final int BUBBLE = 0, SELECT = 1, INSERT = 2, QUICK = 3, QUICKOPT = 4, MERGEREC = 5, MERGENONREC = 6;
        int[] algorithms = {BUBBLE, SELECT, INSERT, QUICK, QUICKOPT, MERGEREC, MERGENONREC};

        int max = 14, runs = 5;
        double[][] stats = new double[algorithms.length][max];
        for(int i=0; i < algorithms.length; i++) {
            switch(i) {
                case BUBBLE: System.out.print("Running Bubble Sort ..."); break;
                case SELECT: System.out.print("Running Selection Sort ..."); break;
                case INSERT: System.out.print("Running Insertion Sort ..."); break;
                case QUICK: System.out.print("Running Quicksort..."); break;
                case QUICKOPT: System.out.print("Running Optimized Quicksort..."); break;
                case MERGEREC: System.out.print("Running MergeSort Recursive ..."); break;
                case MERGENONREC: System.out.print("Running MergeSort Non Recursive ..."); break;
            }
            for(int j = 0; j < max; j++) {
                double avg = 0;
                for(int k = 0; k < runs; k++) {
                    int n = (int) Math.pow(2, j+1);
                    long[] a;
                    if(useOrderedArray){ a = orderedArray(n); }
                    else { a = randArray(n); }

                    startTimer();
                    switch(i) {
                        case BUBBLE: BubbleSort(a); break;
                        case SELECT: SelectionSort(a); break;
                        case INSERT: InsertionSort(a); break;
                        case QUICK: QuickSort(a); break;
                        case QUICKOPT: QuickSortOptimized(a); break;
                        case MERGEREC: MergeSort(a); break;
                        case MERGENONREC: MergeSortNonRec(a); break;
                    }
                    avg += endTimer();

                    if (testSort(a) == false)
                        System.out.println("The sorting is INCORRECT!" + "(N=" + a.length + ", round=" + k + ").");
                }
                avg /= runs;
                stats[i][j] = avg;
            }
            System.out.println("done.");
        }

        DecimalFormat format = new DecimalFormat("0.0000");
        System.out.println();
        System.out.println("Average running time:");
        System.out.println("N\t BubbleSort\tSelectionSort\tInsertionSort\tQuickSort\tQuickSortOpt\tMergeSortRec\tMergeSortNonRec");
        for(int i=0; i<stats[0].length; i++) {
            System.out.print((int) Math.pow(2, i+1) + "\t  ");
            for(int j=0; j<stats.length; j++) {
                System.out.print(format.format(stats[j][i]) + "\t  ");
            }
            System.out.println();
        }
    }
}
