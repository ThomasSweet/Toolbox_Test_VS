package de.sb.toolbox.math;

import static de.sb.toolbox.math.ComplexMath.acos;
import static de.sb.toolbox.math.ComplexMath.acosh;
import static de.sb.toolbox.math.ComplexMath.asin;
import static de.sb.toolbox.math.ComplexMath.asinh;
import static de.sb.toolbox.math.ComplexMath.atan;
import static de.sb.toolbox.math.ComplexMath.atanh;
import static de.sb.toolbox.math.ComplexMath.cos;
import static de.sb.toolbox.math.ComplexMath.cosh;
import static de.sb.toolbox.math.ComplexMath.exp;
import static de.sb.toolbox.math.ComplexMath.log;
import static de.sb.toolbox.math.ComplexMath.pow;
import static de.sb.toolbox.math.ComplexMath.root;
import static de.sb.toolbox.math.ComplexMath.sin;
import static de.sb.toolbox.math.ComplexMath.sinh;
import static de.sb.toolbox.math.ComplexMath.tan;
import static de.sb.toolbox.math.ComplexMath.tanh;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.function.UnaryOperator;
import javax.imageio.ImageIO;
import org.junit.Test;
import de.sb.toolbox.io.IOStreams;
import de.sb.toolbox.math.Complex.MutableDoublePrecision;
import de.sb.toolbox.math.Complex.MutableSinglePrecision;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;


public class ComplexTest {
	static private final double DOUBLE_PRECISION = 1E-12f;
	static private final float SINGLE_PRECISION = 1E-6f;
	static private final double DOUBLE_SQRT = Math.sqrt(2);
	static private final float SINGLE_SQRT = (float) DOUBLE_SQRT;


	@Test
	public void testAccessors() {
		testAccessorsDoublePrecision(new DoubleCartesianComplex(), DOUBLE_PRECISION);
		testAccessorsSinglePrecision(new FloatCartesianComplex(), SINGLE_PRECISION);
		testAccessorsSinglePrecision(new PrimitiveCartesian(), SINGLE_PRECISION);
	}


	@Test
	public void testUnaryOperators () {
		testUnaryOperatorsDoublePrecision(new DoubleCartesianComplex(), DOUBLE_PRECISION);
		testUnaryOperatorsSinglePrecision(new FloatCartesianComplex(), SINGLE_PRECISION);
		testUnaryOperatorsSinglePrecision(new PrimitiveCartesian(), SINGLE_PRECISION);
	}

	
	@Test
	public void testBinaryOperators () {
		testBinaryOperatorsDoublePrecision(new DoubleCartesianComplex(), DOUBLE_PRECISION);
		testBinaryOperatorsSinglePrecision(new FloatCartesianComplex(), SINGLE_PRECISION);
		testBinaryOperatorsSinglePrecision(new PrimitiveCartesian(), SINGLE_PRECISION);
	}


	@Test
	public void testFunctionsPowRoot () {
		testPowRootDoublePrecision(new DoubleCartesianComplex(), DOUBLE_PRECISION);
		testPowRootSinglePrecision(new FloatCartesianComplex(), SINGLE_PRECISION);
		testPowRootSinglePrecision(new PrimitiveCartesian(), SINGLE_PRECISION);
	}


	@Test
	public void testFunctionsExpLog () {
		testExpLogDoublePrecision(new DoubleCartesianComplex(), DOUBLE_PRECISION);
		testExpLogSinglePrecision(new FloatCartesianComplex(), SINGLE_PRECISION);
		testExpLogSinglePrecision(new PrimitiveCartesian(), SINGLE_PRECISION);
	}


	@Test
	public void testFunctionsTrigonometric () {
		testTrigonometricDoublePrecision(new DoubleCartesianComplex(), DOUBLE_PRECISION);
		testTrigonometricSinglePrecision(new FloatCartesianComplex(), SINGLE_PRECISION);
		testTrigonometricSinglePrecision(new PrimitiveCartesian(), SINGLE_PRECISION);
	}


	@Test
	public void testFunctionsHyperbolic () {
		testHyperbolicDoublePrecision(new DoubleCartesianComplex(), DOUBLE_PRECISION);
		testHyperbolicSinglePrecision(new FloatCartesianComplex(), SINGLE_PRECISION);
		testHyperbolicSinglePrecision(new PrimitiveCartesian(), SINGLE_PRECISION);
	}


	@Test
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void testFunctionWheels () throws IOException {
		final String format = "de/sb/toolbox/math/wheels/%s-10x10x100.png";

		testFunctionWheel(String.format(format, "z"),  z -> z);
		testFunctionWheel(String.format(format, "sq(z)"),  z -> pow((MutableDoublePrecision) z, 2L));
		testFunctionWheel(String.format(format, "cb(z)"),  z -> pow((MutableDoublePrecision) z, 3L));
		testFunctionWheel(String.format(format, "qa(z)"),  z -> pow((MutableDoublePrecision) z, 4L));
		testFunctionWheel(String.format(format, "inv(z)"),  z -> root((MutableDoublePrecision) z, -1));
		testFunctionWheel(String.format(format, "sqrt(z)"), z -> root((MutableDoublePrecision) z, +2));
		testFunctionWheel(String.format(format, "cbrt(z)"), z -> root((MutableDoublePrecision) z, +3));
		testFunctionWheel(String.format(format, "qart(z)"), z -> root((MutableDoublePrecision) z, +4));
		testFunctionWheel(String.format(format, "exp(z)"), z -> exp((MutableDoublePrecision) z));
		testFunctionWheel(String.format(format, "exp(+2,z)"), z -> exp(+2, (MutableDoublePrecision) z));
		testFunctionWheel(String.format(format, "exp(-2,z)"), z -> exp(-2, (MutableDoublePrecision) z));
		testFunctionWheel(String.format(format, "exp(z,z)"), z -> exp((MutableDoublePrecision) z, (MutableDoublePrecision) z));
		testFunctionWheel(String.format(format, "log(z)"), z -> log((MutableDoublePrecision) z));
		testFunctionWheel(String.format(format, "log(+2,z)"), z -> log(+2, (MutableDoublePrecision) z));
		testFunctionWheel(String.format(format, "log(-2,z)"), z -> log(-2, (MutableDoublePrecision) z));
		testFunctionWheel(String.format(format, "log(z,z)"), z -> log((MutableDoublePrecision) z, (MutableDoublePrecision) z));
		testFunctionWheel(String.format(format, "log(z+1,z)"), z -> log((MutableDoublePrecision) z.clone().add(1), (MutableDoublePrecision) z));
		testFunctionWheel(String.format(format, "sin(z)"), z -> sin((MutableDoublePrecision) z));
		testFunctionWheel(String.format(format, "cos(z)"), z -> cos((MutableDoublePrecision) z));
		testFunctionWheel(String.format(format, "tan(z)"), z -> tan((MutableDoublePrecision) z));
		testFunctionWheel(String.format(format, "asin(z)"), z -> asin((MutableDoublePrecision) z));
		testFunctionWheel(String.format(format, "acos(z)"), z -> acos((MutableDoublePrecision) z));
		testFunctionWheel(String.format(format, "atan(z)"), z -> atan((MutableDoublePrecision) z));
		testFunctionWheel(String.format(format, "sinh(z)"), z -> sinh((MutableDoublePrecision) z));
		testFunctionWheel(String.format(format, "cosh(z)"), z -> cosh((MutableDoublePrecision) z));
		testFunctionWheel(String.format(format, "tanh(z)"), z -> tanh((MutableDoublePrecision) z));
		testFunctionWheel(String.format(format, "asinh(z)"), z -> asinh((MutableDoublePrecision) z));
		testFunctionWheel(String.format(format, "acosh(z)"), z -> acosh((MutableDoublePrecision) z));
		testFunctionWheel(String.format(format, "atanh(z)"), z -> atanh((MutableDoublePrecision) z));
	}


