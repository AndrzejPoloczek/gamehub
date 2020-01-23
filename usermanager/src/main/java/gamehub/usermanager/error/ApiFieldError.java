package gamehub.usermanager.error;

class ApiFieldError {

	final private String fieldname;
	final private String code;
	final private String message;
	
	public ApiFieldError(String fieldname, String code, String message) {
		this.fieldname = fieldname;
		this.code = code;
		this.message = message;
	}

	public String getFieldname() {
		return fieldname;
	}
	
	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
}
