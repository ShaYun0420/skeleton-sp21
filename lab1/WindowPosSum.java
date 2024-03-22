public class WindowPosSum {
    public static void windowPosSum(int[] arr, int n) {
        int len = arr.length;
        for (int i = 0; i < len; ++i) {
            if (arr[i] <= 0)
                continue;
            for (int j = i + 1; j <= i + n; ++j) {
                if (j == len)
                    break;
                arr[i] += arr[j];
            }
        }
    }

    public static void main(String[] args) {
        int[] a = {1, 2, -3, 4, 5, 4};
        int n = 3;
        windowPosSum(a, n);
        System.out.println(java.util.Arrays.toString(a));
    }
}
