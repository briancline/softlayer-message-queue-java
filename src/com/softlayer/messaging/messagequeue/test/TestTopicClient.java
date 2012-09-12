package com.softlayer.messaging.messagequeue.test;

import java.io.IOException;
import java.util.Hashtable;

import org.apache.log4j.BasicConfigurator;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;

import com.softlayer.messaging.messagequeue.UnexpectedReplyTypeException;
import com.softlayer.messaging.messagequeue.client.Client;
import com.softlayer.messaging.messagequeue.model.Message;
import com.softlayer.messaging.messagequeue.model.MessageResponse;
import com.softlayer.messaging.messagequeue.model.Subscription;
import com.softlayer.messaging.messagequeue.model.SubscriptionResponse;
import com.softlayer.messaging.messagequeue.model.Topic;
import com.softlayer.messaging.messagequeue.model.TopicResponse;
import com.softlayer.messaging.messagequeue.model.Subscription.Endpoint;
import com.softlayer.messaging.messagequeue.util.EndpointType;
import com.softlayer.messaging.messagequeue.util.HttpMethod;

/**
 * Unless otherwise noted, all files are released under the MIT license,
 * exceptions contain licensing information in them.
 * 
 * Copyright (C) 2012 SoftLayer Technologies, Inc.
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 * 
 * Except as contained in this notice, the name of SoftLayer Technologies, Inc.
 * shall not be used in advertising or otherwise to promote the sale, use or
 * other dealings in this Software without prior written authorization from
 * SoftLayer Technologies, Inc.
 */

public class TestTopicClient {

	private String accountId;
	private String baseurl;
	private String username;
	private String password;
	private Client client;
	private String topicName;
	
	@Before
	public void setUp() throws Exception {

		BasicConfigurator.configure();
		accountId = "<your account id>";
		baseurl = "https://dal05.mq.softlayer.net/v1/" + accountId;
		username = "<your username>";
		password = "<your api key>";
		topicName = "javaTestTopic2";
		client = new Client(baseurl, username, password, true);
		
	}

	@Test
	public void testCreate() throws IOException, JSONException,
			UnexpectedReplyTypeException {
		Topic t = new Topic();
		t.setName(topicName);
		t.setTags(new String[]{"java", "topic", "tags"});
		TopicResponse response = client.getTopicClient().create(t);
		assert (response.getName().equalsIgnoreCase(topicName));
	}

	@Test
	public void testGetString() throws IOException, JSONException,
			UnexpectedReplyTypeException {
		Topic response = client.getTopicClient().get(topicName);
		assert (response.getName().equalsIgnoreCase(topicName));
	}

	@Test
	public void testList() throws IOException, JSONException,
			UnexpectedReplyTypeException {
		Topic[] topics = client.getTopicClient().list();
		assert (topics.length > 0);
	}

	@Test
	public void testPostMessage() throws IOException, JSONException,
			UnexpectedReplyTypeException {
		Message topicMessage = new Message();

		topicMessage.setBody("Bam!!! Right through your topic");

		MessageResponse response = client.getTopicClient().postMessage(topicName, topicMessage);
		assert (response.getMessage().equalsIgnoreCase("Object created"));

	}

	@Test
	public void testSubscribe() throws IOException, JSONException,
			UnexpectedReplyTypeException {
		Subscription sub = new Subscription();

		Endpoint end = sub.new Endpoint();

		sub.setSubscriptionType(EndpointType.http);
		end.setMethod(HttpMethod.get);
		end.setUrl("http://www.example.com/");
		Hashtable<String, String> headers = new Hashtable<String, String>();
		headers.put("Content-Type", "application/x-www-form-urlencoded");
		end.setHeaders(headers);
		end.setBody("Hello World!");

		sub.setEndpoint(end);
		
		Topic topic = new Topic();
		topic.setName("testSubscriptionTopic");
		TopicResponse topResponse = client.getTopicClient().create(topic);
		
		assert (topResponse.getMessage().equalsIgnoreCase("Object created"));
		
		SubscriptionResponse subResponse = client.getTopicClient().subscribe(topic.getName(), sub);

		assert (subResponse.getMessage().equalsIgnoreCase("Object created"));
	}

	@Test
	public void testGetSubscriptions() throws IOException, JSONException,
			UnexpectedReplyTypeException {
		Topic topic = new Topic();
		topic.setName("testGetSubscriptions");
		client.getTopicClient().create(topic);
		
		Subscription subscription = new Subscription();
		Endpoint endpoint = subscription.new Endpoint();
		endpoint.setMethod(HttpMethod.get);
		endpoint.setUrl("http://www.example.com/");
		
		subscription.setEndpoint(endpoint);
		subscription.setSubscriptionType(EndpointType.http);
		
		client.getTopicClient().subscribe(topic.getName(), subscription);
		
		SubscriptionResponse subResponse = client.getTopicClient().getSubscriptions(topic.getName());
		assert (subResponse.getItems().length > 0);
	}

	@Test
	public void testDeleteSubscription() throws IOException, JSONException,
			UnexpectedReplyTypeException {
		Topic topic = new Topic();
		topic.setName("testDeleteSubscription");
		
		TopicResponse topicResponse = client.getTopicClient().create(topic);
		
		assert (topicResponse.getMessage().equals("Object created"));
		
		Subscription subscription = new Subscription();
		Endpoint endpoint = subscription.new Endpoint();
		endpoint.setMethod(HttpMethod.get);
		endpoint.setUrl("http://www.example.com/");
		
		subscription.setEndpoint(endpoint);
		subscription.setSubscriptionType(EndpointType.http);
		
		SubscriptionResponse subscriptionResponse = client.getTopicClient().subscribe(topic.getName(), subscription);
		
		topicResponse = client.getTopicClient().deleteSubscription(topic.getName(),
				subscriptionResponse.getId());
		
		assert (topicResponse.getMessage()
				.equalsIgnoreCase("Object queued for deletion"));
	}

	@Test
	public void testDeleteStringBoolean() throws IOException, JSONException,
			UnexpectedReplyTypeException {
		TopicResponse response = client.getTopicClient().delete(topicName, true);
		assert (response.getMessage()
				.equalsIgnoreCase("Object queued for deletion"));
	}

}
