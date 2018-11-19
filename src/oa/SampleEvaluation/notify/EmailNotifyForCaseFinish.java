package oa.SampleEvaluation.notify;

public class EmailNotifyForCaseFinish extends EmailNotify {

	public String getInformation() {
		return "---------------\u76f4\u5c6c\u4e3b\u7ba1.Notify()----------------";
	}

	@Override
	protected void setIsLastGate() {
		this.isLastGate = true;
	}

	@Override
	protected void changeMailToUsr() {

		usr.add(getEmail(getDeptBoss("21")));
	}

}