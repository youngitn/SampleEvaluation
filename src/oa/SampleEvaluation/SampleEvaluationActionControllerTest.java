package oa.SampleEvaluation;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

import jcx.db.talk;
import oa.SampleEvaluation.common.CommonDataObj;
import oa.SampleEvaluation.common.DoQuery;

public class SampleEvaluationActionControllerTest {

	@Test
	public void testDoQuery() {
		talk t = new talk("mssql", "10.1.1.64", "ysphr", "1qaz@WSX", "ysphr");

		SampleEvaluationActionController s = new SampleEvaluationActionController();

		try {
			s.cdo = new CommonDataObj("52116", t, "SAMPLE_EVALUATION", "PNO", "APPLICANT");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		try {
			DoQuery.getQueryResult(s.cdo);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}




}
