package oa.SampleEvaluation.common.global;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.ysp.service.BaseService;

import jcx.db.talk;
import jcx.jform.hproc;

public class DtoUtil {

	/**
	 * 取得物件屬性中對應的資料表欄位的名稱list
	 * 
	 * @param o
	 * @return
	 */
	public static ArrayList<Field> getDeclaredXmakerFields(final Object o) {
		ArrayList<Field> fields = new ArrayList<Field>();
		try {
			Field[] fld = o.getClass().getDeclaredFields();
			for (Field field : fld) {
				field.setAccessible(true);
				Annotation[] annotations = field.getAnnotations();
				for (Annotation annotation : annotations) {
					if (annotation instanceof xmaker) {
						xmaker myAnnotation = (xmaker) annotation;
						System.out.println("name: " + myAnnotation.name());
						fields.add(field);
					}
				}

			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return fields;
	}

	/**
	 * 表單資料塞入物件
	 * 
	 * @param o
	 * @param service
	 * @return
	 */
	public static Object setFormDataToDto(final Object o, Object service) {

		try {
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

	public static Object getDbDataToDtoByPno(final Object o, talk t, String pno) {
		try {
			// ArrayList<Object> list = new ArrayList<Object>();
			Field[] fld = o.getClass().getDeclaredFields();
			Class clazz = o.getClass();
			ResultSet r = getResultSet(clazz, t, pno, fld);

			while (r.next()) {
				for (Field field : fld) {
					field.setAccessible(true);
					Annotation[] as = field.getDeclaredAnnotations();
					for (Annotation aa : as) {
						if (aa instanceof xmaker) {
							field.set(o, String.valueOf(r.getObject(((xmaker) aa).name())));
						}
					}
				}
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return o;
	}

	public static ArrayList getDbDataToDtoList(final Object o, talk t) {
		ArrayList list = new ArrayList();
		try {
			Class clazz = o.getClass();
			Field[] fld = clazz.getDeclaredFields();
			ResultSet r = getResultSet(clazz, t, fld);
			System.out.println();
			while (r.next()) {
				Constructor<?> ctor = clazz.getConstructor();
				Object object = ctor.newInstance(new Object[] {});
				System.out.println(r.getString("pno"));
				for (Field field : fld) {
					field.setAccessible(true);
					Annotation[] as = field.getDeclaredAnnotations();
					for (Annotation aa : as) {
						if (aa instanceof xmaker) {
							System.out.println(((xmaker) aa).name());
							field.set(object, String.valueOf(r.getObject(((xmaker) aa).name())));
						}
					}
				}
				list.add(object);
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return list;
	}

	public static ResultSet getResultSet(final Class clazz, talk t, String pno, Field[] fld)
			throws SQLException, ClassNotFoundException {
		dbTable a = (dbTable) clazz.getAnnotation(dbTable.class);
		String pkName = a.pkName();
		String tableName = a.name();
		Connection c = t.getConnectionFromPool();
		Statement stmt = c.createStatement();
		ResultSet r = stmt.executeQuery("select * from " + tableName + " where " + pkName + "='" + pno + "'");

		return r;

	}

	public static ResultSet getResultSet(final Class clazz, talk t, Field[] fld)
			throws SQLException, ClassNotFoundException {
		dbTable a = (dbTable) clazz.getAnnotation(dbTable.class);
		String tableName = a.name();
		Connection c = t.getConnectionFromPool();
		Statement stmt = c.createStatement();
		ResultSet r = stmt.executeQuery("select * from " + tableName);

		return r;

	}

	public static Object setDtoDataToForm(final Object o, Object service) {
		try {
			Field[] fld = o.getClass().getDeclaredFields();
			for (Field field : fld) {
				field.setAccessible(true);
				Annotation[] annotations = field.getAnnotations();
				for (Annotation annotation : annotations) {
					if (annotation instanceof xmaker) {
						xmaker myAnnotation = (xmaker) annotation;
						System.out.println("name: " + myAnnotation.name());
						if (service instanceof hproc) {
							((hproc) service).setValue(myAnnotation.name(), (String) field.get(o));
						}
						if (service instanceof BaseService) {
							((BaseService) service).setValue(myAnnotation.name(), (String) field.get(o));
						}

					}
				}

			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return o;
	}

	public static HashMap<String, String> getZZZZZ(final Object o) {
		HashMap<String, String> m = new HashMap<String, String>();
		StringBuilder insertField = new StringBuilder();
		StringBuilder insertValue = new StringBuilder();
		StringBuilder updateFieldWithValue = new StringBuilder();
		try {

			Field[] fld = o.getClass().getDeclaredFields();
			for (Field field : fld) {
				field.setAccessible(true);
				Annotation[] annotations = field.getAnnotations();
				for (Annotation annotation : annotations) {
					if (annotation instanceof xmaker) {
						xmaker myAnnotation = (xmaker) annotation;
						insertField.append(myAnnotation.name() + ",");
						insertValue.append("'" + (String) (field.get(o)) + "'" + ",");
						updateFieldWithValue.append(myAnnotation.name() + "=" + "'" + field.get(o) + "',");
					}
				}

			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		String instf = insertField.toString().replaceAll(",$", "");
		String instv = insertValue.toString().replaceAll(",$", "");
		String up = updateFieldWithValue.toString().replaceAll(",$", "");
		m.put("insertFields", instf);
		m.put("insertValues", instv);
		m.put("updateFieldsWithValues", up);
		return m;
	}

}
