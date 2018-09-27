package oa.SampleEvaluation.flow.approve;

import jcx.jform.bProcFlow;

import java.beans.ConstructorProperties;
import java.io.*;
import java.sql.SQLException;
import java.util.*;

import com.ysp.util.DateTimeUtil;

import jcx.util.*;
import oa.SampleEvaluation.common.AddUtil;
import oa.SampleEvaluationCheck.dao.SampleEvaluationCheckDao;
import oa.SampleEvaluationCheck.dao.SampleEvaluationCheckFlowcDao;
import oa.SampleEvaluationCheck.dao.SampleEvaluationCheckFlowcHisDao;
import oa.SampleEvaluationCheck.tableObject.SampleEvaluationCheck;
import oa.SampleEvaluationCheck.tableObject.SampleEvaluationCheckFlowc;
import oa.SampleEvaluationCheck.tableObject.SampleEvaluationCheckFlowcHis;
import jcx.db.*;

public class Approve extends bProcFlow {

	String table_name = "MIS_SERVICE";

	public boolean action(String value) throws Throwable {
		// �^�ǭȬ� true ��ܰ��汵�U�Ӫ��y�{�B�z
		// �^�ǭȬ� false ��ܱ��U�Ӥ��������y�{�B�z
		// �ǤJ�� value �� "�֭�"

		String state = getState();
		talk t = getTalk();

		switch (FlowState.valueOf(state)) {
		case �ժ�:

			// �i������P�_�ëإ߰e�X������ܪ�������r
			String alertStr = buildApproveConfirmMsgStr();

			if (getValue("IS_CHECK").trim().equals("1") && getValue("LAB_EXE").trim().equals("")) {
				message("�п�ܹ���Ǹg��H��");
				return false;
			}

			if (getValue("IS_TRIAL_PRODUCTION").trim().equals("1")
					&& (getValue("LAB_EXE").trim().equals("") || getValue("ASSESSOR").trim().equals(""))) {
				message("�п�ܸջs�����H���P����Ǹg��H��");
				return false;
			}

			int result = showConfirmDialog(alertStr + "�T�w�e�X���H", "���ɴ���", 0);
			if (result == 1) {
				return false;
			}

			// ��s�D�����M�ջs�����Ŀ����
			// ��s�D�� �����H���M����Ǹg��
			t.execFromPool(
					"UPDATE  sample_evaluation  SET IS_CHECK=?,IS_TRIAL_PRODUCTION=?,LAB_EXE=?,ASSESSOR=?,DESIGNEE=?"
							+ " where pno=?",
					new Object[] { getValue("IS_CHECK"), getValue("IS_TRIAL_PRODUCTION"), getValue("LAB_EXE"),
							getValue("ASSESSOR"), getValue("DESIGNEE"), getValue("PNO") });
			String[] designee = getValue("DESIGNEE").trim().split(" ");
			// �إߤl�y�{FLOWC���� �Ϩ�X�{�b��ñ�֪��C��
			if (getValue("IS_CHECK").trim().equals("1")) {

				SampleEvaluationCheck s = setAllValue(new SampleEvaluationCheck());

				SampleEvaluationCheckDao checkDao = new SampleEvaluationCheckDao(t);
				if (checkDao.findById(s.getOwnPno()) != null) {
					checkDao.update(s);
				} else {
					// insert�@���l�y�{�D��
					new SampleEvaluationCheckDao(t).add(s);

					SampleEvaluationCheckFlowc flowc = new SampleEvaluationCheckFlowc(s.getOwnPno());
					String time = DateTimeUtil.getApproveAddSeconds(0);

					/**
					 * secf.setF_INP_ID("52116"); <---���אּ���פH��
					 * 
					 * @author u52116
					 */
					flowc.setF_INP_ID(designee[0]);
					flowc.setF_INP_STAT("��g����渹");
					flowc.setF_INP_TIME(time);
					SampleEvaluationCheckFlowcDao secfDao = new SampleEvaluationCheckFlowcDao();
					secfDao.create(getTalk().getConnectionFromPool(), flowc);

					// �إߤl�y�{FLOWC_HIS ���� ������ñ�־��v
					time = DateTimeUtil.getApproveAddSeconds(0);
					SampleEvaluationCheckFlowcHis his = new SampleEvaluationCheckFlowcHis(s.getOwnPno(),
							flowc.getF_INP_STAT(), time);

					/**
					 * secf.setF_INP_ID("52116"); <---���אּ���פH��
					 * 
					 * @author u52116
					 */
					his.setF_INP_ID(designee[0]);
					SampleEvaluationCheckFlowcHisDao secfhDao = new SampleEvaluationCheckFlowcHisDao();
					secfhDao.create(getTalk().getConnectionFromPool(), his);
				}

			}

			break;
		case �ջs�渹��g:
			// ��s�D��ջs�渹���
			if (!getValue("NOTIFY_NO_TRIAL_PROD").trim().equals("")) {
				// ��s�D��ջs�渹���
				t.execFromPool("UPDATE  sample_evaluation  SET NOTIFY_NO_TRIAL_PROD=?" + " where pno=?",
						new Object[] { getValue("NOTIFY_NO_TRIAL_PROD"), getValue("PNO") });

				// ��s�l�y�{�D��ջs�渹���
				t.execFromPool("UPDATE  sample_evaluation_check  SET NOTIFY_NO_TRIAL_PROD=?" + " where own_pno=?",
						new Object[] { getValue("NOTIFY_NO_TRIAL_PROD"), getValue("PNO") + "CHECK" });
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
		default:
			break;
		}
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

	/**
	 * �P�_�e���O�_�����ɡA�����ɴN��s�ܸ�Ʈw<br>
	 * �ǤJ:���s�i�ɮפ�table�W��(String)�B�����ɮצW��(FILE)�B�e���W�ɮ׸��|(String)<br>
	 * ���^��<br>
	 * 
	 */
	private void UpdFile(String d_file, File f_file, String file) throws SQLException, Exception {
		String uuid = getValue("UUID");
		String sql = "";
		if (getValue(d_file).startsWith("[CLEAR]")) {
			sql = "update " + table_name + " set " + d_file + " = ''  where uuid='" + uuid + "' ";
			getTalk().execFromPool(sql);
		}
		if (f_file != null) {
			file = f_file.getPath();
		} else {
			file = "";
		}
		if (!"".equals(file)) {
			sql = "update " + table_name + " set " + d_file + " = '" + file + "'  where uuid='" + uuid + "' ";
			getTalk().execFromPool(sql);
		}
	}

	/**
	 * ��s���<br>
	 * �ǤJ:TABLE�W��(String) �U���W��(ArrayList)<br>
	 * ���^��<br>
	 */
	private void updSQL(ArrayList<String> field) throws SQLException, Exception {
		String sql = "update " + table_name + " set ";
		for (int i = 0; i < field.size(); i++) {
			if (!"".equals(field.get(i))) {
				sql += field.get(i) + "=N'" + convert.ToSql(getValue(field.get(i) + "")) + "',";
			}
		}
		sql = sql.substring(0, sql.length() - 1);
		sql += " where uuid = '" + getValue("UUID") + "' ";
		getTalk().execFromPool(sql);
	}

	/**
	 * ���ɴ��� ���ǤJ �^��true/false
	 */
	private boolean doReminder() throws Exception {
		int result = showConfirmDialog("�T�w�e�X���H", "���ɴ���", 0);
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

	private String getNO(String uuid) throws SQLException, Exception {
		String no = "";
		String sql = "select max(no) from MIS_DEV where no like '" + uuid + "%' ";
		String ret[][] = getTalk().queryFromPool(sql);
		if ("".equals(ret[0][0])) {
			no = uuid + "_1";
		} else {
			long j = Long.parseLong(ret[0][0].replace(uuid + "_", ""));
			no = uuid + "_" + (j + 1);
		}
		return no;
	}

}
