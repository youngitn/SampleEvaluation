package oa.SampleEvaluation.flow;

import jcx.db.talk;
import jcx.jform.bProcFlow;
import oa.SampleEvaluation.model.SampleEvaluationPO;
import oa.SampleEvaluation.service.SampleEvaluationService;
import oa.SampleEvaluationCheck.model.SampleEvaluationCheckPO;
import oa.SampleEvaluationCheck.service.SampleEvaluationCheckService;
import oa.SampleEvaluationTest.model.SampleEvaluationTestPO;
import oa.SampleEvaluationTest.service.SampleEvaluationTestService;
import oa.SampleEvaluationTp.model.SampleEvaluationTpPO;
import oa.SampleEvaluationTp.service.SampleEvaluationTpService;
import oa.global.BaseDao;
import oa.global.DtoUtil;

/**
 * The Class BaseSubApprove.
 *
 * @author YoungCheng(u52116) 2019/3/19
 */
public abstract class BaseSubApprove extends bProcFlow {
	
	/**
	 * Sync data.
	 *
	 * @return true, if successful
	 * @throws Throwable the throwable
	 */
	public boolean syncData() throws Throwable {
		// 回傳值為 true 表示執行接下來的流程處理
		// 回傳值為 false 表示接下來不執行任何流程處理
		// 傳入值 value 為 "核准"
		talk t = getTalk();
		fileItemSetChecker();
		BaseDao service = null;
		if ("1".equals(getValue("IS_TRIAL_PRODUCTION").trim())) {
			service = new SampleEvaluationTpService(t);
			SampleEvaluationTpPO tp = new SampleEvaluationTpPO();
			tp.getFormData(this);
			tp.setOwnPno(tp.getOwnPno());
			service.update(tp);
		}
		if ("1".equals(getValue("IS_CHECK").trim())) {
			service = new SampleEvaluationCheckService(t);
			SampleEvaluationCheckPO ck = new SampleEvaluationCheckPO();
			ck.getFormData(this);
			ck.setOwnPno(ck.getOwnPno());
			service.update(ck);
		}

		if ("1".equals(getValue("IS_TEST").trim())) {
			service = new SampleEvaluationTestService(t);
			SampleEvaluationTestPO test = new SampleEvaluationTestPO();
			test.getFormData(this);
			test.setOwnPno(test.getOwnPno());
			service.update(test);
		}

		service = new SampleEvaluationService(t);
		SampleEvaluationPO se = new SampleEvaluationPO();
		se.getFormData(this);
		service.update(se);

		return true;

	}

	/**
	 * 溫馨提醒 不傳入 回傳true/false.
	 *
	 * @param addStr [String]
	 * @return true, if successful
	 * @throws Exception the exception
	 */
	public boolean doReminder(String addStr) throws Exception {
		int result = showConfirmDialog(addStr + "確定送出表單？", "溫馨提醒", 0);
		if (result == 1) {
			message("已取消送出表單");
			return false;
		}
		String space = "";
		for (int i = 0; i < 16; i++) {
			space += "&emsp;";
		}
		percent(100, space + "表單送出中，請稍候...<font color=white>");
		message("簽核完成");
		return true;
	}

	/* (non-Javadoc)
	 * @see jcx.jform.bProcFlow#action(java.lang.String)
	 */
	@Override
	public boolean action(String arg0) throws Throwable {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * File item set checker.
	 */
	protected void fileItemSetChecker() {

		if (!getValue("FILE_SPEC").equals("") || !getValue("FILE_SPEC_NOTE").equals("")) {
			setValue("PROVIDE_SPEC", "1");
		}
		if (!getValue("FILE_COA").equals("") || !getValue("FILE_COANOTE").equals("")) {
			setValue("PROVIDE_COA", "1");
		}
		if (!getValue("FILE_SDS").equals("") || !getValue("FILE_SDS_NOTE").equals("")) {
			setValue("PROVIDE_SDS", "1");
		}
		if (!getValue("FILE_OTHERS").equals("") || !getValue("FILE_OTHERS_NOTE").equals("")) {
			setValue("PROVIDE_OTHERS", "1");
		}
		if (!getValue("FILE_TEST_METHOD").equals("") || !getValue("FILE_TEST_METHOD_NOTE").equals("")) {
			setValue("PROVIDE_TEST_METHOD", "1");
		}
	}
}
