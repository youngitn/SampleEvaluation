package oa.SampleEvaluation;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import oa.SampleEvaluation.dto.SampleEvaluation;

public class dddd {
	public static void main(String[] arg) throws SecurityException, NoSuchFieldException {
		try {
			SampleEvaluation s = new SampleEvaluation();
			//dddd.setFormDataToDto(s);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
	}

	private static Field getField(Class<?> cls, String fieldName) {
		for (Class<?> c = cls; c != null; c = c.getSuperclass()) {
			try {
				final Field field = c.getDeclaredField(fieldName);
				field.setAccessible(true);
				return field;
			} catch (final NoSuchFieldException e) {
				// Try parent
			} catch (Exception e) {
				throw new IllegalArgumentException("Cannot access field " + cls.getName() + "." + fieldName, e);
			}
		}
		throw new IllegalArgumentException("Cannot find field " + cls.getName() + "." + fieldName);
	}

//	public static Object setFormDataToDto(final Object o) {
//		
//		try {
//		
//			Field[] fld = o.getClass().getDeclaredFields();
//			for (Field field : fld) {
//				field.setAccessible(true);
//				Annotation[] annotations = field.getAnnotations();
//				for(Annotation annotation : annotations){
//				    if(annotation instanceof form){
//				    	form myAnnotation = (form) annotation;
//				        System.out.println("name: " + myAnnotation.name());
//				
//				    }
//				}
//				// field.set(o, getValue(field.getName().trim().toUpperCase()));
//			}

//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}
//		return o;
//	}

	public static Object setAllValue(final Object obj, boolean publicOnly) {
		Map<String, Object> m;
		try {
			m = dddd.getFieldNamesAndValues(obj, publicOnly);

			Set<String> ss = m.keySet();
			for (Iterator<String> iterator = ss.iterator(); iterator.hasNext();) {
				String fieldName = iterator.next();
				// Field field = SampleEvaluationX.class.getField(fieldName);

				// Object value = field.get(obj);

				// field.set(obj, getValue(fieldName));
				System.out.println(fieldName);
			}
			System.out.println(m.get("pno"));
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return obj;

	}

	public static Map<String, Object> getFieldNamesAndValues(final Object obj, boolean publicOnly)
			throws IllegalArgumentException, IllegalAccessException {
		Class<? extends Object> c1 = obj.getClass();
		Map<String, Object> map = new HashMap<String, Object>();
		Field[] fields = c1.getDeclaredFields();

		for (int i = 0; i < fields.length; i++) {
			fields[i].setAccessible(true);
			String name = fields[i].getName();
			if (publicOnly) {
				if (Modifier.isPublic(fields[i].getModifiers())) {
					Object value = fields[i].get(obj);
					map.put(name, value);
				}
			} else {
				fields[i].setAccessible(true);
				Object value = fields[i].get(obj);
				map.put(name, value);
			}
		}
		return map;
	}

}
