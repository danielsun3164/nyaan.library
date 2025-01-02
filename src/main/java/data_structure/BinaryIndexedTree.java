package data_structure;

/**
 * Binary Indexed Tree(Fenwick Tree)<br/>
 * https://github.com/NyaanNyaan/library/blob/master/data-structure/binary-indexed-tree.hpp を参考に作成
 *
 * TLE対策のため、IntStreamのかわりにfor文を使用する
 */
class BinaryIndexedTree {

	int n;
	long[] data;

	/**
	 * コンストラクター
	 *
	 * @param n
	 */
	BinaryIndexedTree(int n) {
		this.n = n + 1;
		data = new long[n + 1];
	}

	/**
	 * [0,k]の合計値を計算する
	 *
	 * @param k
	 * @return [0,k]の合計値
	 */
	long sum(int k) {
		if (k < 0) {
			return 0L;
		}
		long result = 0L;
		for (++k; k > 0; k -= (k & -k)) {
			result += data[k];
		}
		return result;
	}

	/**
	 * [l,r]の合計値を計算する
	 *
	 * @param l
	 * @param r
	 * @return [l,r]の合計値
	 */
	long sum(int l, int r) {
		return sum(r) - sum(l - 1);
	}

	/**
	 * k番目の値を取得する
	 *
	 * @param k
	 * @return k番目の値
	 */
	long get(int k) {
		return data[k + 1];
	}

	/**
	 * k番目にvalueを加算する
	 *
	 * @param k
	 * @param value
	 */
	void add(int k, long value) {
		for (++k; k < n; k += (k & -k)) {
			data[k] += value;
		}
	}

	/**
	 * imos法で[l,r]にvalueを加算する
	 *
	 * @param l
	 * @param r
	 * @param value
	 */
	void imos(int l, int r, long value) {
		add(l, value);
		add(r + 1, -value);
	}

	/**
	 * sum(i) >= valueの最小のiを計算する
	 *
	 * @param value
	 * @return sum(i) >= valueの最小のi
	 */
	int lowerBound(long value) {
		if (value <= 0L) {
			return 0;
		}
		int x = 0;
		for (int k = 1 << lg(n); k > 0; k >>= 1) {
			if ((x + k <= n - 1) && (data[x + k] < value)) {
				value -= data[x + k];
				x += k;
			}
		}
		return x;
	}

	/**
	 * sum(i) > valueの最小のiを計算する
	 *
	 * @param value
	 * @return sum(i) > valueの最小のi
	 */
	int upperBound(long value) {
		if (value < 0L) {
			return 0;
		}
		int x = 0;
		for (int k = 1 << lg(n); k > 0; k >>= 1) {
			if ((x + k <= n - 1) && (data[x + k] <= value)) {
				value -= data[x + k];
				x += k;
			}
		}
		return x;
	}

	private static int lg(int n) {
		return Integer.SIZE - Integer.numberOfLeadingZeros(n);
	}
}
