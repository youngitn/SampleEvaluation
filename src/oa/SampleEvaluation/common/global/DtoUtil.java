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
import oa.SampleEvaluation.query.QueryStatus;

public class DtoUtil {

	public String getAppDateWhereStr(String Q_SDATE, String Q_EDATE) {
		if (!"".equals(Q_SDATE + Q_EDATE)) {
			if ("".equals(Q_SDATE) && !"".equals(Q_EDATE)) {
				return "AND APP_DATE <=" + Q_EDATE;
			} else if (!"".equals(Q_SDATE) && "".equals(Q_EDATE)) {
				return "AND APP_DATE >=" + Q_SDATE;
			} else {
				return "AND APP_DATE >=" + Q_SDATE + " AND APP_DATE <=" + Q_EDATE;
			}
		}

		return "";

	}

	public static String getSelectConditionByDtoWithXmakerAdapDbFieldName(final Object o) {

		StringBuilder sqlWhere = new StringBuilder();
		String status = "";
		try {
			Field[] fld = o.getClass().getDeclaredFields();
			for (Field field : fld) {
				field.setAccessible(true);
				Annotation[] annotations = field.getAnnotations();
				for (Annotation annotation : annotations) {
					if (annotation instanceof xmaker) {
						xmaker myAnnotation = (xmaker) annotation;
						String adapName = myAnnotation.adapDbFieldName();
						String val = (String) field.get(o);

						if (!"".equals(val) && !(null == val)) {
							if (myAnnotation.isDateStart()) {
								sqlWhere.append(myAnnotation.adapDbFieldName()).append(">=").append(field.get(o))
										.append(" AND ");
							} else if (myAnnotation.isDateEnd()) {
								sqlWhere.append(myAnnotation.adapDbFieldName()).append("<=").append(field.get(o))
										.append(" AND ");
							} else if (myAnnotation.isFlowStatus()) {

								status = QueryStatus.getFlowStateSqlStrByQueryCondition((String) field.get(o));

							} else if (!"".equals(adapName)) {
								System.out.println("name: " + myAnnotation.adapDbFieldName());
								sqlWhere.append(myAnnotation.adapDbFieldName()).append(" like ").append("'%")
										.append(field.get(o)).append("%'").append(" AND ");

							}
						}

					}
				}

			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		String condition = sqlWhere.insert(0, "WHERE ").delete(sqlWhere.lastIndexOf("AND"), sqlWhere.length())
				.toString() + status;
		System.out.println("sqlWhere=  " + condition);
		return condition;
	}

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
	 * @param o       需new為實體物件
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
	public static Object getDbDataToDtoById(Class<?> clazz, talk t, String pno) throws SQLException {
		Object object = null;
		ResultSet r = null;
		try {

			Field[] fld = clazz.getDeclaredFields();
			r = DtoUtil.getResultSet(clazz, t, pno);
			while (r.next()) {
				Constructor<?> ctor = clazz.getConstructor();
				object = ctor.newInstance();
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
	public static ArrayList<Object> getDbDataToDtoList(final Class<Object> clazz, talk t, String condition)
			throws SQLException {
		ArrayList list = new ArrayList();
		ResultSet r = null;

		try {
			r = DtoUtil.getResultSetWithCondition(clazz, t, condition);
			list = resultSetToArrayList(r, clazz);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			r.close();
		}
		return list;
	}

	/**
	 * 
	 * @param clazz
	 * @param t
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public static String[][] getDbDataTo2DStringArray(final Class<Object> clazz, talk t, String condition)
			throws Exception {

		return arrayListTo2DStringArray(getDbDataToDtoList(clazz, t, condition), clazz);
	}

	/**
	 * 注意 回傳值類型
	 * 
	 * @param o
	 * @param t
	 * @return
	 * @throws Exception
	 */
	public static ArrayList<Object> resultSetToArrayList(ResultSet r, Class<Object> clazz) throws Exception {
		ArrayList<Object> list = new ArrayList<Object>();
		Field[] fld = clazz.getDeclaredFields();
		Constructor<?> ctor = null;
		Object object = null;
		Annotation[] as = null;
		while (r.next()) {
			ctor = clazz.getConstructor();
			object = ctor.newInstance();

			for (Field field : fld) {
				field.setAccessible(true);
				as = field.getDeclaredAnnotations();
				for (Annotation aa : as) {
					if (aa instanceof xmaker) {
						if (((xmaker) aa).isText()) {
							field.set(object, (String) ((xmaker) aa).name());
						}
						if (((xmaker) aa).isFlowStatus()) {
							// TODO 如何彈性在這邊加入運算
							field.set(object, "");
						} else {
							field.set(object, String.valueOf(r.getObject(((xmaker) aa).name())));
						}
					}
				}
			}
			list.add(object);
		}
		return list;
	}

	public static String[][] arrayListTo2DStringArray(ArrayList<Object> arraylist, Class<Object> clazz)
			throws Exception {

		Field[] fld = clazz.getDeclaredFields();
		String[][] ret = new String[arraylist.size()][fld.length];
		int rc = 0;
		int c = 0;
		Annotation[] as = null;
		for (Object object : arraylist) {
			c = 0;

			for (Field field : fld) {
				field.setAccessible(true);
				as = field.getDeclaredAnnotations();
				for (Annotation aa : as) {
					if (aa instanceof xmaker) {
						ret[rc][c] = (String) field.get(object);
					}
				}
				c++;
			}
			rc++;
		}

		return ret;

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

			e.printStackTrace();
		} catch (ClassNotFoundException e) {

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
			System.out.println("do ->select * from " + tableName + " " + condition);
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
							value = (String) field.get(o);
							if ("null".equals(value)) {
								value = "";
							}
							((hproc) service).setValue(myAnnotation.name(), value);
						}
						if (service instanceof BaseService) {
							value = (String) field.get(o);
							if ("null".equals(value)) {
								value = "";
							}
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
			Annotation[] annotations = null;
			xmaker myAnnotation = null;
			for (Field field : fld) {
				field.setAccessible(true);
				annotations = field.getAnnotations();
				for (Annotation annotation : annotations) {
					if (annotation instanceof xmaker) {
						myAnnotation = (xmaker) annotation;
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