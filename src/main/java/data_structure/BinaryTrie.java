package data_structure;

/**
 * Binary Trie<br/>
 * https://github.com/NyaanNyaan/library/blob/master/data-structure/binary-trie.hpp を参考に作成
 *
 * TLE対策のため、Nodeクラスをpool、nextの配列に変更
 */
class BinaryTrie {

	/** 扱う最大データ量 */
	private static final int NODES = 16_777_216;
	/** 扱う最大回数 */
	private static final int MAX_LOG = 30;
	/** 0 */
	private static final int NIL = 0;

	final int[] pool;
	final int[][] next;
	int pid;
	long lazy;
	int root;

	/**
	 * コンストラクター
	 */
	BinaryTrie() {
		this(0L);
	}

	/**
	 * コンストラクター
	 *
	 * @param lazy
	 */
	BinaryTrie(long lazy) {
		pool = new int[NODES];
		next = new int[2][NODES];
		this.pid = NIL;
		this.lazy = lazy;
		root = NIL;
		myNew(0);
	}

	/**
	 * 新しいノードを作成する
	 *
	 * @param exist
	 * @return 新しいノードのpid
	 */
	private int myNew(int exist) {
		pool[pid] = exist;
		next[0][pid] = next[1][pid] = NIL;
		return pid++;
	}

	/**
	 * lとrのノードを新しいノードの子供としてマージする
	 *
	 * @param l
	 * @param r
	 * @return 新しいノードのpid
	 */
	int merge(int l, int r) {
		pool[pid] = pool[l] + pool[r];
		next[0][pid] = l;
		next[1][pid] = r;
		return pid++;
	}

	/**
	 * 指定されたnode以下に値がvalueのノードを追加する
	 *
	 * @param value
	 * @param node
	 * @param bitIndex
	 * @return 追加されたノードのpid
	 */
	private int add(long value, int node, int bitIndex) {
		if (-1 == bitIndex) {
			if (node == NIL) {
				return myNew(1);
			}
			pool[node]++;
			return node;
		} else {
			if ((1L & (lazy >> bitIndex)) == (1L & (value >> bitIndex))) {
				return merge(add(value, next[0][node], bitIndex - 1), next[1][node]);
			} else {
				return merge(next[0][node], add(value, next[1][node], bitIndex - 1));
			}
		}
	}

	/**
	 * 値がvalueのノードを追加する
	 *
	 * @param value
	 */
	void add(long value) {
		root = add(value, root, MAX_LOG);
	}

	/**
	 * 指定されたnode以下の値がvalueのノードを削除する
	 *
	 * @param value
	 * @param node
	 * @param bitIndex
	 * @return 削除されたノードのpid
	 */
	private int delete(long value, int node, int bitIndex) {
		if (-1 == bitIndex) {
			pool[node]--;
			return node;
		} else {
			if ((1L & (lazy >> bitIndex)) == (1L & (value >> bitIndex))) {
				return merge(delete(value, next[0][node], bitIndex - 1), next[1][node]);
			} else {
				return merge(next[0][node], delete(value, next[1][node], bitIndex - 1));
			}
		}
	}

	/**
	 * 値がvalueのノードを削除する
	 *
	 * @param value
	 */
	void delete(long value) {
		root = delete(value, root, MAX_LOG);
	}

	/**
	 * 指定されたnode以下の値がvalueのノードを検索する
	 *
	 * @param value
	 * @param node
	 * @param bitIndex
	 * @return 指定されたnode以下の値がvalueのノードのpid
	 */
	private int find(long value, int node, int bitIndex) {
		if (-1 == bitIndex) {
			return pool[node];
		} else {
			if ((1L & (lazy >> bitIndex)) == (1L & (value >> bitIndex))) {
				return find(value, next[0][node], bitIndex - 1);
			} else {
				return find(value, next[1][node], bitIndex - 1);
			}
		}
	}

	/**
	 * 値がvalueのノードを検索する
	 *
	 * @param value
	 * @return 値がvalueのノードのpid
	 */
	int find(long value) {
		return find(value, root, MAX_LOG);
	}

	/**
	 * 指定されたnode以下の値が最大のノードを検索する
	 *
	 * @param node
	 * @param bitIndex
	 * @return 最大値のノードのpid
	 */
	private long maxElement(int node, int bitIndex) {
		if (-1 == bitIndex) {
			return 0L;
		}
		if (pool[next[(int) (~(lazy >> bitIndex) & 1L)][node]] > 0) {
			long result = maxElement(next[(int) (~(lazy >> bitIndex) & 1L)][node], bitIndex - 1);
			result |= 1L << bitIndex;
			return result;
		} else {
			return maxElement(next[(int) ((lazy >> bitIndex) & 1L)][node], bitIndex - 1);
		}
	}

	/**
	 * 値が最大のノードを検索する
	 *
	 * @return 最大値のノードのpid
	 */
	long maxElement() {
		return maxElement(root, MAX_LOG);
	}

	/**
	 * 指定されたnode以下の値が最小のノードを検索する
	 *
	 * @param node
	 * @param bitIndex
	 * @return 最小値のノードのpid
	 */
	private long minElement(int node, int bitIndex) {
		if (-1 == bitIndex) {
			return 0L;
		}
		if (pool[next[(int) (1 & (lazy >> bitIndex))][node]] > 0) {
			return minElement(next[(int) ((lazy >> bitIndex) & 1L)][node], bitIndex - 1);
		} else {
			long result = minElement(next[(int) (~(lazy >> bitIndex) & 1L)][node], bitIndex - 1);
			result |= 1L << bitIndex;
			return result;
		}
	}

	/**
	 * 値が最小のノードを検索する
	 *
	 * @return 最小値のノードのpid
	 */
	long minElement() {
		return minElement(root, MAX_LOG);
	}

	/**
	 * 指定されたnode以下の値がk番目最大のノードを検索する
	 *
	 * @param node
	 * @param k
	 * @param bitIndex
	 * @return 値がk番目最大のノードのpid
	 */
	private long getKthElement(int node, int k, int bitIndex) {
		if (-1 == bitIndex) {
			return 0L;
		}
		int ex0 = pool[next[(int) ((lazy >> bitIndex) & 1L)][node]];
		if (ex0 < k) {
			long result = getKthElement(next[(int) (~(lazy >> bitIndex) & 1L)][node], k - ex0, bitIndex - 1);
			result |= 1L << bitIndex;
			return result;
		} else {
			return getKthElement(next[(int) ((lazy >> bitIndex) & 1L)][node], k, bitIndex - 1);
		}
	}

	/**
	 * 値がk番目最大のノードを検索する
	 *
	 * @param k
	 * @return 値がk番目最大のノードのpid
	 */
	long getKthElement(int k) {
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
}
