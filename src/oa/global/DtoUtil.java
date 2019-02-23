package oa.global;

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
 * ����(or Class)�@���Ѽ�,�Ȥ䴩��h�~�����V�W�d��.<br>
 * EX: son fields = son fields+father fields<br>
 * �a�J����(or Class)�`�N�O�_���l�]���O.
 * 
 * �`�N:�o�̳�����k(setFormDataToDto()&setDtoDataToForm())�|�̿�jcx����(hproc....)
 * 
 * @author u52116
 *
 */
public class DtoUtil {

	/**
	 * �N���e������ƶ�J����.
	 *
	 * @param o       �ݬ����骫��,�B�ݷf�txmaker,�@������ŹϨϥ�
	 * @param service [hproc,BaseService,bProcFlow....]
	 * @return [Object]
	 */
	public static Object setFormDataIntoDto(final Object o, Object service) {

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
	 * �ھ�QueryConditionDto���O��xmaker�ҩw�q��adapDbFieldName,<br>
	 * �զ�where�r��æ^�ǡC.
	 *
	 * @param dto [Object]
	 * @return [String]
	 */
	public static String queryConditionDtoConvertToSqlWhereString(Object dto) {

		StringBuilder sqlWhere = new StringBuilder();
		String status = "";
		try {

			Field[] fld = getFields(dto);
			for (Field field : fld) {
				field.setAccessible(true);
				Annotation[] annotations = field.getAnnotations();
				for (Annotation annotation : annotations) {
					if (annotation instanceof xmaker) {
						xmaker myAnnotation = (xmaker) annotation;
						String adapName = myAnnotation.mappingDbFieldName();
						String val = (String) field.get(dto);

						if (!"".equals(val) && !(null == val)) {
							// �����O����_��h�զX�����r��
							if (myAnnotation.isDateStart()) {
								sqlWhere.append(adapName).append(">=").append(field.get(dto)).append(" AND ");
							} // �����O�������h�զX�����r��
							else if (myAnnotation.isDateEnd()) {
								sqlWhere.append(adapName).append("<=").append(field.get(dto)).append(" AND ");
							}
							// �����Oñ�֪��A�h�ϥ�QueryStatus.getFlowStateSqlStrByQueryCondition("����value")
							// �եX�d�ߦr��
							else if (myAnnotation.isFlowStatus()) {

								status = QueryStatus.getFlowStateSqlStrByQueryCondition((String) field.get(dto));

							} // ��L�D�S�����Ҩϥ�'db���W�� like % ��%'�եX�r��
							else if (!"".equals(adapName)) {
								System.out.println("name: " + myAnnotation.mappingDbFieldName());
								sqlWhere.append(adapName).append(" like ").append("'%").append(field.get(dto))
										.append("%'").append(" AND ");

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
			// �h���]loop�W�[������"AND"
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
	 * ���o�����ݩʤ���������ƪ���쪺�W��list.
	 *
	 * @param dto [Object]
	 * @return [ArrayList<Field>]
	 */
	public static ArrayList<Field> getDeclaredXmakerFields(final Object dto) {
		ArrayList<Field> fields = new ArrayList<Field>();
		try {

			Field[] fld = getFields(dto);
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
	 * �ھ�pno���oDB���, �ھ�clazz�Ź�,�N��ƶ�JDTO<br>
	 * �`�N �^�ǭ�����; ResultSet to Object<br>
	 * .
	 *
	 * @param clazz ���O�Ź�
	 * @param t     [talk]
	 * @param pno   [String]
	 * @return the db data to dto by id
	 * @throws SQLException the SQL exception
	 */
	public static Object getDtoById(Class<?> clazz, talk t, String pno) throws SQLException {
		Object object = null;
		ResultSet r = null;
		try {

			Field[] fld = getFields(clazz);
			// Field[] fld = clazz.getDeclaredFields();
			r = DtoUtil.getResultSet(clazz, t, pno);
			while (r.next()) {
				object = setResultSetToDto(clazz, fld, r);
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			r.close();
		}
		return object;
	}

	public static Object setResultSetToDto(Class clazz, Field[] fld, ResultSet r) throws Exception {
		Constructor<?> ctor = clazz.getConstructor();
		Object object = ctor.newInstance();
		for (Field field : fld) {
			field.setAccessible(true);
			Annotation[] as = field.getDeclaredAnnotations();
			for (Annotation aa : as) {
				if (aa instanceof xmaker) {
					// field.set(object, String.valueOf(r.getObject(((xmaker) aa).name())));

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
		return object;
	}

	/**
	 * �q��Ʈw���o�h�����, �B�^�������� ArrayList<�N�Jclazz>.
	 *
	 * @param clazz     [Class<Object>]
	 * @param t         talk
	 * @param condition [String]
	 * @return [ArrayList<Object>]
	 * @throws SQLException the SQL exception
	 */
	public static ArrayList<Object> resultSetToArrayList(final Class<Object> clazz, talk t, String condition)
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
	 * �ھڬd�߱���, �^�ǤG���}�C�d�ߵ��G.
	 *
	 * @param clazz     �ݷf�txmaker
	 * @param t         [talk]
	 * @param condition [String]
	 * @return [String[][]]
	 * @throws Exception the exception
	 */
	public static String[][] getDbDataTo2DStringArray(final Class<Object> clazz, talk t, String condition)
			throws Exception {

		return arrayListTo2DStringArray(resultSetToArrayList(clazz, t, condition), clazz);
	}

	/**
	 * �`�N �^�ǭ�����.
	 *
	 * @param r     [ResultSet]
	 * @param clazz [Class<Object>]
	 * @return [ArrayList<Object>]
	 * @throws Exception the exception
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
			object = setResultSetToDto(clazz, fld, r);

			list.add(object);
		}

		return list;
	}

	/**
	 * Array list to 2 D string array.
	 *
	 * @param arraylist [ArrayList<Object>]
	 * @param clazz     [Class<Object>]
	 * @return [String[][]]
	 * @throws Exception the exception
	 */
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
	 * �`�N �^�ǭ�����.
	 *
	 * @param clazz [Class]
	 * @param t     [talk]
	 * @param pno   [String]
	 * @return [ResultSet]
	 * @throws SQLException the SQL exception
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
	 * �`�N �^�ǭ�����,<br>
	 * �ǤJ��dto���ݩ�A,B,C�ŧi����=�եX�Ӫ�select A,B,C����.
	 *
	 * @param clazz     [Class]
	 * @param t         [talk]
	 * @param condition [String]
	 * @return [ResultSet]
	 * @throws SQLException           the SQL exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	public static ResultSet getResultSetWithCondition(final Class clazz, talk t, String condition)
			throws SQLException, ClassNotFoundException {
		dbTable a = (dbTable) clazz.getAnnotation(dbTable.class);
		String tableName = a.name();
		ResultSet r = null;
		Connection c = null;
		try {
			c = t.getConnectionFromPool();
			PreparedStatement pstmt = null;
			pstmt = c.prepareStatement("select * from " + tableName + " " + condition);

			r = pstmt.executeQuery();
			System.out.println("do ->select * from " + tableName + " " + condition);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			// c.close();
		}
		return r;

	}

	/**
	 * �ھڶǤJ�����ݩ�, �ЫئU���a�Ȫ�INSERT��UPDATE SQL�r��.
	 *
	 * @param o [Object]
	 * @return [HashMap<DbProcessType,String>]
	 */
	public static HashMap<DbProcessType, String> getSqlStringForDbCreateAndUpdate(final Object o) {
		String whereStringForUpdatePkField = " where ";
		HashMap<DbProcessType, String> m = new HashMap<DbProcessType, String>();
		StringBuilder insertField = new StringBuilder();
		StringBuilder insertValue = new StringBuilder();
		StringBuilder updateFieldWithValue = new StringBuilder();
		Field[] fld = getFields(o);
		dbTable a = (dbTable) o.getClass().getAnnotation(dbTable.class);
		String pkName = a.pkName();
		String tableName = a.name();
		Annotation[] annotations = null;
		xmaker myAnnotation = null;
		try {

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
						// pk �K��s
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
	 * �䴩��h�~������X.
	 *
	 * @param clazz [Class<?>]
	 * @return [Field[]]
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

}