package oa.global;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import com.ysp.service.BaseService;

import jcx.jform.bNotify;
import jcx.jform.bProcFlow;
import jcx.jform.hproc;
import oa.global.annotation.xmaker;

/**
 * The Class Model.
 *
 * @author YoungCheng(u52116) 2019/3/19
 */
public  abstract class Model {
	
	/**
	 * Gets the FormData.
	 *
	 * @param service [Object]
	 * @return [void]
	 */
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

	/**
	 * Sets the DataToForm.
	 *
	 * @param service  void
	 */
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
