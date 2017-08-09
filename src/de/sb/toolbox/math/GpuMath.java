package de.sb.toolbox.math;

import java.util.stream.IntStream;
import java.util.stream.Stream;
import de.sb.toolbox.math.FunctionTables.SwapEntry;
import de.sb.toolbox.util.ArraySupport;

public class GpuMath {

	/**
	 * @param inverse whether or not an inverse FFT is to be performed
	 * @param vector an array of <tt>2<sup>magnitude</sup>=N/2</tt> complex numbers in Cartesian
	 *        form, alternating even indexed real parts with odd indexed imaginary ones
	 * @throws NullPointerException if the given vector is {@code null}
	 * @throws IllegalArgumentException if the length of the given vector is odd or not a power of
	 *         two
	 * @see #fft(double[])
	 */
	static public void fft (final boolean inverse, final float[] vector) throws NullPointerException, IllegalArgumentException {
		if (inverse)
			for (int index = 1; index < vector.length; index += 2)
				vector[index] *= -1;

		fft(vector);

		if (inverse)
			for (int index = 1; index < vector.length; index += 2)
				vector[index] *= -1;
	}


	/**
	 * @param real an array of real parts for <tt>2<sup>magnitude</sup>=N</tt> complex numbers
	 * @param imag an array of imaginary parts for <tt>2<sup>magnitude</sup>=N</tt> complex numbers
	 * @throws NullPointerException if any of the given vectors is {@code null}
	 * @throws IllegalArgumentException if the length of the given vectors is not a power of two, or
	 *         not the same
	 * @see #fft(float[])
	 */
	static public void fft (final float[] real, final float[] imag) throws NullPointerException, IllegalArgumentException {
		if (real.length != imag.length) throw new IllegalArgumentException();

		final float[] vector = new float[real.length + imag.length];
		ArraySupport.braid(real, imag, vector);
		fft(vector);
		ArraySupport.unbraid(vector, real, imag);
	}


	/**
	 * @param vector an array of <tt>2<sup>magnitude</sup>=N/2</tt> complex numbers in Cartesian
	 *        form, alternating even indexed real parts with odd indexed imaginary ones
	 * @throws NullPointerException if the given vector is {@code null}
	 * @throws IllegalArgumentException if the given vector's length is odd or not a power of two
	 */
	static private void fft (final float[] vector) throws NullPointerException, IllegalArgumentException {
		if (vector.length == 0 | vector.length == 2) return;
		final int magnitude = IntMath.floorLog2(vector.length) - 1;
		if (vector.length != 2 << magnitude) throw new IllegalArgumentException();

		for (final SwapEntry entry : FunctionTables.getPerfectShuffleTable(magnitude)) {
			final int left = entry.getLeft() << 1, right = entry.getRight() << 1;
			ArraySupport.swap(vector, left, vector, right);
			ArraySupport.swap(vector, left + 1, vector, right + 1);
		}

		final FunctionTables.Trigonometric trigonometricTable = FunctionTables.getTrigonometricTable(magnitude);
		for (int mag = 0; mag < magnitude; ++mag) {
			for (int offset = 0; offset < 1 << mag; ++offset) {
				final int angleIndex = offset << (magnitude - mag - 1);
				final float sin = (float) trigonometricTable.sin(angleIndex);
				final float cos = (float) trigonometricTable.cos(angleIndex);

				for (int left = offset << 1, right = left + (2 << mag); left < 2 << magnitude; left += 4 << mag, right += 4 << mag) {
					float re = vector[right], im = vector[right + 1];
					final float twidRe = cos * re - sin * im;
					final float twidIm = cos * im + sin * re;
					re = vector[left]; im = vector[left + 1];
					vector[right]     = re - twidRe;
					vector[right + 1] = im - twidIm;
					vector[left]      = re + twidRe;
					vector[left  + 1] = im + twidIm;
				}
			}
		}

		final float norm = (float) Math.sqrt(Math.scalb(1.0, -magnitude));
		for (int index = 0; index < 2 << magnitude; ++index) {
			vector[index] *= norm;
		}
	}


	/**
	 * @param inverse whether or not an inverse FFT is to be performed
	 * @param vector an array of <tt>2<sup>magnitude</sup>=N/2</tt> complex numbers in Cartesian
	 *        form, alternating even indexed real parts with odd indexed imaginary ones
	 * @throws NullPointerException if the given vector is {@code null}
	 * @throws IllegalArgumentException if the length of the given vector is odd or not a power of
	 *         two
	 * @see #fft(double[])
	 */
	static public void fft (final boolean inverse, final double[] vector) throws NullPointerException, IllegalArgumentException {
		if (inverse)
			for (int index = 1; index < vector.length; index += 2)
				vector[index] *= -1;

		fft(vector);

		if (inverse)
			for (int index = 1; index < vector.length; index += 2)
				vector[index] *= -1;
	}


	/**
	 * @param real an array of real parts for <tt>2<sup>magnitude</sup>=N</tt> complex numbers
	 * @param imag an array of imaginary parts for <tt>2<sup>magnitude</sup>=N</tt> complex numbers
	 * @throws NullPointerException if any of the given vectors is {@code null}
	 * @throws IllegalArgumentException if the length of the given vectors is not a power of two, or
	 *         not the same
	 * @see #fft(double[])
	 */
	static public void fft (final double[] real, final double[] imag) throws NullPointerException, IllegalArgumentException {
		if (real.length != imag.length) throw new IllegalArgumentException();

		final double[] vector = new double[real.length + imag.length];
		ArraySupport.braid(real, imag, vector);
		fft(vector);
		ArraySupport.unbraid(vector, real, imag);
	}


