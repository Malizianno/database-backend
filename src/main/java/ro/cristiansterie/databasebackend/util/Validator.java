package ro.cristiansterie.databasebackend.util;

import java.util.UUID;
import java.util.regex.Pattern;

public class Validator {
	private static final Pattern UUID_REGEX =
			Pattern.compile("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$");

	public static boolean isUUIDValid(String uuid) {
		return uuid != null && UUID_REGEX.matcher(uuid)
		                                 .matches();
	}

	public static boolean isUUIDValid(UUID uuid) {
		return uuid != null && UUID_REGEX.matcher(uuid.toString())
		                                 .matches();
	}
}
