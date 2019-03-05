package oa.global.annotation;

import static java.lang.annotation.ElementType.FIELD;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ FIELD })
public @interface xmaker {
	
	/**
	 * 設定UI欄位名稱
	 * @return
	 */
	public String name();
	
	
	/**
	 * 適用當該欄位為查詢條件時
	 *  if UI name != DB field name, 須設置這個屬性以對應查詢條件
	 *  通常查詢欄位和DB欄位名稱可能不同,
	 *  如EMPID在查詢頁面的UI名稱會加一個Q :Q_EMPID
	 *  則須設置此識別參數=DB欄位名稱 以作為對應.
	 *  
	 * @return
	 */
	public String mappingDbFieldName() default "";;
	
	/**
	 * 適用當該欄位為查詢條件時
	 * 欄位如為日期起日 須設置
	 * 
	 * @return
	 */
	public boolean isDateStart() default false;

	/**
	 * 適用當該欄位為查詢條件時
	 *  欄位如為日期迄日 須設置
	 * @return
	 */
	public boolean isDateEnd() default false;
	
	/**
	 * select結果欄位如希望直接顯示欄位名稱text 如:select 'text'.. 須設置
	 * @return
	 */
	public boolean isText() default false;
	
	
	/**
	 * 作為查詢條件或結果時用來判斷該欄位是否為流程狀態
	 * @return 
	 */
	public boolean isFlowStatus() default false;
}
