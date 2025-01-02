package data_structure;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

/**
 * https://onlinejudge.u-aizu.ac.jp/problems/DSL_2_E を参考に作成
 */
class BinaryIndexedTreeTest {

	/** 100000 */
	private static final int N = 100_000;

	void check(int n, int q, int[] r, int[] s, int[] t, int[] x, int[] expected) {
		BinaryIndexedTree bit = new BinaryIndexedTree(N + 1);
		IntStream.range(0, q).forEach(i -> {
			if (0 == r[i]) {
				bit.imos(s[i], t[i], x[i]);
			} else {
				assertEquals(expected[i], bit.sum(s[i]));
			}
		});
	}

	@Test
	void case1() {
		check(3, 5, new int[] { 0, 0, 0, 1, 1 }, new int[] { 1, 2, 3, 2, 3 }, new int[] { 2, 3, 3, 0, 0 },
				new int[] { 1, 2, 3, 0, 0 }, new int[] { 0, 0, 0, 3, 5 });
	}

	@Test
	void case2() {
		check(4, 3, new int[] { 1, 0, 1 }, new int[] { 2, 1, 2 }, new int[] { 0, 4, 0 }, new int[] { 0, 1, 0 },
				new int[] { 0, 0, 1 });
	}
}
