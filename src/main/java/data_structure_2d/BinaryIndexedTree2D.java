package data_structure_2d;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * 二次元Binary Indexed Tree<br/>
 * https://github.com/NyaanNyaan/library/blob/master/data-structure-2d/2d-binary-indexed-tree.hpp を参考に作成
 *
 * TLE対策のため、IntStreamのかわりにfor文を使用する
 */
class BinaryIndexedTree2D {
	final int h, w;
	final long[][] bit;

	/**
	 * コンストラクター
	 *
	 * @param h
	 * @param w
	 */
	BinaryIndexedTree2D(int h, int w) {
		this.h = h;
		this.w = w;
		bit = new long[h + 1][w + 1];
		IntStream.range(0, h + 1).forEach(i -> Arrays.fill(bit[i], 0L));
	}

	/**
	 * (x,y)にwを加算する
	 *
	 * @param x
	 * @param y
	 * @param value
	 */
	void add(int x, int y, long value) {
		if ((x < 0) || (x >= h) || (y < 0) || (y >= w)) {
			return;
		}
		for (int a = x + 1; a <= h; a += (a & -a)) {
			for (int b = y + 1; b <= w; b += (b & -b)) {
				bit[a][b] += value;
			}
		}
	}

	/**
	 * imos法で[(x1,y1), (x2,y2)) にvalueを足す
	 *
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @param value
	 */
	void imos(int x1, int y1, int x2, int y2, long value) {
		add(x1, y1, value);
		add(x1, y2, -value);
		add(x2, y1, -value);
		add(x2, y2, value);
	}

	/**
	 * [(0,0), (x,y))の和を計算する<br/>
	 * x,y<0のときは0、x>hのときはx=h、y>wのときはy=wとみなす<br/>
	 * imos法のときは(x,y)nお値を返す
	 *
	 * @param x
	 * @param y
	 * @return [(0,0), (x,y)]の和
	 */
	long sum(int x, int y) {
		if ((x < 0) || (y < 0)) {
			return 0L;
		}
		int nx = (x > h) ? h : x, ny = (y > w) ? w : y;
		long result = 0L;
		for (int a = nx; a > 0; a -= (a & -a)) {
			for (int b = ny; b > 0; b -= (b & -b)) {
				result += bit[a][b];
			}
		}
		return result;
	}

	/**
	 * [(x1,y1), (x2,y2)) の和を計算する
	 *
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @return [(x1,y1), (x2,y2)) の和
	 */
	long sum(int x1, int y1, int x2, int y2) {
		if ((x1 > x2) || (y1 > y2)) {
			return 0L;
		}
		return sum(x2, y2) - sum(x2, y1) - sum(x1, y2) + sum(x1, y1);
	}
}
