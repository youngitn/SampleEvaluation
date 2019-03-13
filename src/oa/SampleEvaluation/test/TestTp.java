package oa.SampleEvaluation.test;

import jcx.db.talk;
import jcx.jform.hproc;
import oa.SampleEvaluation.model.SampleEvaluationPO;
import oa.SampleEvaluation.service.SampleEvaluationService;
import oa.SampleEvaluationCheck.model.SampleEvaluationCheckPO;
import oa.SampleEvaluationCheck.service.SampleEvaluationCheckService;
import oa.SampleEvaluationTp.model.SampleEvaluationTpPO;
import oa.SampleEvaluationTp.service.SampleEvaluationTpService;
import oa.global.BaseDao;
import oa.global.DtoUtil;

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
		SampleEvaluationTpPO tp = (SampleEvaluationTpPO) DtoUtil.setFormDataIntoDto(new SampleEvaluationTpPO(), this);
		tp.setOwnPno("iiiiiii" + "hTP");
		tp.setPno("PNOOOOO");
		service.upsert(tp);

		service = new SampleEvaluationCheckService(t);
		SampleEvaluationCheckPO ck = (SampleEvaluationCheckPO) DtoUtil.setFormDataIntoDto(new SampleEvaluationCheckPO(), this);
		ck.setOwnPno("GGGG" + "CHECK");
		service.upsert(ck);

		service = new SampleEvaluationService(t);
		SampleEvaluationPO se = (SampleEvaluationPO) DtoUtil.setFormDataIntoDto(new SampleEvaluationPO(), this);
		se.setPno("GGGG");
		service.upsert(se);
	}

	public void testFindById() throws Throwable {

		BaseDao bao = new SampleEvaluationTpService(t);
		SampleEvaluationTpPO s = (SampleEvaluationTpPO) bao.findById("iiiiiiihTP");
		System.out.println(s.getAppType());
//
	}

	public void testAdd() throws Throwable {
		SampleEvaluationTpPO s = new SampleEvaluationTpPO();
		s.setOwnPno("20189981TP");
		s.setQty("99");
		BaseDao bao = new SampleEvaluationTpService(t);
		bao.add(s);
//
	}

	public void testUpdate() throws Throwable {
		SampleEvaluationTpPO s = (SampleEvaluationTpPO) DtoUtil.getDtoById(SampleEvaluationTpPO.class, t,
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
