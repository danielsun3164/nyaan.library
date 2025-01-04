package data_structure;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * https://github.com/NyaanNyaan/library/blob/master/verify/verify-unit-test/erasable-priority-queue.test.cpp を参考に作成
 */
class ErasablePriorityQueueTest {

	@Test
	void case1() {
		ErasablePriorityQueue<Integer> que = new ErasablePriorityQueue<>();
		que.add(1);
		que.add(3);
		que.add(5);
		assertEquals(1, que.poll());
		assertEquals(3, que.peek());
		que.remove(5);
		que.add(6);
		assertEquals(3, que.poll());
		assertEquals(6, que.peek());
	}
}