	static private void testAccessorsSinglePrecision (final MutableSinglePrecision<?> z, final float precision) {
		assertEquals(+0, z.re(), precision);
		assertEquals(+0, z.im(), precision);
		assertTrue(z.isReal());
		assertTrue(z.isImaginary());
		assertTrue(z.isZero());
		assertFalse(z.isInfinite());
		assertFalse(z.isNaN());

		z.setCartesian(+2, +3);
		assertEquals(+2, z.re(), precision);
		assertEquals(+3, z.im(), precision);
		assertFalse(z.isReal());
		assertFalse(z.isImaginary());
		assertFalse(z.isZero());
		assertFalse(z.isInfinite());
		assertFalse(z.isNaN());

		z.setCartesian(-2, -3);
		assertEquals(-2, z.re(), precision);
		assertEquals(-3, z.im(), precision);
		assertFalse(z.isReal());
		assertFalse(z.isImaginary());
		assertFalse(z.isZero());
		assertFalse(z.isInfinite());
		assertFalse(z.isNaN());

		z.setCartesian(+1, +0);
		assertEquals(+1, z.abs(), precision);
		assertEquals(+0, z.arg(), precision);
		assertTrue(z.isReal());
		assertFalse(z.isImaginary());
		assertFalse(z.isZero());
		assertFalse(z.isInfinite());
		assertFalse(z.isNaN());

		z.setCartesian(+1, +1);
		assertEquals(+SINGLE_SQRT, z.abs(), precision);
		assertEquals((float) +Math.PI * 1/4, z.arg(), precision);
		assertFalse(z.isReal());
		assertFalse(z.isImaginary());
		assertFalse(z.isZero());
		assertFalse(z.isInfinite());
		assertFalse(z.isNaN());

		z.setCartesian(+0, +1);
		assertEquals(+1, z.abs(), precision);
		assertEquals((float) +Math.PI * 1/2, z.arg(), precision);
		assertFalse(z.isReal());
		assertTrue(z.isImaginary());
		assertFalse(z.isZero());
		assertFalse(z.isInfinite());
		assertFalse(z.isNaN());

		z.setCartesian(-1, +1);
		assertEquals(+SINGLE_SQRT, z.abs(), precision);
		assertEquals((float) +Math.PI * 3/4, z.arg(), precision);
		assertFalse(z.isReal());
		assertFalse(z.isImaginary());
		assertFalse(z.isZero());
		assertFalse(z.isInfinite());
		assertFalse(z.isNaN());

		z.setCartesian(-1, -0);
		assertEquals(+1, z.abs(), precision);
		assertEquals((float) -Math.PI * 1/1, z.arg(), precision);
		assertTrue(z.isReal());
		assertFalse(z.isImaginary());
		assertFalse(z.isZero());
		assertFalse(z.isInfinite());
		assertFalse(z.isNaN());

		z.setCartesian(-1, -1);
		assertEquals(+SINGLE_SQRT, z.abs(), precision);
		assertEquals((float) -Math.PI * 3/4, z.arg(), precision);
		assertFalse(z.isReal());
		assertFalse(z.isImaginary());
		assertFalse(z.isZero());
		assertFalse(z.isInfinite());
		assertFalse(z.isNaN());

		z.setCartesian(+0, -1);
		assertEquals(+1, z.abs(), precision);
		assertEquals((float) -Math.PI * 1/2, z.arg(), precision);
		assertFalse(z.isReal());
		assertTrue(z.isImaginary());
		assertFalse(z.isZero());
		assertFalse(z.isInfinite());
		assertFalse(z.isNaN());

		z.setCartesian(+1, -1);
		assertEquals(+SINGLE_SQRT, z.abs(), precision);
		assertEquals((float) -Math.PI * 1/4, z.arg(), precision);
		assertFalse(z.isReal());
		assertFalse(z.isImaginary());
		assertFalse(z.isZero());
		assertFalse(z.isInfinite());
		assertFalse(z.isNaN());

		z.setCartesian(+0, Float.NEGATIVE_INFINITY);
		assertFalse(z.isReal());
		assertTrue(z.isImaginary());
		assertFalse(z.isZero());
		assertTrue(z.isInfinite());
		assertFalse(z.isNaN());

		z.setCartesian(+0, Float.NaN);
		assertFalse(z.isReal());
		assertTrue(z.isImaginary());
		assertFalse(z.isZero());
		assertFalse(z.isInfinite());
		assertTrue(z.isNaN());

		z.setPolar(+1, +0);
		assertEquals(+1, z.re(), precision);
		assertEquals(+0, z.im(), precision);

		z.setPolar(+1, (float) +Math.PI * 1/2);
		assertEquals(+0, z.re(), precision);
		assertEquals(+1, z.im(), precision);

		z.setPolar(+1, (float) -Math.PI);
		assertEquals(-1, z.re(), precision);
		assertEquals(+0, z.im(), precision);

		z.setPolar(+1, (float) -Math.PI * 1/2);
		assertEquals(+0, z.re(), precision);
		assertEquals(-1, z.im(), precision);	
	}


