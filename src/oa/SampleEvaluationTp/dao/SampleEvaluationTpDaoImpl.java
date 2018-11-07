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
	public String add(SampleEvaluationTp s) throws SQLException, Exception {

		return t.execFromPool(
				"insert into sample_evaluation_tp  ( OWN_PNO,PNO,app_type,urgency,material,sap_code,ab_code,mfg_lot_no,qty,pack,unit,mfr,supplier,provide_coa,provide_spec,provide_test_method,provide_sds,provide_others,note,applicant,app_date,receipt_unit,project_code,project_leader,notify_no_check,notify_no_trial_prod,qr_no,is_check,is_trial_production,lab_exe,assessor,designee,doc_ctrler ,evaluation_result,FILE_SPEC ,FILE_COA,FILE_TEST_METHOD,FILE_OTHERS,FILE_SDS,FILE_1,FILE_2,FILE_3,FILE_4,FILE_5,FILE_6,FILE_7,FILE_8,FILE_9,FILE_10,FILE_EVALUATION_RESULT) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,? )",
				new Object[] { s.getOwnPno(), s.getPno(), s.getAppType(), s.getUrgency(), s.getMaterial(),
						s.getSapCode(), s.getAbCode(), s.getMfgLotNo(), s.getQty(), s.getPack(), s.getUnit(),
						s.getMfr(), s.getSupplier(), s.getProvideCoa(), s.getProvideSpec(), s.getProvideTestMethod(),
						s.getProvideSds(), s.getProvideOthers(), s.getNote(), s.getApplicant(), s.getAppDate(),
						s.getReceiptUnit(), s.getProjectCode(), s.getProjectLeader(), s.getNotifyNoCheck(),
						s.getNotifyNoTrialProd(), s.getQrNo(), s.getIsCheck(), s.getIsTrialProduction(), s.getLabExe(),
						s.getAssessor(), s.getDesignee(), s.getDocCtrler(), s.getEvaluationResult(), s.getFileSpec(),
						s.getFileCoa(), s.getFileTestMethod(), s.getFileOthers(), s.getFileSds(), s.getFile1(),
						s.getFile2(), s.getFile3(), s.getFile4(), s.getFile5(), s.getFile6(), s.getFile7(),
						s.getFile8(), s.getFile9(), s.getFile10(), s.getFileEvaluationResult() });
	}

	@Override
	public String update(SampleEvaluationTp s) throws SQLException, Exception {
		return t.execFromPool(
				"UPDATE  sample_evaluation_tp  SET app_type=?,urgency=?,material=?,sap_code=?,ab_code=?,mfg_lot_no=?,qty=?,pack=?,unit=?,mfr=?,supplier=?,provide_coa=?,provide_spec=?,provide_test_method=?,provide_sds=?,provide_others=?,note=?,applicant=?,app_date=?,receipt_unit=?,project_code=?,project_leader=?,notify_no_check=?,notify_no_trial_prod=?,qr_no=?,is_check=?,is_trial_production=?,lab_exe=?,assessor=?,designee=?,doc_ctrler=?,evaluation_result=?,FILE_SPEC=? ,FILE_COA=?,FILE_TEST_METHOD=?,FILE_OTHERS=?,FILE_SDS=?,FILE_1=?,FILE_2=?,FILE_3=?,FILE_4=?,FILE_5=?,FILE_6=?,FILE_7=?,FILE_8=?,FILE_9=?,FILE_10=?,FILE_EVALUATION_RESULT=?"
						+ " where own_pno=?",
				new Object[] { s.getAppType(), s.getUrgency(), s.getMaterial(), s.getSapCode(), s.getAbCode(),
						s.getMfgLotNo(), s.getQty(), s.getPack(), s.getUnit(), s.getMfr(), s.getSupplier(),
						s.getProvideCoa(), s.getProvideSpec(), s.getProvideTestMethod(), s.getProvideSds(),
						s.getProvideOthers(), s.getNote(), s.getApplicant(), s.getAppDate(), s.getReceiptUnit(),
						s.getProjectCode(), s.getProjectLeader(), s.getNotifyNoCheck(), s.getNotifyNoTrialProd(),
						s.getQrNo(), s.getIsCheck(), s.getIsTrialProduction(), s.getLabExe(), s.getAssessor(),
						s.getDesignee(), s.getDocCtrler(), s.getEvaluationResult(), s.getFileSpec(), s.getFileCoa(),
						s.getFileTestMethod(), s.getFileOthers(), s.getFileSds(), s.getFile1(), s.getFile2(),
						s.getFile3(), s.getFile4(), s.getFile5(), s.getFile6(), s.getFile7(), s.getFile8(),
						s.getFile9(), s.getFile10(), s.getOwnPno(), s.getFileEvaluationResult() });

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

	@Override
	public Class getClazz() {
		return SampleEvaluationTp.class;
	}

}
