package data_structure;

import java.util.Arrays;

/**
 * Union Find(Disjoint Set Union)<br/>
 * https://github.com/NyaanNyaan/library/blob/master/data-structure/union-find.hpp を参考に作成
 */
class UnionFind {

	final int[] data;

	/**
	 * コンストラクター
	 *
	 * @param n
	 */
	UnionFind(int n) {
		data = new int[n];
		Arrays.fill(data, -1);
	}

	/**
	 * kのリーダーを取得する
	 *
	 * @param k
	 * @return kのリーダー
	 */
	int find(int k) {
		return (data[k] < 0) ? k : (data[k] = find(data[k]));
	}

	/**
	 * xとyをマージする
	 *
	 * @param x
	 * @param y
	 * @return すでにマージ済みの場合はfalse、それ以外の場合はtrue
	 */
	boolean unite(int x, int y) {
		return unite(x, y, null);
	}

	/**
	 * xとyをマージする
	 *
	 * @param x
	 * @param y
	 * @param f マージ後の追加処理
	 * @return すでにマージ済みの場合はfalse、それ以外の場合はtrue
	 */
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

	/**
	 * kが所属するグループの要素数を取得する
	 *
	 * @param k
	 * @return kが所属するグループの要素数
	 */
	int size(int k) {
		return -data[find(k)];
	}

	/**
	 * xとyは同じグループに所属するかを取得する
	 *
	 * @param x
	 * @param y
	 * @return xとyは同じグループに所属するか
	 */
	boolean same(int x, int y) {
		return find(x) == find(y);
	}

	static interface IntBiConsumer {
		void accept(int a, int b);
	}
}