	static private void testAccessorsDoublePrecision (final MutableDoublePrecision<?> z, final double precision) {
		assertEquals(+0, z.re(), precision);
		assertEquals(+0, z.im(), precision);
		assertTrue(z.isReal());
		assertTrue(z.isImaginary());
		assertTrue(z.isZero());
		assertFalse(z.isInfinite());
		assertFalse(z.isNaN());

		z.setCartesian(+2, +3);
		assertEquals(+2, z.re(), precision);
		assertEquals(+3, z.im(), precision);
		assertFalse(z.isReal());
		assertFalse(z.isImaginary());
		assertFalse(z.isZero());
		assertFalse(z.isInfinite());
		assertFalse(z.isNaN());

		z.setCartesian(-2, -3);
		assertEquals(-2, z.re(), precision);
		assertEquals(-3, z.im(), precision);
		assertFalse(z.isReal());
		assertFalse(z.isImaginary());
		assertFalse(z.isZero());
		assertFalse(z.isInfinite());
		assertFalse(z.isNaN());

		z.setCartesian(+1, +0);
		assertEquals(+1, z.abs(), precision);
		assertEquals(+0, z.arg(), precision);
		assertTrue(z.isReal());
		assertFalse(z.isImaginary());
		assertFalse(z.isZero());
		assertFalse(z.isInfinite());
		assertFalse(z.isNaN());

		z.setCartesian(+1, +1);
		assertEquals(+DOUBLE_SQRT, z.abs(), precision);
		assertEquals(+Math.PI * 1/4, z.arg(), precision);
		assertFalse(z.isReal());
		assertFalse(z.isImaginary());
		assertFalse(z.isZero());
		assertFalse(z.isInfinite());
		assertFalse(z.isNaN());

		z.setCartesian(+0, +1);
		assertEquals(+1, z.abs(), precision);
		assertEquals(+Math.PI * 1/2, z.arg(), precision);
		assertFalse(z.isReal());
		assertTrue(z.isImaginary());
		assertFalse(z.isZero());
		assertFalse(z.isInfinite());
		assertFalse(z.isNaN());

		z.setCartesian(-1, +1);
		assertEquals(+DOUBLE_SQRT, z.abs(), precision);
		assertEquals(+Math.PI * 3/4, z.arg(), precision);
		assertFalse(z.isReal());
		assertFalse(z.isImaginary());
		assertFalse(z.isZero());
		assertFalse(z.isInfinite());
		assertFalse(z.isNaN());

		z.setCartesian(-1, -0);
		assertEquals(+1, z.abs(), precision);
		assertEquals(-Math.PI * 1/1, z.arg(), precision);
		assertTrue(z.isReal());
		assertFalse(z.isImaginary());
		assertFalse(z.isZero());
		assertFalse(z.isInfinite());
		assertFalse(z.isNaN());

		z.setCartesian(-1, -1);
		assertEquals(+DOUBLE_SQRT, z.abs(), precision);
		assertEquals(-Math.PI * 3/4, z.arg(), precision);
		assertFalse(z.isReal());
		assertFalse(z.isImaginary());
		assertFalse(z.isZero());
		assertFalse(z.isInfinite());
		assertFalse(z.isNaN());

		z.setCartesian(+0, -1);
		assertEquals(+1, z.abs(), precision);
		assertEquals(-Math.PI * 1/2, z.arg(), precision);
		assertFalse(z.isReal());
		assertTrue(z.isImaginary());
		assertFalse(z.isZero());
		assertFalse(z.isInfinite());
		assertFalse(z.isNaN());

		z.setCartesian(+1, -1);
		assertEquals(+DOUBLE_SQRT, z.abs(), precision);
		assertEquals(-Math.PI * 1/4, z.arg(), precision);
		assertFalse(z.isReal());
		assertFalse(z.isImaginary());
		assertFalse(z.isZero());
		assertFalse(z.isInfinite());
		assertFalse(z.isNaN());

		z.setCartesian(+0, Double.NEGATIVE_INFINITY);
		assertFalse(z.isReal());
		assertTrue(z.isImaginary());
		assertFalse(z.isZero());
		assertTrue(z.isInfinite());
		assertFalse(z.isNaN());

		z.setCartesian(+0, Double.NaN);
		assertFalse(z.isReal());
		assertTrue(z.isImaginary());
		assertFalse(z.isZero());
		assertFalse(z.isInfinite());
		assertTrue(z.isNaN());

		z.setPolar(+1, +0);
		assertEquals(+1, z.re(), precision);
		assertEquals(+0, z.im(), precision);

		z.setPolar(+1, +Math.PI * 1/2);
		assertEquals(+0, z.re(), precision);
		assertEquals(+1, z.im(), precision);

		z.setPolar(+1, -Math.PI);
		assertEquals(-1, z.re(), precision);
		assertEquals(+0, z.im(), precision);

		z.setPolar(+1, -Math.PI * 1/2);
		assertEquals(+0, z.re(), precision);
		assertEquals(-1, z.im(), precision);	
	}


	static private void testUnaryOperatorsSinglePrecision (final MutableSinglePrecision<?> z, final float precision) {
		z.setCartesian(-2, -3);
		z.conj();
		assertEquals(-2, z.re(), precision);
		assertEquals(+3, z.im(), precision);

		z.neg();
		assertEquals(+2, z.re(), precision);
		assertEquals(-3, z.im(), precision);

		z.imul();
		assertEquals(+3, z.re(), precision);
		assertEquals(+2, z.im(), precision);

		z.idiv();
		assertEquals(+2, z.re(), precision);
		assertEquals(-3, z.im(), precision);

		z.sq();
		assertEquals(-5, z.re(), precision);
		assertEquals(-12, z.im(), precision);

		z.sqrt();
		assertEquals(+2, z.re(), precision);
		assertEquals(-3, z.im(), precision);

		z.cb();
		assertEquals(-46, z.re(), precision);
		assertEquals(-9, z.im(), precision);

		z.cbrt();
		assertEquals(+2, z.re(), precision);
		assertEquals(-3, z.im(), precision);

		z.inv();
		assertEquals(+0.15384615384615385f, z.re(), precision);
		assertEquals(+0.23076923076923073f, z.im(), precision);
	}


