package oa.SampleEvaluationCheck.dao;

public class NotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor for NotFoundException. The input message is returned in
	 * toString() message.
	 */
	public NotFoundException(String msg) {
		super(msg);
	}

}
