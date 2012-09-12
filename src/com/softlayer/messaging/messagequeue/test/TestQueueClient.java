package com.softlayer.messaging.messagequeue.test;

import java.io.IOException;
import java.util.Hashtable;

import org.apache.log4j.BasicConfigurator;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import com.google.gson.Gson;
import com.softlayer.messaging.messagequeue.UnexpectedReplyTypeException;
import com.softlayer.messaging.messagequeue.client.Client;
import com.softlayer.messaging.messagequeue.model.Message;
import com.softlayer.messaging.messagequeue.model.MessageResponse;
import com.softlayer.messaging.messagequeue.model.Queue;
import com.softlayer.messaging.messagequeue.model.QueueResponse;

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

public class TestQueueClient {

	private String accountId;
	private String baseurl;
	private String username;
	private String password;
	private Client client;
	private String queueName;
	private Queue q;

	@Before
	public void setUp() throws Exception {

		BasicConfigurator.configure();
		accountId = "<your account id>";
		baseurl = "https://dal05.mq.softlayer.net/v1/" + accountId;
		username = "<your username>";
		password = "<your api key>";
		
		queueName = "javaTestQueue3";
		client = new Client(baseurl, username, password, true);
		q = new Queue();
		q.setName(queueName);
		q.setExpiration(60480);
		q.setVisibility_interval(30);
		q.setTags(new String[] { "java", "test" });
		Gson gson = new Gson();
		System.out.println(gson.toJson(q));

	}

	@Test
	public void testCreate() throws IOException, JSONException,
			UnexpectedReplyTypeException {
		QueueResponse response = client.getQueueClient().create(q);
		assert (response.getMessage().equalsIgnoreCase("Object Created"));
	}

	@Test
	public void testGetString() throws IOException, JSONException,
			UnexpectedReplyTypeException {

		Queue qb = client.getQueueClient().get(queueName);
		assert (qb.getName().equalsIgnoreCase(queueName));

	}

	@Test
	public void testPostMessage() throws IOException, JSONException,
			UnexpectedReplyTypeException {
		Message message = new Message();
		message.setBody("BAM right through your monitor!!!!");
		Hashtable<String,String> options = new Hashtable<String,String>();
		options.put("test", "option");
		message.setFields(options);
		message.setVisibility_delay(10);
		MessageResponse response = client.getQueueClient().postMessage(queueName, message);
		assert (response.getMessage().equalsIgnoreCase("Object created"));
	}

	@Test
	public void testGetMessagesString() throws IOException, JSONException,
			UnexpectedReplyTypeException {
		Message[] messages = client.getQueueClient().getMessages(queueName);
		for (Message body : messages) {

			assert (body.getId() != null);

		}
	}

	@Test
	public void testPostMessage2() throws IOException, JSONException,
			UnexpectedReplyTypeException {
		Message message = new Message();
		message.setBody("BAM right through your monitor 2!!!!");
		MessageResponse response = client.getQueueClient().postMessage(queueName, message);
		assert (response.getMessage().equalsIgnoreCase("Object created"));
	}

	@Test
	public void testPostMessage3() throws IOException, JSONException,
			UnexpectedReplyTypeException {
		Message message = new Message();
		message.setBody("BAM right through your monitor 3!!!!");
		MessageResponse response = client.getQueueClient().postMessage(queueName, message);
		assert (response.getMessage().equalsIgnoreCase("Object created"));
	}

	@Test
	public void testGetMessagesStringInt() throws IOException, JSONException,
			UnexpectedReplyTypeException {
		Message[] messages = client.getQueueClient().getMessages(queueName, 5);
		for (Message body : messages) {

			assert (body.getId() != null);

		}
	}

	@Test
	public void testPostMessage4() throws IOException, JSONException,
			UnexpectedReplyTypeException {
		Message message = new Message();
		message.setBody("BAM right through your monitor 4!!!!");
		MessageResponse response = client.getQueueClient().postMessage(queueName, message);
		assert (response.getMessage().equalsIgnoreCase("Object created"));
	}

	@Test
	public void testDeleteMessageStringString() throws IOException,
			JSONException, UnexpectedReplyTypeException {
		Message message = new Message();
		message.setBody("Example Message");
		MessageResponse messageResponse = client.getQueueClient().postMessage(queueName, message);
		assert (client.getQueueClient().deleteMessage(queueName, messageResponse.getId()).getMessage()
				.equalsIgnoreCase("Object queued for deletion"));
	}

	@Test
	public void testList() throws IOException, JSONException,
			UnexpectedReplyTypeException {
		Queue[] queues = client.getQueueClient().list();
		assert (queues.length > 0);
	}

	@Test
	public void testDeleteStringBoolean() throws IOException, JSONException,
			UnexpectedReplyTypeException {
		try {
			QueueResponse response = client.getQueueClient().delete(queueName, true);
			assert (response.getMessage()
					.equalsIgnoreCase("Object queued for deletion"));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
}
