package de.sb.toolbox.math;

import static de.sb.toolbox.math.TypeMath.HALF_MAX_VALUE;
import static de.sb.toolbox.math.TypeMath.HALF_MIN_NORMAL;
import static de.sb.toolbox.math.TypeMath.HALF_MIN_VALUE;
import static de.sb.toolbox.math.TypeMath.halfAbs;
import static de.sb.toolbox.math.TypeMath.halfToShortBits;
import static de.sb.toolbox.math.TypeMath.isFiniteHalf;
import static de.sb.toolbox.math.TypeMath.isInfiniteHalf;
import static de.sb.toolbox.math.TypeMath.isNanHalf;
import static de.sb.toolbox.math.TypeMath.shortBitsToHalf;
import static de.sb.toolbox.math.TypeMath.halfSignum;
import static java.lang.Float.NEGATIVE_INFINITY;
import static java.lang.Float.NaN;
import static java.lang.Float.POSITIVE_INFINITY;
import static java.lang.Math.nextDown;
import static java.lang.Math.nextUp;
import static java.lang.Math.scalb;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;


/**
 * Unit test for the virtual {@code half} type support, i.e. the <i>IEEE 754-2008</i> floating-point "half format" bit layout..
 */
public class TypeMathTest {

	@Test
	public void testNormalRangeToFloat () {
		assertEquals(+HALF_MIN_NORMAL, shortBitsToHalf((short) 0b0000010000000000), 0);
		assertEquals(-HALF_MIN_NORMAL, shortBitsToHalf((short) 0b1000010000000000), 0);
		assertEquals(+0.500f, shortBitsToHalf((short) 0b0011100000000000), 0);
		assertEquals(-0.500f, shortBitsToHalf((short) 0b1011100000000000), 0);
		assertEquals(+1.000f, shortBitsToHalf((short) 0b0011110000000000), 0);
		assertEquals(-1.000f, shortBitsToHalf((short) 0b1011110000000000), 0);
		assertEquals(+2.000f, shortBitsToHalf((short) 0b0100000000000000), 0);
		assertEquals(-2.000f, shortBitsToHalf((short) 0b1100000000000000), 0);
		assertEquals(+HALF_MAX_VALUE, shortBitsToHalf((short) 0b0111101111111111), 0);
		assertEquals(-HALF_MAX_VALUE, shortBitsToHalf((short) 0b1111101111111111), 0);
	}


	@Test
	public void testSubnormalRangeToFloat () {
		assertEquals(+HALF_MIN_VALUE, shortBitsToHalf((short) 0b0000000000000001), 0);
		assertEquals(-HALF_MIN_VALUE, shortBitsToHalf((short) 0b1000000000000001), 0);
		assertEquals(+HALF_MIN_NORMAL - HALF_MIN_VALUE, shortBitsToHalf((short) 0b0000001111111111), 0);
		assertEquals(-HALF_MIN_NORMAL + HALF_MIN_VALUE, shortBitsToHalf((short) 0b1000001111111111), 0);
	}


	@Test
	public void testSpecialToFloat () {
		assertEquals(POSITIVE_INFINITY, shortBitsToHalf((short) 0b0111110000000000), 0);
		assertEquals(NEGATIVE_INFINITY, shortBitsToHalf((short) 0b1111110000000000), 0);
		assertEquals(+0f, shortBitsToHalf((short) 0b0000000000000000), 0);
		assertEquals(-0f, shortBitsToHalf((short) 0b1000000000000000), 0);
		assertTrue(Float.isNaN(shortBitsToHalf((short) 0b0111111000000000)));
		assertTrue(Float.isNaN(shortBitsToHalf((short) 0b1111111000000000)));
		assertTrue(Float.isNaN(shortBitsToHalf((short) 0b0111110101010101)));
		assertTrue(Float.isNaN(shortBitsToHalf((short) 0b1111110101010101)));
	}


