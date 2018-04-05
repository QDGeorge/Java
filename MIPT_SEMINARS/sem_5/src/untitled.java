public class untitled {

}
/* чтобы потоки видели изменили переменных друг друга, нужно писать
volatile int x;
 */
/*
class MyRunnable implements Runnable {
        volatile boolean done;
        public void run() {
            while (!done) {
                doSmth();
            }
        }
    }

    MyRunnable r = new MyRunnable();
    r.done = false;
    Thread t = new Thread(r);
    t.start();
    r.done = true;
 */