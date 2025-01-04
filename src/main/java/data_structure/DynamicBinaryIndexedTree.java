package data_structure;

import java.util.HashMap;
import java.util.Map;

/**
 * 動的Binary Indexed Tree<br/>
 * https://github.com/NyaanNyaan/library/blob/master/data-structure/dynamic-binary-indexed-tree.hpp を参考に作成
 */
class DynamicBinaryIndexedTree {
	final int n;
	Map<Integer, Long> data = new HashMap<>();

	/**
	 * コンストラクター
	 *
	 * @param n
	 */
	DynamicBinaryIndexedTree(int n) {
		this.n = n + 1;
	}

	/**
	 * kに値valueを加算する
	 *
	 * @param k
	 * @param value
	 */
	void add(int k, long value) {
		for (++k; k < n; k += k & -k) {
			data.put(k, data.getOrDefault(k, 0L) + value);
		}
	}

	/**
	 * [0,k)の合計値を計算する
	 *
	 * @param k
	 * @return [0,k)の合計値
	 */
	long sum(int k) {
		if (k < 0) {
			return 0L;
		}
		long result = 0L;
		for (; k > 0; k -= k & -k) {
			result += data.getOrDefault(k, 0L);
		}
		return result;
	}

	/**
	 * [l,r)の合計値を計算する
	 *
	 * @param l
	 * @param r
	 * @return [l,r)の合計値
	 */
	long sum(int l, int r) {
		return sum(r) - sum(l);
	}

	/**
	 * k番目の要素の値を取得する
	 *
	 * @param k
	 * @return k番目の要素の値
	 */
	long get(int k) {
		return data.getOrDefault(k + 1, 0L);
	}

	/**
	 * data[i]>=valueとなる最小のiを取得する
	 *
	 * @param value
	 * @return data[i]>=valueとなる最小のi
	 */
	int lowerBound(long value) {
		if (value <= 0L) {
			return 0;
		}
		int x = 0;
		for (int k = 1 << lg(n); k > 0; k >>= 1) {
			if ((x + k <= n - 1) && (data.getOrDefault(x + k, 0L) < value)) {
				value -= data.getOrDefault(x + k, 0L);
				x += k;
			}
		}
		return x;
	}

	private static int lg(int n) {
		return Integer.SIZE - Integer.numberOfLeadingZeros(n);
	}
}
