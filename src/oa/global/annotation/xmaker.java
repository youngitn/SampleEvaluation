package oa.global.annotation;

import static java.lang.annotation.ElementType.FIELD;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The Interface xmaker.
 *
 * @author YoungCheng(u52116) 2019/3/19
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ FIELD })
public @interface xmaker {
	
	/**
	 * �]�wUI���W��.
	 *
	 * @return  [String]
	 */
	public String name();
	
	
	/**
	 * �A�η����쬰�d�߱����
	 *  if UI name != DB field name, ���]�m�o���ݩʥH�����d�߱���
	 *  �q�`�d�����MDB���W�٥i�ण�P,
	 *  �pEMPID�b�d�߭�����UI�W�ٷ|�[�@��Q :Q_EMPID
	 *  �h���]�m���ѧO�Ѽ�=DB���W�� �H�@������.
	 *  
	 *
	 * @return  [String]
	 */
	public String mappingDbFieldName() default "";;
	
	/**
	 * �A�η����쬰�d�߱����
	 * ���p������_�� ���]�m.
	 *
	 * @return true, if is date start
	 */
	public boolean isDateStart() default false;

	/**
	 * �A�η����쬰�d�߱����
	 *  ���p��������� ���]�m.
	 *
	 * @return true, if is date end
	 */
	public boolean isDateEnd() default false;
	
	/**
	 * select���G���p�Ʊ檽��������W��text �p:select 'text'.. ���]�m
	 *
	 * @return true, if is text
	 */
	public boolean isText() default false;
	
	
	/**
	 * �@���d�߱���ε��G�ɥΨӧP�_�����O�_���y�{���A.
	 *
	 * @return true, if is flow status
	 */
	public boolean isFlowStatus() default false;
}
