package oa.SampleEvaluation.service;

import java.sql.SQLException;

import oa.SampleEvaluation.common.AddUtil;
import oa.SampleEvaluation.controller.HprocImpl;
import oa.SampleEvaluation.controller.SampleEvaluationActionController;
import oa.SampleEvaluation.model.SampleEvaluationPO;

/**
 * The Class TempSaveService.
 *
 * @author YoungCheng(u52116) 2019/3/19
 */
public class TempSaveService {
	
	/** The service. */
	SampleEvaluationService service;
	
	/** The se. */
	SampleEvaluationPO se;
	
	/** The h. */
	HprocImpl h;

	/**
	 * Instantiates a new temp save service.
	 *
	 * @param h [HprocImpl]
	 */
	public TempSaveService(HprocImpl h) {
		this.service = new SampleEvaluationService(h.getTalk());
		this.se = new SampleEvaluationPO();
		this.h = h;
	}

	/**
	 * Save.
	 *
	 * @throws SQLException the SQL exception
	 * @throws Exception the exception
	 */
	public void save() throws SQLException, Exception {
		se.getDataFromForm(h);
		if ("".equals(se.getQty()))
			se.setQty("0");
		se.setPno(h.getUser());
		service.add(se);
		h.message("資料已暫存");
	}

	/**
	 * Load.
	 *
	 * @throws SQLException the SQL exception
	 * @throws Exception the exception
	 */
	public void load() throws SQLException, Exception {
		se = (SampleEvaluationPO) service.findById(h.getUser());
		if (se == null) {
			h.message("無暫存紀錄");
		} else {
			se.setDataToForm(h);
			h.message("讀取成功");
		}
	}

	/**
	 * Update.
	 *
	 * @throws SQLException the SQL exception
	 * @throws Exception the exception
	 */
	public void update() throws SQLException, Exception {

		se.getDataFromForm(h);
		if ("".equals(se.getQty()))
			se.setQty("0");
		se.setPno(h.getUser());
		service.update(se);
		h.message("資料已覆蓋");
	}
}
