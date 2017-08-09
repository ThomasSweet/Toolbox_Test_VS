package de.sb.toolbox.math;

import static java.lang.Math.PI;
import static java.lang.Math.round;
import static java.lang.System.currentTimeMillis;
import org.junit.Test;


/**
 * Stress test for basic arithmetic operators.
 */
public class OperatorStressTest {
	static private final FunctionTables.Trigonometric SINE_TABLE = FunctionTables.getTrigonometricTable(16);
	static private final double CLOCK_RATE = 2.5E9;
	static private final long WARMUP_OPS = 50_000, STRESS_OPS = 1_000_000_000;


	/**
	 * 32bit INT_MUL.
	 */
	@Test
	public void measureMultiplyInt () {
		double checksum = mulInt(WARMUP_OPS, 15);

		final long timestamp = currentTimeMillis();
		checksum += mulInt(STRESS_OPS, 10);
		final long millies = currentTimeMillis() - timestamp;

		final long ticks = ticks(millies, STRESS_OPS, 1);
		printResult("32bit INT_MUL", ticks, millies, checksum);
	}


	/**
	 * 32bit LONG_MUL.
	 */
	@Test
	public void measureMultiplyLong () {
		double checksum = mulLong(WARMUP_OPS, 15);

		final long timestamp = currentTimeMillis();
		checksum += mulLong(STRESS_OPS, 15);
		final long millies = currentTimeMillis() - timestamp;

		final long ticks = ticks(millies, STRESS_OPS, 1);
		printResult("64bit INT_MUL", ticks, millies, checksum);
	}


	/**
	 * 32bit FP_MUL.
	 */
	@Test
	public void measureMultiplyFloat () {
		double checksum = mulFloat(WARMUP_OPS, 15);

		final long timestamp = currentTimeMillis();
		checksum += mulFloat(STRESS_OPS, 15);
		final long millies = currentTimeMillis() - timestamp;

		final long ticks = ticks(millies, STRESS_OPS, .5);
		printResult("32bit FP_MUL", ticks, millies, checksum);
	}


	/**
	 * 64bit FP_MUL.
	 */
	@Test
	public void measureMultiplyDouble () {
		double checksum = mulDouble(WARMUP_OPS, 15);

		final long timestamp = currentTimeMillis();
		checksum += mulDouble(STRESS_OPS, 15);
		final long millies = currentTimeMillis() - timestamp;

		final long ticks = ticks(millies, STRESS_OPS, .5);
		printResult("64bit FP_MUL", ticks, millies, checksum);
	}


	/**
	 * 32bit INT_DIV.
	 */
	@Test
	public void measureDivideInt () {
		double checksum = divInt(WARMUP_OPS, 15);

		final long timestamp = currentTimeMillis();
		checksum += divInt(STRESS_OPS, 15);
		final long millies = currentTimeMillis() - timestamp;

		final long ticks = ticks(millies, STRESS_OPS, 4);
		printResult("32bit INT_DIV", ticks, millies, checksum);
	}


	/**
	 * 64bit INT_DIV.
	 */
	@Test
	public void measureDivideLong () {
		double checksum = divLong(WARMUP_OPS, 15);

		final long timestamp = currentTimeMillis();
		checksum += divLong(STRESS_OPS, 15);
		final long millies = currentTimeMillis() - timestamp;

		final long ticks = ticks(millies, STRESS_OPS, 4);
		printResult("64bit INT_DIV", ticks, millies, checksum);
	}


	/**
	 * 32bit FP_DIV.
	 */
	@Test
	public void measureDivideFloat () {
		double checksum = divFloat(WARMUP_OPS, 15);

		final long timestamp = currentTimeMillis();
		checksum += divFloat(STRESS_OPS, 15);
		final long millies = currentTimeMillis() - timestamp;

		final long ticks = ticks(millies, STRESS_OPS, .5);
		printResult("32bit FP_DIV", ticks, millies, checksum);
	}


	/**
	 * 64bit FP_DIV.
	 */
	@Test
	public void measureDivideDouble () {
		double checksum = divDouble(WARMUP_OPS, 15);

		final long timestamp = currentTimeMillis();
		checksum += divDouble(STRESS_OPS, 15);
		final long millies = currentTimeMillis() - timestamp;

		final long ticks = ticks(millies, STRESS_OPS, .5);
		printResult("64bit FP_DIV", ticks, millies, checksum);
	}


