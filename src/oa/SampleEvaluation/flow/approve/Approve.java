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
		// 回傳值為 true 表示執行接下來的流程處理
		// 回傳值為 false 表示接下來不執行任何流程處理
		// 傳入值 value 為 "核准"

		String state = getState();
		talk t = getTalk();

		switch (FlowState.valueOf(state)) {
		case 組長:

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
				alertStr = "是否進行請驗/試製評估流程--皆未勾選任何項目 送出後將直接結案;<br>";
			}
			int result = showConfirmDialog(alertStr + "確定送出表單？", "溫馨提醒", 0);
			if (result == 1) {
				return false;
			}

			// 更新主表請驗單號欄位
			t.execFromPool("UPDATE  sample_evaluation  SET IS_CHECK=?,IS_TRIAL_PRODUCTION=?" + " where pno=?",
					new Object[] { getValue("IS_CHECK"), getValue("IS_TRIAL_PRODUCTION"), getValue("PNO") });
			// 建立子流程FLOWC物件 使其出現在待簽核表單列表
			if (getValue("IS_CHECK").trim().equals("1")) {

				SampleEvaluationCheck s = new SampleEvaluationCheck();
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
				s.setPno(getValue("PNO"));
				// 子流程 ID = 表單單號+CHECK
				String ownPno = getValue("PNO") + "CHECK";

				// 為子流程主檔填入ID
				s.setOwnPno(ownPno);

				// insert一筆子流程主檔
				new SampleEvaluationCheckDao(t).add(s);
				SampleEvaluationCheckFlowc flowc = new SampleEvaluationCheckFlowc(ownPno);
				String time = DateTimeUtil.getApproveAddSeconds(0);

				/**
				 * secf.setF_INP_ID("52116"); <---須改為分案人員
				 * 
				 * @author u52116
				 */
				flowc.setF_INP_ID("52116");
				flowc.setF_INP_STAT("填寫請驗單號");
				flowc.setF_INP_TIME(time);
				SampleEvaluationCheckFlowcDao secfDao = new SampleEvaluationCheckFlowcDao();
				secfDao.create(getTalk().getConnectionFromPool(), flowc);

				// 建立子流程FLOWC_HIS 物件 能夠顯示簽核歷史
				time = DateTimeUtil.getApproveAddSeconds(0);
				SampleEvaluationCheckFlowcHis his = new SampleEvaluationCheckFlowcHis(ownPno, flowc.getF_INP_STAT(),
						time);

				/**
				 * secf.setF_INP_ID("52116"); <---須改為分案人員
				 * 
				 * @author u52116
				 */
				his.setF_INP_ID(getValue("PROJECT_LEADER").trim());
				SampleEvaluationCheckFlowcHisDao secfhDao = new SampleEvaluationCheckFlowcHisDao();
				secfhDao.create(getTalk().getConnectionFromPool(), his);
			}

			break;
		case 試製單號填寫:
			// 更新主表試製單號欄位
			if (!getValue("NOTIFY_NO_TRIAL_PROD").trim().equals("")) {
				t.execFromPool("UPDATE  sample_evaluation  SET NOTIFY_NO_TRIAL_PROD=?" + " where pno=?",
						new Object[] { getValue("NOTIFY_NO_TRIAL_PROD"), getValue("PNO") });
				t.execFromPool("UPDATE  sample_evaluation_check  SET NOTIFY_NO_TRIAL_PROD=?" + " where own_pno=?",
						new Object[] { getValue("NOTIFY_NO_TRIAL_PROD"), getValue("OWN_PNO") });
			} else {
				message("請輸入試製通知單號");
				return false;
			}

			break;
		case 受理單位主管分案:
			// 更新主表分案人欄位
			if (!getValue("PROJECT_LEADER").trim().equals("")) {
				t.execFromPool("UPDATE  sample_evaluation  SET PROJECT_LEADER=?" + " where pno=?",
						new Object[] { getValue("PROJECT_LEADER"), getValue("PNO") });
				t.execFromPool("UPDATE  sample_evaluation_check  SET PROJECT_LEADER=?" + " where own_pno=?",
						new Object[] { getValue("PROJECT_LEADER"), getValue("OWN_PNO") });
			} else {
				message("請選擇 受理單位分案人員 欄位");
				return false;
			}

			break;
		default:
			break;
		}

		message("簽核完成！");
		return true;
	}

	/**
	 * 判斷畫面是否有夾檔，有夾檔就更新至資料庫<br>
	 * 傳入:欲存進檔案之table名稱(String)、視為檔案名稱(FILE)、畫面上檔案路徑(String)<br>
	 * 不回傳<br>
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
	 * 更新欄位<br>
	 * 傳入:TABLE名稱(String) 各欄位名稱(ArrayList)<br>
	 * 不回傳<br>
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
	 * 溫馨提醒 不傳入 回傳true/false
	 */
	private boolean doReminder() throws Exception {
		int result = showConfirmDialog("確定送出表單？", "溫馨提醒", 0);
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
