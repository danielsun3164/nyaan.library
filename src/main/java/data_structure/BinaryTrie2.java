package data_structure;

import java.util.ArrayList;
import java.util.List;

/**
 * Binary Trie<br/>
 * https://github.com/NyaanNyaan/library/blob/master/data-structure/binary-trie.hpp を参考に作成
 */
class BinaryTrie2 {

	/** */
	private static final int NODES = 16_777_216;
	/** */
	private static final int MAX_LOG = 30;

	final Node[] pool;
	int pid;
	long lazy;
	final Node nil;
	Node root;

	/**
	 * コンストラクター
	 */
	BinaryTrie2() {
		this(0, 0L);
	}

	/**
	 * コンストラクター
	 *
	 * @param pid
	 * @param lazy
	 */
	BinaryTrie2(int pid, long lazy) {
		pool = new Node[NODES];
		this.pid = pid;
		this.lazy = lazy;
		nil = myNew(0, -1);
		nil.next[0] = nil.next[1] = root = nil;
	}

	/**
	 * 新しいノードを作成する
	 *
	 * @param exist
	 * @param id
	 * @return 新しいノード
	 */
	private Node myNew(int exist, int id) {
		pool[pid] = new Node();
		pool[pid].next[0] = pool[pid].next[1] = nil;
		pool[pid].exist = exist;
		if (-1 != id) {
			pool[pid].accept.add(id);
		}
		return pool[pid++];
	}

	/**
	 * lとrのノードを新しいノードの子供としてマージする
	 *
	 * @param l
	 * @param r
	 * @return 新しいノード
	 */
	Node merge(Node l, Node r) {
		if (null == pool[pid]) {
			pool[pid] = new Node();
		}
		pool[pid].next[0] = l;
		pool[pid].next[1] = r;
		pool[pid].exist = l.exist + r.exist;
		return pool[pid++];
	}

	/**
	 * 指定されたnode以下に値がvalueのノードを追加する
	 *
	 * @param value
	 * @param id
	 * @param node
	 * @param bitIndex
	 * @return 追加されたノード
	 */
	private Node add(long value, int id, Node node, int bitIndex) {
		if (-1 == bitIndex) {
			if (node == nil) {
				return myNew(1, id);
			}
			node.exist++;
			node.accept.add(id);
			return node;
		} else {
			if ((1L & (lazy >> bitIndex)) == (1L & (value >> bitIndex))) {
				return merge(add(value, id, node.next[0], bitIndex - 1), node.next[1]);
			} else {
				return merge(node.next[0], add(value, id, node.next[1], bitIndex - 1));
			}
		}
	}

	/**
	 * 値がvalueのノードを追加する
	 *
	 * @param value
	 * @param id
	 */
	void add(long value, int id) {
		root = add(value, id, root, MAX_LOG);
	}

	/**
	 * 値がvalueのノードを追加する
	 *
	 * @param value
	 */
	void add(long value) {
		add(value, -1);
	}

	/**
	 * 指定されたnode以下の値がvalueのノードを削除する
	 *
	 * @param value
	 * @param id
	 * @param node
	 * @param bitIndex
	 * @return 削除されたノードのpid
	 */
	private Node delete(long value, int id, Node node, int bitIndex) {
		if (-1 == bitIndex) {
			node.exist--;
			return node;
		} else {
			if ((1L & (lazy >> bitIndex)) == (1L & (value >> bitIndex))) {
				return merge(delete(value, id, node.next[0], bitIndex - 1), node.next[1]);
			} else {
				return merge(node.next[0], delete(value, id, node.next[1], bitIndex - 1));
			}
		}
	}

	/**
	 * 値がvalueのノードを削除する
	 *
	 * @param value
	 * @param id
	 */
	void delete(long value, int id) {
		root = delete(value, id, root, MAX_LOG);
	}

	/**
	 * 値がvalueのノードを削除する
	 *
	 * @param value
	 */
	void delete(long value) {
		delete(value, -1);
	}

