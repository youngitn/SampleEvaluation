package oa.SampleEvaluation.common.global;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.HashMap;

import jcx.db.talk;

public class BaseDao {

	protected talk t;
	protected Class clazz;

	public void add(final Object o) throws SQLException, Exception {

		HashMap<DbProcessType, String> m = DtoUtil.getSqlStringForDbCreateAndUpdate(o);
		t.execFromPool(m.get(DbProcessType.INSERT));
	}

	public void update(final Object o) throws SQLException, Exception {
		HashMap<DbProcessType, String> m = DtoUtil.getSqlStringForDbCreateAndUpdate(o);
		t.execFromPool(m.get(DbProcessType.UPDATE));
	}

	public void upsert(final Object o) throws SQLException, Exception {
		HashMap<DbProcessType, String> m = DtoUtil.getSqlStringForDbCreateAndUpdate(o);
		Class clazz = o.getClass();
		dbTable a = (dbTable) clazz.getAnnotation(dbTable.class);
		String pkName = a.pkName();
		Field[] fld = o.getClass().getDeclaredFields();
		String pk = "";
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
		if (findById(pk) == null) {
			t.execFromPool(m.get(DbProcessType.INSERT));
		} else {
			t.execFromPool(m.get(DbProcessType.UPDATE));
		}
	}

	public Object findById(String pno) throws SQLException, Exception {
		return DtoUtil.getDbDataToDtoById(clazz, t, pno);
	}

}
