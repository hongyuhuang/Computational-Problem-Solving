public class App {
    public static void main(String[] args) {
        Integer integer1 = new Integer("-69");
        Integer integer2 = new Integer("3");
        Integer integer3 = new Integer("-2");
        Integer integer4 = new Integer("24");


        // System.out.println(Integer.divide(integer1, integer2));
        Rational rational1 = new Rational(integer1, integer1, integer2);
        Rational rational2 = new Rational(integer3, integer4);

        System.out.println(Integer.divide(integer1, integer2));

    }
}