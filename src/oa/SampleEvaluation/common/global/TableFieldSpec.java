package oa.SampleEvaluation.common.global;

public class TableFieldSpec {
	public String name;
	public String pkName;
	public String[][] allColumn;
	public String applicantFieldName;
	public String appDateFieldName;

	public TableFieldSpec() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPkName() {
		return pkName;
	}

	public void setPkName(String pkName) {
		this.pkName = pkName;
	}

	public String[][] getAllColumn() {
		return allColumn;
	}

	public void setAllColumn(String[][] allColumn) {
		this.allColumn = allColumn;
	}

	public String getApplicantFieldName() {
		return applicantFieldName;
	}

	public void setApplicantFieldName(String applicantFieldName) {
		this.applicantFieldName = applicantFieldName;
	}

	public String getAppDateFieldName() {
		return appDateFieldName;
	}

	public void setAppDateFieldName(String appDateFieldName) {
		this.appDateFieldName = appDateFieldName;
	}

}