	static private void testUnaryOperatorsDoublePrecision (final MutableDoublePrecision<?> z, final double precision) {
		z.setCartesian(-2, -3);
		z.conj();
		assertEquals(-2, z.re(), precision);
		assertEquals(+3, z.im(), precision);

		z.neg();
		assertEquals(+2, z.re(), precision);
		assertEquals(-3, z.im(), precision);

		z.imul();
		assertEquals(+3, z.re(), precision);
		assertEquals(+2, z.im(), precision);

		z.idiv();
		assertEquals(+2, z.re(), precision);
		assertEquals(-3, z.im(), precision);

		z.sq();
		assertEquals(-5, z.re(), precision);
		assertEquals(-12, z.im(), precision);

		z.sqrt();
		assertEquals(+2, z.re(), precision);
		assertEquals(-3, z.im(), precision);

		z.cb();
		assertEquals(-46, z.re(), precision);
		assertEquals(-9, z.im(), precision);

		z.cbrt();
		assertEquals(+2, z.re(), precision);
		assertEquals(-3, z.im(), precision);

		z.inv();
		assertEquals(+0.15384615384615385d, z.re(), precision);
		assertEquals(+0.23076923076923073d, z.im(), precision);
	}


	static private <T extends MutableSinglePrecision<T>> void testBinaryOperatorsSinglePrecision (final T z, final float precision) {
		final T y = z.clone();

		y.setCartesian(-2, +3);
		z.add(y);
		assertEquals(-2, z.re(), precision);
		assertEquals(+3, z.im(), precision);

		z.add(-1);
		assertEquals(-3, z.re(), precision);
		assertEquals(+3, z.im(), precision);

		y.setCartesian(+2, -3);
		z.sub(y);
		assertEquals(-5, z.re(), precision);
		assertEquals(+6, z.im(), precision);

		z.sub(+1);
		assertEquals(-6, z.re(), precision);
		assertEquals(+6, z.im(), precision);

		y.setCartesian(-2, +3);
		z.mul(y);
		assertEquals(-6, z.re(), precision);
		assertEquals(-30, z.im(), precision);

		z.mul(-2);
		assertEquals(+12, z.re(), precision);
		assertEquals(+60, z.im(), precision);

		z.div(-2);
		assertEquals(-6, z.re(), precision);
		assertEquals(-30, z.im(), precision);

		z.div(y);
		assertEquals(-6, z.re(), precision);
		assertEquals(+6, z.im(), precision);
	}


	static private <T extends MutableDoublePrecision<T>> void testBinaryOperatorsDoublePrecision (final T z, final double precision) {
		final T y = z.clone();

		y.setCartesian(-2, +3);
		z.add(y);
		assertEquals(-2, z.re(), precision);
		assertEquals(+3, z.im(), precision);

		z.add(-1);
		assertEquals(-3, z.re(), precision);
		assertEquals(+3, z.im(), precision);

		y.setCartesian(+2, -3);
		z.sub(y);
		assertEquals(-5, z.re(), precision);
		assertEquals(+6, z.im(), precision);

		z.sub(+1);
		assertEquals(-6, z.re(), precision);
		assertEquals(+6, z.im(), precision);

		y.setCartesian(-2, +3);
		z.mul(y);
		assertEquals(-6, z.re(), precision);
		assertEquals(-30, z.im(), precision);

		z.mul(-2);
		assertEquals(+12, z.re(), precision);
		assertEquals(+60, z.im(), precision);

		z.div(-2);
		assertEquals(-6, z.re(), precision);
		assertEquals(-30, z.im(), precision);

		z.div(y);
		assertEquals(-6, z.re(), precision);
		assertEquals(+6, z.im(), precision);
	}


	static private <T extends MutableSinglePrecision<T>> void testPowRootSinglePrecision (final T z, final float precision) {
		T r, s;

		r = root(z, 0);
		assertTrue(r.isNaN());
		r = pow(z, Float.POSITIVE_INFINITY);
		assertTrue(r.isNaN());
		r = pow(z, Float.NEGATIVE_INFINITY);
		assertTrue(r.isNaN());
		r = pow(z, 0);
		assertEquals(+1, r.re(), 0);
		assertEquals(+0, r.im(), 0);

		z.setCartesian(+2, -3);
		r = root(z, -4); s = z.clone().inv().sqrt().sqrt();
		assertEquals(s.re(), r.re(), precision);
		assertEquals(s.im(), r.im(), precision);
		r = root(z, -3); s = z.clone().inv().cbrt();
		assertEquals(s.re(), r.re(), precision);
		assertEquals(s.im(), r.im(), precision);
		r = root(z, -2); s = z.clone().inv().sqrt();
		assertEquals(s.re(), r.re(), precision);
		assertEquals(s.im(), r.im(), precision);
		r = root(z, -1); s = z.clone().inv();
		assertEquals(s.re(), r.re(), precision);
		assertEquals(s.im(), r.im(), precision);
		r = root(z, 0);
		assertTrue(r.isInfinite());
		r = root(z, +1);
		assertEquals(z.re(), r.re(), precision);
		assertEquals(z.im(), r.im(), precision);
		r = root(z, +2); s = z.clone().sqrt();
		assertEquals(s.re(), r.re(), precision);
		assertEquals(s.im(), r.im(), precision);
		r = root(z, +3); s = z.clone().cbrt();
		assertEquals(s.re(), r.re(), precision);
		assertEquals(s.im(), r.im(), precision);
		r = root(z, +4); s = z.clone().sqrt().sqrt();
		assertEquals(s.re(), r.re(), precision);
		assertEquals(s.im(), r.im(), precision);

		z.setCartesian(+2, -3);
		r = pow(z, -4); s = z.clone().inv().sq().sq();
		assertEquals(s.re(), r.re(), precision);
		assertEquals(s.im(), r.im(), precision);
		r = pow(z, -3); s = z.clone().inv().cb();
		assertEquals(s.re(), r.re(), precision);
		assertEquals(s.im(), r.im(), precision);
		r = pow(z, -2); s = z.clone().inv().sq();
		assertEquals(s.re(), r.re(), precision);
		assertEquals(s.im(), r.im(), precision);
		r = pow(z, -1); s = z.clone().inv();
		assertEquals(s.re(), r.re(), precision);
		assertEquals(s.im(), r.im(), precision);
		r = pow(z, 0);
		assertEquals(+1, r.re(), 0);
		assertEquals(+0, r.im(), 0);
		r = pow(z, +1);
		assertEquals(z.re(), r.re(), precision);
		assertEquals(z.im(), r.im(), precision);
		r = pow(z, +2); s = z.clone().sq();
		assertEquals(s.re(), r.re(), precision);
		assertEquals(s.im(), r.im(), precision);
		r = pow(z, +3); s = z.clone().cb();
		assertEquals(s.re(), r.re(), precision);
		assertEquals(s.im(), r.im(), precision);
		r = pow(z, +4); s = z.clone().sq().sq();
		assertEquals(s.re(), r.re(), precision * 100);
		assertEquals(s.im(), r.im(), precision * 100);
	}


