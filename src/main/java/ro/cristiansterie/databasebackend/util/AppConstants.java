package ro.cristiansterie.databasebackend.util;

public class AppConstants {
	public static final String USERNAME_NOT_FOUND_MESSAGE       = "UserEntity not found with username: ";
	public static final String UPDATE_NOT_ALLOWED               = "User not in DB. Update not allowed for username: ";

	public static final String JWT_INVALID_TOKEN                = "{}: Invalid JWT token: {}";
	public static final String INVALID_OR_EXPIRED_JWT_TOKEN     = "\"{\\\"error\\\":\\\"Invalid or expired JWT\\\"}\"";
	public static final String RESPONSE_WRITER_ISSUE            = "The getWriter() method on the Response Object failed!";
}
