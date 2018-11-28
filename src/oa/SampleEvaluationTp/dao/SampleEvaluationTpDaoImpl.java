package oa.SampleEvaluationTp.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.commons.lang.ArrayUtils;

import jcx.db.talk;
import oa.SampleEvaluation.common.DaoUtil;
import oa.SampleEvaluation.common.global.FinalString;
import oa.SampleEvaluation.daointerface.ITableDao;
import oa.SampleEvaluationTp.dto.SampleEvaluationTp;

/**
 * SampleEvaluation
 * 
 */

public class SampleEvaluationTpDaoImpl implements ITableDao<SampleEvaluationTp> {

	talk t;

	public SampleEvaluationTpDaoImpl(talk t) {
		this.t = t;
	}

	@Override
	public String add(SampleEvaluationTp s) throws SQLException, Exception {

		return t.execFromPool("insert into sample_evaluation_tp  ( " + FinalString.SUB_TABLE_FIELD_FOR_TALK
				+ ") values (" + FinalString.SUB_TABLE_FIELD_MAP_VLUE_FOR_TALK + " )", DaoUtil.getTableValueArray(s));
	}

	@Override
	public String update(SampleEvaluationTp s) throws SQLException, Exception {
		Object[] o = DaoUtil.getTableValueArray(s);
		o = ArrayUtils.remove(o, 0);
		o = ArrayUtils.add(o, s.getOwnPno());
		return t.execFromPool("UPDATE  sample_evaluation_tp  SET " + FinalString.SUB_TABLE_FIELD_WITH_MAP_VLUE_FOR_TALK
				+ " where own_pno=?", o);

	}

	@Override
	public String delete(String id) throws SQLException, Exception {
		return t.execFromPool("DELETE from sample_evaluation_tp where pno=?", new Object[] { id });
	}

	@Override
	public SampleEvaluationTp findById(String id) throws SQLException, Exception {
		String[][] ret = t.queryFromPool("select * from sample_evaluation_Tp where own_pno='" + id + "'");
		if (ret != null && ret.length > 0) {
			SampleEvaluationTp sampleEvaluationTp = new SampleEvaluationTp(ret[0]);
			return sampleEvaluationTp;
		} else {
			return null;
		}
	}

	@Override
	public ArrayList<SampleEvaluationTp> findAllList(String params) throws SQLException, Exception {
		ArrayList<SampleEvaluationTp> retList = new ArrayList<SampleEvaluationTp>();
		String[][] ret = t.queryFromPool("select * from sample_evaluation_Tp " + params);
		if (ret != null && ret.length > 0) {
			for (int i = 0; i < ret.length; i++) {
				retList.add(new SampleEvaluationTp(ret[0]));
			}
			return retList;
		} else {
			return null;
		}

	}

	@Override
	public String[][] findArrayById(String id) throws SQLException, Exception {

		String[][] ret = t.queryFromPool("select * from sample_evaluation_tp where pno='" + id + "'");
		if (ret != null && ret.length > 0) {
			return ret;
		} else {
			return null;
		}
	}

	@Override
	public String[][] findAllArray(String params, String selectFields) throws SQLException, Exception {
		// ArrayList<SampleEvaluation> retList = new ArrayList<SampleEvaluation>();
		String[][] ret = t.queryFromPool("select " + selectFields.toString() + " from sample_evaluation_tp " + params);
		if (ret != null && ret.length > 0) {

			return ret;
		} else {
			return null;
		}
	}

}
