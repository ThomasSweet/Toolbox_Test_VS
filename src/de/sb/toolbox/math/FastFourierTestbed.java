package de.sb.toolbox.math;

import static de.sb.toolbox.math.IntMath.floorLog2;
import static de.sb.toolbox.math.IntMath.perfectShuffle;
import static de.sb.toolbox.util.ArraySupport.swap;
import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.pow;
import static java.lang.Math.sin;
import de.sb.toolbox.Copyright;


/**
 * This facade adds additional mathematical operations for {@code 64-bit} floating-point arithmetics.
 */
@Copyright(year = 2013, holders = "Sascha Baumeister")
public final class FastFourierTestbed {

	/**
	 * Prevents external instantiation.
	 */
	private FastFourierTestbed () {}



	/**
	 * Performs an <i>in-place Fast Fourier Transform</i> of the given vector of <tt>&half;N</tt> <i>braided</i> complex
	 * numbers.
	 * @param inverse whether or not an {@code inverse} fourier transform shall be performed
	 * @param separate whether or not <i>channel separation</i> shall be performed
	 * @param vector an array of <tt>&half;N</tt> braided complex numbers in Cartesian form, alternating even indexed real parts
	 *        with odd indexed imaginary ones
	 * @throws NullPointerException if the given vector is {@code null}
	 * @throws IllegalArgumentException if the given vector's length is odd or not a power of two
	 */
	static public void fft (final boolean inverse, final double[] vector) throws NullPointerException, IllegalArgumentException {
		if (inverse) {
			for (int index = 1; index < vector.length; index += 2)
				vector[index] *= -1;
		}

		final int magnitude = floorLog2(vector.length) - 1;
		fft(magnitude, vector);

		if (inverse) {
			final double norm = 2d / vector.length;
			for (int index = 0; index < vector.length; index += 2) {
				vector[index] *= +norm;
				vector[index + 1] *= -norm;
			}
		}
	}


	/**
	 * Performs an <i>in-place Fast Fourier Transform</i> of the given vector of <tt>&half;N</tt> <i>braided</i> complex
	 * numbers. Note that an <i>inverse</i> transform can be performed in two ways:
	 * <ul>
	 * <li>by conjugating both the argument and result of this operation, and additionally norming the result by {@code 2/N}.
	 * </li>
	 * <li>by swapping the real and imaginary parts of both the argument and the result.</i>
	 * </ul>
	 * @param vector an array of <tt>&half;N</tt> braided complex numbers in Cartesian form, alternating even indexed real parts
	 *        with odd indexed imaginary ones
	 * @throws NullPointerException if the given argument is {@code null}
	 * @throws IllegalArgumentException if the given argument's length is odd or not a power of two
	 */
	static private void fft (final int magnitude, final double[] vector) throws NullPointerException, IllegalArgumentException {
		if (vector.length == 0) return;
		if (vector.length != 2 * pow(2, magnitude)) throw new IllegalArgumentException();

		for (int left = 0; left < vector.length; left += 2) {
			final int right = perfectShuffle(left / 2, magnitude) * 2;
			if (right > left) {
				swap(vector, left, vector, right);
				swap(vector, left + 1, vector, right + 1);
			}
		}

		for (int step = 2; step < vector.length; step *= 2) {
			for (int offset = 0; offset < step / 2; ++offset) {
				final double angle = 2 * PI * offset / step;

				for (int index = 2 * offset; index < vector.length; index += 2*step) {
					final double re = vector[index + step];
					final double im = vector[index + step + 1];
					final double taoRe = cos(angle) * re - sin(angle) * im;
					final double taoIm = cos(angle) * im + sin(angle) * re;

					vector[index + step] = vector[index] - taoRe;
					vector[index + step + 1] = vector[index + 1] - taoIm;
					vector[index] += taoRe;
					vector[index + 1] += taoIm;
				}
			}
		}
	}


