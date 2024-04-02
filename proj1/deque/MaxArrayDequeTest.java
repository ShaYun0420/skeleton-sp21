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

    private class NumberComparator implements Comparator<Integer> {
        @Override
        public int compare(Integer o1, Integer o2) {
            return o1 - o2;
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

    @Test
    public void largeArrayTest() {
        NumberComparator numberComp = new NumberComparator();
        MaxArrayDeque<Integer> mad = new MaxArrayDeque<>(numberComp);
        for (int i = 0; i < 10000; ++i) {
            mad.addFirst(i);
        }
        int maxNum = mad.max();
        Assert.assertEquals("max doesn't work, expected: 9999 but got: " + maxNum, 9999, maxNum);
    }
}
