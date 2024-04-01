package deque;
import jh61b.junit.In;
import org.junit.Test;
import org.junit.Assert;

public class ArrayDequeTest {
    @Test
    public void addGetTest() {
        ArrayDeque<Integer> arrd1 = new ArrayDeque<>();
        for (int i = 10; i < 20; ++i) {
            arrd1.addLast(i);
        }
        for (int i = 9; i > -1; --i) {
            arrd1.addFirst(i);
        }
        for (int i = 0; i < 20; ++i) {
            Assert.assertEquals("i should equals get(i)", i, (int) arrd1.get(i));
        }
    }
}
