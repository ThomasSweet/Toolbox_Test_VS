package de.sb.toolbox.math;

import static java.lang.System.arraycopy;
import java.util.concurrent.ThreadLocalRandom;
import org.junit.Test;


public class FastFourierStressTest {
	static private final int LOOP_COUNT = 1000;
	static private final int MIN_MAGNITUDE = 1;
	static private final int MAX_MAGNITUDE = 16;
	static private final boolean TEST_REFERENCE = false;


	@Test
	public void stressTest () {
		double sum = 0;

		float[] template1;
		double[] template2;
		FloatCartesianComplex[] template3;
		DoubleCartesianComplex[] template4;
		long[] template5;

		// warmup phase
		template2 = new double[2 << 5];
		for (int index = 0; index < template2.length; ++index) {
			template2[index] = ThreadLocalRandom.current().nextDouble() * 2 - 1;
		}
		template1 = new float[2 << 5];
		for (int index = 0; index < template1.length; ++index) {
			template1[index] = (float) template2[index];
		}
		template3 = new FloatCartesianComplex[1 << 5];
		for (int index = 0; index < template1.length; index += 2) {
			template3[index >> 1] = new FloatCartesianComplex(template1[index], template1[index + 1]);
		}
		template4 = new DoubleCartesianComplex[1 << 5];
		for (int index = 0; index < template1.length; index += 2) {
			template4[index >> 1] = new DoubleCartesianComplex(template1[index], template1[index + 1]);
		}
		template5 = new long[1 << 5];
		for (int index = 0; index < template1.length; index += 2) {
			template5[index >> 1] = PrimitiveCartesian.fromCartesian(template1[index], template1[index + 1]);
		}

		if (TEST_REFERENCE) sum += loopFloatReference(25000, template3);
		if (TEST_REFERENCE) sum += loopDoubleReference(25000, template4);
		sum += loopFloatMath(25000, template1);
		sum += loopDoubleMath(25000, template2);
		sum += loopFloatCartesian(25000, template3);
		sum += loopDoubleCartesian(25000, template4);
		sum += loopPrimitiveCartesian(25000, template5);
		sum += loopFastFourierTestbed(25000, template2);


		// test phase
		for (int magnitude = MIN_MAGNITUDE; magnitude <= MAX_MAGNITUDE; ++magnitude) {
			template2 = new double[2 << magnitude];
			for (int index = 0; index < template2.length; ++index) {
				template2[index] = 2 * ThreadLocalRandom.current().nextDouble() - 1;
			}
			template1 = new float[2 << magnitude];
			for (int index = 0; index < template1.length; ++index) {
				template1[index] = (float) template2[index];
			}
			template3 = new FloatCartesianComplex[1 << magnitude];
			for (int index = 0; index < template1.length; index += 2) {
				template3[index >> 1] = new FloatCartesianComplex(template1[index], template1[index + 1]);
			}
			template4 = new DoubleCartesianComplex[1 << magnitude];
			for (int index = 0; index < template1.length; index += 2) {
				template4[index >> 1] = new DoubleCartesianComplex(template1[index], template1[index + 1]);
			}
			template5 = new long[1 << magnitude];
			for (int index = 0; index < template1.length; index += 2) {
				template5[index >> 1] = PrimitiveCartesian.fromCartesian(template1[index], template1[index + 1]);	
			}

			final long t0 = System.currentTimeMillis();
			if (TEST_REFERENCE) sum += loopFloatReference(LOOP_COUNT, template3);

			final long t1 = System.currentTimeMillis();
			if (TEST_REFERENCE) sum += loopDoubleReference(LOOP_COUNT, template4);

			final long t2 = System.currentTimeMillis();
			sum += loopFloatMath(LOOP_COUNT, template1);

			final long t3 = System.currentTimeMillis();
			sum += loopDoubleMath(LOOP_COUNT, template2);

			final long t4 = System.currentTimeMillis();
			sum += loopFloatCartesian(LOOP_COUNT, template3);

			final long t5 = System.currentTimeMillis();
			sum += loopDoubleCartesian(LOOP_COUNT, template4);

			final long t6 = System.currentTimeMillis();
			sum += loopPrimitiveCartesian(LOOP_COUNT, template5);

			final long t7 = System.currentTimeMillis();
			sum += loopFastFourierTestbed(LOOP_COUNT, template2);

			final long t8 = System.currentTimeMillis();
			System.out.format("magnitude=%d/%d, vector-size=%d, loop-count=%d.\n", magnitude, MAX_MAGNITUDE, 1 << magnitude, LOOP_COUNT);
			System.out.format("Time FFT float (REF):  %sµs.\n", 1000L * (t1 - t0) / (double) LOOP_COUNT);
			System.out.format("Time FFT double (REF): %sµs.\n", 1000L * (t2 - t1) / (double) LOOP_COUNT);
			System.out.format("Time FFT float (FM):   %sµs.\n", 1000L * (t3 - t2) / (double) LOOP_COUNT);
			System.out.format("Time FFT double (DM):  %sµs.\n", 1000L * (t4 - t3) / (double) LOOP_COUNT);
			System.out.format("Time FFT float (CC):   %sµs.\n", 1000L * (t5 - t4) / (double) LOOP_COUNT);
			System.out.format("Time FFT double (CC):  %sµs.\n", 1000L * (t6 - t5) / (double) LOOP_COUNT);
			System.out.format("Time FFT float (LC):   %sµs.\n", 1000L * (t7 - t6) / (double) LOOP_COUNT);
			System.out.format("Time FFT double (TB):  %sµs.\n", 1000L * (t8 - t7) / (double) LOOP_COUNT);
			System.out.format("checksum: %s\n\n", sum);
		}
	}


