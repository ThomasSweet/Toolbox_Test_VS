package de.sb.toolbox.math;

import java.util.concurrent.ThreadLocalRandom;
import org.junit.Assert;
import org.junit.Test;


public class SineTest {

	@Test
	public void test() {
		for (int loop = 0; loop < 10000; ++loop) {
			final double x = ThreadLocalRandom.current().nextDouble() * 20 - 10;
			Assert.assertEquals(Math.sin(x), rootSine(x), 1E-12);
			Assert.assertEquals(Math.cos(x), rootCosine(x), 1E-12);
		}
	}


	static private final double rootSine(double x) {
		while (x < 0.0) x += 2.0 * Math.PI;
		while (x > 2.0 * Math.PI) x -= 2.0 * Math.PI;

		if (x <= 0.5 * Math.PI) return +Math.sin(x);
		if (x <= 1.0 * Math.PI) return +Math.sin(1.0 * Math.PI - x);
		if (x <= 1.5 * Math.PI) return -Math.sin(x - 1.0 * Math.PI);
		if (x <= 2.0 * Math.PI) return -Math.sin(2.0 * Math.PI - x);
		throw new AssertionError();
	}


	static private final double rootCosine(double x) {
		while (x < 0.0) x += 2.0 * Math.PI;
		while (x > 2.0 * Math.PI) x -= 2.0 * Math.PI;

		if (x <= 0.5 * Math.PI) return +Math.sin(0.5 * Math.PI - x);
		if (x <= 1.0 * Math.PI) return -Math.sin(x - 0.5 * Math.PI);
		if (x <= 1.5 * Math.PI) return -Math.sin(1.5 * Math.PI - x);
		if (x <= 2.0 * Math.PI) return +Math.sin(x - 1.5 * Math.PI);
		throw new AssertionError();
	}
}