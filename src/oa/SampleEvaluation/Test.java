package oa.SampleEvaluation;

import java.lang.reflect.Method;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import com.ysp.service.BaseService;
import com.ysp.util.DateTimeUtil;
import com.yungshingroup.utils.Fmt;

import jcx.db.talk;
import jcx.jform.bBase;
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

		// s.cdo = new CommonDataObj("52116", t, "SAMPLE_EVALUATION", "PNO",
		// "APPLICANT");
		CommonDataObj cdo = new CommonDataObj(t, "SAMPLE_EVALUATION", "PNO", "APPLICANT"); 
		cdo.setTableApplicantFieldName("APPLICANT");
		cdo.setTableAppDateFieldName("APP_DATE");

//		cdo.setQueryFieldNameEmpid("QUERY_EMP_ID");
//		cdo.setQueryFieldNameBillId("QUERY_PNO");
//		cdo.setQueryFieldNameStartAppDate("QUERY_REQ_SDATE");
//		cdo.setQueryFieldNameEndAppDate("QUERY_REQ_EDATE");
//		cdo.setQueryFieldNameFlowStatus("r_status");
//
//		cdo.setQueryFieldValueEmpid("52116");
//		cdo.setQueryFieldValueBillId("5211600001");
//		cdo.setQueryFieldValueStartAppDate("20180901");
//		cdo.setQueryFieldValueEndAppDate("20180930");
//		cdo.setQueryFieldValueFlowStatus("");
		cdo.setFunctionName("樣品評估申請作業");
		
		

//		s.doQuery();
		
		 System.out.println(Fmt.nowDate(1).substring(0, 6));
		 
		 String str="2010-5-27";
		 SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");

		 Date date =(Date) sdf.parse(str);

		 Calendar calendar = Calendar.getInstance();

		 calendar.setTime(date);
		 
		
		 calendar.set(Calendar.DAY_OF_MONTH, 0);
	        String lastDay = sdf.format(calendar.getTime());
	        System.out.println(lastDay);
	       
	
	}

}
