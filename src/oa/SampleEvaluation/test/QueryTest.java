package oa.SampleEvaluation.test;

import jcx.jform.hproc;
import oa.SampleEvaluation.model.QueryConditionDTO;
import oa.SampleEvaluation.model.SampleEvaluationPO;
import oa.global.DtoUtil;

/**
 * The Class QueryTest.
 *
 * @author YoungCheng(u52116) 2019/3/19
 */
public class QueryTest extends hproc {

	/* (non-Javadoc)
	 * @see jcx.jform.hproc#action(java.lang.String)
	 */
	@Override
	public String action(String arg0) throws Throwable {
		// TODO Auto-generated method stub
//		Test tes = new Test();
//		talk t = new talk("mssql", "10.1.1.64", "ysphr", "1qaz@WSX", "ysphr");

		// ����J��Ū���i�J����
		QueryConditionDTO targetLikeThis = new QueryConditionDTO();
		targetLikeThis = (QueryConditionDTO) DtoUtil.setFormDataIntoDto(new QueryConditionDTO(), this);

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
