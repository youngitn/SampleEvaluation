package oa.SampleEvaluation.common;

import java.sql.SQLException;

import oa.SampleEvaluation.controller.HprocImpl;
import oa.SampleEvaluation.dao.SampleEvaluationService;
import oa.SampleEvaluation.dto.SampleEvaluation;
import oa.global.BaseDao;
import oa.global.DtoUtil;
import oa.global.UIHidderString;

// TODO: Auto-generated Javadoc
/**
 * The Class Detail.
 */
public class Detail {

	/** The controller. */
	HprocImpl controller;

	/**
	 * Instantiates a new detail.
	 *
	 * @param sampleEvaluationActionController the sample evaluation action
	 *                                         controller
	 */
	public Detail(HprocImpl sampleEvaluationActionController) {

		if (sampleEvaluationActionController instanceof HprocImpl)
			controller = sampleEvaluationActionController;

	}

	/**
	 * Show.
	 *
	 * @throws SQLException the SQL exception
	 * @throws Exception    the exception
	 */
	public void show() throws SQLException, Exception {

		String pno = controller.getValue("QUERY_LIST.PNO");

		BaseDao bao = new SampleEvaluationService(controller.getTalk());
		SampleEvaluation s = (SampleEvaluation) bao.findById(pno);
		s.setDtoDataToForm(controller);
		// getValue必須在setDtoDataToForm之後,否則抓到的都是空值
		String appDate = controller.getValue("APP_DATE");
		String urgency = controller.getValue("URGENCY");
		String deadDate = controller.getDeadLine(appDate, urgency);
		FormInitUtil init = new FormInitUtil(controller);

		init.doDetailPageProcess();

		controller.setValue("DL", deadDate);

		controller.showSubFlowSignPeopleTab();
		controller.addScript(UIHidderString.hideDmakerAddButton() + UIHidderString.hideDmakerFlowPanel());

	}

}
