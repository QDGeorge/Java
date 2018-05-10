import java.lang.reflect.Field;
import java.util.HashMap;

public class MyClass {
    private Object obj1;
    private Object obj2;

    public MyClass() {
        if (System.currentTimeMillis()%2 == 0) {
            obj1 = new HashMap();
        } else {obj2 = new HashMap();}
    }

    public static void main(String args[]) throws Exception{
        Object obj = new MyClass();
        Class clazz = obj.getClass();
        Field fields[] = clazz.getDeclaredFields();
        for (Field f : fields) {
            if (f.get(obj) != null) {
                System.out.println(f.getName());
                f.setAccessible(true);
                // TODO: нужно вставить в хэшмэп
            }
        }
    }
}