	static private double loopFloatReference (final int loopCount, final FloatCartesianComplex[] template) {
		final FloatCartesianComplex[] vector = new FloatCartesianComplex[template.length];
		for (int index = 0; index < vector.length; ++index) vector[index] = template[index].clone();
		double sum = 0;

		for (int loop = loopCount; loop > 0; --loop) {
			FastFourierTransform.transform(false, vector);
			FastFourierTransform.transform(true, vector);
			sum += vector[0].re();
		}
		return sum;
	}


	static private double loopDoubleReference (final int loopCount, final DoubleCartesianComplex[] template) {
		final DoubleCartesianComplex[] vector = new DoubleCartesianComplex[template.length];
		for (int index = 0; index < vector.length; ++index) vector[index] = template[index].clone();
		double sum = 0;

		for (int loop = loopCount; loop > 0; --loop) {
			FastFourierTransform.transform(false, vector);
			FastFourierTransform.transform(true, vector);
			sum += vector[0].re();
		}
		return sum;
	}


	static private double loopFloatMath (final int loopCount, final float[] template) {
		final float[] vector = new float[template.length];
		double sum = 0;

		for (int loop = loopCount; loop > 0; --loop) {
			arraycopy(template, 0, vector, 0, template.length);
			FloatMath.fft(false, false, vector);
			FloatMath.fft(true, false, vector);
			sum += vector[0];
		}
		return sum;
	}

	static private double loopDoubleMath (final int loopCount, final double[] template) {
		final double[] vector = new double[template.length];
		double sum = 0;

		for (int loop = loopCount; loop > 0; --loop) {
			arraycopy(template, 0, vector, 0, template.length);
			DoubleMath.fft(false, false, vector);
			DoubleMath.fft(true, false, vector);
			sum += vector[0];
		}
		return sum;
	}

	static private double loopFloatCartesian (final int loopCount, final FloatCartesianComplex[] template) {
		final FloatCartesianComplex[] vector = new FloatCartesianComplex[template.length];
		for (int index = 0; index < vector.length; ++index) vector[index] = template[index].clone();
		final FloatCartesianComplex unit = new FloatCartesianComplex(1);
		double sum = 0;

		for (int loop = loopCount; loop > 0; --loop) {
			unit.fft(false, false, vector);
			unit.fft(true, false, vector);
			sum += vector[0].re();
		}
		return sum;
	}


	static private double loopDoubleCartesian (final int loopCount, final DoubleCartesianComplex[] template) {
		final DoubleCartesianComplex[] vector = new DoubleCartesianComplex[template.length];
		for (int index = 0; index < vector.length; ++index) vector[index] = template[index].clone();
		final DoubleCartesianComplex unit = new DoubleCartesianComplex(1);
		double sum = 0;

		for (int loop = loopCount; loop > 0; --loop) {
			unit.fft(false, false, vector);
			unit.fft(true, false, vector);
			sum += vector[0].re();
		}
		return sum;
	}


	static private double loopPrimitiveCartesian (final int loopCount, final long[] template) {
		final long[] vector = template.clone();
		double sum = 0;

		for (int loop = loopCount; loop > 0; --loop) {
			PrimitiveCartesian.fft(false, false, vector);
			PrimitiveCartesian.fft(true, false, vector);
			sum += vector[0];
		}
		return sum;
	}


	static private double loopFastFourierTestbed (final int loopCount, final double[] template) {
		final double[] vector = new double[template.length];
		double sum = 0;

		for (int loop = loopCount; loop > 0; --loop) {
			arraycopy(template, 0, vector, 0, template.length);
			FastFourierTestbed.fft(false, vector);
			FastFourierTestbed.fft(true, vector);
			sum += vector[0];
		}
		return sum;
	}
}