/**
 * 
 */
package com.igrandee.product.ecloud.viewmodel.sat;

/**
 * @author selvarajan_j
 *
 */
public class BulkQuestionVM {

	public  String qstobject[];
	public String ansobject[];
	public String optionObject[];
	private int courselectureid;
	private String qstcategory;
	
	/**
	 * @return the qstcategory
	 */
	public String getQstcategory() {
		return qstcategory;
	}
	/**
	 * @param qstcategory the qstcategory to set
	 */
	public void setQstcategory(String qstcategory) {
		this.qstcategory = qstcategory;
	}
	private String entryType;
	/**
	 * @return the entryType
	 */
	public String getEntryType() {
		return entryType;
	}
	/**
	 * @param entryType the entryType to set
	 */
	public void setEntryType(String entryType) {
		this.entryType = entryType;
	}
	
	/**
	 * @return the courselectureid
	 */
	public int getCourselectureid() {
		return courselectureid;
	}
	/**
	 * @param courselectureid the courselectureid to set
	 */
	public void setCourselectureid(int courselectureid) {
		this.courselectureid = courselectureid;
	}
	/**
	 * @return the qstobject
	 */
	public String[] getQstobject() {
		return qstobject;
	}
	/**
	 * @param qstobject the qstobject to set
	 */
	public void setQstobject(String[] qstobject) {
		this.qstobject = qstobject;
	}
	/**
	 * @return the ansobject
	 */
	public String[] getAnsobject() {
		return ansobject;
	}
	/**
	 * @param ansobject the ansobject to set
	 */
	public void setAnsobject(String[] ansobject) {
		this.ansobject = ansobject;
	}
	/**
	 * @return the optionObject
	 */
	public String[] getOptionObject() {
		return optionObject;
	}
	/**
	 * @param optionObject the optionObject to set
	 */
	public void setOptionObject(String[] optionObject) {
		this.optionObject = optionObject;
	}
}
