package oa.SampleEvaluation.notify;

public class EmailNotifyForCaseSubFlowFinish extends EmailNotify {

	public String getInformation() {
		return "---------------\u76f4\u5c6c\u4e3b\u7ba1.Notify()----------------";
	}

	@Override
	protected void setIsLastGate() {
		this.isLastGate = true;
	}

	/**
	 * 通知採購處經理
	 */
	@Override
	protected void changeMailToUsr() {

		usr.add(getEmail(getDeptBoss("21")));
	}

}