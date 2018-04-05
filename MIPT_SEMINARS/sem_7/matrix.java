import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
// Спросить,почему программа не завершает работу без прерывания с клавиатуры.
public class matrix {
    public int[][] random_push(int x, int y) throws Exception {
        int[][] m = new int[x][y];
        Random r = new Random();
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                m[i][j] = r.nextInt(9);
                System.out.print(m[i][j] + " ");
            }
            System.out.println();
        }
        return m;
    }

    public int[][] classic(int[][] m1, int[][] m2) throws Exception{
        int x = m1.length;
        int y = m1[0].length;
        int z = m2[0].length;
        int[][] res = new int[x][z];
        for (int i = 0; i < x; ++i) {
            for (int j = 0; j < z; ++j) {
                for (int k = 0; k < y; ++k) {
                    res[i][j] += m1[i][k] * m2[k][j];
                }
            }
        }
        return res;
    }

    public int[][] par(int[][] m1, int[][] m2) throws Exception {
        int x = m1.length;
        int y = m1[0].length;
        int z = m2[0].length;
        int[][] res = new int[x][z];
        Future ff[] = new Future[x];
        ExecutorService service = Executors.newCachedThreadPool();
        for (int i = 0; i < x; ++i) {
            final int fi = i;
            Future f = service.submit(() -> {
                for (int j = 0; j < z; ++j) {
                    for (int k = 0; k < y; ++k) {
                        res[fi][j] += m1[fi][k] * m2[k][j];
                    }
                }
            });
            ff[i] = f;
        }
        for (int i = 0; i < x; ++i) {
            ff[i].get();
        }
        service.shutdown();
        return res;
    }

    public void printer(int[][] m) {
        int x = m.length;
        int y = m[0].length;
        for (int i = 0; i < x; ++i) {
            for (int j = 0; j < y; ++j) {
                System.out.print(m[i][j] + " ");
            }
            System.out.println();

        }
    }

    public static void main(String[] arg) throws Exception {
        Scanner in = new Scanner(System.in);
        int x = in.nextInt();
        int y = in.nextInt();
        int z = in.nextInt();
        matrix m = new matrix();
        int[][] m1 = m.random_push(x, y);
        int[][] m2 = m.random_push(y, z);
        int[][] res1 = m.classic(m1, m2);
        System.out.println("Классическое умножение");
        m.printer(res1);
        int[][] res2 = m.par(m1, m2);
        System.out.println("Параллельное умножение");
        m.printer(res2);
    }
}
