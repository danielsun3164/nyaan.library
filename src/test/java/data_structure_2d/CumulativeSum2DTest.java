package data_structure_2d;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

/**
 * https://onlinejudge.u-aizu.ac.jp/problems/DSL_5_B を参考に作成
 */
public class CumulativeSum2DTest {

	/** 1000 */
	private static final int N = 1_000;

	void check(int n, int[] x1, int[] y1, int[] x2, int[] y2, long expected) {
		CumulativeSum2D ruiseki = new CumulativeSum2D(N, N);
		IntStream.range(0, n).forEach(i -> ruiseki.imos(x1[i], y1[i], x2[i], y2[i], 1L));
		ruiseki.build();
		assertEquals(expected,
				IntStream.range(0, N)
						.mapToLong(i -> IntStream.range(0, N).mapToLong(j -> ruiseki.get(i, j)).max().getAsLong()).max()
						.getAsLong());
	}

	@Test
	void case1() {
		check(2, new int[] { 0, 2 }, new int[] { 0, 1 }, new int[] { 3, 4 }, new int[] { 2, 3 }, 2L);
	}

	@Test
	void case2() {
		check(2, new int[] { 0, 2 }, new int[] { 0, 0 }, new int[] { 2, 4 }, new int[] { 2, 2 }, 1L);
	}

	@Test
	void case3() {
		check(3, new int[] { 0, 0, 0 }, new int[] { 0, 0, 0 }, new int[] { 2, 2, 2 }, new int[] { 2, 2, 2 }, 3L);
	}
}
