package data_structure;

import java.util.HashMap;
import java.util.Map;

/**
 * 動的Union Find<br/>
 * https://github.com/NyaanNyaan/library/blob/master/data-structure/dynamic-union-find.hpp を参考に作成
 */
class DynamicUnionFind {

	final Map<Integer, Integer> m = new HashMap<>();

	/**
	 * コンストラクター
	 */
	DynamicUnionFind() {}

	/**
	 * data[k]を取得する
	 *
	 * @param k
	 * @return data[k]
	 */
	int data(int k) {
		return m.getOrDefault(k, -1);
	}

	/**
	 * kのリーダーを取得する
	 *
	 * @param k
	 * @return kのリーダー
	 */
	int find(int k) {
		int n = data(k);
		if (n >= 0) {
			int p = find(n);
			m.put(k, p);
			return p;
		}
		return k;
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
		int nx = m.getOrDefault(x, -1), ny = m.getOrDefault(y, -1);
		if (nx > ny) {
			int tmp = x;
			x = y;
			y = tmp;
		}
		m.put(x, m.getOrDefault(x, -1) + m.getOrDefault(y, -1));
		m.put(y, x);
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
		return -data(find(k));
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

	/**
	 * すべてのグループをクリアする
	 */
	void clear() {
		m.clear();
	}

	static interface IntBiConsumer {
		void accept(int a, int b);
	}
}
