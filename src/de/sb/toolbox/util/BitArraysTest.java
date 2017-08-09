package de.sb.toolbox.util;

import org.junit.Assert;
import org.junit.Test;
import de.sb.toolbox.gui.BitArrays;

public class BitArraysTest {

	@Test
	public void testSingleWordSingleBitOperations() {
		long word = 0;

		for (byte index = 0; index < 64; ++index) word = BitArrays.set(word, index, true);
		Assert.assertEquals(-1L, word);
		for (byte index = 0; index < 64; ++index) word = BitArrays.set(word, index, false);
		Assert.assertEquals(0L, word);
		for (byte index = 0; index < 64; ++index) word = BitArrays.flip(word, index);
		Assert.assertEquals(-1L, word);
	}


	@Test
	public void testMultiWordSingleBitOperations() {
		final long[] words = new long[] { 0, 0, 0 };

		for (long index = 0; index < 192; ++index) BitArrays.set(words, index, true);
		Assert.assertArrayEquals(new long[] { -1, -1, -1 }, words);
		for (long index = 0; index < 192; ++index) BitArrays.set(words, index, false);
		Assert.assertArrayEquals(new long[] { 0, 0, 0 }, words);
		for (long index = 0; index < 192; ++index) BitArrays.flip(words, index);
		Assert.assertArrayEquals(new long[] { -1, -1, -1 }, words);

		try {
			BitArrays.on (words, -1);
			Assert.fail();
		} catch (final ArrayIndexOutOfBoundsException exception) {}
		try {
			BitArrays.off(words, -1);
			Assert.fail();
		} catch (final ArrayIndexOutOfBoundsException exception) {}
		try {
			BitArrays.flip(words, -1);
			Assert.fail();
		} catch (final ArrayIndexOutOfBoundsException exception) {}
		try {
			BitArrays.on (words, 192);
			Assert.fail();
		} catch (final ArrayIndexOutOfBoundsException exception) {}
		try {
			BitArrays.off(words, 192);
			Assert.fail();
		} catch (final ArrayIndexOutOfBoundsException exception) {}
		try {
			BitArrays.flip(words, 192);
			Assert.fail();
		} catch (final ArrayIndexOutOfBoundsException exception) {}
	}


	@Test
	public void testSingleWordRangedBitOperations() {
		long word = 0;

		word = BitArrays.on(word, (byte) 0, (byte) 64);
		Assert.assertEquals(-1L, word);
		word = BitArrays.off(word, (byte) 0, (byte) 64);
		Assert.assertEquals(0L, word);

		word = BitArrays.on(word, (byte) 0, (byte) 0);
		Assert.assertEquals(-1L, word);
		word = BitArrays.off(word, (byte) 0, (byte) 0);
		Assert.assertEquals(0L, word);

		word = BitArrays.on(word, (byte) 64, (byte) 64);
		Assert.assertEquals(-1L, word);
		word = BitArrays.off(word, (byte) 64, (byte) 64);
		Assert.assertEquals(0L, word);

		word = BitArrays.on(word, (byte) -64, (byte) -64);
		Assert.assertEquals(-1L, word);
		word = BitArrays.off(word, (byte) -64, (byte) -64);
		Assert.assertEquals(0L, word);

		word = BitArrays.on(word, (byte) 0, (byte) 32);
		Assert.assertEquals(0xffffffffL, word);
		word = BitArrays.on(word, (byte) 32, (byte) 32);
		Assert.assertEquals(-1L, word);
		word = BitArrays.off(word, (byte) 32, (byte) 32);
		Assert.assertEquals(0xffffffffL, word);
		word = BitArrays.off(word, (byte) 0, (byte) 32);
		Assert.assertEquals(0L, word);

		word = BitArrays.flip(word, (byte) 0, (byte) 32);
		Assert.assertEquals(0xffffffffL, word);
		word = BitArrays.flip(word, (byte) 32, (byte) 32);
		Assert.assertEquals(-1L, word);
		word = BitArrays.flip(word, (byte) 32, (byte) 32);
		Assert.assertEquals(0xffffffffL, word);
		word = BitArrays.flip(word, (byte) 0, (byte) 32);
		Assert.assertEquals(0L, word);

		word = BitArrays.flip(word, (byte) 0, (byte) 32);
		Assert.assertEquals(0x00000000ffffffffL, word);
		word = BitArrays.rotate(word, (byte) 48);
		Assert.assertEquals(0xffff00000000ffffL, word);
		word = BitArrays.rotate(word, (byte) 1);
		Assert.assertEquals(0xfffe00000001ffffL, word);
		word = BitArrays.rotate(word, (byte) -2);
		Assert.assertEquals(0xffff800000007fffL, word);
		word = BitArrays.rotate(word, (byte) -47);
		word = BitArrays.flip(word, (byte) 0, (byte) 32);
		Assert.assertEquals(0L, word);
	}


