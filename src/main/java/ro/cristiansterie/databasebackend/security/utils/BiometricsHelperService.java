package ro.cristiansterie.databasebackend.security.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ro.cristiansterie.databasebackend.dto.UserDTO;
import ro.cristiansterie.databasebackend.service.UserService;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
public class BiometricsHelperService {

	private final UserService userService;
	// XXX: use Redis for distributed systems
	private final Map<String, String> challengeStore = new ConcurrentHashMap<>();

	public BiometricsHelperService(UserService userService) {
		this.userService = userService;
	}

	// register key for username
	public boolean register(String username, String key) {
		try {
			var registeredUser = userService.save(userService.findByUsername(username)
			                                                 .withBiometrics(key));
			if (validateUserRegistration(registeredUser)) {
				return true;
			}
		} catch (
				Exception ex) {
			log.error(ex.getMessage(), ex);
		}

		return false;
	}

	// get a challenge
	public String getChallenge(String username) {
		var user = userService.findByUsername(username);

		if (user == null || !user.username()
		                         .equals(username)) {
			throw new UsernameNotFoundException("username " + username + " not found.");
		}
		String challenge = UUID.randomUUID()
		                       .toString();
		challengeStore.put(username, challenge);

		return challengeStore.get(username);
	}

	// verify challenge for username
	public boolean verify(String username, String originalChallenge, String signature) {
		String storedChallenge = challengeStore.remove(username);

		if (storedChallenge == null || !storedChallenge.equals(originalChallenge)) {
			throw new IllegalArgumentException("Invalid or expired challenge.");
		}

		var user = userService.findByUsername(username);

		if (user == null || !user.username()
		                         .equals(username)) {
			throw new UsernameNotFoundException("username " + username + " not found.");
		}

		return BiometricsUtils.verifySignature(
				user.publicKeyPem(),
				storedChallenge.getBytes(StandardCharsets.UTF_8),
				Base64.getDecoder()
				      .decode(signature)
		);
	}

	private boolean validateUserRegistration(UserDTO user) {
		return user != null && user.id() != null && user.username() != null && user.publicKeyPem() != null;
	}
}
