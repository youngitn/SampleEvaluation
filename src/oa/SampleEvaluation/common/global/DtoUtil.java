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

import com.ysp.service.BaseService;

import jcx.db.talk;
import jcx.jform.bNotify;
import jcx.jform.bProcFlow;
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
						// System.out.println("xmaker.name: " + myAnnotation.name());
						if (service instanceof hproc) {
							field.set(o, ((hproc) service).getValue(myAnnotation.name()));
						}
						if (service instanceof bProcFlow) {
							field.set(o, ((bProcFlow) service).getValue(myAnnotation.name()));
						}
						if (service instanceof BaseService) {
							field.set(o, ((BaseService) service).getValue(myAnnotation.name()));
						}
						if (service instanceof bProcFlow) {
							field.set(o, ((bProcFlow) service).getValue(myAnnotation.name()));
						}
						if (service instanceof bNotify) {
							field.set(o, ((bNotify) service).getValue(myAnnotation.name()));
						}

					}
				}

			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return o;
	}

	/**
	 * 注意 回傳值類型; ResultSet to Object
	 * 
	 * @param o
	 * @param t
	 * @param pno
	 * @return
	 * @throws SQLException
	 */
	public static Object getDbDataToDtoById(Class clazz, talk t, String pno) throws SQLException {
		Object object = null;
		ResultSet r = null;
		try {
			// ArrayList<Object> list = new ArrayList<Object>();

			Field[] fld = clazz.getDeclaredFields();
			r = DtoUtil.getResultSet(clazz, t, pno);
			while (r.next()) {
				Constructor<?> ctor = clazz.getConstructor();
				object = ctor.newInstance(new Object[] {});
				for (Field field : fld) {
					field.setAccessible(true);
					Annotation[] as = field.getDeclaredAnnotations();
					for (Annotation aa : as) {
						if (aa instanceof xmaker) {
							field.set(object, String.valueOf(r.getObject(((xmaker) aa).name())));
						}
					}
				}
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			r.close();
		}
		return object;
	}

	/**
	 * 注意 回傳值類型
	 * 
	 * @param o
	 * @param t
	 * @return
	 * @throws SQLException
	 */
	public static ArrayList getDbDataToDtoList(final Class clazz, talk t, String condition) throws SQLException {
		ArrayList list = new ArrayList();
		ResultSet r = null;
		try {

			Field[] fld = clazz.getDeclaredFields();
			r = DtoUtil.getResultSetWithCondition(clazz, t, condition);
			// System.out.println();
			while (r.next()) {
				Constructor<?> ctor = clazz.getConstructor();
				Object object = ctor.newInstance(new Object[] {});
				// System.out.println(r.getString("pno"));
				for (Field field : fld) {
					field.setAccessible(true);
					Annotation[] as = field.getDeclaredAnnotations();
					for (Annotation aa : as) {
						if (aa instanceof xmaker) {
							// System.out.println(((xmaker) aa).name());
							field.set(object, String.valueOf(r.getObject(((xmaker) aa).name())));
						}
					}
				}
				list.add(object);
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			r.close();
		}
		return list;
	}

	/**
	 * 注意 回傳值類型
	 * 
	 * @param clazz
	 * @param t
	 * @param pno
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public static ResultSet getResultSet(final Class clazz, talk t, String pno) throws SQLException {
		dbTable a = (dbTable) clazz.getAnnotation(dbTable.class);
		String pkName = a.pkName();
		String tableName = a.name();
		ResultSet r = null;
		Connection c = null;
		Statement stmt = null;
		try {
			c = t.getConnectionFromPool();
			stmt = c.createStatement();
			r = stmt.executeQuery("select * from " + tableName + " where " + pkName + "='" + pno + "'");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// c.close();

		}
		return r;
	}

	/**
	 * 注意 回傳值類型
	 * 
	 * @param clazz
	 * @param t
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public static ResultSet getResultSetWithCondition(final Class clazz, talk t, String condition)
			throws SQLException, ClassNotFoundException {
		dbTable a = (dbTable) clazz.getAnnotation(dbTable.class);
		String tableName = a.name();
		ResultSet r = null;
		Connection c = null;
		try {
			c = t.getConnectionFromPool();
			Statement stmt = c.createStatement();
			r = stmt.executeQuery("select * from " + tableName + " " + condition);
			System.out.println("select * from " + tableName + " " + condition);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			// c.close();
		}
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

	public static HashMap<DbProcessType, String> getSqlStringForDbCreateAndUpdate(final Object o) {
		String whereStringForUpdatePkField = " where ";
		HashMap<DbProcessType, String> m = new HashMap<DbProcessType, String>();
		StringBuilder insertField = new StringBuilder();
		StringBuilder insertValue = new StringBuilder();
		StringBuilder updateFieldWithValue = new StringBuilder();
		Class clazz = o.getClass();
		Field[] fld = clazz.getDeclaredFields();
		dbTable a = (dbTable) clazz.getAnnotation(dbTable.class);
		String pkName = a.pkName();
		String tableName = a.name();
		try {

			for (Field field : fld) {
				field.setAccessible(true);
				Annotation[] annotations = field.getAnnotations();
				for (Annotation annotation : annotations) {
					if (annotation instanceof xmaker) {
						xmaker myAnnotation = (xmaker) annotation;
						insertField.append(myAnnotation.name() + ",");
						String value = "";
						if (field.get(o) != null) {
							value = (String) (field.get(o));
						}
						insertValue.append("'" + value + "'" + ",");
						// pk 免更新
						if (!myAnnotation.name().equals(pkName)) {
							updateFieldWithValue.append(myAnnotation.name() + "=" + "'" + value + "',");
						} else {
							whereStringForUpdatePkField = " where " + pkName + "='" + value + "'";
						}

					}
				}

			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		String instf = insertField.toString().replaceAll(",$", "");
		String instv = insertValue.toString().replaceAll(",$", "");
		String up = updateFieldWithValue.toString().replaceAll(",$", "");
		m.put(DbProcessType.INSERT, "INSERT INTO  " + tableName + "  (" + instf + ") VALUES (" + instv + " )");
		m.put(DbProcessType.UPDATE, "UPDATE  " + tableName + "  SET " + up + whereStringForUpdatePkField);
		return m;
	}

	public static ArrayList<Object> getDbDataListToDtoListByCondition(Class clazz, talk t, String condition)
			throws SQLException, ClassNotFoundException {
		DtoUtil.getResultSetWithCondition(clazz, t, condition);
		return null;
	}

}
