package oa.SampleEvaluation;

import java.util.ArrayList;

import jcx.jform.hproc;
import oa.SampleEvaluation.common.global.DtoUtil;
import oa.SampleEvaluation.query.QueryConditionDto;
import oa.SampleEvaluation.query.QueryResultService;

public class QueryTest extends hproc {

	@Override
	public String action(String arg0) throws Throwable {
		// TODO Auto-generated method stub
//		Test tes = new Test();
//		talk t = new talk("mssql", "10.1.1.64", "ysphr", "1qaz@WSX", "ysphr");

		// ����J��Ū���i�J����
		QueryConditionDto targetLikeThis = new QueryConditionDto();
		targetLikeThis = (QueryConditionDto) DtoUtil.setFormDataToDto(new QueryConditionDto(), this);
		// �α������d��
		// s.doQuery();
		// �Ψ��oSQL�d�ߦr��
//		String targetCondition = targetLikeThis.getSqlSelectStr();
//		//String[][] step1ret = getTalk().queryFromPool(targetCondition);
//		//QueryService qs = new QueryService(getTalk());
//		//�o��n���^�Ӫ�list ���ӬO���G
//		QueryResultService qrs = new QueryResultService(getTalk());
//		ArrayList al = qrs.findByCondition(targetCondition);
		return null;
	}

}
