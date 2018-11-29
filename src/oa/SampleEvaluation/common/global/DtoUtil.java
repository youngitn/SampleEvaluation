package oa.SampleEvaluation.common.global;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import com.ysp.service.BaseService;

import jcx.jform.hproc;

public class DtoUtil {
	public static Object setFormDataToDto(final Object o, Object service) {
		try {
			// SampleEvaluationX s = new SampleEvaluationX();
			Field[] fld = o.getClass().getDeclaredFields();
			for (Field field : fld) {
				field.setAccessible(true);
				Annotation[] annotations = field.getAnnotations();
				for (Annotation annotation : annotations) {
					if (annotation instanceof xmaker) {
						xmaker myAnnotation = (xmaker) annotation;
						System.out.println("name: " + myAnnotation.name());
						if (service instanceof hproc) {
							field.set(o, ((hproc) service).getValue(myAnnotation.name()));
						}
						if (service instanceof BaseService) {
							field.set(o, ((BaseService) service).getValue(myAnnotation.name()));
						}

					}
				}

			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return o;
	}

	public static Object getDBDataToDto(final Object o, Object service) {
		try {
			// SampleEvaluationX s = new SampleEvaluationX();
			Field[] fld = o.getClass().getDeclaredFields();
			for (Field field : fld) {
				field.setAccessible(true);
				Annotation[] annotations = field.getAnnotations();
				for (Annotation annotation : annotations) {
					if (annotation instanceof xmaker) {
						xmaker myAnnotation = (xmaker) annotation;
						System.out.println("name: " + myAnnotation.name());
						if (service instanceof hproc) {
							field.set(o, ((hproc) service).getValue(myAnnotation.name()));
						}
						if (service instanceof BaseService) {
							field.set(o, ((BaseService) service).getValue(myAnnotation.name()));
						}

					}
				}

			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return o;
	}
}
