package oa.SampleEvaluation;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;

@Table("Sample_Evaluation") // 声明了Person对象的数据表
public class SampleEvaluationX { // 不会强制要求继承某个类

	@Name // 表示该字段可以用来标识此对象，或者是字符型主键，或者是唯一性约束
	private String pno;

	@Column // 表示该对象属性可以映射到数据库里作为一个字段
	private String sap_Code;

	public String getPno() {
		return pno;
	}

	public void setPno(String pno) {
		this.pno = pno;
	}

	public String getSapCode() {
		return sap_Code;
	}

	public void setSapCode(String sapCode) {
		this.sap_Code = sapCode;
	}

	
}
