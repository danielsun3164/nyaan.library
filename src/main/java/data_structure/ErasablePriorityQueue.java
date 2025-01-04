package data_structure;

import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Queue;

class ErasablePriorityQueue<T extends Comparable<T>> {

	Queue<T> q1, q2;

	/**
	 * コンストラクター
	 */
	ErasablePriorityQueue() {
		q1 = new PriorityQueue<>();
		q2 = new PriorityQueue<>();
	}

	/**
	 * 要素tを追加する
	 *
	 * @param t
	 * @return 追加が成功したかどうか
	 */
	boolean add(T t) {
		return q1.add(t);
	}

	/**
	 * 要素数を取得する
	 *
	 * @return 要素数
	 */
	int size() {
		return q1.size() - q2.size();
	}

	/**
	 * キューの先頭を取得しますが、削除しません。
	 *
	 * @return キューの先頭要素。キューが空の場合はnullを返します。
	 */
	T peek() {
		normalize();
		return q1.peek();
	}

	/**
	 * キューの先頭を取得および削除します。
	 *
	 * @return キューの先頭要素。キューが空の場合はnullを返します。
	 */
	T poll() {
		normalize();
		return q1.poll();
	}

	/**
	 * 指定された要素tを削除する
	 *
	 * @param t
	 * @return 削除成功したかどうか
	 */
	boolean remove(T t) {
		return q2.add(t);
	}

	private void normalize() {
		while (!q1.isEmpty() && !q2.isEmpty() && Objects.equals(q1.peek(), q2.peek())) {
			q1.poll();
			q2.poll();
		}
	}
}
