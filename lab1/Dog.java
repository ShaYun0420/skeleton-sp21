public class Dog {
    public int weight;

    public Dog(int w) {
        weight = w;
    }
    public void make_noise() {
        if (weight < 10) {
            System.out.println("Yip!");
        } else if (weight < 30) {
            System.out.println("Bark!");
        } else {
            System.out.println("Woo~~");
        }
    }
}