	/**
	 * @param vector an array of <tt>2<sup>magnitude</sup>=N/2</tt> complex numbers in Cartesian
	 *        form, alternating even indexed real parts with odd indexed imaginary ones
	 * @throws NullPointerException if the given vector is {@code null}
	 * @throws IllegalArgumentException if the given vector's length is odd or not a power of two
	 */
	static private void fft (final double[] vector) throws NullPointerException, IllegalArgumentException {
		if (vector.length == 0 | vector.length == 2) return;
		final int magnitude = IntMath.floorLog2(vector.length) - 1;
		if (vector.length != 2 << magnitude) throw new IllegalArgumentException();

		for (final SwapEntry entry : FunctionTables.getPerfectShuffleTable(magnitude)) {
			final int left = entry.getLeft() << 1, right = entry.getRight() << 1;
			ArraySupport.swap(vector, left, vector, right);
			ArraySupport.swap(vector, left + 1, vector, right + 1);
		}

		// with Aparapi:
//		class IndexConsumer extends com.aparapi.Kernel {
//			private int depth, offset;
//			private double sin, cos;
//			public void run () {
//				final int left  = (this.offset << 1) + (this.getGlobalId() << this.depth << 2);
//				final int right = left + (2 << this.depth);
//				double re = vector[right], im = vector[right + 1];
//				final double twidRe = this.cos * re - this.sin * im;
//				final double twidIm = this.cos * im + this.sin * re;
//				re = vector[left]; im = vector[left + 1];
//				vector[right]     = re - twidRe;
//				vector[right + 1] = im - twidIm;
//				vector[left]      = re + twidRe;
//				vector[left + 1]  = im + twidIm;
//			}
//		};

	 	// with stream-api:
		class IndexConsumer implements java.util.function.IntConsumer {
			private int depth, offset;
			private double sin, cos;
			public void accept (final int index) {
				final int left  = (this.offset << 1) + (index << this.depth << 2);
				final int right = left + (2 << this.depth);
				double re = vector[right], im = vector[right + 1];
				final double twidRe = cos * re - sin * im;
				final double twidIm = cos * im + sin * re;
				re = vector[left]; im = vector[left + 1];
				vector[right]     = re - twidRe;
				vector[right + 1] = im - twidIm;
				vector[left]      = re + twidRe;
				vector[left + 1]  = im + twidIm;
			}
		}

		final IndexConsumer consumer = new IndexConsumer();
//		try {
//			consumer.setAutoCleanUpArrays(true);

			final FunctionTables.Trigonometric trigonometricTable = FunctionTables.getTrigonometricTable(magnitude);
			for (int depth = 0; depth < magnitude; ++depth) {
				consumer.depth = depth;
				for (int offset = 0;offset < 1 << depth; ++offset) {
					consumer.offset = offset;
	
					final int angleIndex = offset << magnitude - depth - 1;
					consumer.sin = trigonometricTable.sin(angleIndex);
					consumer.cos = trigonometricTable.cos(angleIndex);
					final int range = ((2 << magnitude) - (offset << 1) + (4 << depth) - 1) >> depth >> 2;
	
					// with Aparapi:
//					consumer.execute(range);

					// with stream-api:
					java.util.stream.IntStream stream = java.util.stream.IntStream.range(0, range);
					if (range >= 10000) stream = stream.parallel();
					stream.forEach(consumer);
				}
			}
//		} finally {
//			consumer.dispose();
//		}

		final double norm = Math.sqrt(Math.scalb(1.0, -magnitude));
		for (int index = 0; index < 2 << magnitude; ++index) {
			vector[index] *= norm;
		}
	}


	static private class FftState {
		public final int depth;
		public final int offset;
		public final double sin;
		public final double cos;

		public FftState (final int depth, final int offset, final double sin, final double cos) {
			this.depth = depth;
			this.offset = offset;
			this.sin = sin;
			this.cos = cos;
		}
	}


	static private Stream<FftState> newStateStream (final double[] vector, final int magnitude, final FunctionTables.Trigonometric table) {
		return IntStream
			.range(0, magnitude)
			.boxed()
			.flatMap(mag -> IntStream
				.range(0, 1 << mag)
				.mapToObj(offset -> {
					final int angleIndex = offset << (magnitude - mag - 1);
					final double sin = table.sin(angleIndex);
					final double cos = table.cos(angleIndex);
					return new FftState(mag, offset, sin, cos);
				})
			);
	}


	static private class Indexer {
		public final FftState state;
		public final int index;

		public Indexer (final FftState state, final int index) {
			this.state = state;
			this.index = index;
		}
	}


	@SuppressWarnings("unused")
	static private Stream<Indexer> newIndexerStream (final double[] vector, final int magnitude, final FunctionTables.Trigonometric table) {
		return newStateStream(vector, magnitude, table)
			.flatMap(state -> IntStream
				.range(0, ((2 << magnitude) - (state.offset << 1) + (4 << state.depth) - 1) >> state.depth >> 2)
				.mapToObj(index -> new Indexer(state, index))
			)
			.peek(indexer -> {
				final int left  = (indexer.state.offset << 1) + (indexer.index << indexer.state.depth << 2);
				final int right = left + (2 << indexer.state.depth);
				double re = vector[right], im = vector[right + 1];
				final double twidRe = indexer.state.cos * re - indexer.state.sin * im;
				final double twidIm = indexer.state.cos * im + indexer.state.sin * re;
				re = vector[left]; im = vector[left + 1];
				vector[right]     = re - twidRe;
				vector[right + 1] = im - twidIm;
				vector[left]      = re + twidRe;
				vector[left  + 1] = im + twidIm;
			});
	}
}