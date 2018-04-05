/*
public class CircularBuffer {
    private volatile int read_p;
    private volatile int write_p;
    private volatile int capacity;
    private volatile int number_of_elements;
    private volatile String arr[];

    public CircularBuffer(int capacity_) {
        this.capacity = capacity_;
        this.arr = new String[this.capacity];
        this.read_p = 0;
        this.write_p = 0;
        this.number_of_elements = 0;
    }
    public synchronized boolean push(String s) {
        if (!this.isFull()) {
            this.arr[this.write_p] = s;
            write_p = (write_p + 1) % this.capacity;
            this.number_of_elements++;
            return true;
        }
        else {
            System.out.println("Buffer is full");
            return false;
        }
    }
    public synchronized String pop() {
        if (!this.isEmpty()) {
            String ans = this.arr[read_p];
            this.arr[read_p] = null;
            read_p = (read_p + 1) % this.capacity;
            this.number_of_elements--;
            return ans;
        }
        else {
            return "Buffer is empty";
        }
    }
    public synchronized boolean isEmpty() {
        return (this.number_of_elements == 0);
    }
    public synchronized boolean isFull() {
        return (this.number_of_elements == this.capacity);
    }
    public synchronized void print_buff() {
        for (int i = 0; i < this.capacity; i++) {
            System.out.println(this.arr[i]);
        }
    }
    public static synchronized void main(String[] args) throws Exception {
        CircularBuffer Buff = new CircularBuffer(3);
        Runnable r_1 = new Runnable() {
            @Override
            public void run() {
                int i = 0;
                while (!Thread.interrupted()) {
                    i++;
                    try {
                        Buff.push("" + i);
                        System.out.println("Push : " + i);
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        break;
                    }
                }
            }
        };
        Runnable r_2 = new Runnable() {
            @Override
            public void run() {
                while (!Thread.interrupted()) {
                    try {
                        System.out.println("Pop : " + Buff.pop());
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        break;
                    }
                }
            }
        };

        Thread t_1 = new Thread(r_1);
        Thread t_2 = new Thread(r_2);
        t_1.start();
        t_2.start();
        Thread.sleep(10000);
        t_1.interrupt();
        t_2.interrupt();

    }
}
*/
public class CircularBuffer {
    private volatile int read_p;
    private volatile int write_p;
    private volatile int capacity;
    private volatile int number_of_elements;
    private volatile String arr[];

    public CircularBuffer(int capacity_) {
        this.capacity = capacity_;
        this.arr = new String[this.capacity];
        this.read_p = 0;
        this.write_p = 0;
        this.number_of_elements = 0;
    }
    public synchronized boolean push(String s) throws InterruptedException {
        /* check isFull */
        if (this.isFull()) {
            System.out.println("Buffer is full");
            wait();
            return false;
        }
        else {
            this.arr[this.write_p] = s;
            write_p = (write_p + 1) % this.capacity;
            this.number_of_elements++;
            notify();
            return true;
        }
    }
    public synchronized String pop() throws InterruptedException {
        if (this.isEmpty()) {
            wait();
            return "Buffer is empty\n";
        }
        else {
            String ans = this.arr[read_p];
            this.arr[read_p] = null;
            read_p = (read_p + 1) % this.capacity;
            this.number_of_elements--;
            notify();
            return ans;
        }
    }
    public synchronized boolean isEmpty() {
        return (this.number_of_elements == 0);
    }
    public synchronized boolean isFull() {
        return (this.number_of_elements == this.capacity);
    }
    public synchronized void print_buff() {
        for (int i = 0; i < this.capacity; i++) {
            System.out.println(this.arr[i]);
        }
    }


    public static synchronized void main(String[] args) throws Exception {
        CircularBuffer Buff = new CircularBuffer(4);

        Runnable producer = new Runnable() {
            @Override
            public void run() {
                int i = 0;
                while (!Thread.interrupted()) {
                    i++;
                    try {
                        Buff.push("" + i);
                        System.out.println("Push : " + i + " " + Thread.currentThread().getName());
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        Thread.interrupted();
                        break;
                    }
                }
            }
        };
        Runnable consumer = new Runnable() {
            @Override
            public void run() {
                while (!Thread.interrupted()) {
                    try {
                        System.out.println("Pop : " + Buff.pop() + " " + Thread.currentThread().getName());
                        Thread.sleep(900);
                    } catch (InterruptedException e) {
                        Thread.interrupted();
                        break;
                    }
                }
            }
        };

        Thread producerThread = new Thread(producer); producerThread.start();
        Thread consumerThread0 = new Thread(consumer); consumerThread0.start();
        Thread consumerThread1 = new Thread(consumer); consumerThread1.start();

        while (true) {
            Thread.yield();
        }

    }
}

