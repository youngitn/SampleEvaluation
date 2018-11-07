package oa.SampleEvaluation.flow.approve;

import jcx.jform.bProcFlow;
import oa.SampleEvaluation.flow.approve.gateEnum.*;

import java.lang.reflect.Constructor;
import java.sql.SQLException;

import com.ysp.field.Mail;
import com.ysp.service.BaseService;
import com.ysp.service.MailService;
import com.ysp.util.DateTimeUtil;

import oa.SampleEvaluation.notify.*;
import oa.SampleEvaluation.dao.AbstractGenericDao;
import oa.SampleEvaluation.dao.AbstractGenericFlowcDao;
import oa.SampleEvaluation.dao.AbstractGenericFlowcHisDao;
import oa.SampleEvaluation.dao.SampleEvaluationDaoImpl;
import oa.SampleEvaluation.dto.AbstractGenericFlowcDto;
import oa.SampleEvaluation.dto.AbstractGenericFlowcHisDto;
import oa.SampleEvaluation.dto.SampleEvaluation;
import oa.SampleEvaluation.dto.SampleEvaluationSubBaseDto;
import oa.SampleEvaluationCheck.dto.SampleEvaluationCheck;
import oa.SampleEvaluationTp.dto.SampleEvaluationTp;
import jcx.db.*;

public class Approve extends bProcFlow {

	String nowState;
	talk t;
	String isCheckValue;
	String isTrialProdValue;

	public boolean action(String value) throws Throwable {
		nowState = getState();
		t = getTalk();
		SampleEvaluation s = null;
		String alertStr = "";
		BaseService service = new BaseService(this);
		String labExe = getValue("LAB_EXE").trim();
		String lassessor = getValue("ASSESSOR").trim();
		String docCtrler = getValue("DOC_CTRLER").trim();
		String designee = getValue("DESIGNEE").trim();
		designee.trim().split(" ");
		getValue("PNO");
		this.isCheckValue = getValue("IS_CHECK").trim();
		this.isTrialProdValue = getValue("IS_TRIAL_PRODUCTION").trim();

		switch (FlowState.valueOf(nowState)) {
		case 組長:

			if ((isCheckValue.equals("1") || isTrialProdValue.equals("1")) && labExe.equals("")) {
				message("請選擇實驗室經辦人員");
				return false;
			}

			if (isTrialProdValue.equals("1") && lassessor.equals("")) {
				message("請選擇試製評估人員");
				return false;
			}

			if ((isTrialProdValue.equals("1") || isCheckValue.equals("1")) && docCtrler.equals("")) {
				message("請選擇文管人員");
				return false;
			}
			if (getValue("QR_NO").trim().equals("")) {
				message("請填寫QR號碼");
				return false;
			}
			if (doReminder("")) {
				// 更新主表請驗和試製評估勾選欄位
				// 更新主表 評估人員和實驗室經辦
				s = new SampleEvaluation();
				s.setAllValue(service);
				new SampleEvaluationDaoImpl(t).update(s);

				// 建立子流程FLOWC物件 使其出現在待簽核表單列表
				if (isCheckValue.equals("1")) {

					SampleEvaluationSubBaseDto secDto = new SampleEvaluationCheck();
					secDto.setAllValue(service);
					goSubFlow("Check", secDto);
					String title = "簽核通知：" + this.getFunctionName() + "_請驗流程" + "( 單號：" + getValue("PNO") + " )";
					// 有請驗流程 寄出通知信
					sendSubFlowMail(service, getValue("DOC_CTRLER"), secDto, title);

				}
				if (isTrialProdValue.equals("1")) {

					SampleEvaluationSubBaseDto setDto = new SampleEvaluationTp();
					setDto.setAllValue(service);
					goSubFlow("Tp", setDto);
					String title = "簽核通知：" + this.getFunctionName() + "_試製流程" + "( 單號：" + getValue("PNO") + " )";
					// 有試製流程 寄出通知信
					sendSubFlowMail(service, getValue("ASSESSOR"), setDto, title);
				}
			}
			break;
		case 受理單位主管分案:
			// 更新主表分案人欄位
			if (!designee.equals("")) {
				t.execFromPool("UPDATE  sample_evaluation  SET DESIGNEE=?" + " where pno=?",
						new Object[] { designee, getValue("PNO") });
				t.execFromPool("UPDATE  sample_evaluation_check  SET DESIGNEE=?" + " where own_pno=?",
						new Object[] { designee, getValue("OWN_PNO") });
			} else {
				message("請選擇 受理單位指派人員 欄位");
				return false;
			}

			return doReminder(alertStr);
		case 待處理:
		case 採購經辦確認:
			// 更新主表分案人欄位
			s = new SampleEvaluation();
			s.setAllValue(service);
			new SampleEvaluationDaoImpl(t).update(s);
			return doReminder(alertStr);

		case 採購經辦:
			if (getValue("EVALUATION_RESULT").trim().equals("")
					|| getValue("FILE_EVALUATION_RESULT").trim().equals("")) {
				message("評估結果與其夾檔不得為空");
				return false;
			} else if (doReminder(alertStr)) {
				s = new SampleEvaluation();
				s.setAllValue(service);
				new SampleEvaluationDaoImpl(t).update(s);
				return true;
			}
		default:
			break;
		}
		return true;
	}