	static private <T extends MutableDoublePrecision<T>> void testPowRootDoublePrecision (final T z, final double precision) {
		T r, s;

		r = root(z, 0);
		assertTrue(r.isNaN());
		r = pow(z, Double.POSITIVE_INFINITY);
		assertTrue(r.isNaN());
		r = pow(z, Double.NEGATIVE_INFINITY);
		assertTrue(r.isNaN());
		r = pow(z, 0);
		assertEquals(+1, r.re(), 0);
		assertEquals(+0, r.im(), 0);

		z.setCartesian(+2, -3);
		r = root(z, -4); s = z.clone().inv().sqrt().sqrt();
		assertEquals(s.re(), r.re(), precision);
		assertEquals(s.im(), r.im(), precision);
		r = root(z, -3); s = z.clone().inv().cbrt();
		assertEquals(s.re(), r.re(), precision);
		assertEquals(s.im(), r.im(), precision);
		r = root(z, -2); s = z.clone().inv().sqrt();
		assertEquals(s.re(), r.re(), precision);
		assertEquals(s.im(), r.im(), precision);
		r = root(z, -1); s = z.clone().inv();
		assertEquals(s.re(), r.re(), precision);
		assertEquals(s.im(), r.im(), precision);
		r = root(z, 0);
		assertTrue(r.isInfinite());
		r = root(z, +1);
		assertEquals(z.re(), r.re(), precision);
		assertEquals(z.im(), r.im(), precision);
		r = root(z, +2); s = z.clone().sqrt();
		assertEquals(s.re(), r.re(), precision);
		assertEquals(s.im(), r.im(), precision);
		r = root(z, +3); s = z.clone().cbrt();
		assertEquals(s.re(), r.re(), precision);
		assertEquals(s.im(), r.im(), precision);
		r = root(z, +4); s = z.clone().sqrt().sqrt();
		assertEquals(s.re(), r.re(), precision);
		assertEquals(s.im(), r.im(), precision);

		z.setCartesian(+2, -3);
		r = pow(z, -4); s = z.clone().inv().sq().sq();
		assertEquals(s.re(), r.re(), precision);
		assertEquals(s.im(), r.im(), precision);
		r = pow(z, -3); s = z.clone().inv().cb();
		assertEquals(s.re(), r.re(), precision);
		assertEquals(s.im(), r.im(), precision);
		r = pow(z, -2); s = z.clone().inv().sq();
		assertEquals(s.re(), r.re(), precision);
		assertEquals(s.im(), r.im(), precision);
		r = pow(z, -1); s = z.clone().inv();
		assertEquals(s.re(), r.re(), precision);
		assertEquals(s.im(), r.im(), precision);
		r = pow(z, 0);
		assertEquals(+1, r.re(), 0);
		assertEquals(+0, r.im(), 0);
		r = pow(z, +1);
		assertEquals(z.re(), r.re(), precision);
		assertEquals(z.im(), r.im(), precision);
		r = pow(z, +2); s = z.clone().sq();
		assertEquals(s.re(), r.re(), precision);
		assertEquals(s.im(), r.im(), precision);
		r = pow(z, +3); s = z.clone().cb();
		assertEquals(s.re(), r.re(), precision);
		assertEquals(s.im(), r.im(), precision);
		r = pow(z, +4); s = z.clone().sq().sq();
		assertEquals(s.re(), r.re(), precision);
		assertEquals(s.im(), r.im(), precision);
	}


	static private <T extends MutableSinglePrecision<T>> void testExpLogSinglePrecision (final T z, final float precision) {
		T r;

		r = log(z);
		assertTrue(r.isInfinite());
		assertFalse(r.isNaN());

		z.setCartesian(+2, -3);
		r = log(exp(z));
		assertEquals(z.re(), r.re(), precision);
		assertEquals(z.im(), r.im(), precision);

		r = log(-2, exp(-2, z));
		assertEquals(+0.09284064709081637, r.re(), precision);
		assertEquals(-2.57921275158413940, r.im(), precision);
		r = log(-1, exp(-1, z));
		assertEquals(+0, r.re(), precision);
		assertEquals(-3, r.im(), precision);
		r = log(+0, exp(+0, z));
		assertEquals(+1, r.re(), precision);
		assertEquals(+0, r.im(), precision);
		r = log(+1, exp(+1, z));
		assertEquals(+1, r.re(), precision);
		assertEquals(+0, r.im(), precision);
		r = log(+2, exp(+2, z));
		assertEquals(z.re(), r.re(), precision);
		assertEquals(z.im(), r.im(), precision);

		r = log(z, z);
		assertEquals(1.0, r.re(), precision);
		assertEquals(0.0, r.im(), precision);
		r = exp(z, z);
		assertEquals(0.60756666473147850, r.re(), precision);
		assertEquals(0.30875601809790204, r.im(), precision);
	}


	static private <T extends MutableDoublePrecision<T>> void testExpLogDoublePrecision (final T z, final double precision) {
		T r;

		r = log(z);
		assertTrue(r.isInfinite());
		assertFalse(r.isNaN());

		z.setCartesian(+2, -3);
		r = log(exp(z));
		assertEquals(z.re(), r.re(), precision);
		assertEquals(z.im(), r.im(), precision);

		r = log(-2, exp(-2, z));
		assertEquals(+0.09284064709081637, r.re(), precision);
		assertEquals(-2.57921275158413940, r.im(), precision);
		r = log(-1, exp(-1, z));
		assertEquals(+0, r.re(), precision);
		assertEquals(-3, r.im(), precision);
		r = log(+0, exp(+0, z));
		assertEquals(+1, r.re(), precision);
		assertEquals(+0, r.im(), precision);
		r = log(+1, exp(+1, z));
		assertEquals(+1, r.re(), precision);
		assertEquals(+0, r.im(), precision);
		r = log(+2, exp(+2, z));
		assertEquals(z.re(), r.re(), precision);
		assertEquals(z.im(), r.im(), precision);

		r = log(z, z);
		assertEquals(1.0, r.re(), precision);
		assertEquals(0.0, r.im(), precision);
		r = exp(z, z);
		assertEquals(0.60756666473147850, r.re(), precision);
		assertEquals(0.30875601809790204, r.im(), precision);
	}


