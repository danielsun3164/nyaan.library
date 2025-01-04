package data_structure;

import java.util.function.IntConsumer;

/**
 * セグ木状に区間を分割したときの処理<br/>
 * https://github.com/NyaanNyaan/library/blob/master/data-structure/divide-interval.hpp を参考に作成
 *
 * 2*B 個の頂点を持つグラフを考える。<br/>
 * B+i が元のグラフの頂点 i に対応する
 */
class DivideInterval {

	final int n, b;

	DivideInterval(int n) {
		this.n = n;
		int b = 1;
		while (b < n) {
			b <<= 1;
		}
		this.b = b;
	}

	/**
	 * O(N) 根から葉方向へ push する
	 *
	 * @param f f(p,c) : p -> c へ伝播
	 */
	void push(IntBiConsumer f) {
		for (int p = 1; p < b; p++) {
			f.accept(p, p << 1);
			f.accept(p, (p << 1) | 1);
		}
	}

	/**
	 * O(N) 葉から根の方向に update する
	 *
	 * @param f f(p, c1, c2) : c1 と c2 の結果を p へマージ
	 */
	void update(IntTriConsumer f) {
		for (int p = b - 1; p > 0; p--) {
			f.accept(p, p << 1, (p << 1) | 1);
		}
	}

	/**
	 * [l, r) に対応する index の列を返す
	 *
	 * 順番は左から右へ並んでいる<br/>
	 * 例: [1, 11) : [1, 2), [2, 4), [4, 8), [8, 10), [10, 11)
	 *
	 * @param l
	 * @param r
	 * @return [l, r) に対応する index の列
	 */
	int[] range(int l, int r) {
		if (!((0 <= l) && (l <= r) && (r <= n))) {
			throw new IllegalArgumentException("l=" + l + ", r=" + r + ", n=" + n);
		}
		int[] il = new int[n], ir = new int[n];
		int lIndex = 0, rIndex = 0;
		for (l += b, r += b; l < r; l >>= 1, r >>= 1) {
			if (1 == (1 & l)) {
				il[lIndex++] = l++;
			}
			if (1 == (1 & r)) {
				ir[rIndex++] = --r;
			}
		}
		int[] result = new int[lIndex + rIndex];
		System.arraycopy(il, 0, result, 0, lIndex);
		for (int i = rIndex - 1; i >= 0; i--) {
			result[lIndex++] = ir[i];
		}
		return result;
	}

	/**
	 * [l, r) に対応する index に対してクエリを投げる(区間は昇順)
	 *
	 * @param l
	 * @param r
	 * @param f f(i) : 区間 i にクエリを投げる
	 */
	void apply(int l, int r, IntConsumer f) {
		if (!((0 <= l) && (l <= r) && (r <= n))) {
			throw new IllegalArgumentException("l=" + l + ", r=" + r + ", n=" + n);
		}
		for (int i : range(l, r)) {
			f.accept(i);
		}
	}

	static interface IntBiConsumer {
		void accept(int a, int b);
	}

	static interface IntTriConsumer {
		void accept(int a, int b, int c);
	}
}
