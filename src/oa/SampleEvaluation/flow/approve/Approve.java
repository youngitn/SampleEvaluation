package oa.SampleEvaluation.flow.approve;

import jcx.jform.bProcFlow;
import oa.SampleEvaluation.flow.approve.gateEnum.*;
import java.beans.ConstructorProperties;
import java.io.*;
import java.sql.SQLException;
import java.util.*;

import com.ysp.field.Mail;
import com.ysp.service.BaseService;
import com.ysp.service.MailService;
import com.ysp.util.DateTimeUtil;

import jcx.util.*;
import oa.SampleEvaluation.EmailNotify;
import oa.SampleEvaluation.common.AddUtil;
import oa.SampleEvaluation.common.UserData;
import oa.SampleEvaluation.dao.SampleEvaluationDaoImpl;
import oa.SampleEvaluation.enums.AppType;
import oa.SampleEvaluation.enums.Urgency;
import oa.SampleEvaluation.tableObject.SampleEvaluation;
import oa.SampleEvaluationCheck.dao.SampleEvaluationCheckDao;
import oa.SampleEvaluationCheck.dao.SampleEvaluationCheckFlowcDao;
import oa.SampleEvaluationCheck.dao.SampleEvaluationCheckFlowcHisDao;
import oa.SampleEvaluationCheck.tableObject.SampleEvaluationCheck;
import oa.SampleEvaluationCheck.tableObject.SampleEvaluationCheckFlowc;
import oa.SampleEvaluationCheck.tableObject.SampleEvaluationCheckFlowcHis;
import jcx.db.*;

public class Approve extends bProcFlow {

	String table_name;
	String state;
	talk t;

