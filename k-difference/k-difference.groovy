public class Solution {

    static int kDifference(int[] a, int k) {
        Arrays.sort(a);
        // Iterate though the possible pairs
        int pairs = 0;
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a.length; j++) {
                if(i != j && a[i] + k == a[j]) {
                    pairs++;
                    break;
                }
            }
        }
        return pairs;
    }

}