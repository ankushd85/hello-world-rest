package com.org.app.controller;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * @author pfe456
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class RESTDemoAPICallControllerTest {

	@Autowired
	private WebTestClient webClient;

	/**
	 * GET call to pull all the posts
	 */
	@Test
	public void getPostsTest() {
		this.webClient.get().uri("/myorg/mylob/helloworld/external/apis/v1/posts")
			.exchange().expectStatus().is2xxSuccessful();
	}

	/**
	 * GET call with incorrect url
	 */
	@Test
	public void getPostsWrongUriTest() {
		this.webClient.get().uri("/myorg/mylob/helloworld/external/apis/v1/post")
			.exchange().expectStatus().is4xxClientError();
	}
}