	public boolean action(String value) throws Throwable {
		// 回傳值為 true 表示執行接下來的流程處理
		// 回傳值為 false 表示接下來不執行任何流程處理
		// 傳入值 value 為 "核准"

		state = getState();
		t = getTalk();
		SampleEvaluation s = null;
		SampleEvaluationCheck sc = null;
		String alertStr = "";
		switch (FlowState.valueOf(state)) {
		case 組長:

			// 進行相關判斷並建立送出表單時顯示的提醒文字
			alertStr = buildApproveConfirmMsgStr();

			if (getValue("IS_CHECK").trim().equals("1") && getValue("LAB_EXE").trim().equals("")) {
				message("請選擇實驗室經辦人員");
				return false;
			}

			if (getValue("IS_TRIAL_PRODUCTION").trim().equals("1")
					&& (getValue("LAB_EXE").trim().equals("") || getValue("ASSESSOR").trim().equals(""))) {
				message("請選擇試製評估人員與實驗室經辦人員");
				return false;
			}

			// 更新主表請驗和試製評估勾選欄位
			// 更新主表 評估人員和實驗室經辦
			s = setAllValue(new SampleEvaluation());
			new SampleEvaluationDaoImpl(t).update(s);

			// 建立子流程FLOWC物件 使其出現在待簽核表單列表
			if (getValue("IS_CHECK").trim().equals("1")) {

				sc = setAllValue(new SampleEvaluationCheck());

				SampleEvaluationCheckDao checkDao = new SampleEvaluationCheckDao(t);
				if (checkDao.findById(sc.getOwnPno()) != null) {
					checkDao.update(sc);
				} else {
					// insert一筆子流程主檔
					new SampleEvaluationCheckDao(t).add(sc);

					SampleEvaluationCheckFlowc flowc = new SampleEvaluationCheckFlowc(sc.getOwnPno());
					String time = DateTimeUtil.getApproveAddSeconds(0);

					// 取得被分案組長empid
					String[] designee = getValue("DESIGNEE").trim().split(" ");
					flowc.setF_INP_ID(designee[0]);
					flowc.setF_INP_STAT("填寫請驗單號");
					flowc.setF_INP_TIME(time);
					SampleEvaluationCheckFlowcDao secfDao = new SampleEvaluationCheckFlowcDao();
					secfDao.create(getTalk().getConnectionFromPool(), flowc);

					// 建立子流程FLOWC_HIS 物件 能夠顯示簽核歷史
					time = DateTimeUtil.getApproveAddSeconds(0);
					SampleEvaluationCheckFlowcHis his = new SampleEvaluationCheckFlowcHis(sc.getOwnPno(),
							flowc.getF_INP_STAT(), time);

					his.setF_INP_ID(designee[0]);
					SampleEvaluationCheckFlowcHisDao secfhDao = new SampleEvaluationCheckFlowcHisDao();
					secfhDao.create(getTalk().getConnectionFromPool(), his);

				}
				// 有請驗流程 寄出通知信
				BaseService service = new BaseService(this);
				MailService mailService = new MailService(service);
				String title = "簽核通知：" + this.getFunctionName() + "_請驗流程" + "( 單號：" + getValue("PNO") + " )";
				// Mail to
				String[] u = getValue("DESIGNEE").trim().split(" ");
				String[] usr = { getEmail(u[0]) };

				// 內容
				String content = buildContent();

				mailService.sendMailbccUTF8(usr, title, content, null, "", Mail.MAIL_HTML_CONTENT_TYPE);

			}

			break;
		case 試製單號填寫:
			// 更新主表試製單號欄位
			if (!getValue("NOTIFY_NO_TRIAL_PROD").trim().equals("")) {
				// 更新主表試製單號欄位
				s = setAllValue(new SampleEvaluation());
				new SampleEvaluationDaoImpl(t).update(s);

				// 更新子流程主表試製單號欄位
				sc = setAllValue(new SampleEvaluationCheck());
				new SampleEvaluationCheckDao(t).update(sc);
			} else {
				message("請輸入試製通知單號");
				return false;
			}

			break;
		case 受理單位主管分案:
			// 更新主表分案人欄位
			if (!getValue("DESIGNEE").trim().equals("")) {
				t.execFromPool("UPDATE  sample_evaluation  SET DESIGNEE=?" + " where pno=?",
						new Object[] { getValue("DESIGNEE"), getValue("PNO") });
				t.execFromPool("UPDATE  sample_evaluation_check  SET DESIGNEE=?" + " where own_pno=?",
						new Object[] { getValue("DESIGNEE"), getValue("OWN_PNO") });
			} else {
				message("請選擇 受理單位指派人員 欄位");
				return false;
			}

			break;
		case 待處理:
			// 更新主表分案人欄位
			s = new SampleEvaluation();
			s = setAllValue(s);
			new SampleEvaluationDaoImpl(t).update(s);
			break;
		default:
			break;
		}
		doReminder(alertStr);
		message("簽核完成");
		return true;
	}

	private String buildApproveConfirmMsgStr() {
		String alertStr = "";
		if (getValue("IS_CHECK").equals("0")) {

			alertStr += "將不會進行請驗流程;<br>";
		} else {

			alertStr += "將進行請驗流程;<br>";
		}
		if (getValue("IS_TRIAL_PRODUCTION").equals("0")) {

			alertStr += "將不會進行試製評估流程;<br>";
		} else {

			alertStr += "將進行試製評估流程;<br>";
		}
		if (getValue("IS_TRIAL_PRODUCTION").equals("0") && getValue("IS_CHECK").equals("0")) {
			alertStr = "皆未勾選\"是否進行請驗/試製評估流程\"中的任何項目<br> 送出後將直接結案;<br>";
		}
		return alertStr;
	}

