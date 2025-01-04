package data_structure;

import java.util.Arrays;

/**
 * Union Find(Disjoint Set Union)<br/>
 * https://github.com/NyaanNyaan/library/blob/master/data-structure/union-find.hpp を参考に作成
 */
class UnionFind {

	final int[] data;

	UnionFind(int n) {
		data = new int[n];
		Arrays.fill(data, -1);
	}

	int find(int k) {
		return (data[k] < 0) ? k : (data[k] = find(data[k]));
	}

	boolean unite(int x, int y) {
		return unite(x, y, null);
	}

	boolean unite(int x, int y, IntBiConsumer f) {
		if ((x = find(x)) == (y = find(y))) {
			return false;
		}
		if (data[x] > data[y]) {
			int t = x;
			x = y;
			y = t;
		}
		data[x] += data[y];
		data[y] = x;
		if (null != f) {
			f.accept(x, y);
		}
		return true;
	}

	int size(int k) {
		return -data[find(k)];
	}

	boolean same(int x, int y) {
		return find(x) == find(y);
	}

	static interface IntBiConsumer {
		void accept(int a, int b);
	}
}
