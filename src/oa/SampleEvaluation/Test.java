package oa.SampleEvaluation;

import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import com.ysp.util.DateTimeUtil;

import jcx.db.talk;
import jcx.jform.hproc;
import oa.SampleEvaluation.common.AddUtil;
import oa.SampleEvaluation.common.CommonDataObj;
import oa.SampleEvaluation.tableObject.SampleEvaluation;
import oa.SampleEvaluation.dao.SampleEvaluationDaoImpl;

import oa.SampleEvaluationCheck.dao.SampleEvaluationCheckFlowcDao;
import oa.SampleEvaluationCheck.tableObject.SampleEvaluationCheck;
import oa.SampleEvaluationCheck.tableObject.SampleEvaluationCheckFlowc;
import oa.SampleEvaluation.enums.*;

public class Test {

	public static void main(String[] args) throws Throwable {
		// TODO Auto-generated method stub
		talk t = new talk("mssql", "10.1.1.64", "ysphr", "1qaz@WSX", "ysphr");

		ArrayList<String> flist = new ArrayList<String>();
		flist.add("PNO");
		flist.add("APPLICANT");
		flist.add("APP_TYPE");
		flist.add("URGENCY");
		flist.add("APP_DATE");
		flist.add("'簽核狀態'");
		flist.add("'明細'");
		flist.add("'簽核紀錄'");
		System.out.println(AppType.valueOf("A"));
		System.out.println(Arrays.toString(flist.toArray(new String[0])));
//		SampleEvaluationCheck s = new SampleEvaluationCheck();
//		s.setAppType("a");
//		s.setUrgency("a");
//		s.setMaterial("a");
//		s.setSapCode("a");
//		s.setAbCode("a");
//		s.setMfgLotNo("a");
//		s.setQty("0");
//		s.setPack("a");
//		s.setUnit("a");
//		s.setMfr("a");
//		s.setSupplier("a");
//		s.setProvideCoa("a");
//		s.setProvideSpec("a");
//		s.setProvideTestMethod("a");
//		s.setProvideSds("d");
//		s.setProvideOthers("s");
//		s.setNote("NOTE");
//		s.setApplicant("51111");
//		s.setAppDate("a");
//		s.setReceiptUnit("a");
//		s.setProjectCode("a");
//		s.setProjectLeader("a");
//		s.setNotifyNoCheck("a");
//		s.setNotifyNoTrialProd("a");
//		s.setQrNo("a");
//		s.setIsCheck("a");
//		s.setIsTrialProduction("a");
//		s.setPno("5566");
//		new SampleEvaluationDaoImpl(t).add(s);
//		String time = DateTimeUtil.getApproveAddSeconds(0);
//		SampleEvaluationCheckFlowc secf = new SampleEvaluationCheckFlowc("5566");
//		secf.setF_INP_ID("52116");
//		secf.setF_INP_STAT("待處理");
//		secf.setF_INP_TIME(time);
//		SampleEvaluationCheckFlowcDao secfDao = new SampleEvaluationCheckFlowcDao();
//		secfDao.create(t.getConnectionFromPool(), secf);
		SampleEvaluationActionController s = new SampleEvaluationActionController();

		// s.cdo = new CommonDataObj("52116", t, "SAMPLE_EVALUATION", "PNO",
		// "APPLICANT");
		s.cdo.setTableApplicantFieldName("APPLICANT");
		s.cdo.setTableAppDateFieldName("APP_DATE");

		s.cdo.setQueryFieldNameEmpid("QUERY_EMP_ID");
		s.cdo.setQueryFieldNameBillId("QUERY_PNO");
		s.cdo.setQueryFieldNameStartAppDate("QUERY_REQ_SDATE");
		s.cdo.setQueryFieldNameEndAppDate("QUERY_REQ_EDATE");
		s.cdo.setQueryFieldNameFlowStatus("r_status");

		s.cdo.setQueryFieldValueEmpid("52116");
		s.cdo.setQueryFieldValueBillId("5211600001");
		s.cdo.setQueryFieldValueStartAppDate("20180901");
		s.cdo.setQueryFieldValueEndAppDate("20180930");
		s.cdo.setQueryFieldValueFlowStatus("");

		s.cdo.setFunctionName("樣品評估申請作業");
//		s.doQuery();
	}

}
