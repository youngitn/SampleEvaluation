package oa.SampleEvaluation.dto;

public abstract class AbstractGenericFlowcHisDto<E> implements ISampleEvaluationFlowcHisDto<E> {

	public abstract Class getClazz();

	public E newInstance() {
		try {
			return (E) getClazz().newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}