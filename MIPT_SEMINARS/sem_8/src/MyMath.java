public class MyMath {
    static int add(int a, int b) throws OverflowException {
        /* если одного знака, возможна ошибка */
        if ((a > 0 && b > 0) || (a < 0 && b < 0)) {
            int c = Integer.MAX_VALUE - Math.abs(a);
            if (Math.abs(b) > c) {
                throw new OverflowException();
            } else {
                return a + b;
            }
        } else {
            return a + b;
        }
    }

    static int div(int a, int b) throws DivByZeroException {
        if (b == 0) {
            throw new DivByZeroException();
        } else {
            return a / b;
        }
    }

    public static void main(String[] arg) throws Exception {
        int a = 2147483645;
        int b = 31;
        int c = 0;
        int res = 0;
        try {
            res = add(a, b);
            System.out.println("" + res);
        } catch (MathException e) {
            System.out.println(e.getLocalizedMessage());
            float res_1 = (float) a + (float) b;
            System.out.println("" + res_1);
        }

        try {
            res = div(a, c);
            System.out.println(res);
        } catch (MathException e) {
            System.out.println(e.getLocalizedMessage());
        }

        int[] array = new int[3];
        array[0] = 1;
        array[1] = 2;
        array[2] = 3;
        try {
            int d = array[4];
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println(e);
        }

        String s = null;
        try {
            s.compareTo(" ");
        } catch (NullPointerException e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }
}
