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
		// �^�ǭȬ� true ��ܰ��汵�U�Ӫ��y�{�B�z
		// �^�ǭȬ� false ��ܱ��U�Ӥ��������y�{�B�z
		// �ǤJ�� value �� "�֭�"

		state = getState();
		t = getTalk();
		SampleEvaluation s = null;
		SampleEvaluationCheck sc = null;
		String alertStr = "";
		switch (FlowState.valueOf(state)) {
		case �ժ�:

			// �i������P�_�ëإ߰e�X������ܪ�������r
			alertStr = buildApproveConfirmMsgStr();

			if (getValue("IS_CHECK").trim().equals("1") && getValue("LAB_EXE").trim().equals("")) {
				message("�п�ܹ���Ǹg��H��");
				return false;
			}

			if (getValue("IS_TRIAL_PRODUCTION").trim().equals("1")
					&& (getValue("LAB_EXE").trim().equals("") || getValue("ASSESSOR").trim().equals(""))) {
				message("�п�ܸջs�����H���P����Ǹg��H��");
				return false;
			}

			// ��s�D�����M�ջs�����Ŀ����
			// ��s�D�� �����H���M����Ǹg��
			s = setAllValue(new SampleEvaluation());
			new SampleEvaluationDaoImpl(t).update(s);

			// �إߤl�y�{FLOWC���� �Ϩ�X�{�b��ñ�֪��C��
			if (getValue("IS_CHECK").trim().equals("1")) {

				sc = setAllValue(new SampleEvaluationCheck());

				SampleEvaluationCheckDao checkDao = new SampleEvaluationCheckDao(t);
				if (checkDao.findById(sc.getOwnPno()) != null) {
					checkDao.update(sc);
				} else {
					// insert�@���l�y�{�D��
					new SampleEvaluationCheckDao(t).add(sc);

					SampleEvaluationCheckFlowc flowc = new SampleEvaluationCheckFlowc(sc.getOwnPno());
					String time = DateTimeUtil.getApproveAddSeconds(0);

					// ���o�Q���ײժ�empid
					String[] designee = getValue("DESIGNEE").trim().split(" ");
					flowc.setF_INP_ID(designee[0]);
					flowc.setF_INP_STAT("��g����渹");
					flowc.setF_INP_TIME(time);
					SampleEvaluationCheckFlowcDao secfDao = new SampleEvaluationCheckFlowcDao();
					secfDao.create(getTalk().getConnectionFromPool(), flowc);

					// �إߤl�y�{FLOWC_HIS ���� ������ñ�־��v
					time = DateTimeUtil.getApproveAddSeconds(0);
					SampleEvaluationCheckFlowcHis his = new SampleEvaluationCheckFlowcHis(sc.getOwnPno(),
							flowc.getF_INP_STAT(), time);

					his.setF_INP_ID(designee[0]);
					SampleEvaluationCheckFlowcHisDao secfhDao = new SampleEvaluationCheckFlowcHisDao();
					secfhDao.create(getTalk().getConnectionFromPool(), his);

				}
				// ������y�{ �H�X�q���H
				BaseService service = new BaseService(this);
				MailService mailService = new MailService(service);
				String title = "ñ�ֳq���G" + this.getFunctionName() + "_����y�{" + "( �渹�G" + getValue("PNO") + " )";
				// Mail to
				String[] u = getValue("DESIGNEE").trim().split(" ");
				String[] usr = { getEmail(u[0]) };

				// ���e
				String content = buildContent();

				mailService.sendMailbccUTF8(usr, title, content, null, "", Mail.MAIL_HTML_CONTENT_TYPE);

			}

			break;
		case �ջs�渹��g:
			// ��s�D��ջs�渹���
			if (!getValue("NOTIFY_NO_TRIAL_PROD").trim().equals("")) {
				// ��s�D��ջs�渹���
				s = setAllValue(new SampleEvaluation());
				new SampleEvaluationDaoImpl(t).update(s);

				// ��s�l�y�{�D��ջs�渹���
				sc = setAllValue(new SampleEvaluationCheck());
				new SampleEvaluationCheckDao(t).update(sc);
			} else {
				message("�п�J�ջs�q���渹");
				return false;
			}

			break;
		case ���z���D�ޤ���:
			// ��s�D����פH���
			if (!getValue("DESIGNEE").trim().equals("")) {
				t.execFromPool("UPDATE  sample_evaluation  SET DESIGNEE=?" + " where pno=?",
						new Object[] { getValue("DESIGNEE"), getValue("PNO") });
				t.execFromPool("UPDATE  sample_evaluation_check  SET DESIGNEE=?" + " where own_pno=?",
						new Object[] { getValue("DESIGNEE"), getValue("OWN_PNO") });
			} else {
				message("�п�� ���z�������H�� ���");
				return false;
			}

			break;
		case �ݳB�z:
			// ��s�D����פH���
			s = new SampleEvaluation();
			s = setAllValue(s);
			new SampleEvaluationDaoImpl(t).update(s);
			break;
		default:
			break;
		}
		doReminder(alertStr);
		message("ñ�֧���");
		return true;
	}

	private String buildApproveConfirmMsgStr() {
		String alertStr = "";
		if (getValue("IS_CHECK").equals("0")) {

			alertStr += "�N���|�i�����y�{;<br>";
		} else {

			alertStr += "�N�i�����y�{;<br>";
		}
		if (getValue("IS_TRIAL_PRODUCTION").equals("0")) {

			alertStr += "�N���|�i��ջs�����y�{;<br>";
		} else {

			alertStr += "�N�i��ջs�����y�{;<br>";
		}
		if (getValue("IS_TRIAL_PRODUCTION").equals("0") && getValue("IS_CHECK").equals("0")) {
			alertStr = "�ҥ��Ŀ�\"�O�_�i�����/�ջs�����y�{\"�������󶵥�<br> �e�X��N��������;<br>";
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
		// �l�y�{ ID = ���渹+CHECK
		String ownPno = getValue("PNO") + "CHECK";
		// ���l�y�{�D�ɶ�JID
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
	 * ���ɴ��� ���ǤJ �^��true/false
	 */
	private boolean doReminder(String addStr) throws Exception {
		int result = showConfirmDialog(addStr + "�T�w�e�X���H", "���ɴ���", 0);
		if (result == 1) {
			message("�w�����e�X���");
			return false;
		}
		String space = "";
		for (int i = 0; i < 16; i++) {
			space += "&emsp;";
		}
		percent(100, space + "���e�X���A�еy��...<font color=white>");
		return true;
	}

	protected String buildContent() {
		// TODO Auto-generated method stub
		// ���e
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
		content += "�z���@�� " + getFunctionName() + " ����ñ�֡F" + Mail.HTML_LINE_BREAK;
		content += "�жi�J Emaker ���ΪA�Ȩt�� " + Mail.getOaSystemUrl() + " ñ�֡C" + Mail.HTML_LINE_BREAK;
		content += Mail.HTML_LINE_BREAK;
		content += Mail.MAIL_CONTENT_LINE_WORD + Mail.HTML_LINE_BREAK;
		content += "�渹�G" + getValue("PNO") + Mail.HTML_LINE_BREAK;
		content += "�ӽФ���G" + convert.FormatedDate(getValue("APP_DATE"), "/") + Mail.HTML_LINE_BREAK;
		content += "�ӽФH�G" + dep_name + " " + name + "(" + appUser.getEmpid() + ")" + Mail.HTML_LINE_BREAK;
		content += "�ӽ������G" + AppType.getAppType(getValue("APP_TYPE")) + Mail.HTML_LINE_BREAK;
		content += "�止�ʡG" + Urgency.getUrgency(getValue("URGENCY")) + Mail.HTML_LINE_BREAK;
		content += "���ƦW�١G" + getValue("MATERIAL") + Mail.HTML_LINE_BREAK;
		try {
			content += "���z���G" + getDepName(getValue("RECEIPT_UNIT")) + Mail.HTML_LINE_BREAK;
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		content += "�p�e�N���G" + getValue("PROJECT_CODE") + Mail.HTML_LINE_BREAK;
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

		content += "�p�e�D���H�G" + projectLeaderLine + Mail.HTML_LINE_BREAK;
		content += "�O�_�i�����/�ջs�y�{�G" + buildApproveConfirmMsgStr() + Mail.HTML_LINE_BREAK;
		content += "==========================" + Mail.HTML_LINE_BREAK;
		content += "���l��Ѩt�Φ۰ʵo�X�A�ФŦ^�H�A����!!" + Mail.HTML_LINE_BREAK;
		content += "�N���O���G" + Mail.HTML_LINE_BREAK;
		content += "" + getHisOpinion() + Mail.HTML_LINE_BREAK;
		return content;
	}

	// �γ��N�X������W��
	protected String getDepName(String dep_no) throws SQLException, Exception {
		String sql = "select DEP_NAME from DEP_ACTIVE_VIEW where DEP_NO = '" + dep_no + "'";
		String rec[][] = getTalk().queryFromPool(sql);
		if (rec.length > 0) {
			return rec[0][0];
		} else {
			return dep_no;
		}
	}

	// �N���O��
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