	/**
	 * 32bit FP_INV.
	 */
	@Test
	public void measureInvertFloat () {
		double checksum = invFloat(WARMUP_OPS, 15);

		final long timestamp = currentTimeMillis();
		checksum += invFloat(STRESS_OPS, 15);
		final long millies = currentTimeMillis() - timestamp;

		final long ticks = ticks(millies, STRESS_OPS, .5);
		printResult("32bit FP_INV", ticks, millies, checksum);
	}


	/**
	 * 64bit FP_INV.
	 */
	@Test
	public void measureInvertDouble () {
		double checksum = invDouble(WARMUP_OPS, 15);

		final long timestamp = currentTimeMillis();
		checksum += invDouble(STRESS_OPS, 15);
		final long millies = currentTimeMillis() - timestamp;

		final long ticks = ticks(millies, STRESS_OPS, .5);
		printResult("64bit FP_INV", ticks, millies, checksum);
	}


	/**
	 * 64bit FP_DIV/2 (A).
	 */
	@Test
	public void measureDivideBy2ADouble () {
		double checksum = div2ADouble(WARMUP_OPS, 15);

		final long timestamp = currentTimeMillis();
		checksum += div2ADouble(STRESS_OPS, 15);
		final long millies = currentTimeMillis() - timestamp;

		final long ticks = ticks(millies, STRESS_OPS, .5);
		printResult("64bit FP_DIV/2 (A)", ticks, millies, checksum);
	}


	/**
	 * 64bit FP_DIV/2 (B).
	 */
	@Test
	public void measureDivideBy2BDouble () {
		double checksum = div2BDouble(WARMUP_OPS, 15);

		final long timestamp = currentTimeMillis();
		checksum += div2BDouble(STRESS_OPS, 15);
		final long millies = currentTimeMillis() - timestamp;

		final long ticks = ticks(millies, STRESS_OPS, .5);
		printResult("64bit FP_DIV/2 (B)", ticks, millies, checksum);
	}


	/**
	 * 64bit FP_DIV/3 (A).
	 */
	@Test
	public void measureDivideBy3ADouble () {
		double checksum = div3ADouble(WARMUP_OPS, 15);

		final long timestamp = currentTimeMillis();
		checksum += div3ADouble(STRESS_OPS, 15);
		final long millies = currentTimeMillis() - timestamp;

		final long ticks = ticks(millies, STRESS_OPS, .5);
		printResult("64bit FP_DIV/3 (A)", ticks, millies, checksum);
	}


	/**
	 * 64bit FP_DIV/3 (B).
	 */
	@Test
	public void measureDivideBy3BDouble () {
		double checksum = div3BDouble(WARMUP_OPS, 15);

		final long timestamp = currentTimeMillis();
		checksum += div3BDouble(STRESS_OPS, 15);
		final long millies = currentTimeMillis() - timestamp;

		final long ticks = ticks(millies, STRESS_OPS, .5);
		printResult("64bit FP_DIV/3 (B)", ticks, millies, checksum);
	}


	/**
	 * 64bit FP_ABS.
	 */
	@Test
	public void measureAbsDouble () {
		double checksum = absDouble(WARMUP_OPS);

		final long timestamp = currentTimeMillis();
		checksum += absDouble(STRESS_OPS / 10);
		final long millies = currentTimeMillis() - timestamp;

		final long ticks = ticks(millies, STRESS_OPS / 10, 3.5);
		printResult("64bit FP_ABS", ticks, millies, checksum);
	}


	/**
	 * 64bit FP_SCALB.
	 */
	@Test
	public void measureScalbDouble () {
		double checksum = scalbDouble(WARMUP_OPS);

		final long timestamp = currentTimeMillis();
		checksum += scalbDouble(STRESS_OPS / 10);
		final long millies = currentTimeMillis() - timestamp;

		final long ticks = ticks(millies, STRESS_OPS / 10, 4);
		printResult("64bit FP_SCALB", ticks, millies, checksum);
	}


	/**
	 * 64bit FP_SQRT.
	 */
	@Test
	public void measureSqrtDouble () {
		double checksum = sqrtDouble(WARMUP_OPS);

		final long timestamp = currentTimeMillis();
		checksum += sqrtDouble(STRESS_OPS / 10);
		final long millies = currentTimeMillis() - timestamp;

		final long ticks = ticks(millies, STRESS_OPS / 10, 4);
		printResult("64bit FP_SQRT", ticks, millies, checksum);
	}


