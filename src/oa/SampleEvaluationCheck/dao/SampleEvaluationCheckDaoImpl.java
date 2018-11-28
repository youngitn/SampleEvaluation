package oa.SampleEvaluationCheck.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.commons.lang.ArrayUtils;

import jcx.db.talk;
import oa.SampleEvaluation.common.DaoUtil;
import oa.SampleEvaluation.common.global.FinalString;
import oa.SampleEvaluation.daointerface.ITableDao;
import oa.SampleEvaluationCheck.dto.SampleEvaluationCheck;

/**
 * SampleEvaluation
 * 
 */

public class SampleEvaluationCheckDaoImpl implements ITableDao<SampleEvaluationCheck> {

	talk t;

	public SampleEvaluationCheckDaoImpl(talk t) {
		this.t = t;
	}

	public String add(SampleEvaluationCheck s) throws SQLException, Exception {

		return t.execFromPool("insert into sample_evaluation_check  (" + FinalString.SUB_TABLE_FIELD_FOR_TALK
				+ ") values (" + FinalString.SUB_TABLE_FIELD_MAP_VLUE_FOR_TALK + " )", DaoUtil.getTableValueArray(s));
	}

	public String update(SampleEvaluationCheck s) throws SQLException, Exception {
		Object[] o = DaoUtil.getTableValueArray(s);
		o = ArrayUtils.remove(o, 0);
		o = ArrayUtils.add(o, s.getOwnPno());
		return t.execFromPool("UPDATE  sample_evaluation_check  SET "
				+ FinalString.SUB_TABLE_FIELD_WITH_MAP_VLUE_FOR_TALK + " where own_pno=?", o);

	}

	public String delete(String id) throws SQLException, Exception {
		return t.execFromPool("DELETE from sample_evaluation_check where pno=?", new Object[] { id });
	}

	public SampleEvaluationCheck findById(String id) throws SQLException, Exception {
		String[][] ret = t.queryFromPool("select * from sample_evaluation_check where own_pno='" + id + "'");
		if (ret != null && ret.length > 0) {
			SampleEvaluationCheck sampleEvaluation = new SampleEvaluationCheck(ret[0]);
			return sampleEvaluation;
		} else {
			return null;
		}
	}

	public ArrayList<SampleEvaluationCheck> findAllList(String params) throws SQLException, Exception {
		ArrayList<SampleEvaluationCheck> retList = new ArrayList<SampleEvaluationCheck>();
		String[][] ret = t.queryFromPool("select * from sample_evaluation_check " + params);
		if (ret != null && ret.length > 0) {
			for (int i = 0; i < ret.length; i++) {
				retList.add(new SampleEvaluationCheck(ret[0]));
			}
			return retList;
		} else {
			return null;
		}

	}

	@Override
	public String[][] findArrayById(String id) throws SQLException, Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[][] findAllArray(String params, String selectFields) throws SQLException, Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
