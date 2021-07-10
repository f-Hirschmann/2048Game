package spw4.game2048;

import java.util.*;

public class IntRandomStub extends Random {
    private final Iterator<Integer> iterator;

    public IntRandomStub(List<Integer> values) {
        iterator = values.iterator();
    }

    @Override
    public int nextInt() {
        return iterator.next();
    }
    @Override
    public int nextInt(int ignored) {
        return nextInt();
    }
}
