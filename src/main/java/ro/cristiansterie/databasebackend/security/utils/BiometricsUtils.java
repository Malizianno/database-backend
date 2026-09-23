package ro.cristiansterie.databasebackend.security.utils;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class BiometricsUtils {

	public static boolean verifySignature(String publicKeyPem, byte[] data, byte[] signatureBytes) {
		try {
			String sanitizedPem = publicKeyPem
					.replace("-----BEGIN PUBLIC KEY-----", "")
					.replace("-----END PUBLIC KEY-----", "")
					.replaceAll("\\s+", "");

			byte[] keyBytes = Base64.getDecoder()
			                        .decode(sanitizedPem);
			X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
			KeyFactory kf = KeyFactory.getInstance("EC"); // or "RSA" depending on key type
			PublicKey publicKey = kf.generatePublic(spec);

			Signature sig = Signature.getInstance("SHA256withECDSA"); // or "SHA256withRSA"
			sig.initVerify(publicKey);
			sig.update(data);
			return sig.verify(signatureBytes);
		} catch (
				Exception e) {
			return false;
		}
	}
}
