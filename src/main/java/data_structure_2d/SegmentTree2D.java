package data_structure_2d;

import java.util.Arrays;
import java.util.function.BinaryOperator;
import java.util.function.Supplier;

/**
 * 二次元セグメント木<br/>
 * https://github.com/NyaanNyaan/library/blob/master/data-structure-2d/2d-segment-tree.hpp を参考に作成
 *
 * TLE対策のため、IntStreamのかわりにfor文を使用する
 */
class SegmentTree2D<T> {

	int h, w;
	T[] seg;
	BinaryOperator<T> f;
	Supplier<T> e;

	/**
	 * コンストラクター
	 *
	 * @param h
	 * @param w
	 * @param f
	 * @param e
	 */
	SegmentTree2D(int h, int w, BinaryOperator<T> f, Supplier<T> e) {
		this.f = f;
		this.e = e;
		init(h, w);
	}

	/**
	 * 初期化
	 *
	 * @param h
	 * @param w
	 */
	@SuppressWarnings("unchecked")
	private void init(int h, int w) {
		this.h = this.w = 1;
		while (this.h < h) {
			this.h <<= 1;
		}
		while (this.w < w) {
			this.w <<= 1;
		}
		seg = (T[]) new Object[(this.h * this.w) << 2];
		Arrays.fill(seg, e.get());
	}

	/**
	 * (x,y)のidを計算する
	 *
	 * @param x
	 * @param y
	 * @return (x,y)のid
	 */
	private int id(int x, int y) {
		return x * 2 * w + y;
	}

	/**
	 * (x,y)にvalueを設定する
	 *
	 * @param x
	 * @param y
	 * @param value
	 */
	void set(int x, int y, T value) {
		seg[id(x + h, y + w)] = value;
	}

	/**
	 * 値を再計算する
	 */
	void build() {
		// j in [w, 2w)
		for (int j = w; j < (w << 1); j++) {
			for (int i = h - 1; i > 0; i--) {
				seg[id(i, j)] = f.apply(seg[id(i << 1, j)], seg[id((i << 1) | 1, j)]);
			}
		}
		// i in [0, 2h)
		for (int i = 0; i < (h << 1); i++) {
			for (int j = w - 1; j > 0; j--) {
				seg[id(i, j)] = f.apply(seg[id(i, j << 1)], seg[id(i, (j << 1) | 1)]);
			}
		}
	}

	/**
	 * (x,y)の値を取得する
	 *
	 * @param x
	 * @param y
	 * @return (x,y)の値
	 */
	T get(int x, int y) {
		return seg[id(x + h, y + w)];
	}

	/**
	 * (x,y)の値をvalueに設定して、合計値の配列も更新する
	 *
	 * @param x
	 * @param y
	 * @param value
	 */
	void update(int x, int y, T value) {
		int nx = x + h, ny = y + w;
		seg[id(nx, ny)] = value;
		for (int i = (nx >> 1); i > 0; i >>= 1) {
			seg[id(i, ny)] = f.apply(seg[id(i << 1, ny)], seg[id((i << 1) | 1, ny)]);
		}
		for (int i = nx; i > 0; i >>= 1) {
			for (int j = (ny >> 1); j > 0; j >>= 1) {
				seg[id(i, j)] = f.apply(seg[id(i, j << 1)], seg[id(i, (j << 1) | 1)]);
			}
		}
	}

	/**
	 * [(x,y1)~(x,y2)]の合計値を取得する
	 *
	 * @param x
	 * @param y1
	 * @param y2
	 * @return [(x,y1)~(x,y2))の合計値
	 */
	private T innerQuery(int x, int y1, int y2) {
		T result = e.get();
		for (; y1 < y2; y1 >>= 1, y2 >>= 1) {
			if (1 == (y1 & 1)) {
				result = f.apply(result, seg[id(x, y1++)]);
			}
			if (1 == (y2 & 1)) {
				result = f.apply(result, seg[id(x, --y2)]);
			}
		}
		return result;
	}

	/**
	 * [(x1,y1), (x2,y2)) 半開の合計値を計算する
	 *
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @return [(x1,y1), (x2,y2))の合計値
	 */
	T query(int x1, int y1, int x2, int y2) {
		if ((x1 >= x2) || (y1 >= y2)) {
			return e.get();
		}
		T result = e.get();
		x1 += h;
		y1 += w;
		x2 += h;
		y2 += w;
		for (; x1 < x2; x1 >>= 1, x2 >>= 1) {
			if (1 == (x1 & 1)) {
				result = f.apply(result, innerQuery(x1++, y1, y2));
			}
			if (1 == (x2 & 1)) {
				result = f.apply(result, innerQuery(--x2, y1, y2));
			}
		}
		return result;
	}

	/**
	 * [(0,0), (x,y)) 半開
	 *
	 * @param x
	 * @param y
	 * @return [(0,0), (x,y))の合計値
	 */
	T query(int x, int y) {
		return query(0, 0, x, y);
	}
}
