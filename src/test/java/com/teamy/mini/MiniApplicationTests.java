package com.teamy.mini;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.spec.ECGenParameterSpec;

@Slf4j
@SpringBootTest
class MiniApplicationTests {

	@Test
	void contextLoads() throws NoSuchAlgorithmException, InvalidAlgorithmParameterException {

		final KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC");
		keyPairGenerator.initialize(new ECGenParameterSpec("secp256r1"));

		final KeyPair keyPair = keyPairGenerator.generateKeyPair();

		log.info("publicKey: {}", Base64.encodeBase64String(keyPair.getPublic().getEncoded()));
		log.info("privateKey: {}", Base64.encodeBase64String(keyPair.getPrivate().getEncoded()));


	}

}
