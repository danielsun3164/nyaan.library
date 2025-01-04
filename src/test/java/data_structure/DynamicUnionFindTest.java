package data_structure;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * https://onlinejudge.u-aizu.ac.jp/problems/DSL_1_A を参考に作成
 */
public class DynamicUnionFindTest {

	/**
	 * https://onlinejudge.u-aizu.ac.jp/problems/DSL_1_A を参考に作成
	 */
	void check(int n, int q, int[] c, int[] x, int[] y, boolean[] expected) {
		DynamicUnionFind uf = new DynamicUnionFind();
		for (int i = 0; i < q; i++) {
			if (0 == c[i]) {
				uf.unite(x[i], y[i]);
			} else {
				assertEquals(expected[i], uf.same(x[i], y[i]));
			}
		}
	}

	@Test
	void case1() {
		check(5, 12, new int[] { 0, 0, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1 }, new int[] { 1, 2, 1, 3, 1, 3, 1, 2, 3, 0, 0, 3 },
				new int[] { 4, 3, 2, 4, 4, 2, 3, 4, 0, 4, 2, 0 },
				new boolean[] { false, false, false, false, true, true, false, true, false, false, true, true });
	}
}
