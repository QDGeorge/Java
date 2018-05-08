/*
public class Pair {
    Object V1;
    Object V2;
}*/

interface PairI<T, V> {
    T getV1();
    V getV2();
}

public class Pair<T, V> implements PairI<T, V> {
    public T getV1() {

        return null;
    }

    public V getV2() {
        return null;
    }
}
