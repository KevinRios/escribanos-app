package com.escribanos.util;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import com.escribanos.config.JwtConfig;

@Component
public class JwtGenerator implements ITokenGenerator {

    private final JwtConfig jwtConfig;
    
    public JwtGenerator(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    /* el token se regenera automaticamente en cada peticion
       para evitar que el usuario tenga que reiniciar la aplicacion o autenticarse constantemente
       esto es solo para que el uso sea fluido en esta prueba	
    */
    @Override
    public String createJWT() {
        try {
			String encodedHeader = base64urlEncode(generateHeader().toString().getBytes(StandardCharsets.UTF_8));
			String encodedPayload = base64urlEncode(generatePayload().toString().getBytes(StandardCharsets.UTF_8));

			String data = encodedHeader + "." + encodedPayload;
			SecretKeySpec keySpec = new SecretKeySpec(jwtConfig.getSecret().getBytes(StandardCharsets.UTF_8), "HmacSHA256");
			Mac mac = Mac.getInstance("HmacSHA256");
			mac.init(keySpec);
			byte[] signedData = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
			String encodedSignature = base64urlEncode(signedData);
			return encodedHeader + "." + encodedPayload + "." + encodedSignature;
		} catch (Exception e) {
			throw new RuntimeException("Error al generar el token JWT", e);
		}
	}
	

	private String base64urlEncode(byte[] data) {
		String encoded = Base64.getEncoder().encodeToString(data);
		encoded = encoded.replace("+", "-").replace("/", "_").replace("=", "");
		return encoded;
	}

	private JSONObject generateHeader() {
		JSONObject header = new JSONObject();
		header.put("alg", "HS256");
		header.put("typ", "JWT");
		return header;
	}

	private JSONObject generatePayload() {
		long now = System.currentTimeMillis();
		JSONObject payload = new JSONObject();
		payload.put("iss", jwtConfig.getIssuer());
		payload.put("iat", now / 1000);
		payload.put("exp", (now + jwtConfig.getExpirationTime()) / 1000);
		payload.put("aud", jwtConfig.getAudience());
		payload.put("sub", jwtConfig.getSubject());
		payload.put("role", new String[] { jwtConfig.getRole() });
		return payload;
	}


}