	/**
	 * 指定されたnode以下の値がvalueのノードを検索する
	 *
	 * @param value
	 * @param node
	 * @param bitIndex
	 * @return 指定されたnode以下の値がvalueのノード
	 */
	private Data1 find(long value, Node node, int bitIndex) {
		if (-1 == bitIndex) {
			return new Data1(node.exist, node.accept);
		} else {
			if ((1L & (lazy >> bitIndex)) == (1L & (value >> bitIndex))) {
				return find(value, node.next[0], bitIndex - 1);
			} else {
				return find(value, node.next[1], bitIndex - 1);
			}
		}
	}

	/**
	 * 値がvalueのノードを検索する
	 *
	 * @param value
	 * @return 値がvalueのノード
	 */
	Data1 find(long value) {
		return find(value, root, MAX_LOG);
	}

	/**
	 * 指定されたnode以下の値が最大のノードを検索する
	 *
	 * @param node
	 * @param bitIndex
	 * @return 最大値のノード
	 */
	private Data2 maxElement(Node node, int bitIndex) {
		if (-1 == bitIndex) {
			return new Data2(0L, node.accept);
		}
		if (node.next[(int) (~(lazy >> bitIndex) & 1L)].exist > 0) {
			Data2 result = maxElement(node.next[(int) (~(lazy >> bitIndex) & 1L)], bitIndex - 1);
			result.value |= 1L << bitIndex;
			return result;
		} else {
			return maxElement(node.next[(int) ((lazy >> bitIndex) & 1L)], bitIndex - 1);
		}
	}

	/**
	 * 値が最大のノードを検索する
	 *
	 * @return 最大値のノード
	 */
	Data2 maxElement() {
		return maxElement(root, MAX_LOG);
	}

	/**
	 * 指定されたnode以下の値が最小のノードを検索する
	 *
	 * @param node
	 * @param bitIndex
	 * @return 最小値のノード
	 */
	private Data2 minElement(Node node, int bitIndex) {
		if (-1 == bitIndex) {
			return new Data2(0L, node.accept);
		}
		if (node.next[(int) (1 & (lazy >> bitIndex))].exist > 0) {
			return minElement(node.next[(int) ((lazy >> bitIndex) & 1L)], bitIndex - 1);
		} else {
			Data2 result = minElement(node.next[(int) (~(lazy >> bitIndex) & 1L)], bitIndex - 1);
			result.value |= 1L << bitIndex;
			return result;
		}
	}

	/**
	 * 値が最小のノードを検索する
	 *
	 * @return 最小値のノード
	 */
	Data2 minElement() {
		return minElement(root, MAX_LOG);
	}

	/**
	 * 指定されたnode以下の値がk番目最大のノードを検索する
	 *
	 * @param node
	 * @param k
	 * @param bitIndex
	 * @return 値がk番目最大のノード
	 */
	private Data2 getKthElement(Node node, int k, int bitIndex) {
		if (-1 == bitIndex) {
			return new Data2(0L, node.accept);
		}
		int ex0 = node.next[(int) ((lazy >> bitIndex) & 1L)].exist;
		if (ex0 < k) {
			Data2 result = getKthElement(node.next[(int) (~(lazy >> bitIndex) & 1L)], k - ex0, bitIndex - 1);
			result.value |= 1L << bitIndex;
			return result;
		} else {
			return getKthElement(node.next[(int) ((lazy >> bitIndex) & 1L)], k, bitIndex - 1);
		}
	}

	/**
	 * 値がk番目最大のノードを検索する
	 *
	 * @param k
	 * @return 値がk番目最大のノード
	 */
	Data2 getKthElement(int k) {
		return getKthElement(root, k, MAX_LOG);
	}

	/**
	 * 値のXOR操作を行う
	 *
	 * @param value
	 */
	void xor(long value) {
		lazy ^= value;
	}

	/**
	 * ノードを表すクラス
	 */
	static class Node {
		Node[] next = new Node[2];
		int exist;
		List<Integer> accept = new ArrayList<>();
	}

	/**
	 * exist, acceptを格納するクラス
	 */
	static class Data1 {
		int exist;
		List<Integer> accept;

		Data1(int exist, List<Integer> accept) {
			this.exist = exist;
			this.accept = accept;
		}
	}

	/**
	 * value, acceptを格納するクラス
	 */
	static class Data2 {
		long value;
		List<Integer> accept;

		Data2(long value, List<Integer> accept) {
			this.value = value;
			this.accept = accept;
		}
	}
}
