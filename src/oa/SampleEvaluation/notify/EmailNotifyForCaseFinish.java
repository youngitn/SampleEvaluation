package oa.SampleEvaluation.notify;

/**
 * The Class EmailNotifyForCaseFinish.
 *
 * @author YoungCheng(u52116) 2019/3/19
 */
public class EmailNotifyForCaseFinish extends EmailNotify {

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
	 * �q�����ʳB�g�z.
	 */
	@Override
	protected void changeMailToUsr() {

		usr.add(getEmail(getDeptBoss("21")));
	}

}