	/**
	 * 64bit FP_LOG[1/2,2].
	 */
	@Test
	public void measureLogDouble () {
		double checksum = logDouble(WARMUP_OPS);

		final long timestamp = currentTimeMillis();
		checksum += logDouble(STRESS_OPS / 10);
		final long millies = currentTimeMillis() - timestamp;

		final long ticks = ticks(millies, STRESS_OPS / 10, 4);
		printResult("64bit FP_LOG[1/2,2]", ticks, millies, checksum);
	}


	/**
	 * 64bit FP_EXP[1/2,2].
	 */
	@Test
	public void measureExpDouble () {
		double checksum = expDouble(WARMUP_OPS);

		final long timestamp = currentTimeMillis();
		checksum += expDouble(STRESS_OPS / 10);
		final long millies = currentTimeMillis() - timestamp;

		final long ticks = ticks(millies, STRESS_OPS / 10, 4);
		printResult("64bit FP_EXP[1/2,2]", ticks, millies, checksum);
	}


	/**
	 * 64bit FP_SIN[0,2PI].
	 */
	@Test
	public void measureSin0Double () {
		double checksum = sin0Double(WARMUP_OPS);

		final long timestamp = currentTimeMillis();
		checksum += sin0Double(STRESS_OPS / 10);
		final long millies = currentTimeMillis() - timestamp;

		final long ticks = ticks(millies, STRESS_OPS / 10, 4);
		printResult("64bit FP_SIN[0,2PI]", ticks, millies, checksum);
	}


	/**
	 * 64bit FP_SIN[-PI,PI].
	 */
	@Test
	public void measureSin1Double () {
		double checksum = sin1Double(WARMUP_OPS);

		final long timestamp = currentTimeMillis();
		checksum += sin1Double(STRESS_OPS / 10);
		final long millies = currentTimeMillis() - timestamp;

		final long ticks = ticks(millies, STRESS_OPS / 10, 4);
		printResult("64bit FP_SIN[-PI,PI]", ticks, millies, checksum);
	}


	/**
	 * 64bit FP_IDX_SIN[-N/2,N-N/2].
	 */
	@Test
	public void measureIdxSinDouble () {
		double checksum = idxSinDouble(WARMUP_OPS);

		final long timestamp = currentTimeMillis();
		checksum += idxSinDouble(STRESS_OPS / 10);
		final long millies = currentTimeMillis() - timestamp;

		final long ticks = ticks(millies, STRESS_OPS / 10, 4);
		printResult("64bit FP_IDX_SIN[-N/2,N-N/2]", ticks, millies, checksum);
	}


	/**
	 * 64bit FP_APX_SIN[0,2PI].
	 */
	@Test
	public void measureApproximateSin0Double () {
		double checksum = approximateSin0Double(WARMUP_OPS);

		final long timestamp = currentTimeMillis();
		checksum += approximateSin0Double(STRESS_OPS / 10);
		final long millies = currentTimeMillis() - timestamp;

		final long ticks = ticks(millies, STRESS_OPS / 10, 4);
		printResult("64bit FP_APX_SIN[0,2PI]", ticks, millies, checksum);
	}


	/**
	 * 64bit FP_APX_SIN[-PI,PI].
	 */
	@Test
	public void measureApproximateSin1Double () {
		double checksum = approximateSin1Double(WARMUP_OPS);

		final long timestamp = currentTimeMillis();
		checksum += approximateSin1Double(STRESS_OPS / 10);
		final long millies = currentTimeMillis() - timestamp;

		final long ticks = ticks(millies, STRESS_OPS / 10, 4);
		printResult("64bit FP_APX_SIN[-PI,PI]", ticks, millies, checksum);
	}


	/**
	 * 64bit FP_TANH.
	 */
	@Test
	public void measureTanhDouble () {
		double checksum = tanhDouble(WARMUP_OPS);

		final long timestamp = currentTimeMillis();
		checksum += tanhDouble(STRESS_OPS / 10);
		final long millies = currentTimeMillis() - timestamp;

		final long ticks = ticks(millies, STRESS_OPS / 10, 4);
		printResult("64bit FP_TANH", ticks, millies, checksum);
	}


	/**
	 * 64bit FP_ATAN2.
	 */
	@Test
	public void measureAtan2Double () {
		double checksum = atan2Double(WARMUP_OPS);

		final long timestamp = currentTimeMillis();
		checksum += atan2Double(STRESS_OPS / 10);
		final long millies = currentTimeMillis() - timestamp;

		final long ticks = ticks(millies, STRESS_OPS / 10, 7);
		printResult("64bit FP_ATAN2", ticks, millies, checksum);
	}


