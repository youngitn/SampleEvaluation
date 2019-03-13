package oa.SampleEvaluation.test;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;
import java.util.ArrayList;

import jcx.db.talk;
import oa.SampleEvaluation.common.MailToolInApprove;
import oa.SampleEvaluation.model.QueryConditionDTO;
import oa.SampleEvaluation.model.SampleEvaluationPO;
import oa.SampleEvaluation.service.SampleEvaluationService;
import oa.global.DtoUtil;
import static org.junit.Assert.*;
import org.junit.Test;

import com.ysp.service.BaseService;

public class TestInsert {
	talk t = new talk("mssql", "10.1.1.64", "ysphr", "1qaz@WSX", "ysphr");

	/**
	 * 增加一筆紀錄到table SampleEvaluation
	 * 
	 * @throws SQLException
	 * @throws Exception
	 */
	@Test
	public void se() throws SQLException, Exception {
		SampleEvaluationPO sePO = new SampleEvaluationPO();
		SampleEvaluationService seService = new SampleEvaluationService(t);
		String pno = "20190998";
		sePO.setPno(pno);
		sePO.setMfrCountry("Taiwan");
		seService.add(sePO);
		sePO = (SampleEvaluationPO) seService.findById(pno);
		assertEquals(pno, sePO.getPno());
		System.out.println(sePO.getMfrCountry());
	}
	
	@Test
	public void testNotify() {
		
	}

}
