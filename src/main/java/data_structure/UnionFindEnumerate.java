package data_structure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

/**
 * https://github.com/NyaanNyaan/library/blob/master/data-structure/union-find-enumerate.hpp を参考に作成
 */
class UnionFindEnumerate {

	final int[] data, next;

	/**
	 * コンストラクター
	 *
	 * @param n
	 */
	UnionFindEnumerate(int n) {
		data = new int[n];
		Arrays.fill(data, -1);
		next = IntStream.range(0, n).toArray();
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
		swap(next, x, y);
		return true;
	}

	private void swap(int[] a, int i, int j) {
		int t = a[i];
		a[i] = a[j];
		a[j] = t;
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

	int[] enumerate(int i) {
		List<Integer> list = new ArrayList<>();
		list.add(i);
		IntStream.iterate(i, j -> j != i, j -> next[j]).forEach(j -> list.add(j));
		return list.stream().mapToInt(Integer::intValue).toArray();
	}

	static interface IntBiConsumer {
		void accept(int a, int b);
	}

}
