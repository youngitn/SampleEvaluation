package oa.SampleEvaluation;

import java.util.ArrayList;

import jcx.db.talk;
import jcx.jform.hproc;
import oa.SampleEvaluation.common.global.BaseDao;
import oa.SampleEvaluation.common.global.DtoUtil;
import oa.SampleEvaluation.dao.SampleEvaluationService;
import oa.SampleEvaluation.dto.SampleEvaluation;
import oa.SampleEvaluationTp.dto.SampleEvaluationTp;

public class Test extends hproc {
	talk t = new talk("mssql", "10.1.1.64", "ysphr", "1qaz@WSX", "ysphr");

	public static void main(String[] arg) throws Throwable {
		Test tes = new Test();
		// tes.test1();
//		tes.test2();
//		tes.test3();
//		tes.test4();
//		DtoUtil.getDeclaredXmakerFields(new SampleEvaluationTp());
		// tes.testUpdate();
		// tes.testAdd();
		tes.testFindById();

	}

	public void testFindById() throws Throwable {

		BaseDao bao = new SampleEvaluationService(t);
		SampleEvaluation s = (SampleEvaluation) bao.findById("20189988");
		System.out.println(s.getQty());
//
	}

	public void testAdd() throws Throwable {
		SampleEvaluation s = new SampleEvaluation();
		s.setPno("20189988");
		s.setQty("99");
		BaseDao bao = new SampleEvaluationService(t);
		bao.add(s);
//
	}

	public void testUpdate() throws Throwable {
		SampleEvaluation s = (SampleEvaluation) DtoUtil.getDbDataToDtoById(SampleEvaluation.class, t, "20189999");
		s.setQty("99.99");
		BaseDao bao = new SampleEvaluationService(t);
		bao.update(s);
//
	}

	public void test4() throws Throwable {
		talk t = new talk("mssql", "10.1.1.64", "ysphr", "1qaz@WSX", "ysphr");

		ArrayList<SampleEvaluationTp> ss = (ArrayList<SampleEvaluationTp>) DtoUtil
				.getDbDataToDtoList(new SampleEvaluationTp(), t);
		for (SampleEvaluationTp s : ss) {
			System.out.println("44444");
			System.out.println(s.getAppDate());
			System.out.println(s.getOwnPno());
		}
//
	}

	public void test3() throws Throwable {
		talk t = new talk("mssql", "10.1.1.64", "ysphr", "1qaz@WSX", "ysphr");

		ArrayList<SampleEvaluation> ss = (ArrayList<SampleEvaluation>) DtoUtil
				.getDbDataToDtoList(new SampleEvaluation(), t);
		for (SampleEvaluation s : ss) {
			System.out.println(s.getAppDate());
			System.out.println(s.getPno());
		}
//
	}

	public void test2() throws Throwable {
		talk t = new talk("mssql", "10.1.1.64", "ysphr", "1qaz@WSX", "ysphr");
		SampleEvaluation s = (SampleEvaluation) DtoUtil.getDbDataToDtoById(SampleEvaluation.class, t, "20180003");
		DtoUtil.setDtoDataToForm(s, this);
	}

	public void test1() throws Throwable {

	}

	@Override
	public String action(String arg0) throws Throwable {
		// TODO Auto-generated method stub
		// test1();
		test2();
		return null;
	}

}
