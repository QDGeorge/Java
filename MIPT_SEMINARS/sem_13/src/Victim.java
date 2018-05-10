public class Victim {
    private int x;
    public Victim(String s) {
        System.out.println(s);
    }
    private Victim(int x) {
        System.out.println(x + "");
    }
    int getX() {
        return x;
    }
    void setDummyX(int x) {
        System.out.println(x);
    }
}
