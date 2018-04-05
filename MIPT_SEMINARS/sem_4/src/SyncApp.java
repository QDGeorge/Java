/*
public class SyncApp {
    static Object obj = new Object();

    public static void main(String[] args) throws Exception{
        Runnable r = new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("Started");
                    synchronized (obj) {
                        obj.wait();
                    }
                }
                catch (InterruptedException e) {}
                System.out.println("Done");
            }
        };
        Thread t = new Thread(r);
        t.start(); // запускает параллельный процесс
        System.out.println("Before sleep");
        Thread.sleep(1000); // если не написать, этот notify может выполниться быстрее, чем другой поток дойдет до
        // wait, и тогда программа повиснет
        System.out.println("After sleep, before notify");
        synchronized (obj) {
            obj.notify(); // отдает команду wait, что пора продолжать
        }
        System.out.println("After notify");
    }
}
*/
public class SyncApp {
    static Object obj = new Object();

    public static void main(String[] args) throws Exception{
        Runnable r = new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("Started");
                    synchronized (obj) {
                        obj.wait();
                    }
                }
                catch (InterruptedException e) {}
                System.out.println("Done");
            }
        };
        for (int i = 0; i < 5; i++) {
            Thread t = new Thread(r);
            t.start(); // запускает параллельный процесс
        }
        System.out.println("Before sleep");
        Thread.sleep(1000); // если не написать, этот notify может выполниться быстрее, чем другой поток дойдет до
        // wait, и тогда программа повиснет
        System.out.println("After sleep, before notify");
        synchronized (obj) {
            obj.notifyAll(); // отдает команду всем wait, что пора продолжать
        }
        System.out.println("After notify");
    }
}