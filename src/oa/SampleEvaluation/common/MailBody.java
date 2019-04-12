package oa.SampleEvaluation.common;

/**
 * The Class MailBody.
 *
 * @author YoungCheng(u52116) 2019/3/19
 */
public class MailBody {
	
	/** The subject. */
	String subject;
	
	/** The message. */
	String message;

	/**
	 * Gets the Subject.
	 *
	 * @return [String]
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * Sets the Subject.
	 *
	 * @param subject  void
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * Gets the Message.
	 *
	 * @return [String]
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Sets the Message.
	 *
	 * @param message  void
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Instantiates a new mail body.
	 *
	 * @param subject [String]
	 * @param content [String]
	 */
	public MailBody(String subject, String content) {
		super();
		this.subject = subject;
		this.message = content;
	}

}
