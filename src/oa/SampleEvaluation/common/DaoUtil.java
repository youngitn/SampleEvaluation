package oa.SampleEvaluation.common;

import oa.SampleEvaluation.dto.SampleEvaluation;
import oa.SampleEvaluation.dto.SampleEvaluationSubBaseDto;

public class DaoUtil {
	public static Object[] getTableValueArray(SampleEvaluationSubBaseDto s) {
		return new Object[] { s.getOwnPno(), s.getPno(), s.getAppType(), s.getUrgency(), s.getMaterial(),
				s.getSapCode(), s.getAbCode(), s.getMfgLotNo(), s.getQty(), s.getPack(), s.getUnit(), s.getMfr(),
				s.getSupplier(), s.getProvideCoa(), s.getProvideSpec(), s.getProvideTestMethod(), s.getProvideSds(),
				s.getProvideOthers(), s.getNote(), s.getApplicant(), s.getAppDate(), s.getReceiptUnit(),
				s.getProjectCode(), s.getProjectLeader(), s.getNotifyNoCheck(), s.getNotifyNoTrialProd(), s.getQrNo(),
				s.getIsCheck(), s.getIsTrialProduction(), s.getLabExe(), s.getAssessor(), s.getDesignee(),
				s.getDocCtrler(), s.getEvaluationResult(), s.getFileSpec(), s.getFileCoa(), s.getFileTestMethod(),
				s.getFileOthers(), s.getFileSds(), s.getFile1(), s.getFile2(), s.getFile3(), s.getFile4(), s.getFile5(),
				s.getFile6(), s.getFile7(), s.getFile8(), s.getFile9(), s.getFile10(), s.getFileEvaluationResult(),
				s.getAppReason() };
	}

	public static Object[] getTableValueArray(SampleEvaluation s) {
		return new Object[] { s.getPno(), s.getAppType(), s.getUrgency(), s.getMaterial(), s.getSapCode(),
				s.getAbCode(), s.getMfgLotNo(), s.getQty(), s.getPack(), s.getUnit(), s.getMfr(), s.getSupplier(),
				s.getProvideCoa(), s.getProvideSpec(), s.getProvideTestMethod(), s.getProvideSds(),
				s.getProvideOthers(), s.getNote(), s.getApplicant(), s.getAppDate(), s.getReceiptUnit(),
				s.getProjectCode(), s.getProjectLeader(), s.getNotifyNoCheck(), s.getNotifyNoTrialProd(), s.getQrNo(),
				s.getIsCheck(), s.getIsTrialProduction(), s.getLabExe(), s.getAssessor(), s.getDesignee(),
				s.getDocCtrler(), s.getEvaluationResult(), s.getFileSpec(), s.getFileCoa(), s.getFileTestMethod(),
				s.getFileOthers(), s.getFileSds(), s.getFile1(), s.getFile2(), s.getFile3(), s.getFile4(), s.getFile5(),
				s.getFile6(), s.getFile7(), s.getFile8(), s.getFile9(), s.getFile10(), s.getFileEvaluationResult(),
				s.getAppReason() };
	}
}
