package de.sb.toolbox;

import static org.junit.Assert.*;
import java.util.List;
import org.junit.Test;
import de.sb.toolbox.Reflections;

public class ReflectionsTest {

	@Test
	public void testListToArrayConversion () {
		final String[] array1 = new String[] { "ab", "cd" };
		final int[] array2 = new int[] { 1, 2 };

		final List<Object> objectDataList = Reflections.toList(array1, Object.class);
		final List<Integer> primitiveDataList = Reflections.toList(array2, Integer.class);

		assertArrayEquals(array1, (String[]) Reflections.toArray(objectDataList, String.class));
		assertArrayEquals(array2, (int[]) Reflections.toArray(primitiveDataList, int.class));	
	}


	@Test
	public void testHashingAndEquality () {
		final Object object = new Object();
		final String[] leftArray1 = new String[] { "ab", "cd" }, rightArray1 = leftArray1.clone();
		final int[] leftArray2 = new int[] { 1, 2 }, rightArray2 = leftArray2.clone();

		assertEquals(0, Reflections.hashCode(null));
		assertEquals(object.hashCode(), Reflections.hashCode(object));
		assertEquals(Reflections.hashCode(leftArray1), Reflections.hashCode(rightArray1));
		assertEquals(Reflections.hashCode(leftArray2), Reflections.hashCode(rightArray2));

		assertTrue(Reflections.equals(leftArray1, rightArray1));
		assertTrue(Reflections.equals(leftArray2, rightArray2));
		assertFalse(Reflections.equals(leftArray1, rightArray2));
		assertFalse(Reflections.equals(leftArray2, rightArray1));
		assertFalse(Reflections.equals(object, leftArray1));
		assertFalse(Reflections.equals(leftArray1, object));
	}	
}