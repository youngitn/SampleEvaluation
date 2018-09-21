package oa.SampleEvaluationCheck.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import jcx.db.talk;
import oa.SampleEvaluationCheck.object.SampleEvaluationCheck;

/**
 * SampleEvaluation
 * 
 * */

public class SampleEvaluationCheckDao {

	talk t;

	public SampleEvaluationCheckDao(talk t) {
		this.t = t;
	}

	public String add(SampleEvaluationCheck s) throws SQLException, Exception {

		return t.execFromPool(
				"insert into sample_evaluation_check  ( OWN_PNO,PNO,app_type,urgency,material,sap_code,ab_code,mfg_lot_no,qty,pack,unit,mfr,supplier,provide_coa,provide_spec,provide_test_method,provide_sds,provide_others,note,applicant,app_date,receipt_unit,project_code,project_leader,notify_no_check,notify_no_trial_prod,qr_no,is_check,is_trial_production ) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,? )",
				new Object[] { s.getOwnPno(), s.getPno(), s.getAppType(), s.getUrgency(), s.getMaterial(),
						s.getSapCode(), s.getAbCode(), s.getMfgLotNo(), s.getQty(), s.getPack(), s.getUnit(),
						s.getMfr(), s.getSupplier(), s.getProvideCoa(), s.getProvideSpec(), s.getProvideTestMethod(),
						s.getProvideSds(), s.getProvideOthers(), s.getNote(), s.getApplicant(), s.getAppDate(),
						s.getReceiptUnit(), s.getProjectCode(), s.getProjectLeader(), s.getNotifyNoCheck(),
						s.getNotifyNoTrialProd(), s.getQrNo(), s.getIsCheck(), s.getIsTrialProduction() });
	}

	public String update(SampleEvaluationCheck s) throws SQLException, Exception {
		return t.execFromPool(
				"UPDATE  sample_evaluation_check  SET app_type=?,urgency=?,material=?,sap_code=?,ab_code=?,mfg_lot_no=?,qty=?,pack=?,unit=?,mfr=?,supplier=?,provide_coa=?,provide_spec=?,provide_test_method=?,provide_sds=?,provide_others=?,note=?,applicant=?,app_date=?,receipt_unit=?,project_code=?,project_leader=?,notify_no_check=?,notify_no_trial_prod=?,qr_no=?,is_check=?,is_trial_production=?"
						+ " where own_pno=?",
				new Object[] { s.getAppType(), s.getUrgency(), s.getMaterial(), s.getSapCode(), s.getAbCode(),
						s.getMfgLotNo(), s.getQty(), s.getPack(), s.getUnit(), s.getMfr(), s.getSupplier(),
						s.getProvideCoa(), s.getProvideSpec(), s.getProvideTestMethod(), s.getProvideSds(),
						s.getProvideOthers(), s.getNote(), s.getApplicant(), s.getAppDate(), s.getReceiptUnit(),
						s.getProjectCode(), s.getProjectLeader(), s.getNotifyNoCheck(), s.getNotifyNoTrialProd(),
						s.getQrNo(), s.getIsCheck(), s.getIsTrialProduction(), s.getOwnPno() });

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

}
