package oa.SampleEvaluation.service;

import java.sql.SQLException;

import oa.SampleEvaluation.common.AddUtil;
import oa.SampleEvaluation.controller.HprocImpl;
import oa.SampleEvaluation.model.SampleEvaluationPO;

public class TempSaveService {

	public void save(HprocImpl h) throws SQLException, Exception {

		SampleEvaluationService service = new SampleEvaluationService(h.getTalk());
		SampleEvaluationPO se = new SampleEvaluationPO();
		se.getFormData(h);
		if ("".equals(se.getQty()))
			se.setQty("0");
		se.setPno(h.getUser());
		service.add(se);
		h.message("資料已暫存");
	}

	public void load(HprocImpl h) throws SQLException, Exception {

		SampleEvaluationService service = new SampleEvaluationService(h.getTalk());
		SampleEvaluationPO se = new SampleEvaluationPO();
		se = (SampleEvaluationPO) service.findById(h.getUser());
		if (se == null) {
			h.message("無暫存紀錄");
		} else {
			se.setDataToForm(h);
			h.message("讀取成功");
		}
	}
}
