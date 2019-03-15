package oa.SampleEvaluation.service;

import java.sql.SQLException;

import oa.SampleEvaluation.common.AddUtil;
import oa.SampleEvaluation.controller.HprocImpl;
import oa.SampleEvaluation.controller.SampleEvaluationActionController;
import oa.SampleEvaluation.model.SampleEvaluationPO;

public class TempSaveService {
	SampleEvaluationService service;
	SampleEvaluationPO se;
	HprocImpl h;

	public TempSaveService(HprocImpl h) {
		this.service = new SampleEvaluationService(h.getTalk());
		this.se = new SampleEvaluationPO();
		this.h = h;
	}

	public void save() throws SQLException, Exception {
		se.getFormData(h);
		if ("".equals(se.getQty()))
			se.setQty("0");
		se.setPno(h.getUser());
		service.add(se);
		h.message("資料已暫存");
	}

	public void load() throws SQLException, Exception {
		se = (SampleEvaluationPO) service.findById(h.getUser());
		if (se == null) {
			h.message("無暫存紀錄");
		} else {
			se.setDataToForm(h);
			h.message("讀取成功");
		}
	}

	public void update() throws SQLException, Exception {

		se.getFormData(h);
		if ("".equals(se.getQty()))
			se.setQty("0");
		se.setPno(h.getUser());
		service.update(se);
		h.message("資料已覆蓋");
	}
}
