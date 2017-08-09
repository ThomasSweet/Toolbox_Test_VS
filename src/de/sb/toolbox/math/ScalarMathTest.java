package de.sb.toolbox.math;

import static org.junit.Assert.*;
import org.junit.Test;


/**
 * JUnit test class for the {@link ExtendedMath} facade.
 */
public class ScalarMathTest {

	@Test
	public void testAbs() {
		for (long value = -100; value <= 100; ++value) {
			final long expected = Math.abs(value);
			final long actual = LongMath.abs(value);
			assertEquals(expected, actual);
		}
		assertEquals(Math.abs(Long.MAX_VALUE), LongMath.abs(Long.MAX_VALUE));
		assertEquals(Long.MIN_VALUE, LongMath.abs(Long.MIN_VALUE));
	}


	@Test
	public void testSignum() {
		for (long value = -100; value <= 100; ++value) {
			final long expected = (long) Math.signum(value);
			final long actual = LongMath.signum(value);
			assertEquals(expected, actual);
		}
		assertEquals((long) Math.signum(Long.MAX_VALUE), LongMath.signum(Long.MAX_VALUE));
		assertEquals(0L, LongMath.signum(Long.MIN_VALUE));
	}


	@Test
	public void testLog2() {
		final double log2 =  Math.log(2);
		for (long value = 1; value <= 10000; ++value) {
			final double expected = Math.log(value) / log2;
			assertEquals((long) Math.floor(expected), LongMath.floorLog2(value));
			assertEquals((long) Math.ceil (expected), LongMath.ceilLog2(value));
		}

		for (int magnitude = 0; magnitude < 63; ++magnitude) {
			final long value = 1L << magnitude;
			assertEquals(LongMath.floorLog2(value), LongMath.ceilLog2(value));
		}

		assertEquals(-1, IntMath.floorLog2(0));
		assertEquals(-1, LongMath.floorLog2(0L));
		assertEquals(-1, IntMath.ceilLog2(0));
		assertEquals(-1, LongMath.ceilLog2(0L));
		assertEquals(0, IntMath.floorLog2(1));
		assertEquals(0, LongMath.floorLog2(1L));
		assertEquals(0, IntMath.ceilLog2(1));
		assertEquals(0, LongMath.ceilLog2(1L));
		assertEquals(+1, IntMath.floorLog2(2));
		assertEquals(+1, LongMath.floorLog2(2L));
		assertEquals(+1, IntMath.ceilLog2(2));
		assertEquals(+1, LongMath.ceilLog2(2L));

		try { IntMath.floorLog2(-1); fail(); } catch (final ArithmeticException exception) {}
		try { IntMath.ceilLog2(-1); fail(); } catch (final ArithmeticException exception) {}
		try { LongMath.floorLog2(Long.MIN_VALUE); fail(); } catch (final ArithmeticException exception) {}
		try { LongMath.ceilLog2(Long.MIN_VALUE); fail(); } catch (final ArithmeticException exception) {}

		assertEquals(Float.NEGATIVE_INFINITY, FloatMath.log2(0f), 0);
		assertEquals(0f, FloatMath.log2(1f), 0);
		assertEquals(1f, FloatMath.log2(2f), 0);
		assertEquals(2f, FloatMath.log2(4f), 0);
		assertEquals(3f, FloatMath.log2(8f), 0);
		assertEquals(Double.NEGATIVE_INFINITY, DoubleMath.log2(0d), 0);
		assertEquals(0d, DoubleMath.log2(1d), 0);
		assertEquals(1d, DoubleMath.log2(2d), 0);
		assertEquals(2d, DoubleMath.log2(4d), 0);
		assertEquals(3d, DoubleMath.log2(8d), 1E-15);
	}


	@Test
	public void testExp2() {
		for (long exponent = 0; exponent < 63; ++exponent) {
			for (long range = -10; range <= 10; ++range) {
				final long expected = Math.round(Math.pow(2.0, exponent));
				final long actual = LongMath.exp2(exponent + 64 * range);
				assertEquals(expected, actual);
			}
		}
		assertEquals(Integer.MIN_VALUE, IntMath.exp2(63));
	}


	@Test
	public void testMulExp2() {
		for (long value = -10; value <= 10; ++value) {
			for (long exponent = 0; exponent < 60; ++exponent) {
				for (long range = -10; range <= 10; ++range) {
					final long expected = (long) Math.floor(value * Math.pow(2.0, exponent));
					final long actual = LongMath.mulExp2(value, exponent + 64 * range);
					assertEquals(expected, actual);
				}
			}
		}
	}


	@Test
	public void testDivExp2() {
		for (long value = -10; value <= 10; ++value) {
			for (long exponent = 0; exponent < 64; ++exponent) {
				for (long range = -10; range <= 10; ++range) {
					final long expected = Math.floorDiv(value, (long) Math.pow(2.0, exponent));
					final long actual = LongMath.divExp2(value, exponent + 64 * range);
					assertEquals(expected, actual);
				}
			}
		}
	}


	@Test
	public void testModExp2() {
		for (long value = -10; value <= 10; ++value) {
			for (long exponent = 0; exponent < 63; ++exponent) {
				for (long range = -10; range <= 10; ++range) {
					final long expected = Math.floorMod(value, (long) Math.pow(2.0, exponent));
					final long actual = LongMath.modExp2(value, exponent + 64 * range);
					if (expected != actual) {
						System.out.format("value=%s, exponent=%s, range=%s\n", value, exponent, range);
					}
					assertEquals(expected, actual);
				}
			}
		}
	}


	@Test
	public void testMod() {
		assertEquals(Math.floorMod(+3, +2), IntMath.mod(+3, +2));
		assertEquals(Math.floorMod(-3, +2), IntMath.mod(-3, +2));
		assertNotEquals(Math.floorMod(+3, -2), IntMath.mod(+3, -2));
		assertNotEquals(Math.floorMod(-3, -2), IntMath.mod(-3, -2));

		assertEquals(Math.floorMod(+3l, +2l), LongMath.mod(+3l, +2l));
		assertEquals(Math.floorMod(-3l, +2l), LongMath.mod(-3l, +2l));
		assertNotEquals(Math.floorMod(+3l, -2l), LongMath.mod(+3l, -2l));
		assertNotEquals(Math.floorMod(-3l, -2l), LongMath.mod(-3l, -2l));
	}
}