	static private <T extends MutableSinglePrecision<T>> void testTrigonometricSinglePrecision (final T z, final float precision) {
		T r,s;

		r = sin(z);
		assertEquals(+0, r.re(), precision);
		assertEquals(+0, r.im(), precision);
		r = cos(z);
		assertEquals(+1, r.re(), precision);
		assertEquals(+0, r.im(), precision);
		r = tan(z);
		assertEquals(+0, r.re(), precision);
		assertEquals(+0, r.im(), precision);	

		z.setCartesian((float) Math.PI, 0);
		r = sin(z);
		assertEquals(+0, r.re(), precision);
		assertEquals(+0, r.im(), precision);
		r = cos(z);
		assertEquals(-1, r.re(), precision);
		assertEquals(+0, r.im(), precision);
		r = tan(z);
		assertEquals(+0, r.re(), precision);
		assertEquals(+0, r.im(), precision);	
		r = asin(sin(z));
		assertEquals(+0, r.re(), precision);
		assertEquals(+0, r.im(), precision);
		r = acos(cos(z));
		assertEquals((float) -Math.PI, r.re(), precision);
		assertEquals(+0, r.im(), precision);
		r = atan(tan(z));
		assertEquals(+0, r.re(), precision);
		assertEquals(+0, r.im(), precision);

		r = asin(sin(z.setCartesian(0, 0)));
		assertEquals(0, r.re(), precision*10);
		assertEquals(0, r.im(), precision*10);
		r = asin(sin(z.setCartesian(+.2f, +.3f)));
		assertEquals(+.2f, r.re(), precision*10);
		assertEquals(+.3f, r.im(), precision*10);
		r = asin(sin(z.setCartesian(+.2f, -.3f)));
		assertEquals(+.2f, r.re(), precision*10);
		assertEquals(-.3f, r.im(), precision*10);
		r = asin(sin(z.setCartesian(-.2f, +.3f)));
		assertEquals(-.2f, r.re(), precision*10);
		assertEquals(+.3f, r.im(), precision*10);
		r = asin(sin(z.setCartesian(-.2f, -.3f)));
		assertEquals(-.2f, r.re(), precision*10);
		assertEquals(-.3f, r.im(), precision*10);

		r = acos(cos(z.setCartesian(0, 0)));
		assertEquals(0, r.re(), precision*10);
		assertEquals(0, r.im(), precision*10);
		r = acos(cos(z.setCartesian(+.2f, +.3f)));
		assertEquals(+.2f, r.re(), precision*10);
		assertEquals(+.3f, r.im(), precision*10);
		r = acos(cos(z.setCartesian(+.2f, -.3f)));
		assertEquals(+.2f, r.re(), precision*10);
		assertEquals(-.3f, r.im(), precision*10);
		r = acos(cos(z.setCartesian(-.2f, +.3f)));	// cos(-z) = cos(+z)
		assertEquals(+.2f, r.re(), precision*10);
		assertEquals(-.3f, r.im(), precision*10);
		r = acos(cos(z.setCartesian(-.2f, -.3f)));	// cos(-z) = cos(+z)
		assertEquals(+.2f, r.re(), precision*10);
		assertEquals(+.3f, r.im(), precision*10);

		r = atan(tan(z.setCartesian(0, 0)));
		assertEquals(0, r.re(), precision*10);
		assertEquals(0, r.im(), precision*10);
		r = atan(tan(z.setCartesian(+.2f, +.3f)));
		assertEquals(+.2f, r.re(), precision*10);
		assertEquals(+.3f, r.im(), precision*10);
		r = atan(tan(z.setCartesian(+.2f, -.3f)));
		assertEquals(+.2f, r.re(), precision*10);
		assertEquals(-.3f, r.im(), precision*10);
		r = atan(tan(z.setCartesian(-.2f, +.3f)));
		assertEquals(-.2f, r.re(), precision*10);
		assertEquals(+.3f, r.im(), precision*10);
		r = atan(tan(z.setCartesian(-.2f, -.3f)));
		assertEquals(-.2f, r.re(), precision*10);
		assertEquals(-.3f, r.im(), precision*10);

		z.setCartesian(1 / (float) Math.sqrt(2), 0);
		s = acos(z);
		r = asin(z);
		assertEquals(s.re(), r.re(), precision);
		assertEquals(s.im(), r.im(), precision);
		r = atan(z.setCartesian(0, 0));
		assertEquals(0, r.re(), precision);
		assertEquals(0, r.im(), precision);
		r = atan(z.setCartesian(+1, 0));
		assertEquals((float) +Math.PI / 4, r.re(), precision);
		assertEquals(0, r.im(), precision);
		r = atan(z.setCartesian(-1, 0));
		assertEquals((float) -Math.PI / 4, r.re(), precision);
		assertEquals(0, r.im(), precision);
		r = atan(tan(z.setCartesian(+.5f, 0)));
		assertEquals(+.5f, r.re(), precision);
		assertEquals(0, r.im(), precision);
		r = atan(tan(z.setCartesian(-.5f, 0)));
		assertEquals(-.5f, r.re(), precision);
		assertEquals(0, r.im(), precision);
	}


