public class ArgsSum {
    public static void main(String[] args) {
        int n = args.length, sum = 0;
        for (String arg : args) {
            sum += Integer.parseInt(arg);
        }
        System.out.println(sum);
    }
}
