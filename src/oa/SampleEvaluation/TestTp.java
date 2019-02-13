package oa.SampleEvaluation;

import jcx.db.talk;
import jcx.jform.hproc;
import oa.SampleEvaluation.common.global.BaseDao;
import oa.SampleEvaluation.common.global.DtoUtil;
import oa.SampleEvaluation.dao.SampleEvaluationService;
import oa.SampleEvaluation.dto.SampleEvaluation;
import oa.SampleEvaluationCheck.dao.SampleEvaluationCheckService;
import oa.SampleEvaluationCheck.dto.SampleEvaluationCheck;
import oa.SampleEvaluationTp.dao.SampleEvaluationTpService;
import oa.SampleEvaluationTp.dto.SampleEvaluationTp;
import oa.SampleEvaluationTp.dto.TestSeTP;

public class TestTp extends hproc {
	talk t = new talk("mssql", "10.1.1.64", "ysphr", "1qaz@WSX", "ysphr");

	public static void main(String[] arg) throws Throwable {
		TestTp tes = new TestTp();
		tes.testSync();
		//tes.testFindById();
		System.exit(0);

	}

	public void testSync() throws Throwable {

		BaseDao service = new SampleEvaluationTpService(t);
		SampleEvaluationTp tp = (SampleEvaluationTp) DtoUtil.setFormDataToDto(new SampleEvaluationTp(), this);
		tp.setOwnPno("iiiiiii" + "hTP");
		tp.setPno("PNOOOOO");
		service.upsert(tp);

		service = new SampleEvaluationCheckService(t);
		SampleEvaluationCheck ck = (SampleEvaluationCheck) DtoUtil.setFormDataToDto(new SampleEvaluationCheck(), this);
		ck.setOwnPno("GGGG" + "CHECK");
		service.upsert(ck);

		service = new SampleEvaluationService(t);
		SampleEvaluation se = (SampleEvaluation) DtoUtil.setFormDataToDto(new SampleEvaluation(), this);
		se.setPno("GGGG");
		service.upsert(se);
	}

	public void testFindById() throws Throwable {

		BaseDao bao = new SampleEvaluationTpService(t);
		SampleEvaluationTp s = (SampleEvaluationTp) bao.findById("iiiiiiihTP");
		System.out.println(s.getAppType());
//
	}

	public void testAdd() throws Throwable {
		SampleEvaluationTp s = new SampleEvaluationTp();
		s.setOwnPno("20189981TP");
		s.setQty("99");
		BaseDao bao = new SampleEvaluationTpService(t);
		bao.add(s);
//
	}

	public void testUpdate() throws Throwable {
		SampleEvaluationTp s = (SampleEvaluationTp) DtoUtil.getDbDataToDtoById(SampleEvaluationTp.class, t,
				"20189981TP");
		s.setQty("99.99");
		BaseDao bao = new SampleEvaluationService(t);
		bao.update(s);
//
	}

	@Override
	public String action(String arg0) throws Throwable {
		// TODO Auto-generated method stub
		// test1();
		testSync();
		// testFindById();
		return null;
	}

}
