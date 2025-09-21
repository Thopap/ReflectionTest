package de.thopap.example;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hl7.fhir.r4.model.BaseReference;
import org.hl7.fhir.r4.model.Identifier;
import org.hl7.fhir.r4.model.Reference;
import org.junit.jupiter.api.Test;

public class ReflectionTestOnSuperMethods {

	@Test
	public void testJavaReflection1() throws NoSuchMethodException, SecurityException, IllegalAccessException, InvocationTargetException {
		var example1 = new ExampleDto1();
		Identifier id = new Identifier();
		id.setValue("abc");
		example1.setIdentifier(id);
		
		Method method = BaseReference.class.getMethod("getIdentifier");
		assertNotNull(example1.getIdentifier());	
		assertNotNull(method.invoke(example1, new Object[0]));		
	}
	
	@Test
	public void testJavaReflection2() throws NoSuchMethodException, SecurityException, IllegalAccessException, InvocationTargetException {
		var example2 = new ExampleDto2();
		Identifier id = new Identifier();
		id.setValue("abc");
		example2.setIdentifier(id);

		
		Method method = BaseReference.class.getMethod("getIdentifier");
		assertNotNull(example2.getIdentifier());	
		assertNotNull(method.invoke(example2, new Object[0]));		
	}
	
	@Test
	public void testDiffClassMethods() {
		var example1 = new ExampleDto1();
		var example2 = new ExampleDto2();
		
		List<String> methodSignatureDiff1 = new ArrayList<>();
		List<String> methodSignatureDiff2 = new ArrayList<>();
		
		Arrays.stream(example1.getClass().getMethods()).forEach(m -> methodSignatureDiff1.add(m.getName() + " : " + m.toString()));
		Arrays.stream(example2.getClass().getMethods()).forEach(m -> methodSignatureDiff1.removeIf(p -> p.equals(m.getName() + " : " + m.toString())));
		Arrays.stream(example2.getClass().getMethods()).forEach(m -> methodSignatureDiff2.add(m.getName() + " : " + m.toString()));
		Arrays.stream(example1.getClass().getMethods()).forEach(m -> methodSignatureDiff2.removeIf(p -> p.equals(m.getName() + " : " + m.toString())));
		
        for (String method : methodSignatureDiff1) {
            System.out.println("Example1 Diff in:" + method);
        }
        for (String method : methodSignatureDiff2) {
            System.out.println("Example2 Diff in:" + method);
        }
		
		
		
	}
	
	public static class ExampleDto1 extends Reference {
		/*public Identifier getIdentifier() {
			return super.getIdentifier();
		}*/
	}
	
	public static class ExampleDto2 extends Reference {
		public org.hl7.fhir.r4.model.Identifier getIdentifier() {
			return super.getIdentifier();
		}
	}
	

}