	private void sendSubFlowMail(BaseService service, String mailTo, SampleEvaluationSubBaseDto dto, String title)
			throws Exception {
		MailService mailService = new MailService(service);
		// Mail to
		String[] ret = mailTo.trim().split(" ");
		String[] usr = { getEmail(ret[0]) };

		// 內容
		EmailNotify en = new EmailNotify();
		en.setService(service);
		String content = en.getContent(dto);

		mailService.sendMailbccUTF8(usr, title, content, null, "", Mail.MAIL_HTML_CONTENT_TYPE);
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

	private void goSubFlow(String type, SampleEvaluationSubBaseDto s)
			throws ClassNotFoundException, SQLException, Exception {

		Class<?> subMainDao = Class.forName("oa.SampleEvaluation" + type + ".dao.SampleEvaluation" + type + "DaoImpl");
		Constructor<?> DaoCon = subMainDao.getConstructor(talk.class);
		AbstractGenericDao<SampleEvaluationSubBaseDto> Dao = (AbstractGenericDao<SampleEvaluationSubBaseDto>) DaoCon
				.newInstance(t);

		if (Dao.findById(s.getOwnPno()) != null) {
			Dao.update(s);
		} else {
			// insert一筆子流程主檔
			Dao.add(s);

			AbstractGenericFlowcDto<?> Dto = (AbstractGenericFlowcDto<?>) Class
					.forName("oa.SampleEvaluation" + type + ".dto.SampleEvaluation" + type + "Flowc").newInstance();

			Dto.setOwnPno(s.getOwnPno());
			String time = DateTimeUtil.getApproveAddSeconds(0);

			Dto.setF_INP_ID(s.getApplicant());
			String gateName = "填寫請驗單號";
			if (type.equals("Tp")) {
				gateName = "評估人員";
			}
			Dto.setF_INP_STAT(gateName);
			Dto.setF_INP_TIME(time);

			AbstractGenericFlowcDao<AbstractGenericFlowcDto<?>> secfDao = (AbstractGenericFlowcDao<AbstractGenericFlowcDto<?>>) Class
					.forName("oa.SampleEvaluation" + type + ".dao.SampleEvaluation" + type + "FlowcDaoImpl")
					.newInstance();

			secfDao.create(t.getConnectionFromPool(), Dto);

			// 建立子流程FLOWC_HIS 物件 能夠顯示簽核歷史
			time = DateTimeUtil.getApproveAddSeconds(0);

			AbstractGenericFlowcHisDto<?> his = (AbstractGenericFlowcHisDto<?>) Class
					.forName("oa.SampleEvaluation" + type + ".dto.SampleEvaluation" + type + "FlowcHis").newInstance();

			his.setF_INP_STAT(Dto.getF_INP_STAT());
			his.setOwnPno(s.getOwnPno());
			his.setF_INP_TIME(time);
			his.setF_INP_ID(s.getApplicant());
			AbstractGenericFlowcHisDao<AbstractGenericFlowcHisDto<?>> secfhDao = (AbstractGenericFlowcHisDao<AbstractGenericFlowcHisDto<?>>) Class
					.forName("oa.SampleEvaluation" + type + ".dao.SampleEvaluation" + type + "FlowcHisDaoImpl")
					.newInstance();

			secfhDao.create(t.getConnectionFromPool(), his);

		}

	}

}
