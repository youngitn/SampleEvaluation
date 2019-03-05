package oa.global.annotation;

import static java.lang.annotation.ElementType.FIELD;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ FIELD })
public @interface xmaker {
	
	/**
	 * �]�wUI���W��
	 * @return
	 */
	public String name();
	
	
	/**
	 * �A�η����쬰�d�߱����
	 *  if UI name != DB field name, ���]�m�o���ݩʥH�����d�߱���
	 *  �q�`�d�����MDB���W�٥i�ण�P,
	 *  �pEMPID�b�d�߭�����UI�W�ٷ|�[�@��Q :Q_EMPID
	 *  �h���]�m���ѧO�Ѽ�=DB���W�� �H�@������.
	 *  
	 * @return
	 */
	public String mappingDbFieldName() default "";;
	
	/**
	 * �A�η����쬰�d�߱����
	 * ���p������_�� ���]�m
	 * 
	 * @return
	 */
	public boolean isDateStart() default false;

	/**
	 * �A�η����쬰�d�߱����
	 *  ���p��������� ���]�m
	 * @return
	 */
	public boolean isDateEnd() default false;
	
	/**
	 * select���G���p�Ʊ檽��������W��text �p:select 'text'.. ���]�m
	 * @return
	 */
	public boolean isText() default false;
	
	
	/**
	 * �@���d�߱���ε��G�ɥΨӧP�_�����O�_���y�{���A
	 * @return 
	 */
	public boolean isFlowStatus() default false;
}