	static private <T extends MutableDoublePrecision<T>> void testTrigonometricDoublePrecision (final T z, final double precision) {
		T r,s;

		r = sin(z);
		assertEquals(+0, r.re(), precision);
		assertEquals(+0, r.im(), precision);
		r = cos(z);
		assertEquals(+1, r.re(), precision);
		assertEquals(+0, r.im(), precision);
		r = tan(z);
		assertEquals(+0, r.re(), precision);
		assertEquals(+0, r.im(), precision);	

		z.setCartesian(Math.PI, 0);
		r = sin(z);
		assertEquals(+0, r.re(), precision);
		assertEquals(+0, r.im(), precision);
		r = cos(z);
		assertEquals(-1, r.re(), precision);
		assertEquals(+0, r.im(), precision);
		r = tan(z);
		assertEquals(+0, r.re(), precision);
		assertEquals(+0, r.im(), precision);	
		r = asin(sin(z));
		assertEquals(+0, r.re(), precision);
		assertEquals(+0, r.im(), precision);
		r = acos(cos(z));
		assertEquals(-Math.PI, r.re(), precision);
		assertEquals(+0, r.im(), precision);
		r = atan(tan(z));
		assertEquals(+0, r.re(), precision);
		assertEquals(+0, r.im(), precision);

		r = asin(sin(z.setCartesian(0, 0)));
		assertEquals(0, r.re(), precision*10);
		assertEquals(0, r.im(), precision*10);
		r = asin(sin(z.setCartesian(+.2d, +.3d)));
		assertEquals(+.2d, r.re(), precision*10);
		assertEquals(+.3d, r.im(), precision*10);
		r = asin(sin(z.setCartesian(+.2d, -.3d)));
		assertEquals(+.2d, r.re(), precision*10);
		assertEquals(-.3d, r.im(), precision*10);
		r = asin(sin(z.setCartesian(-.2d, +.3d)));
		assertEquals(-.2d, r.re(), precision*10);
		assertEquals(+.3d, r.im(), precision*10);
		r = asin(sin(z.setCartesian(-.2d, -.3d)));
		assertEquals(-.2d, r.re(), precision*10);
		assertEquals(-.3d, r.im(), precision*10);

		r = acos(cos(z.setCartesian(0, 0)));
		assertEquals(0, r.re(), precision*10);
		assertEquals(0, r.im(), precision*10);
		r = acos(cos(z.setCartesian(+.2d, +.3d)));
		assertEquals(+.2d, r.re(), precision*10);
		assertEquals(+.3d, r.im(), precision*10);
		r = acos(cos(z.setCartesian(+.2d, -.3d)));
		assertEquals(+.2d, r.re(), precision*10);
		assertEquals(-.3d, r.im(), precision*10);
		r = acos(cos(z.setCartesian(-.2d, +.3d)));	// cos(-z) = cos(+z)
		assertEquals(+.2d, r.re(), precision*10);
		assertEquals(-.3d, r.im(), precision*10);
		r = acos(cos(z.setCartesian(-.2d, -.3d)));	// cos(-z) = cos(+z)
		assertEquals(+.2d, r.re(), precision*10);
		assertEquals(+.3d, r.im(), precision*10);

		r = atan(tan(z.setCartesian(0, 0)));
		assertEquals(0, r.re(), precision*10);
		assertEquals(0, r.im(), precision*10);
		r = atan(tan(z.setCartesian(+.2d, +.3d)));
		assertEquals(+.2d, r.re(), precision*10);
		assertEquals(+.3d, r.im(), precision*10);
		r = atan(tan(z.setCartesian(+.2d, -.3d)));
		assertEquals(+.2d, r.re(), precision*10);
		assertEquals(-.3d, r.im(), precision*10);
		r = atan(tan(z.setCartesian(-.2d, +.3d)));
		assertEquals(-.2d, r.re(), precision*10);
		assertEquals(+.3d, r.im(), precision*10);
		r = atan(tan(z.setCartesian(-.2d, -.3d)));
		assertEquals(-.2d, r.re(), precision*10);
		assertEquals(-.3d, r.im(), precision*10);

		z.setCartesian(1 / Math.sqrt(2), 0);
		s = acos(z);
		r = asin(z);
		assertEquals(s.re(), r.re(), precision);
		assertEquals(s.im(), r.im(), precision);
		r = atan(z.setCartesian(0, 0));
		assertEquals(0, r.re(), precision);
		assertEquals(0, r.im(), precision);
		r = atan(z.setCartesian(+1, 0));
		assertEquals(+Math.PI / 4, r.re(), precision);
		assertEquals(0, r.im(), precision);
		r = atan(z.setCartesian(-1, 0));
		assertEquals(-Math.PI / 4, r.re(), precision);
		assertEquals(0, r.im(), precision);
		r = atan(tan(z.setCartesian(+.5d, 0)));
		assertEquals(+.5d, r.re(), precision);
		assertEquals(0, r.im(), precision);
		r = atan(tan(z.setCartesian(-.5d, 0)));
		assertEquals(-.5d, r.re(), precision);
		assertEquals(0, r.im(), precision);
	}


	static private <T extends MutableSinglePrecision<T>> void testHyperbolicSinglePrecision (final T z, final double precision) {
		T r,s;

		r = sinh(z);
		assertEquals(+0, r.re(), precision);
		assertEquals(+0, r.im(), precision);
		r = cosh(z);
		assertEquals(+1, r.re(), precision);
		assertEquals(+0, r.im(), precision);
		r = tanh(z);
		assertEquals(+0, r.re(), precision);
		assertEquals(+0, r.im(), precision);

		z.setCartesian(0, 0);
		r = tanh(z); s = sinh(z).div(cosh(z));
		assertEquals(s.re(), r.re(), precision);
		assertEquals(s.im(), r.im(), precision);
		z.setCartesian(+.2f, +.3f);
		r = tanh(z); s = sinh(z).div(cosh(z));
		assertEquals(s.re(), r.re(), precision);
		assertEquals(s.im(), r.im(), precision);
		z.setCartesian(+.2f, -.3f);
		r = tanh(z); s = sinh(z).div(cosh(z));
		assertEquals(s.re(), r.re(), precision);
		assertEquals(s.im(), r.im(), precision);
		z.setCartesian(-.2f, +.3f);
		r = tanh(z); s = sinh(z).div(cosh(z));
		assertEquals(s.re(), r.re(), precision);
		assertEquals(s.im(), r.im(), precision);
		z.setCartesian(-.2f, -.3f);
		r = tanh(z); s = sinh(z).div(cosh(z));
		assertEquals(s.re(), r.re(), precision);
		assertEquals(s.im(), r.im(), precision);

		r = asinh(sinh(z.setCartesian(0, 0)));
		assertEquals(0, r.re(), precision);
		assertEquals(0, r.im(), precision);
		r = asinh(sinh(z.setCartesian(+.2f, +.3f)));
		assertEquals(+.2f, r.re(), precision);
		assertEquals(+.3f, r.im(), precision);
		r = asinh(sinh(z.setCartesian(+.2f, -.3f)));
		assertEquals(+.2f, r.re(), precision);
		assertEquals(-.3f, r.im(), precision);
		r = asinh(sinh(z.setCartesian(-.2f, +.3f)));
		assertEquals(-.2f, r.re(), precision);
		assertEquals(+.3f, r.im(), precision);
		r = asinh(sinh(z.setCartesian(-.2f, -.3f)));
		assertEquals(-.2f, r.re(), precision);
		assertEquals(-.3f, r.im(), precision);

		r = acosh(cosh(z.setCartesian(0, 0)));
		assertEquals(0, r.re(), precision);
		assertEquals(0, r.im(), precision);
		r = acosh(cosh(z.setCartesian(+.2f, +.3f)));
		assertEquals(+.2f, r.re(), precision);
		assertEquals(+.3f, r.im(), precision);
		r = acosh(cosh(z.setCartesian(+.2f, -.3f)));
		assertEquals(+.2f, r.re(), precision);
		assertEquals(-.3f, r.im(), precision);
		r = acosh(cosh(z.setCartesian(-.2f, +.3f)));	// cosh(-z) = cosh(+z)
		assertEquals(+.2f, r.re(), precision);
		assertEquals(-.3f, r.im(), precision);
		r = acosh(cosh(z.setCartesian(-.2f, -.3f)));	// cosh(-z) = cosh(+z)
		assertEquals(+.2f, r.re(), precision);
		assertEquals(+.3f, r.im(), precision);

		r = atanh(tanh(z.setCartesian(0, 0)));
		assertEquals(0, r.re(), precision);
		assertEquals(0, r.im(), precision);
		r = atanh(tanh(z.setCartesian(+.2f, +.3f)));
		assertEquals(+.2f, r.re(), precision);
		assertEquals(+.3f, r.im(), precision);
		r = atanh(tanh(z.setCartesian(+.2f, -.3f)));
		assertEquals(+.2f, r.re(), precision);
		assertEquals(-.3f, r.im(), precision);
		r = atanh(tanh(z.setCartesian(-.2f, +.3f)));
		assertEquals(-.2f, r.re(), precision);
		assertEquals(+.3f, r.im(), precision);
		r = atanh(tanh(z.setCartesian(-.2f, -.3f)));
		assertEquals(-.2f, r.re(), precision);
		assertEquals(-.3f, r.im(), precision);
	}


