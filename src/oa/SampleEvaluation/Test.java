package oa.SampleEvaluation;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import org.nutz.dao.Dao;
import org.nutz.dao.impl.NutDao;
import org.nutz.dao.impl.SimpleDataSource;

import com.yungshingroup.utils.Fmt;

import jcx.db.talk;
import jcx.jform.hproc;
import oa.SampleEvaluation.common.SampleEvaluationDataObj;
import oa.SampleEvaluation.enums.AppType;

public class Test extends hproc {

	public void test1() throws Throwable {
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
		SampleEvaluationDataObj cdo = new SampleEvaluationDataObj(t, "SAMPLE_EVALUATION", "PNO", "APPLICANT");
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

		String str = "2010-5-27";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Date date = (Date) sdf.parse(str);

		Calendar calendar = Calendar.getInstance();

		calendar.setTime(date);

		calendar.set(Calendar.DAY_OF_MONTH, 0);
		String lastDay = sdf.format(calendar.getTime());
		System.out.println(lastDay);

//	        Param1Type param1;
//	        Param2Type param2;
//	        String className = "Class1";
//	        Class cl = Class.forName(className);
//	        Constructor con = cl.getConstructor(Param1Type.class, Param2Type.class);
//	        Object xyz = con.newInstance(param1, param2);
//		String a = "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?";
//		String b =  "PNO, REQ_DATE, REGIO, CUSTNO, CUSTNAME, SP1_PROCESS_CHK, D373_PNO, SP1_PROCESS_NOTE1, "+
//				"SP1_DATE, SP1_PROCESS_NOTE2, SP1_FILE, SP2_PROCESS_CHK, SP2_PROCESS_NOTE1, DEP10_NOTE1, D373_CHK, "+
//				"D373_NOTE1, BUDAT, XBLNR, ZUONR, INVAMT, BALAMT, DISAMT, ANAMT, ZTERM, UOVER_DAYS, ECTIME, AR_DAY,CUSTOM_PAYMNT_ADJUST";
//
//		int count = a.length() - a.replace(",", "").length();
//		int count1 = b.length() - b.replace(",", "").length();
//		System.out.println(count + "  " + count1);
//
//		SampleEvaluationSubBaseDto s = new SampleEvaluationCheck();
//		SampleEvaluationSubBaseDto s2 = new SampleEvaluationTp();
//		s.setPno("521169988");
//		s.setIsCheck("1");
//		s2.setIsCheck("1");
//
//		FlowcUtil.goSubFlow("Check", s, t, "填寫請驗單號");
//		FlowcUtil.goSubFlow("Tp", s2, t, "評估人員");
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
//		s.setNote("NOTRRE");
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
//		s.setPno("77eeee");
//		s.setOwnPno("77994");
//		SampleEvaluationCheckDaoImpl seDao = new SampleEvaluationCheckDaoImpl(t);
//		seDao.update(s);

		// 创建一个数据源
		SimpleDataSource dataSource = new SimpleDataSource();
		// talk t = new talk("mssql", "10.1.1.64", "ysphr", "1qaz@WSX", "ysphr");
		dataSource.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		dataSource.setJdbcUrl("jdbc:sqlserver://10.1.1.64;database=ysphr");

		dataSource.setUsername("ysphr");
		dataSource.setPassword("1qaz@WSX");
		try {
			Dao dao = new NutDao(dataSource);

			// 创建表
			dao.create(SampleEvaluationX.class, false); // false的含义是,如果表已经存在,就不要删除重建了.

			SampleEvaluationX p = new SampleEvaluationX();
			p.setPno("ABC");
			p.setSapCode("SSSSSSS");
			dao.insert(p);
			System.out.println(p.getPno());
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		// 创建一个NutDao实例,在真实项目中, NutDao通常由ioc托管, 使用注入的方式获得.

	}

	@Override
	public String action(String arg0) throws Throwable {
		// TODO Auto-generated method stub
		test1();
		return null;
	}

}
