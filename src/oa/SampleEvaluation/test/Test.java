package oa.SampleEvaluation.test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jcx.db.talk;
import jcx.jform.hproc;
import oa.SampleEvaluation.common.DateTool;
import oa.SampleEvaluation.model.QueryConditionDTO;
import oa.SampleEvaluation.model.SampleEvaluationPO;
import oa.SampleEvaluation.service.QueryResultService;
import oa.SampleEvaluation.service.SampleEvaluationService;
import oa.global.BaseDao;
import oa.global.DtoUtil;
import oa.global.UserData;

/**
 * The Class Test.
 *
 * @author YoungCheng(u52116) 2019/3/19
 */
public class Test extends hproc {
	
	/** The t. */
	talk t = new talk("mssql", "10.1.1.64", "ysphr", "1qaz@WSX", "ysphr");

	/**
	 * The main method.
	 *
	 * @param arg the arguments
	 * @throws Throwable the throwable
	 */
	public static void main(String[] arg) throws Throwable {
		Test tes = new Test();
		talk t = new talk("mssql", "10.1.1.64", "ysphr", "1qaz@WSX", "ysphr");

		SampleEvaluationPO sePO = new SampleEvaluationPO();
		SampleEvaluationService seService = new SampleEvaluationService(t);
		sePO.setPno("20190006");
		QueryConditionDTO targetLikeThis = new QueryConditionDTO();
		targetLikeThis.setQ_PNO("20190001");
		String sqlWhereString = DtoUtil.queryConditionDtoConvertToSqlWhereString(targetLikeThis);
		ArrayList as = seService.findByCondition(sqlWhereString);
		System.out.println("@@@@@@@@@@@@@>"+sqlWhereString);
		System.out.println("@@@@@@@@@@@@@>"+as.size());

		
//		targetLikeThis.setQ_EMPID("52116");
//		targetLikeThis.setQ_SDATE("20190101");
//		targetLikeThis.setQ_URGENCY("A");
//		targetLikeThis.setQ_EDATE("20190109");
//		targetLikeThis.setQ_SAP_CODE("11111111111111");
//		targetLikeThis.setQ_MATERIAL("2222222222222222");
//		targetLikeThis.setQ_MFR("3333333333");
		targetLikeThis.setQ_STATUS("已結案");
		// 用條件執行查詢
		// s.doQuery();
		// 或取得SQL查詢字串

		String targetCondition = DtoUtil.queryConditionDtoConvertToSqlWhereString(targetLikeThis);
		QueryResultService qrs = new QueryResultService(t);
		String[][] al = (String[][]) qrs.getResultBySqlWhereString(targetCondition);
		/*
		 * 使用方式 ----> 在繼承hproc狀況下 String[][] list = new
		 * Query(this).get2DStringArrayResult();
		 */
		System.out.println("ret=>" + al.length);
		// System.out.println(al[0][0]);
		// System.out.println(al[0][1]);
		// tes.test1();
//		tes.test2();
//		tes.test3();
//		tes.test4();
//		DtoUtil.getDeclaredXmakerFields(new SampleEvaluationTp());
		// tes.testUpdate();
		// tes.testAdd();
		// tes.testtest();
//		String ret[][] = t
//				.queryFromPool("SELECT top 1 DEP_NAME  FROM USER_INOFFICE_INFO_VIEW WHERE DEP_NO='" + "264" + "'");
//		if (ret[0][0].contains("醫院")) {
//			System.out.println("YES");
//
//		}

		// System.out.println(DateTool.getBeforeWorkDate("20181220", 11, t));
		System.out.println("------->" + DateTool.getAfterWorkDate("20190103", 150, t));
		// System.out.println( DateTool.getBeforeWorkDateOver25Day("20181224",1, t));
//		SampleEvaluationTpFlowcHis secfh = new SampleEvaluationTpFlowcHis();
//		secfh.setOwnPno("sss");
//		secfh.setfInpId("sss");
//		secfh.setfInpStat("sss");
//		secfh.setfInpTime(DateTimeUtil.getApproveAddSeconds(0));
//		SampleEvaluationTpFlowcHisService service = new SampleEvaluationTpFlowcHisService(t);
//		service.upsert(secfh);
		List<?>[] lsa = new List<?>[10]; // OK, array of unbounded wildcard type.
		Object o = lsa;
		Object[] oa = (Object[]) o;
		List<Integer> li = new ArrayList<Integer>();
		li.add(new Integer(3));
		li.add(5555);
		oa[1] = li; // Correct.
		Integer i = (Integer) lsa[1].get(0); // OK
		System.out.println(i);
		System.out.println(lsa[1].get(1));
		System.out.println(((List) oa[1]).get(1));

		// System.out.println(aa);
		System.exit(0);
	}

	/**
	 * Gets the QueryRightSql.
	 *
	 * @return [String]
	 * @throws SQLException the SQL exception
	 * @throws Exception the exception
	 */
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

	/**
	 * Testtest.
	 *
	 * @throws Throwable the throwable
	 */
	public void testtest() throws Throwable {
		String condition = " where 1= 1 " + getQueryRightSql();
		System.out.println(condition);
		BaseDao dao = new SampleEvaluationService(t);
		ArrayList<SampleEvaluationPO> s = (ArrayList<SampleEvaluationPO>) dao.findByCondition(condition);
		String[][] array = new String[s.size()][8];
		System.out.println(s.size());
		int i = 0;

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

	/**
	 * Test find by id.
	 *
	 * @throws Throwable the throwable
	 */
	public void testFindById() throws Throwable {

		BaseDao bao = new SampleEvaluationService(t);
		SampleEvaluationPO s = (SampleEvaluationPO) bao.findById("20189988");
		System.out.println(s.getQty());
//
	}

	/**
	 * Test add.
	 *
	 * @throws Throwable the throwable
	 */
	public void testAdd() throws Throwable {
		SampleEvaluationPO s = new SampleEvaluationPO();
		s.setPno("20189988");
		s.setQty("99");
		BaseDao bao = new SampleEvaluationService(t);
		bao.add(s);
//
	}

	/**
	 * Test update.
	 *
	 * @throws Throwable the throwable
	 */
	public void testUpdate() throws Throwable {
		SampleEvaluationPO s = (SampleEvaluationPO) DtoUtil.getDtoById(SampleEvaluationPO.class, t, "20189999");
		s.setQty("99.99");
		BaseDao bao = new SampleEvaluationService(t);
		bao.update(s);
//
	}

	/**
	 * Test 4.
	 *
	 * @throws Throwable the throwable
	 */
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

	/**
	 * Test 3.
	 *
	 * @throws Throwable the throwable
	 */
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

	/**
	 * Test 2.
	 *
	 * @throws Throwable the throwable
	 */
	public void test2() throws Throwable {
		talk t = new talk("mssql", "10.1.1.64", "ysphr", "1qaz@WSX", "ysphr");
		SampleEvaluationPO s = (SampleEvaluationPO) DtoUtil.getDtoById(SampleEvaluationPO.class, t, "20180003");
		s.setDataToForm(this);
	}

	/**
	 * Test 1.
	 *
	 * @throws Throwable the throwable
	 */
	public void test1() throws Throwable {

	}

	/* (non-Javadoc)
	 * @see jcx.jform.hproc#action(java.lang.String)
	 */
	@Override
	public String action(String arg0) throws Throwable {
		// TODO Auto-generated method stub
		// test1();
		testtest();
		return null;
	}

}