	static private <T extends MutableDoublePrecision<T>> void testHyperbolicDoublePrecision (final T z, final double precision) {
		T r,s;

		r = sinh(z);
		assertEquals(+0, r.re(), precision);
		assertEquals(+0, r.im(), precision);
		r = cosh(z);
		assertEquals(+1, r.re(), precision);
		assertEquals(+0, r.im(), precision);
		r = tanh(z);
		assertEquals(+0, r.re(), precision);
		assertEquals(+0, r.im(), precision);

		z.setCartesian(0, 0);
		r = tanh(z); s = sinh(z).div(cosh(z));
		assertEquals(s.re(), r.re(), precision);
		assertEquals(s.im(), r.im(), precision);
		z.setCartesian(+.2d, +.3d);
		r = tanh(z); s = sinh(z).div(cosh(z));
		assertEquals(s.re(), r.re(), precision);
		assertEquals(s.im(), r.im(), precision);
		z.setCartesian(+.2d, -.3d);
		r = tanh(z); s = sinh(z).div(cosh(z));
		assertEquals(s.re(), r.re(), precision);
		assertEquals(s.im(), r.im(), precision);
		z.setCartesian(-.2d, +.3d);
		r = tanh(z); s = sinh(z).div(cosh(z));
		assertEquals(s.re(), r.re(), precision);
		assertEquals(s.im(), r.im(), precision);
		z.setCartesian(-.2d, -.3d);
		r = tanh(z); s = sinh(z).div(cosh(z));
		assertEquals(s.re(), r.re(), precision);
		assertEquals(s.im(), r.im(), precision);

		r = asinh(sinh(z.setCartesian(0, 0)));
		assertEquals(0, r.re(), precision);
		assertEquals(0, r.im(), precision);
		r = asinh(sinh(z.setCartesian(+.2d, +.3d)));
		assertEquals(+.2d, r.re(), precision);
		assertEquals(+.3d, r.im(), precision);
		r = asinh(sinh(z.setCartesian(+.2d, -.3d)));
		assertEquals(+.2d, r.re(), precision);
		assertEquals(-.3d, r.im(), precision);
		r = asinh(sinh(z.setCartesian(-.2d, +.3d)));
		assertEquals(-.2d, r.re(), precision);
		assertEquals(+.3d, r.im(), precision);
		r = asinh(sinh(z.setCartesian(-.2d, -.3d)));
		assertEquals(-.2d, r.re(), precision);
		assertEquals(-.3d, r.im(), precision);

		r = acosh(cosh(z.setCartesian(0, 0)));
		assertEquals(0, r.re(), precision);
		assertEquals(0, r.im(), precision);
		r = acosh(cosh(z.setCartesian(+.2d, +.3d)));
		assertEquals(+.2d, r.re(), precision);
		assertEquals(+.3d, r.im(), precision);
		r = acosh(cosh(z.setCartesian(+.2d, -.3d)));
		assertEquals(+.2d, r.re(), precision);
		assertEquals(-.3d, r.im(), precision);
		r = acosh(cosh(z.setCartesian(-.2d, +.3d)));	// cosh(-z) = cosh(+z)
		assertEquals(+.2d, r.re(), precision);
		assertEquals(-.3d, r.im(), precision);
		r = acosh(cosh(z.setCartesian(-.2d, -.3d)));	// cosh(-z) = cosh(+z)
		assertEquals(+.2d, r.re(), precision);
		assertEquals(+.3d, r.im(), precision);

		r = atanh(tanh(z.setCartesian(0, 0)));
		assertEquals(0, r.re(), precision);
		assertEquals(0, r.im(), precision);
		r = atanh(tanh(z.setCartesian(+.2d, +.3d)));
		assertEquals(+.2d, r.re(), precision);
		assertEquals(+.3d, r.im(), precision);
		r = atanh(tanh(z.setCartesian(+.2d, -.3d)));
		assertEquals(+.2d, r.re(), precision);
		assertEquals(-.3d, r.im(), precision);
		r = atanh(tanh(z.setCartesian(-.2d, +.3d)));
		assertEquals(-.2d, r.re(), precision);
		assertEquals(+.3d, r.im(), precision);
		r = atanh(tanh(z.setCartesian(-.2d, -.3d)));
		assertEquals(-.2d, r.re(), precision);
		assertEquals(-.3d, r.im(), precision);
	}


	static private void testFunctionWheel (final String wheelPath, final UnaryOperator<MutableDoublePrecision<?>> function) throws IOException {
		final byte[] expectedWheel, actualWheel;
		try (InputStream byteSource = Thread.currentThread().getContextClassLoader().getResourceAsStream(wheelPath)) {
			expectedWheel = IOStreams.read(byteSource);
		}
		try (ByteArrayOutputStream byteSink = new ByteArrayOutputStream()) {
			final Image wheel = ComplexMath.plotColorWheel(function, -5, -5, +5, +5, 100d, .75f);
			ImageIO.write(SwingFXUtils.fromFXImage(wheel, null), "png", byteSink);
			actualWheel = byteSink.toByteArray();
		}
		assertArrayEquals(expectedWheel, actualWheel);
	}
}