	@Test
	public void testFloatToSpecial () {
		assertEquals((short) 0b0111111000000000, halfToShortBits(NaN));
		assertEquals((short) 0b0111110000000000, halfToShortBits(POSITIVE_INFINITY));
		assertEquals((short) 0b1111110000000000, halfToShortBits(NEGATIVE_INFINITY));
		assertEquals((short) 0b0111110000000000, halfToShortBits(scalb(+1, +16)));
		assertEquals((short) 0b1111110000000000, halfToShortBits(scalb(-1, +16)));
		assertEquals((short) 0b0000000000000000, halfToShortBits(scalb(+1, -25)));
		assertEquals((short) 0b1000000000000000, halfToShortBits(scalb(-1, -25)));
	}


	@Test
	public void testFloatToNormal () {
		assertEquals((short) 0b0000010000000000, halfToShortBits(+HALF_MIN_NORMAL));
		assertEquals((short) 0b1000010000000000, halfToShortBits(-HALF_MIN_NORMAL));
		assertEquals((short) 0b0011110000000000, halfToShortBits(+1f));
		assertEquals((short) 0b1011110000000000, halfToShortBits(-1f));
		assertEquals((short) 0b0111101111111111, halfToShortBits(+HALF_MAX_VALUE));
		assertEquals((short) 0b1111101111111111, halfToShortBits(-HALF_MAX_VALUE));
		assertEquals((short) 0b0111101111111111, halfToShortBits(nextDown(scalb(+1, +16))));
		assertEquals((short) 0b1111101111111111, halfToShortBits(nextUp(scalb(-1, +16))));
	}


	@Test
	public void testFloatToSubNormal () {
		assertEquals((short) 0b0000000000000001, halfToShortBits(+HALF_MIN_VALUE));
		assertEquals((short) 0b1000000000000001, halfToShortBits(-HALF_MIN_VALUE));
		assertEquals((short) 0b0000001111111111, halfToShortBits(+HALF_MIN_NORMAL - HALF_MIN_VALUE));
		assertEquals((short) 0b1000001111111111, halfToShortBits(-HALF_MIN_NORMAL + HALF_MIN_VALUE));
	}


	@Test
	public void testRoundRobin () {
		for (int bits = 0; bits <= 0xffff; ++bits) {
			if ((bits >= 0x7c01 & bits <= 0x7fff) | (bits >= 0xfc01 & bits <= 0xffff)) {
				assertTrue(isNanHalf((short) bits));
				assertFalse(isFiniteHalf((short) bits));
				assertFalse(isInfiniteHalf((short) bits));
				assertEquals((short) 0x7e00, halfToShortBits(shortBitsToHalf((short) bits)));
				assertEquals((short) 0x7e00, halfSignum((short) bits));
				assertEquals((short) (bits & Short.MAX_VALUE), halfAbs((short) bits));
			} else {
				assertFalse(isNanHalf((short) bits));
				assertTrue(isFiniteHalf((short) bits) ^ isInfiniteHalf((short) bits));
				assertEquals((short) bits, halfToShortBits(shortBitsToHalf((short) bits)));

				if ((bits & 0x8000) == 0) {
					final float expected = bits == 0x0000 ? +0f : +1f;
					assertEquals(expected, shortBitsToHalf(halfSignum((short) bits)), 0);
					assertEquals((short) bits, halfAbs((short) bits));
				} else {
					final float expected = bits == 0x8000 ? -0f : -1f;
					assertEquals(expected, shortBitsToHalf(halfSignum((short) bits)), 0);
					assertEquals((short) bits, (short) (0x8000 | halfAbs((short) bits)));
				}
			}
		}
	}


	@Test
	public void printShortBitsToFloat () {
		for (int bits = 0; bits <= 0xffff; ++bits) {
			final float value = shortBitsToHalf((short) bits);
			System.out.format("%04x -> %+1.3a = %+g\n", bits, value, value);
		}
	}
}