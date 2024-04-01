package deque;
import org.junit.Test;
import org.junit.Assert;
import java.util.Comparator;

public class MaxArrayDequeTest {

    private class LengthComparator implements Comparator<String> {

        @Override
        public int compare(String o1, String o2) {
            return o1.length() - o2.length();
        }
    }

    private class lastDigitComparator implements Comparator<Integer> {

        @Override
        public int compare(Integer o1, Integer o2) {
            return o1 % 10 - o2 % 10;
        }
    }

    @Test
    public void testCompare() {
        LengthComparator com = new LengthComparator();
        lastDigitComparator com2 = new lastDigitComparator();
        MaxArrayDeque<String> mad = new MaxArrayDeque<>(com);
        MaxArrayDeque<Integer> madInt = new MaxArrayDeque<>(com2);
        mad.addFirst("CaiXvKun");
        mad.addFirst("Cai");
        mad.addFirst("Kun");
        mad.addFirst("XvKun");
        mad.addFirst("CaiXv");
        madInt.addLast(213);
        madInt.addLast(29);
        madInt.addLast(30);
        madInt.addLast(1231236);
        madInt.addLast(1231);
        String maxString = mad.max();
        int maxInt = madInt.max();
        Assert.assertEquals("CaiXvKun", maxString);
        Assert.assertEquals(29, maxInt);
    }
}
