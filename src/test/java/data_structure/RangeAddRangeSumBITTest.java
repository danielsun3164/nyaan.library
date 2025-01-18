package data_structure;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

/**
 * https://onlinejudge.u-aizu.ac.jp/problems/DSL_2_G を参考に作成
 */
class RangeAddRangeSumBITTest {

	void check(int n, int q, int[] c, int[] s, int[] t, long[] x, long[] expected) {
		RangeAddRangeSumBIT bit = new RangeAddRangeSumBIT(n);
		IntStream.range(0, q).forEach(i -> {
			if (0 == c[i]) {
				bit.add(s[i] - 1, t[i], x[i]);
			} else {
				assertEquals(expected[i], bit.sum(s[i] - 1, t[i]));
			}
		});
	}

	@Test
	void case1() {
		check(3, 5, new int[] { 0, 0, 0, 1, 1 }, new int[] { 1, 2, 3, 1, 2 }, new int[] { 2, 3, 3, 2, 3 },
				new long[] { 1, 2, 3, 0, 0 }, new long[] { 0, 0, 0, 4, 8 });
	}

	@Test
	void case2() {
		check(4, 3, new int[] { 1, 0, 1 }, new int[] { 1, 1, 1 }, new int[] { 4, 4, 4 }, new long[] { 0, 1, 0 },
				new long[] { 0, 0, 4 });
	}
}
