package oa.SampleEvaluationTp.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import jcx.db.talk;
import oa.SampleEvaluation.dao.AbstractGenericDao;
import oa.SampleEvaluationTp.dto.SampleEvaluationTp;

/**
 * SampleEvaluation
 * 
 */

public class SampleEvaluationTpDaoImpl extends AbstractGenericDao<SampleEvaluationTp> {

	talk t;

	public SampleEvaluationTpDaoImpl(talk t) {
		this.t = t;
	}

	@Override
	public String add(SampleEvaluationTp sampleEvaluation) throws SQLException, Exception {

		return t.execFromPool(
				"insert into sample_evaluation_tp  (PNO,app_type,urgency,material,sap_code,ab_code,mfg_lot_no,qty,pack,unit,mfr,supplier,provide_coa,provide_spec,provide_test_method,provide_sds,provide_others,note,applicant,app_date,receipt_unit,project_code,project_leader,notify_no_check,notify_no_trial_prod,qr_no,is_check,is_trial_production,lab_exe=?,assessor=?,designee=?,doc_ctrler=?,EVALUATION_RESULT=? ) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,? )",
				new Object[] { sampleEvaluation.getPno(), sampleEvaluation.getAppType(), sampleEvaluation.getUrgency(),
						sampleEvaluation.getMaterial(), sampleEvaluation.getSapCode(), sampleEvaluation.getAbCode(),
						sampleEvaluation.getMfgLotNo(), sampleEvaluation.getQty(), sampleEvaluation.getPack(),
						sampleEvaluation.getUnit(), sampleEvaluation.getMfr(), sampleEvaluation.getSupplier(),
						sampleEvaluation.getProvideCoa(), sampleEvaluation.getProvideSpec(),
						sampleEvaluation.getProvideTestMethod(), sampleEvaluation.getProvideSds(),
						sampleEvaluation.getProvideOthers(), sampleEvaluation.getNote(),
						sampleEvaluation.getApplicant(), sampleEvaluation.getAppDate(),
						sampleEvaluation.getReceiptUnit(), sampleEvaluation.getProjectCode(),
						sampleEvaluation.getProjectLeader(), sampleEvaluation.getNotifyNoCheck(),
						sampleEvaluation.getNotifyNoTrialProd(), sampleEvaluation.getQrNo(),
						sampleEvaluation.getIsCheck(), sampleEvaluation.getIsTrialProduction(),
						sampleEvaluation.getLabExe(), sampleEvaluation.getAssessor(), sampleEvaluation.getDesignee(),
						sampleEvaluation.getDocCtrler(), sampleEvaluation.getEvaluationResult() });
	}

	@Override
	public String update(SampleEvaluationTp sampleEvaluation) throws SQLException, Exception {
		return t.execFromPool(
				"UPDATE  sample_evaluation  SET app_type=?,urgency=?,material=?,sap_code=?,ab_code=?,mfg_lot_no=?,qty=?,pack=?,unit=?,mfr=?,supplier=?,provide_coa=?,provide_spec=?,provide_test_method=?,provide_sds=?,provide_others=?,note=?,applicant=?,app_date=?,receipt_unit=?,project_code=?,project_leader=?,notify_no_check=?,notify_no_trial_prod=?,qr_no=?,is_check=?,is_trial_production=?,lab_exe=?,assessor=?,designee=?,doc_ctrler=?,EVALUATION_RESULT=?"
						+ " where pno=?",
				new Object[] { sampleEvaluation.getAppType(), sampleEvaluation.getUrgency(),
						sampleEvaluation.getMaterial(), sampleEvaluation.getSapCode(), sampleEvaluation.getAbCode(),
						sampleEvaluation.getMfgLotNo(), sampleEvaluation.getQty(), sampleEvaluation.getPack(),
						sampleEvaluation.getUnit(), sampleEvaluation.getMfr(), sampleEvaluation.getSupplier(),
						sampleEvaluation.getProvideCoa(), sampleEvaluation.getProvideSpec(),
						sampleEvaluation.getProvideTestMethod(), sampleEvaluation.getProvideSds(),
						sampleEvaluation.getProvideOthers(), sampleEvaluation.getNote(),
						sampleEvaluation.getApplicant(), sampleEvaluation.getAppDate(),
						sampleEvaluation.getReceiptUnit(), sampleEvaluation.getProjectCode(),
						sampleEvaluation.getProjectLeader(), sampleEvaluation.getNotifyNoCheck(),
						sampleEvaluation.getNotifyNoTrialProd(), sampleEvaluation.getQrNo(),
						sampleEvaluation.getIsCheck(), sampleEvaluation.getIsTrialProduction(),
						sampleEvaluation.getLabExe(), sampleEvaluation.getAssessor(), sampleEvaluation.getDesignee(),
						sampleEvaluation.getDocCtrler(), sampleEvaluation.getEvaluationResult(),
						sampleEvaluation.getPno() });

	}

	@Override
	public String delete(String id) throws SQLException, Exception {
		return t.execFromPool("DELETE from sample_evaluation where pno=?", new Object[] { id });
	}

	@Override
	public SampleEvaluationTp findById(String id) throws SQLException, Exception {
		String[][] ret = t.queryFromPool("select * from sample_evaluation where pno='" + id + "'");
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
		String[][] ret = t.queryFromPool("select * from sample_evaluation_tp " + params);
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

	@Override
	public Class getClazz() {
		return SampleEvaluationTp.class;
	}

}
