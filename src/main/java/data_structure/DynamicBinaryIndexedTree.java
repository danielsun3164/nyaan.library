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

	DynamicBinaryIndexedTree(int n) {
		this.n = n + 1;
	}

	void add(int k, long value) {
		for (++k; k < n; k += k & -k) {
			data.put(k, data.getOrDefault(k, 0L) + value);
		}
	}

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

	long sum(int l, int r) {
		return sum(r) - sum(l);
	}

	long get(int k) {
		return data.getOrDefault(k + 1, 0L);
	}

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
