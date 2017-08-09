package de.sb.toolbox.util;

import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import org.junit.Test;
import de.sb.toolbox.util.BucketSet;
import de.sb.toolbox.util.Indexer;


public class BucketSetTest {
	private static class LongIndexer implements Indexer {
		private final Long pivotElement;

		public LongIndexer(final long pivotElement) {
			this.pivotElement = pivotElement;
		}

		public int index(Object object) {
			if (this.pivotElement.equals(object) || !(object instanceof Long)) return -1;
			final long distance = this.pivotElement.longValue() ^ ((Long) object).longValue();
			return ((Long.SIZE - Long.numberOfLeadingZeros(distance) - 1) >> 1);
		}

		public int cardinality() {
			return Long.SIZE >> 1;
		}
	}


	public static BucketSet<Long> newCollection () {
		final Indexer indexer = new LongIndexer(127L);
		final BucketSet<Long> collection = new BucketSet<>(indexer, 4);
		assertEquals(indexer, collection.bucketIndexer());
		assertFalse(collection.contains(127L));

		assertEquals(0, collection.size());
		for (long value = 112; value <= 140; ++value) collection.add(value);
		assertEquals(11, collection.size());
		for (int index = 0; index < 64; ++index) collection.add(1L << index);
		assertEquals(68, collection.size());

		return collection;
	}


	@Test
	public void testBasics() {
		final BucketSet<Long> collection = newCollection();

		assertTrue(collection.contains(126L));
		assertFalse(collection.contains(127L));
		assertFalse(collection.contains(1433L));
		assertFalse(collection.contains(Boolean.FALSE));
		assertFalse(collection.remove(null));
		assertFalse(collection.remove(Boolean.FALSE));
		assertFalse(collection.remove(127L));
		assertEquals(68, collection.size());

		assertTrue(collection.remove(126L));
		assertEquals(67, collection.size());
		assertTrue(collection.remove(115L));
		assertEquals(66, collection.size());

		assertFalse(collection.isEmpty());
		collection.clear();
		assertTrue(collection.isEmpty());
		assertEquals(0, collection.size());
	}


	@Test
	public void testCollections() {
		final BucketSet<Long> collection = newCollection();

		assertTrue(collection.containsAll(Arrays.asList(125L, 126L)));
		assertFalse(collection.containsAll(Arrays.asList(125L, 1433L)));
		assertFalse(collection.containsAll(Arrays.asList(Boolean.FALSE)));

		assertTrue(collection.addAll(Arrays.asList(126L, 126L, 1433L)));
		assertTrue(collection.contains(126L));
		assertTrue(collection.contains(1433L));
		assertEquals(69, collection.size());

		assertTrue(collection.removeAll(Arrays.asList(114L, 1433L, 1435L)));
		assertFalse(collection.contains(114L));
		assertFalse(collection.contains(1433L));
		assertFalse(collection.contains(1435L));
		assertEquals(67, collection.size());

		assertTrue(collection.retainAll(Arrays.asList(126L, 1435L)));
		assertTrue(collection.contains(126L));
		assertEquals(1, collection.size());
	}


	@Test
	public void testIterator() {
		final BucketSet<Long> collection = newCollection();

		final Iterator<Long> iterator1 = collection.iterator();
		try { iterator1.remove(); fail(); } catch (final IllegalStateException exception) {}

		long counter = 0;
		while (iterator1.hasNext()) {
			iterator1.next();
			counter += 1;
		}
		assertEquals(68, counter);

		assertTrue(collection.contains(-9223372036854775808L));
		iterator1.remove();
		assertFalse(collection.contains(-9223372036854775808L));
		assertEquals(67, collection.size());

		try { iterator1.next(); fail(); } catch (final NoSuchElementException exception) {}

		final Iterator<Long> iterator2 = collection.iterator();
		assertEquals(124L, iterator2.next().longValue());
		assertTrue(collection.contains(124L));
		iterator2.remove();
		assertFalse(collection.contains(124L));
		assertEquals(125L, iterator2.next().longValue());
		assertEquals(66, collection.size());
	}
}