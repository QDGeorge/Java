import java.lang.reflect.Method;

public class MyClass {
    public Object obj = new HelloBye();
    public void doSmth(String action) throws Exception{
        Class clazz = obj.getClass();
        Method method = clazz.getDeclaredMethod(action);
        Object res = method.invoke(obj);
    }

    public static void main(String args[]) throws Exception{
        MyClass m = new MyClass();
        m.doSmth("bye");
    }
}
