package oa.global;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.lang.ArrayUtils;

import jcx.db.talk;
import oa.SampleEvaluation.model.QueryConditionDTO;
import oa.global.annotation.dbTable;
import oa.global.annotation.xmaker;

/**
 * �ھ�DTO�i���Ʈw�ާ@����k���X
 * 
 * @author u52116
 *
 */
public abstract class BaseDao {

	protected talk t;
	protected Class clazz;

	/**
	 * �N�ǤJ��DTO�i��INSERT�ʧ@.
	 * 
	 * @param o
	 * @throws SQLException
	 * @throws Exception
	 */
	public void add(final Object o) throws SQLException, Exception {

		HashMap<DbProcessType, String> m = DtoUtil.getSqlStringForDbCreateAndUpdate(o);
		t.execFromPool(m.get(DbProcessType.INSERT));
	}

	/**
	 * �N�ǤJ��DTO�i���Ƨ�s
	 * 
	 * @param o
	 * @throws SQLException
	 * @throws Exception
	 */
	public void update(final Object o) throws SQLException, Exception {
		HashMap<DbProcessType, String> m = DtoUtil.getSqlStringForDbCreateAndUpdate(o);
		t.execFromPool(m.get(DbProcessType.UPDATE));
	}

	/**
	 * �p�G��Ʀs�b�h�i���s<br>
	 * �_�h�s�W.
	 * 
	 * @param o
	 * @throws SQLException
	 * @throws Exception
	 */
	public void upsert(final Object o) throws SQLException, Exception {
		HashMap<DbProcessType, String> m = DtoUtil.getSqlStringForDbCreateAndUpdate(o);
		Class clazz = o.getClass();
		dbTable a = (dbTable) clazz.getAnnotation(dbTable.class);
		String pkName = a.pkName();
		Field[] fld1 = clazz.getDeclaredFields();
		Field[] fld2 = clazz.getSuperclass().getDeclaredFields();
		Field[] fld = (Field[]) ArrayUtils.addAll(fld1, fld2);
		String pk = "";
		System.out.println(fld.length);
		for (Field field : fld) {
			field.setAccessible(true);
			Annotation[] annotations = field.getAnnotations();
			for (Annotation annotation : annotations) {
				if (annotation instanceof xmaker) {
					xmaker ann = (xmaker) annotation;
					if (ann.name().equals(pkName)) {
						pk = (String) field.get(o);
					}
				}
			}
		}
		System.out.println("pk-->" + pk + "  pkName-->" + pkName);
		if (findById(pk) == null) {
			t.execFromPool(m.get(DbProcessType.INSERT));
		} else {
			t.execFromPool(m.get(DbProcessType.UPDATE));
		}
	}

	/**
	 * �ھڳ渹�i��d��
	 * 
	 * @param pno
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public Object findById(String pno) throws SQLException, Exception {
		return DtoUtil.getDtoById(clazz, t, pno);
	}

	/**
	 * �ھڶǤJSQL����r��i��d��<br>
	 * condition�r��ݥH"WHERE"�}�Y.<br>
	 * 
	 * @param condition
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public ArrayList<?> findByCondition(String condition) throws SQLException, Exception {
		return DtoUtil.resultSetToArrayList(clazz, t, condition);
	}

	/**
	 * �N�JSQL�������D�d��,<br>
	 * �^�ǤG���}�C���,<br>
	 * �C�����Sing[n][x=���dto�ݩʫŧi����]<br>
	 * condition�r��ݥH"WHERE"�}�Y.<br>
	 * 
	 * @param condition [String]
	 * @return String[][]
	 * @throws SQLException
	 * @throws Exception
	 */
	public String[][] getResultBySqlWhereString(String condition) throws SQLException, Exception {
		return DtoUtil.getDbDataTo2DStringArray(clazz, t, condition);
	}

	/**
	 * Not empty.
	 *
	 * @param s [String]
	 * @return true, if successful
	 */
	public boolean notEmpty(String s) {
		return (s != null && s.length() > 0);
	}

}
