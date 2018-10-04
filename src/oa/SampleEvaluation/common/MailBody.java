package oa.SampleEvaluation.common;

public class MailBody {
	String subject;
	String message;

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public MailBody(String subject, String content) {
		super();
		this.subject = subject;
		this.message = content;
	}

}
