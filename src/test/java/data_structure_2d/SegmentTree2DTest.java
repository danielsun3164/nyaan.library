package data_structure_2d;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

/**
 * https://onlinejudge.u-aizu.ac.jp/problems/DSL_5_B を参考に作成
 */
public class SegmentTree2DTest {

	/** 1000 */
	private static final int N = 1_000;

	void check(int n, int[] x1, int[] y1, int[] x2, int[] y2, long expected) {
		SegmentTree2D<Integer> seg = new SegmentTree2D<>(N + 1, N + 1, (a, b) -> a + b, () -> 0);
		IntStream.range(0, n).forEach(i -> {
			seg.update(x1[i], y1[i], seg.get(x1[i], y1[i]) + 1);
			seg.update(x1[i], y2[i], seg.get(x1[i], y2[i]) - 1);
			seg.update(x2[i], y1[i], seg.get(x2[i], y1[i]) - 1);
			seg.update(x2[i], y2[i], seg.get(x2[i], y2[i]) + 1);
		});
		assertEquals(expected,
				IntStream.range(0, N)
						.mapToLong(i -> IntStream.range(0, N).mapToLong(j -> seg.query(i + 1, j + 1)).max().getAsLong())
						.max().getAsLong());
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
