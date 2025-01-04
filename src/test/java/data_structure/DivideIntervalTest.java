package data_structure;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

import data_structure.UnionFind.IntBiConsumer;

/**
 * https://yukicoder.me/problems/no/1170 を参考に作成
 */
class DivideIntervalTest {

	void check(int n, int a, int b, Integer[] x, int[] expected) {
		DivideInterval di = new DivideInterval(n);
		UnionFind uf = new UnionFind(di.b << 1);
		int[] num = new int[di.b << 1];
		Arrays.fill(num, 0);
		IntStream.range(0, n).forEach(i -> num[di.b + i] = 1);

		IntBiConsumer f = (p, q) -> num[p] += num[q];
		IntConsumer rec = new IntConsumer() {
			@Override
			public void accept(int c) {
				if (di.b <= c) {
					return;
				}
				for (int t = 0; t < 2; t++) {
					if (!uf.same(c, (c << 1) + t)) {
						uf.unite(c, (c << 1) + t, f);
						accept((c << 1) + t);
					}
				}
			}
		};
		IntStream.range(0, n).forEach(i -> {
			int l = lowerBound(x, x[i] + a), r = lowerBound(x, x[i] + b + 1);
			di.apply(l, r, j -> {
				uf.unite(i + di.b, j, f);
				rec.accept(j);
			});
		});
		IntStream.range(0, n).forEach(i -> assertEquals(expected[i], num[uf.find(di.b + i)]));
	}

	int lowerBound(Integer[] x, int value) {
		return ~Arrays.binarySearch(x, value, (a, b) -> (a.compareTo(b) >= 0) ? 1 : -1);
	}

	@Test
	void case1() {
		check(5, 4, 6, new Integer[] { 0, 2, 5, 7, 8 }, new int[] { 2, 3, 2, 3, 3 });
	}

	@Test
	void case2() {
		check(10, 37, 45, new Integer[] { 2, 8, 11, 13, 19, 30, 38, 50, 61, 92 },
				new int[] { 1, 5, 5, 5, 2, 1, 1, 5, 2, 5 });
	}
}
