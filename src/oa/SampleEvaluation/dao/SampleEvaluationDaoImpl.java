package oa.SampleEvaluation.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import jcx.db.talk;
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

		return t.execFromPool(
				"insert into sample_evaluation  (PNO,app_type,urgency,material,sap_code,ab_code,mfg_lot_no,qty,pack,unit,mfr,supplier,provide_coa,provide_spec,provide_test_method,provide_sds,provide_others,note,applicant,app_date,receipt_unit,project_code,project_leader,notify_no_check,notify_no_trial_prod,qr_no,is_check,is_trial_production,lab_exe,assessor,designee,doc_ctrler ,evaluation_result,FILE_SPEC ,FILE_COA,FILE_TEST_METHOD,FILE_OTHERS,FILE_SDS,FILE_1,FILE_2,FILE_3,FILE_4,FILE_5,FILE_6,FILE_7,FILE_8,FILE_9,FILE_10,FILE_EVALUATION_RESULT) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,? )",
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
						sampleEvaluation.getDocCtrler(), sampleEvaluation.getEvaluationResult(),
						sampleEvaluation.getFileSpec(), sampleEvaluation.getFileCoa(),
						sampleEvaluation.getFileTestMethod(), sampleEvaluation.getFileOthers(),
						sampleEvaluation.getFileSds(), sampleEvaluation.getFile1(), sampleEvaluation.getFile2(),
						sampleEvaluation.getFile3(), sampleEvaluation.getFile4(), sampleEvaluation.getFile5(),
						sampleEvaluation.getFile6(), sampleEvaluation.getFile7(), sampleEvaluation.getFile8(),
						sampleEvaluation.getFile9(), sampleEvaluation.getFile10(),
						sampleEvaluation.getFileEvaluationResult() });
	}

	@Override
	public String update(SampleEvaluation sampleEvaluation) throws SQLException, Exception {
		return t.execFromPool(
				"UPDATE  sample_evaluation  SET app_type=?,urgency=?,material=?,sap_code=?,ab_code=?,mfg_lot_no=?,qty=?,pack=?,unit=?,mfr=?,supplier=?,provide_coa=?,provide_spec=?,provide_test_method=?,provide_sds=?,provide_others=?,note=?,applicant=?,app_date=?,receipt_unit=?,project_code=?,project_leader=?,notify_no_check=?,notify_no_trial_prod=?,qr_no=?,is_check=?,is_trial_production=?,lab_exe=?,assessor=?,designee=?,doc_ctrler=?,evaluation_result=?,FILE_SPEC=? ,FILE_COA=?,FILE_TEST_METHOD=?,FILE_OTHERS=?,FILE_SDS=?,FILE_1=?,FILE_2=?,FILE_3=?,FILE_4=?,FILE_5=?,FILE_6=?,FILE_7=?,FILE_8=?,FILE_9=?,FILE_10=?,FILE_EVALUATION_RESULT=?"
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
						sampleEvaluation.getFileSpec(), sampleEvaluation.getFileCoa(),
						sampleEvaluation.getFileTestMethod(), sampleEvaluation.getFileOthers(),
						sampleEvaluation.getFileSds(), sampleEvaluation.getFile1(), sampleEvaluation.getFile2(),
						sampleEvaluation.getFile3(), sampleEvaluation.getFile4(), sampleEvaluation.getFile5(),
						sampleEvaluation.getFile6(), sampleEvaluation.getFile7(), sampleEvaluation.getFile8(),
						sampleEvaluation.getFile9(), sampleEvaluation.getFile10(),
						sampleEvaluation.getFileEvaluationResult(), sampleEvaluation.getPno() });

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
