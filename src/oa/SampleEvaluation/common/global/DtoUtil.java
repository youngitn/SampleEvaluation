package oa.SampleEvaluation.common.global;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.lang.ArrayUtils;

import com.ysp.service.BaseService;

import jcx.db.talk;
import jcx.jform.bNotify;
import jcx.jform.bProcFlow;
import jcx.jform.hproc;
import oa.SampleEvaluation.query.QueryStatus;

/**
 * 物件(or Class)作為參數,僅支援單層繼承欄位向上查找.<br>
 * EX: son fields = son fields+father fields<br>
 * 帶入物件(or Class)注意是否為子孫類別.
 * 
 * @author u52116
 *
 */
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

			Field[] fld = getFields(o);
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

		if (sqlWhere != null && sqlWhere.length() > 0) {
			sqlWhere.insert(0, "WHERE ");
			int index = sqlWhere.lastIndexOf("AND");
			if (index > 0) {
				sqlWhere.delete(index, sqlWhere.length());
			}
		} else {
			//去除因loop增加的末端"AND"
			status = status.replace("AND", "");
			//
			if (!"".equals(status)) {
				sqlWhere.append(" WHERE ");
			}
		}
		String condition = sqlWhere.toString() + status;
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

			Field[] fld = getFields(o);
			// Field[] fld = o.getClass().getDeclaredFields();
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

			Field[] fld = getFields(o);
			// Field[] fld = o.getClass().getDeclaredFields();
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

			Field[] fld = getFields(clazz);
			// Field[] fld = clazz.getDeclaredFields();
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
			System.out.println("getDbDataToDtoList->" + list.size());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (r != null)
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

		Field[] fld = getFields(clazz);
		// Field[] fld = clazz.getDeclaredFields();
		Constructor<?> ctor = null;
		Object object = null;
		Annotation[] as = null;

		while (r.next()) {
			System.out.println("isNew=YES");
			ctor = clazz.getConstructor();
			object = ctor.newInstance();
			for (Field field : fld) {
				field.setAccessible(true);
				as = field.getDeclaredAnnotations();
				for (Annotation aa : as) {
					if (aa instanceof xmaker) {
						if (((xmaker) aa).isText()) {
							field.set(object, (String) ((xmaker) aa).name());

						} else if (((xmaker) aa).isFlowStatus()) {
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

		Field[] fld = getFields(clazz);
		// Field[] fld = clazz.getDeclaredFields();
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
			// Statement stmt = c.createStatement();
			PreparedStatement pstmt = null;
			// String query = "select * from ? ?";
			pstmt = c.prepareStatement("select * from " + tableName + " " + condition);

			r = pstmt.executeQuery();
			// r = stmt.executeQuery("select * from " + tableName + " " + condition);
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
			Field[] fld = getFields(o);

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
		// Class clazz = o.getClass();
		Field[] fld = getFields(o);
		// Field[] fld = clazz.getDeclaredFields();
		dbTable a = (dbTable) o.getClass().getAnnotation(dbTable.class);
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

	public static Field[] getFields(final Object o) {

		return getFields(o.getClass());
	}

	/**
	 * 支援單層繼承欄位輸出
	 * 
	 * @param clazz
	 * @return
	 */
	public static Field[] getFields(Class<?> clazz) {
		Field[] sonFld = clazz.getDeclaredFields();
		Field[] fatherFld = clazz.getSuperclass().getDeclaredFields();
		if (fatherFld != null && fatherFld.length > 0) {
			return (Field[]) ArrayUtils.addAll(sonFld, fatherFld);
		} else {
			return sonFld;
		}

	}

	public static ArrayList<Object> getDbDataListToDtoListByCondition(Class clazz, talk t, String condition)
			throws SQLException, ClassNotFoundException {
		DtoUtil.getResultSetWithCondition(clazz, t, condition);
		return null;
	}

}