	@Test
	public void testMultiWordRangedBitOperations() {
		final long[] words = new long[] { 0, 0, 0 };

		BitArrays.on(words, 0, 192);
		Assert.assertArrayEquals(new long[] { -1, -1, -1 }, words);
		try {
			BitArrays.on(words, -1, 1);
			Assert.fail();
		} catch (final ArrayIndexOutOfBoundsException exception) {}
		try {
			BitArrays.on(words, 192, 1);
			Assert.fail();
		} catch (final ArrayIndexOutOfBoundsException exception) {}
		try {
			BitArrays.on(words, 1, 0);
			Assert.fail();
		} catch (final ArrayIndexOutOfBoundsException exception) {}
		try {
			BitArrays.on(words, 1, 193);
			Assert.fail();
		} catch (final ArrayIndexOutOfBoundsException exception) {}
		
		BitArrays.off(words, 0, 192);
		Assert.assertArrayEquals(new long[] { 0, 0, 0 }, words);
		try {
			BitArrays.off(words, -1, 1);
			Assert.fail();
		} catch (final ArrayIndexOutOfBoundsException exception) {}
		try {
			BitArrays.off(words, 192, 1);
			Assert.fail();
		} catch (final ArrayIndexOutOfBoundsException exception) {}
		try {
			BitArrays.off(words, 1, 0);
			Assert.fail();
		} catch (final ArrayIndexOutOfBoundsException exception) {}
		try {
			BitArrays.off(words, 1, 193);
			Assert.fail();
		} catch (final ArrayIndexOutOfBoundsException exception) {}
		
		BitArrays.on(words, 0, 96);
		Assert.assertArrayEquals(new long[] { -1, 0xffffffffL, 0 }, words);
		BitArrays.on(words, 96, 96);
		Assert.assertArrayEquals(new long[] { -1, -1, -1 }, words);
		BitArrays.off(words, 96, 96);
		Assert.assertArrayEquals(new long[] { -1, 0xffffffffL, 0 }, words);
		BitArrays.off(words, 0, 96);
		Assert.assertArrayEquals(new long[] { 0, 0, 0 }, words);

		BitArrays.flip(words, 0, 96);
		Assert.assertArrayEquals(new long[] { -1, 0xffffffffL, 0 }, words);
		BitArrays.flip(words, 96, 96);
		Assert.assertArrayEquals(new long[] { -1, -1, -1 }, words);
		BitArrays.flip(words, 96, 96);
		Assert.assertArrayEquals(new long[] { -1, 0xffffffffL, 0 }, words);
		BitArrays.flip(words, 0, 96);
		Assert.assertArrayEquals(new long[] { 0, 0, 0 }, words);
	}


	@Test
	public void testSingleWordLogicOperations() {
		final long leftWord = 7, rightWord = 9;
		long word;

		word = BitArrays.or(leftWord, rightWord, (byte) 0, (byte) 0);
		Assert.assertEquals(0x000000000000000fL, word);
		word = BitArrays.and(leftWord, rightWord, (byte) 0, (byte) 0);
		Assert.assertEquals(0x0000000000000001L, word);
		word = BitArrays.xor(leftWord, rightWord, (byte) 0, (byte) 0);
		Assert.assertEquals(0x000000000000000eL, word);

		word = BitArrays.or(leftWord, rightWord, (byte) 1, (byte) 0);
		Assert.assertEquals(0x000000000000000fL, word);
		word = BitArrays.and(leftWord, rightWord, (byte) 1, (byte) 0);
		Assert.assertEquals(0x0000000000000001L, word);
		word = BitArrays.xor(leftWord, rightWord, (byte) 1, (byte) 0);
		Assert.assertEquals(0x000000000000000fL, word);

		word = BitArrays.or(leftWord, rightWord, (byte) 0, (byte) 3);
		Assert.assertEquals(0x0000000000000007L, word);
		word = BitArrays.and(leftWord, rightWord, (byte) 0, (byte) 3);
		Assert.assertEquals(0x0000000000000001L, word);
		word = BitArrays.xor(leftWord, rightWord, (byte) 0, (byte) 3);
		Assert.assertEquals(0x0000000000000006L, word);

		word = BitArrays.or(leftWord, rightWord, (byte) 1, (byte) 1);
		Assert.assertEquals(0x0000000000000007L, word);
		word = BitArrays.and(leftWord, rightWord, (byte) 1, (byte) 1);
		Assert.assertEquals(0x0000000000000005L, word);
		word = BitArrays.xor(leftWord, rightWord, (byte) 1, (byte) 1);
		Assert.assertEquals(0x0000000000000007L, word);

		Assert.assertTrue (BitArrays.intersect(leftWord, rightWord, (byte) 0, (byte) 0));
		Assert.assertTrue (BitArrays.intersect(leftWord, rightWord, (byte) 0, (byte) 1));
		Assert.assertFalse(BitArrays.intersect(leftWord, rightWord, (byte) 1, (byte) 0));
		Assert.assertFalse(BitArrays.intersect(leftWord, rightWord, (byte) 1, (byte) 1));
		
		Assert.assertFalse(BitArrays.complement(leftWord, rightWord, (byte) 0, (byte) 0));
		Assert.assertTrue (BitArrays.complement(leftWord, rightWord, (byte) 1, (byte) 3));
		Assert.assertTrue (BitArrays.complement(leftWord, rightWord, (byte) 0, (byte) 4));
		Assert.assertFalse(BitArrays.complement(leftWord, rightWord, (byte) 4, (byte) 0));
	}


