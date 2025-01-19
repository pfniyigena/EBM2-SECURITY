/** Copyright Mangatek Ltd to present
All rights reserved.
*/
package rw.mangatek.ebm2.core.exception;

/**
 * @author pierre.niyigena
 *
 */
public class EntityExistException extends Exception {

	private static final long serialVersionUID = -8890080495441147845L;

	private String message;
	private Object[] args;

	public EntityExistException(Object obj,String message){
		this.message = String.format("%s %s exists in the system.", obj.getClass(),message);
	}

	public EntityExistException(Object[] args) {
		this.args = args;
	}

	public EntityExistException(String message, Object[] args){
		this.message = message;
		this.args = args;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the args
	 */
	public Object[] getArgs() {
		return args;
	}

	/**
	 * @param args
	 *            the args to set
	 */
	public void setArgs(Object[] args) {
		this.args = args;
	}
}
