package oa.SampleEvaluation.common;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import com.ysp.service.BaseService;

import jcx.jform.bNotify;
import jcx.jform.bProcFlow;
import jcx.jform.hproc;
import oa.global.DtoUtil;
import oa.global.annotation.xmaker;

public  abstract class BaseDto {
	public void getFormData(Object service) {

		try {

			Field[] fld = DtoUtil.getFields(this);
			// Field[] fld = o.getClass().getDeclaredFields();
			for (Field field : fld) {
				field.setAccessible(true);
				Annotation[] annotations = field.getAnnotations();
				for (Annotation annotation : annotations) {
					if (annotation instanceof xmaker) {
						xmaker myAnnotation = (xmaker) annotation;
						// System.out.println("xmaker.name: " + myAnnotation.name());
						if (service instanceof hproc) {
							field.set(this, ((hproc) service).getValue(myAnnotation.name()));
						}
						if (service instanceof bProcFlow) {
							field.set(this, ((bProcFlow) service).getValue(myAnnotation.name()));
						}
						if (service instanceof BaseService) {
							field.set(this, ((BaseService) service).getValue(myAnnotation.name()));
						}
						if (service instanceof bProcFlow) {
							field.set(this, ((bProcFlow) service).getValue(myAnnotation.name()));
						}
						if (service instanceof bNotify) {
							field.set(this, ((bNotify) service).getValue(myAnnotation.name()));
						}

					}
				}

			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void setDataToForm(Object service) {
		try {
			Field[] fld = DtoUtil.getFields(this);

			String value = null;
			xmaker myAnnotation = null;
			Annotation[] annotations = null;
			for (Field field : fld) {
				field.setAccessible(true);
				annotations = field.getAnnotations();
				for (Annotation annotation : annotations) {
					if (annotation instanceof xmaker) {
						myAnnotation = (xmaker) annotation;
						System.out.println("name: " + myAnnotation.name());
						if (service instanceof hproc) {
							value = (String) field.get(this);
							if ("null".equals(value)) {
								value = "";
							}
							((hproc) service).setValue(myAnnotation.name(), value);
						}
						if (service instanceof BaseService) {
							value = (String) field.get(this);
							if ("null".equals(value)) {
								value = "";
							}
							((BaseService) service).setValue(myAnnotation.name(), (String) field.get(this));
						}

					}
				}

			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}
}
