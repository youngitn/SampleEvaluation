package oa.SampleEvaluation.flow.approve;

import jcx.jform.bProcFlow;
import oa.SampleEvaluation.flow.approve.gateEnum.*;
import com.ysp.field.Mail;
import com.ysp.service.BaseService;
import com.ysp.service.MailService;
import com.ysp.util.DateTimeUtil;

import oa.SampleEvaluation.EmailNotify;
import oa.SampleEvaluation.dao.SampleEvaluationDaoImpl;
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
		BaseService service = new BaseService(this);
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

			if ((getValue("IS_TRIAL_PRODUCTION").trim().equals("1") || getValue("IS_CHECK").trim().equals("1"))
					&& getValue("DOC_CTRLER").trim().equals("")) {
				message("請選擇文管人員");
				return false;
			}
			if (getValue("QR_NO").trim().equals("")) {
				message("請填寫QR號碼");
				return false;
			}

			// 更新主表請驗和試製評估勾選欄位
			// 更新主表 評估人員和實驗室經辦
			s = new SampleEvaluation();
			s = s.setAllValue(s, service);
			new SampleEvaluationDaoImpl(t).update(s);

			// 建立子流程FLOWC物件 使其出現在待簽核表單列表
			if (getValue("IS_CHECK").trim().equals("1")) {

				sc = new SampleEvaluationCheck();
				sc = sc.setAllValue(sc, service);
				SampleEvaluationCheckDao checkDao = new SampleEvaluationCheckDao(t);
				if (checkDao.findById(sc.getOwnPno()) != null) {
					checkDao.update(sc);
				} else {
					// insert一筆子流程主檔
					checkDao.add(sc);

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

				MailService mailService = new MailService(service);
				String title = "簽核通知：" + this.getFunctionName() + "_請驗流程" + "( 單號：" + getValue("PNO") + " )";
				// Mail to
				String[] u = getValue("DESIGNEE").trim().split(" ");
				String[] usr = { getEmail(u[0]) };

				// 內容
				EmailNotify en = new EmailNotify();
				en.setService(service);
				String content = en.getContent();

				mailService.sendMailbccUTF8(usr, title, content, null, "", Mail.MAIL_HTML_CONTENT_TYPE);

			}

			break;
		case 試製單號填寫:
			// 更新主表試製單號欄位
			if (!getValue("NOTIFY_NO_TRIAL_PROD").trim().equals("")) {
				// 更新主表試製單號欄位
				s = new SampleEvaluation();
				s = s.setAllValue(s, service);
				new SampleEvaluationDaoImpl(t).update(s);

				// 更新子流程主表試製單號欄位
				sc = new SampleEvaluationCheck();
				sc = sc.setAllValue(sc, service);
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
			s = s.setAllValue(s, service);
			new SampleEvaluationDaoImpl(t).update(s);
			break;
		default:
			break;
		}
		return doReminder(alertStr);

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
		message("簽核完成");
		return true;
	}

}