	/**
	 * Returns the operation duration in ticks.
	 * @param millies the stress test duration in milliseconds
	 * @param count the number of test operations performed 
	 * @param overhead the test overhead per operation in ticks
	 * @return the number of ticks per operation
	 */
	static private long ticks (final long millies, final long count, final double overhead) {
		return round(CLOCK_RATE * .001 * millies / count - overhead);
	}


	/**
	 * Prints a result line to the process output stream.
	 * @param label the label identifying the stress test
	 * @param ticks the estimated amount of processor ticks per operation
	 * @param totalMillies the total number of milliseconds for all operations
	 * @param checksum the checksum (only passed to prevent the optimizer from eliminating the complete test-loop)
	 */
	static private void printResult (final String label, final long ticks, final long totalMillies, final double checksum) {
		System.out.format("%s: ticks=%d, total=%dms\n", label, ticks, totalMillies);
	}



	//****************//
	// Multiplication //
	//****************//

	/**
	 * 1/1=1 ticks overhead per operation.
	 */
	static private int mulInt (final long operationCount, final int operand) {
		int result = 1;
		for (long loop = operationCount; loop > 0; --loop) {
			result *= operand;
		}
		return result;
	}


	/**
	 * 1/1=1 ticks overhead per operation.
	 */
	static private long mulLong (final long operationCount, final long operand) {
		long result = 1;
		for (long loop = operationCount; loop > 0; --loop) {
			result *= operand;
		}
		return result;
	}


	/**
	 * 1/2=.5 ticks overhead per operation.
	 */
	static private float mulFloat (final long operationCount, final float operand) {
		float result = 1.1f, inv = 1 / operand;
		for (long loop = operationCount; loop > 0; loop -= 2) {
			result *= operand;
			result *= inv;
		}
		return result;
	}


	/**
	 * 1/2=.5 ticks overhead per operation.
	 */
	static private double mulDouble (final long operationCount, final double operand) {
		double result = 1.1d, inv = 1 / operand;
		for (long loop = operationCount; loop > 0; loop -= 2) {
			result *= operand;
			result *= inv;
		}
		return result;
	}


	//**********//
	// Division //
	//**********//

	/**
	 * 4/1=4 ticks overhead per operation.
	 */
	static private int divInt (final long operationCount, final int operand) {
		int result = 1000000;
		for (long loop = operationCount; loop > 0; --loop) {
			result /= operand;
			result += 1000000;
		}
		return result;
	}


	/**
	 * 4/1=4 ticks overhead per operation.
	 */
	static private long divLong (final long operationCount, final long operand) {
		long result = 1000000;
		for (long loop = operationCount; loop > 0; --loop) {
			result /= operand;
			result += 1000000;
		}
		return result;
	}


	/**
	 * 1/2=.5 ticks overhead per operation.
	 */
	static private float divFloat (final long operationCount, final float operand) {
		float result = 1.1f, inv = 1 / operand;
		for (long loop = operationCount; loop > 0; loop -= 2) {
			result /= operand;
			result /= inv;
		}
		return result;
	}


	/**
	 * 1/2=.5 ticks overhead per operation.
	 */
	static private double divDouble (final long operationCount, final double operand) {
		double result = 1.1d, inv = 1 / operand;
		for (long loop = operationCount; loop > 0; loop -= 2) {
			result /= operand;
			result /= inv;
		}
		return result;
	}


	/**
	 * 1/2=.5 ticks overhead per operation.
	 */
	static private float invFloat (final long operationCount, final float operand) {
		float result = operand;
		for (long loop = operationCount; loop > 0; loop -= 2) {
			result = 1 / result;
			result = 1 / result;
		}
		return result;
	}


	/**
	 * 1/2=.5 ticks overhead per operation.
	 */
	static private double invDouble (final long operationCount, final double operand) {
		double result = operand;
		for (long loop = operationCount; loop > 0; loop -= 2) {
			result = 1 / result;
			result = 1 / result;
		}
		return result;
	}


	/**
	 * 1/2=.5 ticks overhead per operation.
	 */
	static private double div2ADouble (final long operationCount, final double operand) {
		double result = operand;
		for (long loop = operationCount; loop > 0; loop -= 2) {
			result *= 1d / 2;
			result *= 2;
		}
		return result;
	}


	/**
	 * 1/2=.5 ticks overhead per operation.
	 */
	static private double div2BDouble (final long operationCount, final double operand) {
		double result = operand;
		for (long loop = operationCount; loop > 0; loop -= 2) {
			result /= 2;
			result /= 1d / 2;
		}
		return result;
	}


