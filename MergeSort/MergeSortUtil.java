/**
 * Merge Sort Util
 * @author Valeriy Vasilyev (vasvaller@ya.ru)
 */
public class MergeSortUtil {
    public static void main(String[] args) {
        int[] a = new int[]{5, 2, 4, 6, 1, 3, 2, 6}; // array for example
        MergeSortUtil.sort(a, 1, a.length);

        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i] + " ");
        }
    }
    
    public static void sort(int[] a, int p, int r) {
        if (p < r) {
            int q = (p + r) / 2;
            sort(a, p, q);
            sort(a, q + 1, r);
            merge(a, p, q, r);
        }
    }

    /**
     * @param array - inputed array
     * @param firstIndex - first human-format index, starts with '1', not like java arrays with '0',
     *                   example array: {a, b, c, d}, firstIndex is '1' (a), not '0' (a)
     * @param halfIndex - last index of first half of array (human format),
     *                  1) example array: {a, b, c, d}, halfIndex is '2' (b), not '1' (b)
     *                  2) example array: {a, b, c}, halfIndex is '2' (b), not '1' (b)
     * @param lastIndex - first human-format index (starts with '1', not '0' like in java)
     *                  example array: {a, b, c, d}, lastIndex is '4' (d), not '3' (d)
     */
    private static void merge(int[] array, int firstIndex, int halfIndex, int lastIndex) {
        int[] tempArray = new int[lastIndex - firstIndex + 1];
        int j = firstIndex - 1; // cause java array starts with zero, 'j' is iteration variable of first half of range
        int k = halfIndex; // 'k' is iteration variable of second half of range

        for (int i = 0; i < lastIndex; i++) {
            if (array[j] < array[k]) {
                tempArray[i] = array[j];
                if (j + 1 < halfIndex) j++;
                else {
                    i++;
                    while (i < lastIndex - firstIndex + 1) {
                        tempArray[i] = array[k];
                        i++;
                        k++;
                    }
                    break;
                }
            } else if (array[j] >= array[k]) {
                tempArray[i] = array[k];
                if (k + 1 < lastIndex) k++;
                else {
                    i++;
                    while (i < lastIndex - firstIndex + 1) {
                        tempArray[i] = array[j];
                        i++;
                        j++;
                    }
                    break;
                }
            }
        }

        // replace original sequence in original array
        for (int x : tempArray) {
            array[firstIndex - 1] = x;
            firstIndex++;
        }
    }
}