	/**
	 * Performs an <i>in-place Fast Fourier Transform</i> of the given vector of <tt>&half;N</tt> <i>braided</i> complex
	 * numbers. Note that an <i>inverse</i> transform can be performed in two ways:
	 * <ul>
	 * <li>by conjugating both the argument and result of this operation, and additionally norming the result by {@code 2/N}.
	 * </li>
	 * <li>by swapping the real and imaginary parts of both the argument and the result.</i>
	 * </ul>
	 * @param vector an array of <tt>&half;N</tt> braided complex numbers in Cartesian form, alternating even indexed real parts
	 *        with odd indexed imaginary ones
	 * @throws NullPointerException if the given argument is {@code null}
	 * @throws IllegalArgumentException if the given argument's length is odd or not a power of two
	 */
	@SuppressWarnings("unused")
	static private void slowFFT (final int magnitude, final double[] vector) throws NullPointerException, IllegalArgumentException {
		if (vector.length == 0) return;
		if (vector.length != 2 * pow(2, magnitude)) throw new IllegalArgumentException();

		for (int left = 0; left < vector.length; left += 2) {
			final int right = perfectShuffle(left / 2, magnitude) * 2;
			if (right > left) {
				swap(vector, left, vector, right);
				swap(vector, left + 1, vector, right + 1);
			}
		}

		for (int step = 2; step < vector.length; step *= 2) {
			for (int offset = 0; offset < step / 2; ++offset) {
				final double angle = 2 * PI * offset / step;

				for (int index = 2 * offset; index < vector.length; index += 2*step) {
					final double re = vector[index + step];
					final double im = vector[index + step + 1];
					final double taoRe = cos(angle) * re - sin(angle) * im;
					final double taoIm = cos(angle) * im + sin(angle) * re;

					vector[index + step] = vector[index] - taoRe;
					vector[index + step + 1] = vector[index + 1] - taoIm;
					vector[index] += taoRe;
					vector[index + 1] += taoIm;
				}
			}
		}
	}


	/**
	 * Performs an <i>in-place Fast Fourier Transform</i> of the given vector of <tt>&half;N</tt> <i>braided</i> complex
	 * numbers. Note that an <i>inverse</i> transform can be performed in two ways:
	 * <ul>
	 * <li>by conjugating both the argument and result of this operation, and additionally norming the result by {@code 2/N}.
	 * </li>
	 * <li>by swapping the real and imaginary parts of both the argument and the result.</i>
	 * </ul>
	 * @param vector an array of <tt>&half;N</tt> braided complex numbers in Cartesian form, alternating even indexed real parts
	 *        with odd indexed imaginary ones
	 * @throws NullPointerException if the given argument is {@code null}
	 * @throws IllegalArgumentException if the given argument's length is odd or not a power of two
	 */
	@SuppressWarnings("unused")
	static private void fastFFT (final int magnitude, final double[] vector) throws NullPointerException, IllegalArgumentException {
		if (vector.length == 0) return;
		if (vector.length != 2 << magnitude) throw new IllegalArgumentException();

		for (final FunctionTables.SwapEntry swapEntry : FunctionTables.getPerfectShuffleTable(magnitude)) {
			final int left = 2 * swapEntry.getLeft(), right = 2 * swapEntry.getRight();
			swap(vector, left, vector, right);
			swap(vector, left + 1, vector, right + 1);
		}

		final FunctionTables.Trigonometric sineTable = FunctionTables.getTrigonometricTable(magnitude);
		for (int depth = 0; depth < magnitude; depth += 1) {
			for (int offset = 0; offset < 1 << depth; ++offset) {
				final int angleIndex = offset << magnitude - depth - 1;
				final double cos = sineTable.cos(angleIndex);
				final double sin = sineTable.sin(angleIndex);

				for (int left = 2 * offset, right = left + (2 << depth); left < 2 << magnitude; left += 4 << depth, right += 4 << depth) {
					final double re = vector[right];
					final double im = vector[right + 1];
					final double taoRe = cos * re - sin * im;
					final double taoIm = cos * im + sin * re;

					vector[right] = vector[left] - taoRe;
					vector[right + 1] = vector[left + 1] - taoIm;
					vector[left] += taoRe;
					vector[left + 1] += taoIm;
				}
			}
		}
	}
}