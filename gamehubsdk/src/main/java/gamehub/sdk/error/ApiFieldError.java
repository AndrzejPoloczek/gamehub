package gamehub.sdk.error;

class ApiFieldError {

	private String fieldname;
	private String code;
	private String message;
	
	public ApiFieldError() {
		
	}
	
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
