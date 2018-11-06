package oa.SampleEvaluation;

import java.lang.reflect.Constructor;
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

import org.apache.commons.lang.StringUtils;

import com.ysp.service.BaseService;
import com.ysp.util.DateTimeUtil;
import com.yungshingroup.utils.Fmt;

import jcx.db.talk;
import jcx.jform.bBase;
import jcx.jform.hproc;
import oa.SampleEvaluation.common.AddUtil;
import oa.SampleEvaluation.common.CommonDataObj;
import oa.SampleEvaluation.dao.AbstractGenericDao;
import oa.SampleEvaluation.dao.AbstractGenericFlowcDao;
import oa.SampleEvaluation.dao.AbstractGenericFlowcHisDao;
import oa.SampleEvaluation.dao.SampleEvaluationDaoImpl;
import oa.SampleEvaluation.dto.AbstractGenericFlowcDto;
import oa.SampleEvaluation.dto.AbstractGenericFlowcHisDto;
import oa.SampleEvaluation.dto.SampleEvaluation;
import oa.SampleEvaluation.dto.SampleEvaluationSubBaseDto;
import oa.SampleEvaluationCheck.dao.SampleEvaluationCheckFlowcDaoImpl;
import oa.SampleEvaluationCheck.dao.SampleEvaluationCheckFlowcHisDaoImpl;
import oa.SampleEvaluationCheck.dto.SampleEvaluationCheck;
import oa.SampleEvaluationCheck.dto.SampleEvaluationCheckFlowc;
import oa.SampleEvaluationCheck.dto.SampleEvaluationCheckFlowcHis;
import oa.SampleEvaluationTp.dto.SampleEvaluationTp;
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
		String a = "PNO,app_type,urgency,material,sap_code,ab_code,mfg_lot_no,qty,pack,unit,mfr,supplier,provide_coa,provide_spec,provide_test_method,provide_sds,provide_others,note,applicant,app_date,receipt_unit,project_code,project_leader,notify_no_check,notify_no_trial_prod,qr_no,is_check,is_trial_production,lab_exe=?,assessor=?,designee=?,doc_ctrler=?,EVALUATION_RESULT=?";
		String b = " sampleEvaluation.getPno(), sampleEvaluation.getAppType(), sampleEvaluation.getUrgency(),\r\n" + 
				"						sampleEvaluation.getMaterial(), sampleEvaluation.getSapCode(), sampleEvaluation.getAbCode(),\r\n" + 
				"						sampleEvaluation.getMfgLotNo(), sampleEvaluation.getQty(), sampleEvaluation.getPack(),\r\n" + 
				"						sampleEvaluation.getUnit(), sampleEvaluation.getMfr(), sampleEvaluation.getSupplier(),\r\n" + 
				"						sampleEvaluation.getProvideCoa(), sampleEvaluation.getProvideSpec(),\r\n" + 
				"						sampleEvaluation.getProvideTestMethod(), sampleEvaluation.getProvideSds(),\r\n" + 
				"						sampleEvaluation.getProvideOthers(), sampleEvaluation.getNote(),\r\n" + 
				"						sampleEvaluation.getApplicant(), sampleEvaluation.getAppDate(),\r\n" + 
				"						sampleEvaluation.getReceiptUnit(), sampleEvaluation.getProjectCode(),\r\n" + 
				"						sampleEvaluation.getProjectLeader(), sampleEvaluation.getNotifyNoCheck(),\r\n" + 
				"						sampleEvaluation.getNotifyNoTrialProd(), sampleEvaluation.getQrNo(),\r\n" + 
				"						sampleEvaluation.getIsCheck(), sampleEvaluation.getIsTrialProduction(),\r\n" + 
				"						sampleEvaluation.getLabExe(), sampleEvaluation.getAssessor(), sampleEvaluation.getDesignee(),\r\n" + 
				"						sampleEvaluation.getDocCtrler(), sampleEvaluation.getEvaluationResult()";

		int count = a.length() - a.replace(",", "").length();
		int count1 = b.length() - b.replace(",", "").length();
		System.out.println(count + "  " + count1);
		 SampleEvaluationSubBaseDto s = new SampleEvaluationCheck();
		SampleEvaluationSubBaseDto s2 = new SampleEvaluationTp();
		s.setPno("521169999");
		s2.setPno("521169999");
		s.setOwnPno("52116CHECK");
		s2.setOwnPno("52116TP");
		goSubFlow("Check", s, "52116");
		goSubFlow("Tp", s2, "52116");
	}

	private static void goSubFlow(String type, SampleEvaluationSubBaseDto s, String designeEmpid)
			throws ClassNotFoundException, SQLException, Exception {
		/*******************************************************************************/
		talk t = new talk("mssql", "10.1.1.64", "ysphr", "1qaz@WSX", "ysphr");
		/*******************************************************************************/

		Class<?> subMainDao = Class.forName("oa.SampleEvaluation" + type + ".dao.SampleEvaluation" + type + "DaoImpl");
		Constructor<?>  DaoCon = subMainDao.getConstructor(talk.class);
		AbstractGenericDao Dao = (AbstractGenericDao) DaoCon.newInstance(t);

		if (Dao.findById(s.getOwnPno()) != null) {
			Dao.update(s);
		} else {
			// insert一筆子流程主檔
			Dao.add(s);

			AbstractGenericFlowcDto Dto = (AbstractGenericFlowcDto) Class
					.forName("oa.SampleEvaluation" + type + ".dto.SampleEvaluation" + type + "Flowc").newInstance();
			// AbstractGenericFlowcDto flowc = new
			// SampleEvaluationCheckFlowc(s.getOwnPno());
			Dto.setOwnPno(s.getOwnPno());
			String time = DateTimeUtil.getApproveAddSeconds(0);

			Dto.setF_INP_ID(designeEmpid);
			Dto.setF_INP_STAT("填寫請驗單號");
			Dto.setF_INP_TIME(time);

			AbstractGenericFlowcDao secfDao = (AbstractGenericFlowcDao) Class
					.forName("oa.SampleEvaluation" + type + ".dao.SampleEvaluation" + type + "FlowcDaoImpl")
					.newInstance();
			// AbstractGenericFlowcDao secfDao = new SampleEvaluationCheckFlowcDaoImpl();
			secfDao.create(t.getConnectionFromPool(), Dto);

			// 建立子流程FLOWC_HIS 物件 能夠顯示簽核歷史
			time = DateTimeUtil.getApproveAddSeconds(0);

			AbstractGenericFlowcHisDto his = (AbstractGenericFlowcHisDto) Class
					.forName("oa.SampleEvaluation" + type + ".dto.SampleEvaluation" + type + "FlowcHis").newInstance();
//			SampleEvaluationCheckFlowcHis his = new SampleEvaluationCheckFlowcHis(s.getOwnPno(), flowc.getF_INP_STAT(),
//					time);
			his.setF_INP_STAT(Dto.getF_INP_STAT());
			his.setOwnPno(s.getOwnPno());
			his.setF_INP_TIME(time);
			his.setF_INP_ID(designeEmpid);
			AbstractGenericFlowcHisDao secfhDao = (AbstractGenericFlowcHisDao) Class
					.forName("oa.SampleEvaluation" + type + ".dao.SampleEvaluation" + type + "FlowcHisDaoImpl")
					.newInstance();
			// AbstractGenericFlowcHisDao secfhDao = new
			// SampleEvaluationCheckFlowcHisDaoImpl();
			secfhDao.create(t.getConnectionFromPool(), his);

		}
		// 有請驗流程 寄出通知信

	}

}
