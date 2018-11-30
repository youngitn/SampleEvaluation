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
		System.out.println(m.get("insertFields"));// 取得所有欄位字串
		System.out.println(m.get("insertValues"));// 取得所有更新值字串
		System.out.println(m.get("updateFieldsWithValues"));// 取得 欄位=值 字串
		// return t.execFromPool(
//				"insert into sample_evaluation  (" + FinalString.TABLE_FIELD_FOR_TALK + ") values ("
//						+ FinalString.TABLE_FIELD_MAP_VLUE_FOR_TALK + " )",
//				DaoUtil.getTableValueArray(sampleEvaluation));
	}
}
