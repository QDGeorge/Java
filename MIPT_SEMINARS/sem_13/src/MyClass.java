//Reflection API
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

interface Processor {
    String action();
    void process();
}

public class MyClass {
    Map<String, Processor> pMap = new HashMap<>();

    void doSmth(String action) {
        Processor p = pMap.get(action);
        if (p != null) {p.process();}
    }

    void add(Processor p) {
        pMap.put(p.action(), p);
    }


    static public void main(String args[]) throws Exception{
        MyClass m = new MyClass();
        m.add(new Processor() {
            @Override
            public String action() {
                return "hello";
            }

            @Override
            public void process() {
                System.out.println("hello");
            }
        });
        m.doSmth("hello");
        //

        Object obj = new Victim("qwerty");
        Class clazz = obj.getClass();
        System.out.println(clazz.getName());

        Class parentClazz = clazz.getSuperclass();
        Class interfClasses[] = clazz.getInterfaces();
        //
        Method methods[] = clazz.getDeclaredMethods();
        Field fields[] = clazz.getDeclaredFields();
        Constructor constructors[] = clazz.getDeclaredConstructors();

        System.out.println("");
        for (Method meth : methods) {
            System.out.println(meth);
        }
        System.out.println("");
        for (Field f : fields) {
            System.out.println(f);
        }
        System.out.println("");
        for (Constructor c : constructors) {
            System.out.println(c);
        }

        Field xField = clazz.getDeclaredField("x");
        xField.setAccessible(true); // теперь можем менять поле x (учимся писать костыли)
        xField.setInt(obj, 100); // считаем, что мы знаем, что он int
        System.out.println(((Victim)obj).getX());
        // теперь попробуем вызвать x через reflection
        Method getXMethod = clazz.getDeclaredMethod("getX");
        Object res = getXMethod.invoke(obj);
        System.out.println(res);
        System.out.println("");
        Method getXMethod1 = clazz.getDeclaredMethod("setDummyX", int.class);
        Object res1 = getXMethod1.invoke(obj, -1);
        System.out.println(res1);


        Constructor constr = clazz.getDeclaredConstructor(int.class);
        constr.setAccessible(true);
        Victim v = (Victim) constr.newInstance(99);
        // v = new Victim(99)
        ClassLoader cl = clazz.getClassLoader();
        Class other = cl.loadClass("Victim");

    }
}
