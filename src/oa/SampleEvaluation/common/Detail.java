package oa.SampleEvaluation.common;

import java.sql.SQLException;

import oa.SampleEvaluation.controller.SampleEvaluationActionController;
import oa.SampleEvaluation.dao.SampleEvaluationService;
import oa.SampleEvaluation.dto.SampleEvaluation;
import oa.global.BaseDao;
import oa.global.DtoUtil;
import oa.global.FormInitUtil;
import oa.global.UIHidderString;

public class Detail {
	SampleEvaluationActionController controller;

	public Detail(SampleEvaluationActionController sampleEvaluationActionController) {
		// TODO Auto-generated constructor stub
	}

	public void show() throws SQLException, Exception {
		BaseDao bao = new SampleEvaluationService(controller.getTalk());
		SampleEvaluation s = (SampleEvaluation) bao.findById(controller.getValue("QUERY_LIST.PNO"));
		DtoUtil.setDtoDataToForm(s, this);
		FormInitUtil init = new FormInitUtil(controller);

		init.doDetailPageProcess();
		if (controller.getValue("IS_TRIAL_PRODUCTION").equals("1")) {
			controller.setVisible("ASSESSOR", true);
		}
		controller.setValue("DL",
				controller.getDeadLine(controller.getValue("APP_DATE"), controller.getValue("URGENCY")));

		controller.showSubFlowSignPeopleTab();
		controller.addScript(UIHidderString.hideDmakerAddButton() + UIHidderString.hideDmakerFlowPanel());
		if (controller.getUser().equals(controller.getValue("DESIGNEE").trim().split(" ")[0])) {

		}

	}

}
