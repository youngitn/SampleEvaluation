package oa.SampleEvaluation.common.global;

import java.util.HashMap;

import jcx.db.talk;
import oa.SampleEvaluation.common.DaoUtil;
import oa.SampleEvaluationTp.dto.SampleEvaluationTp;

public class BaseDao {

	public void add(final Object o, talk t) {
		Class clazz = o.getClass();
		DtoUtil.getDeclaredXmakerFields(o);
		dbTable a = (dbTable) clazz.getAnnotation(dbTable.class);
		// String pkName = a.pkName();
		String tableName = a.name();
		HashMap<String, String> m = DtoUtil.getZZZZZ(o);
		System.out.println(m.get("insertFields"));// ���o�Ҧ����r��
		System.out.println(m.get("insertValues"));// ���o�Ҧ���s�Ȧr��
		System.out.println(m.get("updateFieldsWithValues"));// ���o ���=�� �r��
		// return t.execFromPool(
//				"insert into sample_evaluation  (" + FinalString.TABLE_FIELD_FOR_TALK + ") values ("
//						+ FinalString.TABLE_FIELD_MAP_VLUE_FOR_TALK + " )",
//				DaoUtil.getTableValueArray(sampleEvaluation));
	}
}
