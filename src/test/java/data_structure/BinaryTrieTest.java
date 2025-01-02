package data_structure;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

/**
 * https://judge.yosupo.jp/problem/set_xor_min を参考に作成
 */
class BinaryTrieTest {

	void check(int q, int[] t, long[] x, long[] expected) {
		BinaryTrie trie = new BinaryTrie();
		IntStream.range(0, q).forEach(i -> {
			if (0 == t[i]) {
				if (0 == trie.find(x[i])) {
					trie.add(x[i]);
				}
			} else if (1 == t[i]) {
				if (0 != trie.find(x[i])) {
					trie.delete(x[i]);
				}
			} else {
				trie.xor(x[i]);
				assertEquals(expected[i], trie.minElement());
				trie.xor(x[i]);
			}
		});
	}

	@Test
	void case1() {
		check(6, new int[] { 0, 0, 2, 1, 1, 2 }, new long[] { 6, 7, 5, 7, 10, 7 }, new long[] { 0, 0, 2, 0, 0, 1 });
	}

	@Test
	void case2() {
		check(2, new int[] { 0, 2 }, new long[] { 1073741823, 0 }, new long[] { 0, 1073741823 });
	}
}
