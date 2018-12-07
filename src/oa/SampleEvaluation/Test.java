package oa.SampleEvaluation;

import java.sql.SQLException;
import java.util.ArrayList;

import jcx.db.talk;
import jcx.jform.hproc;
import oa.SampleEvaluation.common.SampleEvaluationQuerySpec;
import oa.SampleEvaluation.common.global.BaseDao;
import oa.SampleEvaluation.common.global.DtoUtil;
import oa.SampleEvaluation.common.global.UserData;
import oa.SampleEvaluation.dao.SampleEvaluationFlowcService;
import oa.SampleEvaluation.dao.SampleEvaluationService;
import oa.SampleEvaluation.dto.SampleEvaluation;
import oa.SampleEvaluation.dto.SampleEvaluationFlowc;
import oa.SampleEvaluationCheck.dao.SampleEvaluationCheckFlowcService;
import oa.SampleEvaluationCheck.dto.SampleEvaluationCheckFlowc;
import oa.SampleEvaluationTp.dao.SampleEvaluationTpFlowcService;
import oa.SampleEvaluationTp.dto.SampleEvaluationTpFlowc;

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
		tes.testtest();

	}

	// 取得查詢權限SQL條件
	public String getQueryRightSql() throws SQLException, Exception {
		String sql = "";
		String loginUserId = "52116";
		// SampleEvaluationQuerySpec qc = (SampleEvaluationQuerySpec)
		// cdo.getQuerySpec();
		String queryEmpid = "";
		String queryDepNo = "";
		boolean isAdmin = false;
		String queryEmpDepNo = "";
		boolean isSameDepNoInPurch = false;

		// 判斷登入用戶與所要查詢的起單人是否同單位
		// 目的是讓採購一課or二課 同課間可互查紀錄
		if (queryEmpid != null && !queryEmpid.equals("")) {
			UserData queryUser = new UserData(queryEmpid, t);
			queryEmpDepNo = queryUser.getDepNo();
			isSameDepNoInPurch = true;
		} else if (!queryDepNo.equals("")) {
			isSameDepNoInPurch = true;
		}

		// 如果不是系統管理員群組人員或要查詢得起單人非同課單位，須加入查詢權限
		if (!isAdmin && !isSameDepNoInPurch) {

			sql += " and (";
			// 申請人為自己部屬
			sql += "(" + "Applicant" + " in (select handle_user from hr_condition where empid = '" + loginUserId
					+ "')) ";
			// 自己簽核過的單子
			sql += "or (" + "pno" + " in (select distinct " + "pno" + " from " + "Sample_Evaluation"
					+ "_FLOWC_HIS where F_INP_ID = '" + loginUserId + "')) ";

			sql += ") ";

		}
		return sql;
	}

	public void testtest() throws Throwable {
		String condition = " where 1= 1 "+getQueryRightSql();
		System.out.println(condition);
		BaseDao dao = new SampleEvaluationService(t);
		ArrayList<SampleEvaluation> s = (ArrayList<SampleEvaluation>) dao.findByCondition(condition);
		String[][] array = new String[s.size()][8];
		System.out.println(s.size());
		int i = 0;
		for (SampleEvaluation sampleEvaluation : s) {
			// array[i][0] = sampleEvaluation.getPno();
			SampleEvaluationFlowc sampleEvaluationFlow = (SampleEvaluationFlowc) new SampleEvaluationFlowcService(t)
					.findById(sampleEvaluation.getPno());
			SampleEvaluationTpFlowc sampleEvaluationTpFlow = null;
			SampleEvaluationCheckFlowc sampleEvaluationCheckFlow = null;
			if (sampleEvaluation.getIsCheck().equals("")) {
				sampleEvaluationCheckFlow = (SampleEvaluationCheckFlowc) new SampleEvaluationCheckFlowcService(t)
						.findById(sampleEvaluation.getPno() + "CHECK");
			}
			if (sampleEvaluation.getIsTrialProduction().equals("")) {
				sampleEvaluationTpFlow = (SampleEvaluationTpFlowc) new SampleEvaluationTpFlowcService(t)
						.findById(sampleEvaluation.getPno() + "TP");
			}

			String tp = sampleEvaluationTpFlow != null ? sampleEvaluationTpFlow.getfInpStat() : "無";
			String ck = sampleEvaluationCheckFlow != null ? sampleEvaluationCheckFlow.getfInpStat() : "無";
			String type = sampleEvaluation.getAppType();
			String u = sampleEvaluation.getUrgency();
			String appDate = sampleEvaluation.getAppDate();
			System.out.println(sampleEvaluation.getPno() + " " + type + " " + u + " " + appDate + " "
					+ sampleEvaluationFlow.getfInpStat() + " " + tp + " " + ck + " " + "明細" + "簽核紀錄");
			array[i][0] = sampleEvaluation.getPno();
			array[i][1] = sampleEvaluation.getPno();
			array[i][2] = type;
			array[i][3] = u;
			array[i][4] = appDate;
			array[i][5] = sampleEvaluationFlow.getfInpStat() + " 請驗:" + tp + " 試製:" + ck;
			array[i][6] = "明細";
			array[i][7] = "簽核紀錄";
			i++;
		}
		// String[][] aaa = t.queryFromPool("select * from sample_Evaluation");
		System.out.println();
		for (String[] strings : array) {
			System.out.println(strings[0]);

		}
		try {
			setTableData("QUERY_LIST", array);
		} catch (NullPointerException e) {
			// TODO: handle exception
			System.out.println("");
		}

//
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
//		talk t = new talk("mssql", "10.1.1.64", "ysphr", "1qaz@WSX", "ysphr");
//
//		ArrayList<SampleEvaluationTp> ss = (ArrayList<SampleEvaluationTp>) DtoUtil
//				.getDbDataListToDtoListByCondition(null, t, "");
//		for (SampleEvaluationTp s : ss) {
//			System.out.println("44444");
//			System.out.println(s.getAppDate());
//			System.out.println(s.getOwnPno());
//		}
//
	}

	public void test3() throws Throwable {
//		talk t = new talk("mssql", "10.1.1.64", "ysphr", "1qaz@WSX", "ysphr");
//
//		ArrayList<SampleEvaluation> ss = (ArrayList<SampleEvaluation>) DtoUtil
//				.getDbDataToDtoList(new SampleEvaluation(), t);
//		for (SampleEvaluation s : ss) {
//			System.out.println(s.getAppDate());
//			System.out.println(s.getPno());
//		}
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
		testtest();
		return null;
	}

}