	@Test
	public void testMultiWordLogicOperations() {
		final long[] leftWords = { 7 }, rightWords = { 9 };
		long[] words;

		words = leftWords.clone();
		BitArrays.or(words, rightWords, (byte) 0, (byte) 64);
		Assert.assertArrayEquals(new long[] { 0x000000000000000fL }, words);
		words = leftWords.clone();
		BitArrays.and(words, rightWords, (byte) 0, (byte) 64);
		Assert.assertArrayEquals(new long[] { 0x0000000000000001L }, words);
		words = leftWords.clone();
		BitArrays.xor(words, rightWords, (byte) 0, (byte) 64);
		Assert.assertArrayEquals(new long[] { 0x000000000000000eL }, words);

		words = leftWords.clone();
		BitArrays.or(words, rightWords, (byte) 1, (byte) 63);
		Assert.assertArrayEquals(new long[] { 0x000000000000000fL }, words);
		words = leftWords.clone();
		BitArrays.and(words, rightWords, (byte) 1, (byte) 63);
		Assert.assertArrayEquals(new long[] { 0x0000000000000001L }, words);
		words = leftWords.clone();
		BitArrays.xor(words, rightWords, (byte) 1, (byte) 63);
		Assert.assertArrayEquals(new long[] { 0x000000000000000fL }, words);

		words = leftWords.clone();
		BitArrays.or(words, rightWords, (byte) 0, (byte) 3);
		Assert.assertArrayEquals(new long[] { 0x0000000000000007L }, words);
		words = leftWords.clone();
		BitArrays.and(words, rightWords, (byte) 0, (byte) 3);
		Assert.assertArrayEquals(new long[] { 0x0000000000000001L }, words);
		words = leftWords.clone();
		BitArrays.xor(words, rightWords, (byte) 0, (byte) 3);
		Assert.assertArrayEquals(new long[] { 0x0000000000000006L }, words);

		words = leftWords.clone();
		BitArrays.or(words, rightWords, (byte) 1, (byte) 1);
		Assert.assertArrayEquals(new long[] { 0x0000000000000007L }, words);
		words = leftWords.clone();
		BitArrays.and(words, rightWords, (byte) 1, (byte) 1);
		Assert.assertArrayEquals(new long[] { 0x0000000000000005L }, words);
		words = leftWords.clone();
		BitArrays.xor(words, rightWords, (byte) 1, (byte) 1);
		Assert.assertArrayEquals(new long[] { 0x0000000000000007L }, words);

		Assert.assertTrue (BitArrays.intersect(leftWords, rightWords, (byte) 0, (byte) 64));
		Assert.assertTrue (BitArrays.intersect(leftWords, rightWords, (byte) 0, (byte) 1));
		Assert.assertFalse(BitArrays.intersect(leftWords, rightWords, (byte) 1, (byte) 63));
		Assert.assertFalse(BitArrays.intersect(leftWords, rightWords, (byte) 1, (byte) 1));
		
		Assert.assertFalse(BitArrays.complement(leftWords, rightWords, (byte) 0, (byte) 64));
		Assert.assertTrue (BitArrays.complement(leftWords, rightWords, (byte) 1, (byte) 3));
		Assert.assertTrue (BitArrays.complement(leftWords, rightWords, (byte) 0, (byte) 4));
		Assert.assertFalse(BitArrays.complement(leftWords, rightWords, (byte) 4, (byte) 60));
	}


	@Test
	public void testSingleWordSearchOperations() {
		final long word = 28;
		byte index;
		
		index = BitArrays.firstIndex(word, (byte) 0, (byte) 0);
		Assert.assertEquals(2, index);
		index = BitArrays.lastIndex(word, (byte) 0, (byte) 0);
		Assert.assertEquals(4, index);

		index = BitArrays.firstIndex(word, (byte) 3, (byte) 0);
		Assert.assertEquals(3, index);
		index = BitArrays.lastIndex(word, (byte) 3, (byte) 0);
		Assert.assertEquals(4, index);

		index = BitArrays.firstIndex(word, (byte) 3, (byte) 1);
		Assert.assertEquals(3, index);
		index = BitArrays.lastIndex(word, (byte) 3, (byte) 1);
		Assert.assertEquals(3, index);
	}


	@Test
	public void testConversionOperations() {
		final long word = 0x00000000ffffffffL;

		final boolean[] array = BitArrays.toBooleans(word);
		for (int index =  0;  index < 32; ++index) Assert.assertTrue (array[index]);
		for (int index = 32;  index < 64; ++index) Assert.assertFalse(array[index]);
	}	
}