/*
public class first_f {
    public static void main(String[] args) {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                System.out.println("Hello");
            }
        };
        for (int i = 0; i < 5; i++) {
            Thread t = new Thread(r);
            t.start(); // запускает параллельный процесс
        }
        System.out.println("world!");
    }
}
*/

/*
public class first_f {
    public static void main(String[] args) throws Exception{ // throws Exception - чтобы все было хорошо
        Runnable r = new Runnable() {
            @Override
            public void run() {
                System.out.println("Hello");
            }
        };
        Thread t = new Thread(r);
        t.start(); // запускает параллельный процесс
        t.join(); // ждет выполнение Thread
        Thread.sleep(1000); // сон
        System.out.println("world!");
    }
}
*/
/*
public class first_f {
    public static void main(String[] args) throws Exception{
        Runnable r = new Runnable() {
            @Override
            public void run() {
                System.out.println("Hello " + Thread.currentThread().getName()); // печатаем название thread
            }
        };
        for (int i = 0; i < 5; i++) {
            Thread t = new Thread(r, "MyThread-" + i); // задали имя thread, можем смотреть, кто сейчас выполнился
            t.start(); // запускает параллельный процесс
        }
        Thread.sleep(1000); // сон
        System.out.println("world!");
    }
}
*/

/*
public class first_f {
    public static void main(String[] args) throws Exception{
        Runnable r = new Runnable() {
            @Override
            public void run() {
                while (!Thread.interrupted()) { // бесконечный цикл
                    try {
                        Thread.sleep(5000); // будет просто раз в 5 секунд что-то печатать
                        System.out.println("Hello");
                    }
                    catch (InterruptedException e) { // если мы его прервали, он вылетет (я так до конца и не понял)
                        break;
                    }
                }
                System.out.println("Exited");
            }
        };
        Thread t = new Thread(r, "MyThread-"); // задали имя thread, можем смотреть, кто сейчас выполнился
        t.start(); // запускает параллельный процесс
        Thread.sleep(12000); // сначала спим 12 секунд, в это время 2 раза успел выполниться Thread. Затем мы
        // вызываем прерывание, Thread делает break и выводит world
        t.interrupt();
        System.out.println("world!");
    }
}
*/
/* синхонизация потоков
//synchronized (obj) {
//    obj.notify(); // obj.notifyAll() // obj.wait()
//        }
        */