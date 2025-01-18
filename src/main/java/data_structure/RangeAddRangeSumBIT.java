package data_structure;

/**
 * https://github.com/NyaanNyaan/library/blob/master/data-structure/range-sum-range-add-bit.hpp を参考に作成
 */
class RangeAddRangeSumBIT {
	final BinaryIndexedTree a, b;

	/**
	 * コンストラクター
	 *
	 * @param n
	 */
	RangeAddRangeSumBIT(int n) {
		a = new BinaryIndexedTree(n + 1);
		b = new BinaryIndexedTree(n + 1);
	}

	/**
	 * [l,r)にxを加算する
	 *
	 * @param l
	 * @param r
	 * @param x
	 */
	void add(int l, int r, long x) {
		a.add(l, x);
		a.add(r, -x);
		b.add(l, x * (1 - l));
		b.add(r, x * (r - 1));
	}

	/**
	 * [l,r)の合計値を計算する
	 *
	 * @param l
	 * @param r
	 * @return [l,r)の合計値
	 */
	long sum(int l, int r) {
		l--;
		r--;
		return a.sum(r) * r + b.sum(r) - a.sum(l) * l - b.sum(l);
	}
}
