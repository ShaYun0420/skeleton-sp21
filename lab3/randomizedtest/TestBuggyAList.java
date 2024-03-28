package randomizedtest;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class TestBuggyAList {
  // YOUR TESTS HERE
    @Test
    public void testThreeAddThreeRemove() {
        // initialize two lists
        AListNoResizing<Integer> list1 = new AListNoResizing<>();
        BuggyAList<Integer> list2 = new BuggyAList<>();
        for (int i = 4; i <= 6; ++i) {
            list2.addLast(i);
            list1.addLast(i);
        }
        // remove last first time
        int last1 = list1.removeLast();
        int last2 = list2.removeLast();
        assertEquals(last1, last2);  // 测试返回值
        assertEquals(list1.size(), list2.size());  // 测试删除后列表长度
        // 测试删除后的列表元素是否相同
        assertEquals(list1.get(0), list2.get(0));
        assertEquals(list1.get(1), list2.get(1));

        // remove last second time
        last1 = list1.removeLast();
        last2 = list2.removeLast();
        assertEquals(last1, last2);  // 测试返回值
        assertEquals(list1.size(), list2.size());  // 测试删除后列表长度
        // 测试删除后的列表元素是否相同
        assertEquals(list1.get(0), list2.get(0));

        // remove last third time
        last1 = list1.removeLast();
        last2 = list2.removeLast();
        assertEquals(last1, last2);  // 测试返回值
        assertEquals(list1.size(), list2.size());  // 测试删除后列表长度
        // 测试删除后的列表元素是否相同
        assertEquals(list1.get(0), list2.get(0));
    }

    @Test
    public void randomizedTest() {
        AListNoResizing<Integer> L = new AListNoResizing<>();
        BuggyAList<Integer> buggyList = new BuggyAList<>();

        int N = 5000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 3);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                L.addLast(randVal);
                buggyList.addLast(randVal);
//                System.out.println("addLast(" + randVal + ")");
            } else if (operationNumber == 1) {
                // size
                int size = L.size();
                int size2 = buggyList.size();
//                System.out.println("size: " + size);
                assertEquals(size, size2);
            } else {
                if (L.size() == 0 || buggyList.size() == 0)
                    continue;
                int lastVal = L.getLast();
                int last2 = buggyList.getLast();
//                System.out.println("getLast(" + lastVal + ")");
                int removeVal = L.removeLast();
                int remove2 = buggyList.removeLast();
//                System.out.println("removeLast(" + removeVal + ")");
                assertEquals(lastVal, last2);
                assertEquals(remove2, removeVal);
            }
        }
    }
}
