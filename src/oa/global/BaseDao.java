package oa.global;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.lang.ArrayUtils;

import jcx.db.talk;
import oa.global.annotation.dbTable;
import oa.global.annotation.xmaker;

/**
 * 根據DTO進行資料庫操作的方法集合.
 *
 * @author u52116
 */
public abstract class BaseDao {

	/** The t. */
	protected talk t;

	/** The clazz. */
	protected Class clazz;

	/**
	 * 將傳入的DTO進行INSERT動作.
	 *
	 * @param o [Object]
	 * @throws SQLException the SQL exception
	 * @throws Exception    the exception
	 */
	public void add(final Object o) throws SQLException, Exception {

		HashMap<DbProcessType, String> m = DtoUtil.getSqlStringForDbCreateAndUpdate(o);
		t.execFromPool(m.get(DbProcessType.INSERT));
	}

	/**
	 * 將傳入的DTO進行資料更新.
	 *
	 * @param o [Object]
	 * @throws SQLException the SQL exception
	 * @throws Exception    the exception
	 */
	public void update(final Object o) throws SQLException, Exception {
		HashMap<DbProcessType, String> m = DtoUtil.getSqlStringForDbCreateAndUpdate(o);
		t.execFromPool(m.get(DbProcessType.UPDATE));
	}

	/**
	 * 如果資料存在則進行更新<br>
	 * 否則新增.
	 *
	 * @param o [Object]
	 * @throws SQLException the SQL exception
	 * @throws Exception    the exception
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
	 * 根據單號進行查詢.
	 *
	 * @param pno [String]
	 * @return [Object]
	 * @throws SQLException the SQL exception
	 * @throws Exception    the exception
	 */
	public Object findById(String pno) throws SQLException, Exception {
		return DtoUtil.getDtoById(clazz, t, pno);
	}

	/**
	 * 根據傳入SQL條件字串進行查詢<br>
	 * condition字串需以"WHERE"開頭.<br>
	 *
	 * @param condition [String]
	 * @return [ArrayList<?>]
	 * @throws SQLException the SQL exception
	 * @throws Exception    the exception
	 */
	public ArrayList<?> findByCondition(String condition) throws SQLException, Exception {
		return DtoUtil.resultSetToArrayList(clazz, t, condition);
	}

	/**
	 * 代入SQL條件執行主查詢,<br>
	 * 回傳二維陣列資料,<br>
	 * 每筆資料Sing[n][x=比照dto屬性宣告順序]<br>
	 * condition字串需以"WHERE"開頭.<br>
	 *
	 * @param condition [String]
	 * @return String[][]
	 * @throws SQLException the SQL exception
	 * @throws Exception    the exception
	 */
	public String[][] getResultBySqlWhereString(String condition) throws SQLException, Exception {
		System.out.print("getResultBySqlWhereString-->" + condition);
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
