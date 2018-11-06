package oa.SampleEvaluation.dao;

public abstract class AbstractGenericFlowcHisDao<E> implements ISampleEvaluationSubFlowcHisDao<E> {

	public abstract Class getClazz();

	public E newInstance() {
		try {
			return (E) getClazz().newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}