	private SampleEvaluationCheck setAllValue(SampleEvaluationCheck s) {
		s.setAppType(getValue("APP_TYPE"));
		s.setUrgency(getValue("URGENCY"));
		s.setMaterial(getValue("MATERIAL"));
		s.setSapCode(getValue("SAP_CODE"));
		s.setAbCode(getValue("AB_CODE"));
		s.setMfgLotNo(getValue("MFG_LOT_NO"));
		s.setQty(getValue("QTY"));
		s.setPack(getValue("PACK"));
		s.setUnit(getValue("UNIT"));
		s.setMfr(getValue("MFR"));
		s.setSupplier(getValue("SUPPLIER"));
		s.setProvideCoa(getValue("PROVIDE_COA"));
		s.setProvideSpec(getValue("PROVIDE_SPEC"));
		s.setProvideTestMethod(getValue("PROVIDE_TEST_METHOD"));
		s.setProvideSds(getValue("PROVIDE_SDS"));
		s.setProvideOthers(getValue("PROVIDE_OTHERS"));
		s.setNote(getValue("NOTE"));
		s.setApplicant(getValue("APPLICANT"));
		s.setAppDate(getValue("APP_DATE"));
		s.setReceiptUnit(getValue("RECEIPT_UNIT"));
		s.setProjectCode(getValue("PROJECT_CODE"));
		s.setProjectLeader(getValue("PROJECT_LEADER"));
		s.setNotifyNoCheck(getValue("NOTIFY_NO_CHECK"));
		s.setNotifyNoTrialProd(getValue("NOTIFY_NO_TRIAL_PROD"));
		s.setQrNo(getValue("QR_NO"));
		s.setIsCheck(getValue("IS_CHECK"));
		s.setIsTrialProduction(getValue("IS_TRIAL_PRODUCTION"));
		s.setLabExe(getValue("LAB_EXE").trim());
		s.setAssessor(getValue("ASSESSOR").trim());
		s.setDesignee(getValue("DESIGNEE").trim());
		s.setPno(getValue("PNO"));
		// 子流程 ID = 表單單號+CHECK
		String ownPno = getValue("PNO") + "CHECK";
		// 為子流程主檔填入ID
		s.setOwnPno(ownPno);
		return s;
	}

	private SampleEvaluation setAllValue(SampleEvaluation s) {
		s.setAppType(getValue("APP_TYPE"));
		s.setUrgency(getValue("URGENCY"));
		s.setMaterial(getValue("MATERIAL"));
		s.setSapCode(getValue("SAP_CODE"));
		s.setAbCode(getValue("AB_CODE"));
		s.setMfgLotNo(getValue("MFG_LOT_NO"));
		s.setQty(getValue("QTY"));
		s.setPack(getValue("PACK"));
		s.setUnit(getValue("UNIT"));
		s.setMfr(getValue("MFR"));
		s.setSupplier(getValue("SUPPLIER"));
		s.setProvideCoa(getValue("PROVIDE_COA"));
		s.setProvideSpec(getValue("PROVIDE_SPEC"));
		s.setProvideTestMethod(getValue("PROVIDE_TEST_METHOD"));
		s.setProvideSds(getValue("PROVIDE_SDS"));
		s.setProvideOthers(getValue("PROVIDE_OTHERS"));
		s.setNote(getValue("NOTE"));
		s.setApplicant(getValue("APPLICANT"));
		s.setAppDate(getValue("APP_DATE"));
		s.setReceiptUnit(getValue("RECEIPT_UNIT"));
		s.setProjectCode(getValue("PROJECT_CODE"));
		s.setProjectLeader(getValue("PROJECT_LEADER"));
		s.setNotifyNoCheck(getValue("NOTIFY_NO_CHECK"));
		s.setNotifyNoTrialProd(getValue("NOTIFY_NO_TRIAL_PROD"));
		s.setQrNo(getValue("QR_NO"));
		s.setIsCheck(getValue("IS_CHECK"));
		s.setIsTrialProduction(getValue("IS_TRIAL_PRODUCTION"));
		s.setLabExe(getValue("LAB_EXE").trim());
		s.setAssessor(getValue("ASSESSOR").trim());
		s.setDesignee(getValue("DESIGNEE").trim());
		s.setPno(getValue("PNO"));
		return s;
	}

