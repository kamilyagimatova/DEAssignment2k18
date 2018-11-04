public class Main {

    public static void main(String[] args) {
        ImprovedEuler e1 = new ImprovedEuler(1, 0, 5, 0.1);
        e1.answer();
        System.out.println();
        e1.setH(1);
        e1.answer();
        e1.setXMax(10);
        e1.answer();
    }
}
