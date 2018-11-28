package oa.SampleEvaluation.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.commons.lang.ArrayUtils;

import jcx.db.talk;
import oa.SampleEvaluation.common.DaoUtil;
import oa.SampleEvaluation.common.global.FinalString;
import oa.SampleEvaluation.daointerface.ITableDao;
import oa.SampleEvaluation.dto.SampleEvaluation;

/**
 * SampleEvaluation
 * 
 */

public class SampleEvaluationDaoImpl implements ITableDao<SampleEvaluation> {

	talk t;

	public SampleEvaluationDaoImpl(talk t) {
		this.t = t;
	}

	@Override
	public String add(SampleEvaluation sampleEvaluation) throws SQLException, Exception {
		System.out.println(sampleEvaluation.getPno());
		return t.execFromPool(
				"insert into sample_evaluation  (" + FinalString.TABLE_FIELD_FOR_TALK + ") values ("
						+ FinalString.TABLE_FIELD_MAP_VLUE_FOR_TALK + " )",
				DaoUtil.getTableValueArray(sampleEvaluation));
	}

	@Override
	public String update(SampleEvaluation sampleEvaluation) throws SQLException, Exception {
		Object[] o = DaoUtil.getTableValueArray(sampleEvaluation);
		o = ArrayUtils.remove(o, 0);
		o = ArrayUtils.add(o, sampleEvaluation.getPno());
		System.out.println("o size--->" + o.length);
		System.out.println("o size--->" + o[49]);

		return t.execFromPool(
				"UPDATE  sample_evaluation  SET " + FinalString.TABLE_FIELD_WITH_MAP_VLUE_FOR_TALK + " where pno=?", o);
	}

	@Override
	public String delete(String id) throws SQLException, Exception {
		return t.execFromPool("DELETE from sample_evaluation where pno=?", new Object[] { id });
	}

	@Override
	public SampleEvaluation findById(String id) throws SQLException, Exception {
		String[][] ret = t.queryFromPool("select * from sample_evaluation where pno='" + id + "'");
		if (ret != null && ret.length > 0) {
			SampleEvaluation sampleEvaluation = new SampleEvaluation(ret[0]);
			return sampleEvaluation;
		} else {
			return null;
		}
	}

	@Override
	public ArrayList<SampleEvaluation> findAllList(String params) throws SQLException, Exception {
		ArrayList<SampleEvaluation> retList = new ArrayList<SampleEvaluation>();
		String[][] ret = t.queryFromPool("select * from sample_evaluation " + params);
		if (ret != null && ret.length > 0) {
			for (int i = 0; i < ret.length; i++) {
				retList.add(new SampleEvaluation(ret[0]));
			}
			return retList;
		} else {
			return null;
		}

	}

	@Override
	public String[][] findArrayById(String id) throws SQLException, Exception {
		String[][] ret = t.queryFromPool("select * from sample_evaluation where pno='" + id + "'");
		if (ret != null && ret.length > 0) {
			return ret;
		} else {
			return null;
		}
	}

	@Override
	public String[][] findAllArray(String params, String selectFields) throws SQLException, Exception {
		// ArrayList<SampleEvaluation> retList = new ArrayList<SampleEvaluation>();
		String[][] ret = t.queryFromPool("select " + selectFields.toString() + " from sample_evaluation " + params);
		if (ret != null && ret.length > 0) {

			return ret;
		} else {
			return null;
		}
	}

}
