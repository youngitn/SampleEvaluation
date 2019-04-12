package oa.SampleEvaluation.notify;

/**
 * The Class EmailNotifyForCaseSubFlowFinish.
 *
 * @author YoungCheng(u52116) 2019/3/19
 */
public class EmailNotifyForCaseSubFlowFinish extends EmailNotify {

	/* (non-Javadoc)
	 * @see oa.SampleEvaluation.notify.BaseEmailNotify#getInformation()
	 */
	public String getInformation() {
		return "---------------\u76f4\u5c6c\u4e3b\u7ba1.Notify()----------------";
	}

	/* (non-Javadoc)
	 * @see oa.SampleEvaluation.notify.EmailNotify#setIsLastGate()
	 */
	@Override
	protected void setIsLastGate() {
		this.isLastGate = true;
	}

	/**
	 * 通知採購處經理和組長.
	 */
	@Override
	protected void changeMailToUsr() {

		usr.add(getEmail(getDeptBoss("21")));
		usr.add(getEmail(se.getDesignee().trim().split(" ")[0]));
	}

}