	/**
	 * 1/2=.5 ticks overhead per operation.
	 */
	static private double div3ADouble (final long operationCount, final double operand) {
		double result = operand;
		for (long loop = operationCount; loop > 0; loop -= 2) {
			result *= 1d / 3;
			result *= 3;
		}
		return result;
	}


	/**
	 * 1/2=.5 ticks overhead per operation.
	 */
	static private double div3BDouble (final long operationCount, final double operand) {
		double result = operand;
		for (long loop = operationCount; loop > 0; loop -= 2) {
			result /= 3;
			result /= 1d / 3;
		}
		return result;
	}



	//***********//
	// Functions //
	//***********//

	/**
	 * 7/2=3.5 ticks overhead per operation.
	 */
	static private double absDouble (final long operationCount) {
		double result = 0;
		for (long loop = operationCount; loop > 0; loop -= 2) {
			result += Math.abs(+loop);
			result += Math.abs(-loop);
		}
		return result;
	}


	/**
	 * 4/1=4 ticks overhead per operation.
	 */
	static private double scalbDouble (final long operationCount) {
		double result = 0;
		for (long loop = operationCount; loop > 0; --loop) {
			result += Math.scalb(loop, -10);
		}
		return result;
	}


	/**
	 * 4/1=4 ticks overhead per operation.
	 */
	static private double sqrtDouble (final long operationCount) {
		double result = 0;
		for (long loop = operationCount; loop > 0; --loop) {
			result += Math.sqrt(loop);
		}
		return result;
	}


	/**
	 * 4/1=4 ticks overhead per operation (supersymmetric FP+=FP).
	 */
	static private double logDouble (final long operationCount) {
		double result = 0, increment = 1.5 / operationCount;
		for (double operand = .5; operand <= 2; operand += increment) {
			result += Math.log(operand);
		}
		return result;
	}


	/**
	 * 4/1=4 ticks overhead per operation (supersymmetric FP+=FP).
	 */
	static private double expDouble (final long operationCount) {
		double result = 0, increment = 1.5 / operationCount;
		for (double operand = .5; operand <= 2; operand += increment) {
			result += Math.exp(operand);
		}
		return result;
	}


	/**
	 * 4/1=4 ticks overhead per operation (supersymmetric FP+=FP).
	 */
	static private double sin0Double (final long operationCount) {
		double result = 0, increment = 2 * PI / operationCount;
		for (double operand = 0; operand < 2 * PI; operand += increment) {
			result += Math.sin(operand);
		}
		return result;
	}


	/**
	 * 4/1=4 ticks overhead per operation (supersymmetric FP+=FP).
	 */
	static private double sin1Double (final long operationCount) {
		double result = 0, increment = 2 * PI / operationCount;
		for (double operand = -PI; operand < PI; operand += increment) {
			result += Math.sin(operand);
		}
		return result;
	}

	/**
	 * 4/1=4 ticks overhead per operation.
	 */
	static private double idxSinDouble (final long operationCount) {
		double result = 0;
		for (long operand = operationCount; operand > 0; --operand) {
			result += SINE_TABLE.sin((int) operand);
		}
		return result;
	}


	/**
	 * 4/1=4 ticks overhead per operation (supersymmetric FP+=FP).
	 */
	static private double approximateSin0Double (final long operationCount) {
		double result = 0, increment = 2 * PI / operationCount;
		for (double operand = 0; operand < 2 * PI; operand += increment) {
			result += SINE_TABLE.sin(operand);
		}
		return result;
	}


	/**
	 * 4/1=4 ticks overhead per operation (supersymmetric FP+=FP).
	 */
	static private double approximateSin1Double (final long operationCount) {
		double result = 0, increment = 2 * PI / operationCount;
		for (double operand = -PI; operand < PI; operand += increment) {
			result += SINE_TABLE.sin(operand);
		}
		return result;
	}


	/**
	 * 4/1=4 ticks overhead per operation (supersymmetric FP+=FP).
	 */
	static private double tanhDouble (final long operationCount) {
		double result = 0, increment = 2 * PI / operationCount;
		for (double operand = -PI; operand < PI; operand += increment) {
			result += Math.tanh(operand);
		}
		return result;
	}


	/**
	 * 7/1=7 ticks overhead per operation.
	 */
	static private double atan2Double (final long operationCount) {
		double result = 0;
		for (double loop = 0; loop < operationCount;) {
			result += Math.atan2(loop, ++loop);
		}
		return result;
	}
}