	/**
	 * 溫馨提醒 不傳入 回傳true/false
	 */
	private boolean doReminder(String addStr) throws Exception {
		int result = showConfirmDialog(addStr + "確定送出表單？", "溫馨提醒", 0);
		if (result == 1) {
			message("已取消送出表單");
			return false;
		}
		String space = "";
		for (int i = 0; i < 16; i++) {
			space += "&emsp;";
		}
		percent(100, space + "表單送出中，請稍候...<font color=white>");
		return true;
	}

	protected String buildContent() {
		// TODO Auto-generated method stub
		// 內容
		UserData appUser = null;
		try {
			// TODO for TEST
			appUser = new UserData(getValue("APPLICANT"), getTalk());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String name = appUser.getHecname();
		String dep_name = appUser.getDep_name();
		String content = "";
		content += "您有一筆 " + getFunctionName() + " 等待簽核；" + Mail.HTML_LINE_BREAK;
		content += "請進入 Emaker 應用服務系統 " + Mail.getOaSystemUrl() + " 簽核。" + Mail.HTML_LINE_BREAK;
		content += Mail.HTML_LINE_BREAK;
		content += Mail.MAIL_CONTENT_LINE_WORD + Mail.HTML_LINE_BREAK;
		content += "單號：" + getValue("PNO") + Mail.HTML_LINE_BREAK;
		content += "申請日期：" + convert.FormatedDate(getValue("APP_DATE"), "/") + Mail.HTML_LINE_BREAK;
		content += "申請人：" + dep_name + " " + name + "(" + appUser.getEmpid() + ")" + Mail.HTML_LINE_BREAK;
		content += "申請類型：" + AppType.getAppType(getValue("APP_TYPE")) + Mail.HTML_LINE_BREAK;
		content += "急迫性：" + Urgency.getUrgency(getValue("URGENCY")) + Mail.HTML_LINE_BREAK;
		content += "物料名稱：" + getValue("MATERIAL") + Mail.HTML_LINE_BREAK;
		try {
			content += "受理單位：" + getDepName(getValue("RECEIPT_UNIT")) + Mail.HTML_LINE_BREAK;
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		content += "計畫代號：" + getValue("PROJECT_CODE") + Mail.HTML_LINE_BREAK;
		String projectLeaderLine = "";
		if (!getValue("PROJECT_LEADER").trim().equals("")) {
			UserData u = null;
			try {
				u = new UserData(getValue("PROJECT_LEADER").trim(), getTalk());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			projectLeaderLine = u.getDep_name() + u.getHecname() + "(" + u.getEmpid() + ") ";
		}

		content += "計畫主持人：" + projectLeaderLine + Mail.HTML_LINE_BREAK;
		content += "是否進行請驗/試製流程：" + buildApproveConfirmMsgStr() + Mail.HTML_LINE_BREAK;
		content += "==========================" + Mail.HTML_LINE_BREAK;
		content += "此郵件由系統自動發出，請勿回信，謝謝!!" + Mail.HTML_LINE_BREAK;
		content += "意見記錄：" + Mail.HTML_LINE_BREAK;
		content += "" + getHisOpinion() + Mail.HTML_LINE_BREAK;
		return content;
	}

	// 用單位代碼抓→單位名稱
	protected String getDepName(String dep_no) throws SQLException, Exception {
		String sql = "select DEP_NAME from DEP_ACTIVE_VIEW where DEP_NO = '" + dep_no + "'";
		String rec[][] = getTalk().queryFromPool(sql);
		if (rec.length > 0) {
			return rec[0][0];
		} else {
			return dep_no;
		}
	}

	// 意見記錄
	protected String getHisOpinion() {
		String[][] his = getFlowHistory();
		String value = "";
		for (int i = 1; i < his.length; i++) {
			if (!"AUTO".equals(his[i][3])) {
				value += getName(his[i][1]) + "(" + convert.FormatedDate(his[i][2].substring(0, 9), "/") + ":"
						+ his[i][3] + ");" + Mail.HTML_LINE_BREAK;
			} else {
			}
		}
		return value;
	}

}
