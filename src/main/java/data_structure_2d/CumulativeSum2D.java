package data_structure_2d;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * 二次元累積和<br/>
 * https://github.com/NyaanNyaan/library/blob/master/data-structure-2d/2d-cumulative-sum.hpp を参考に作成
 *
 * TLE対策のため、IntStreamのかわりにfor文を使用する
 */
class CumulativeSum2D {

	final long[][] data;

	/**
	 * コンストラクター
	 *
	 * @param h
	 * @param w
	 */
	CumulativeSum2D(int h, int w) {
		data = new long[h + 2][w + 2];
		IntStream.range(0, h + 2).forEach(i -> Arrays.fill(data[i], 0L));
	}

	/**
	 * (x,y)にvalueを加算する
	 *
	 * @param x
	 * @param y
	 * @param value
	 */
	void add(int x, int y, long value) {
		if (((x + 1) < data.length) && ((y + 1) < data[0].length)) {
			data[x + 1][y + 1] += value;
		}
	}

	/**
	 * imos法で[(x1,y1)~(x2,y2)) の範囲にvalueを加算する
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
	 * imos法で合計計算をする
	 */
	void build() {
		for (int i = 1; i < data.length; i++) {
			for (int j = 1; j < data[i].length; j++) {
				data[i][j] += data[i][j - 1] + data[i - 1][j] - data[i - 1][j - 1];
			}
		}
	}

	/**
	 * imos (x,y) を取得
	 *
	 * @param x
	 * @param y
	 * @return (x,y)
	 */
	long get(int x, int y) {
		return data[x + 1][y + 1];
	}

	/**
	 * [(x1,y1)~(x2,y2))の範囲の合計値を計算する
	 *
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @return [(x1,y1)~(x2,y2))の範囲の合計値
	 */
	long query(int x1, int y1, int x2, int y2) {
		return data[x2][y2] - data[x1][y2] - data[x2][y1] + data[x1][y1];
	}
}
