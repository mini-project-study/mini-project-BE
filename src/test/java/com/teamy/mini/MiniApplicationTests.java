package com.teamy.mini;

import com.teamy.mini.domain.Article;
import com.teamy.mini.domain.File;
import com.teamy.mini.repository.ArticleRepository;
import com.teamy.mini.repository.FileRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.Ignore;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.spec.ECGenParameterSpec;

@Slf4j
@SpringBootTest

class MiniApplicationTests {

	@Autowired
	private ArticleRepository articleRepository;
	@Autowired
	private FileRepository fileRepository;

	@Disabled
	@Test
	void contextLoads() throws NoSuchAlgorithmException, InvalidAlgorithmParameterException {

		final KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC");
		keyPairGenerator.initialize(new ECGenParameterSpec("secp256r1"));

		final KeyPair keyPair = keyPairGenerator.generateKeyPair();

		log.info("publicKey: {}", Base64.encodeBase64String(keyPair.getPublic().getEncoded()));
		log.info("privateKey: {}", Base64.encodeBase64String(keyPair.getPrivate().getEncoded()));
	}

	@Test
	public void test() {
		File file = fileRepository.findFile(1);
		Article article = new Article(1, "1111", "title!!", "content!!", file);

		articleRepository.saveArticle(article);


	}
}
