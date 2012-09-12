package com.softlayer.messaging.messagequeue.client;

import java.io.IOException;
import java.util.Hashtable;

import org.json.JSONException;
import org.json.JSONObject;

import com.softlayer.messaging.messagequeue.UnexpectedReplyTypeException;
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
 * 
 * Client for interacting with the Queue api
 * 
 * @author haroldhannon
 * 
 */
public class QueueClient extends Client {

	private static final String QUEUELIMIT = "batch";
	private static final String FORCE = "force";
	private static final String QUEUESURL = "/queues";
	private static final String MESSAGES = "/messages";

	/**
	 * @param baseUrl
	 *            the primary url string for the message queue service
	 * @param username
	 *            your username for IMS
	 * @param password
	 *            your API key for IMS
	 * @throws IOException
	 * @throws JSONException
	 * @throws UnexpectedReplyTypeException
	 */
	public QueueClient(String baseUrl, String username, String password)
			throws IOException, JSONException, UnexpectedReplyTypeException {
		super(baseUrl, username, password);

	}

	/**
	 * @param baseUrl
	 *            the primary url string for the message queue service
	 * @param username
	 *            your username for IMS
	 * @param password
	 *            your API key for IMS
	 * @param ssl
	 *            boolean indicating if ssl should be used
	 * @throws IOException
	 * @throws JSONException
	 * @throws UnexpectedReplyTypeException
	 */
	public QueueClient(String baseUrl, String username, String password,
			boolean ssl) throws IOException, JSONException,
			UnexpectedReplyTypeException {
		super(baseUrl, username, password, ssl);

	}
	
	/**
	 *
	 * @param token
	 */
	public QueueClient(String token) {
		super(token);
	}

	/**
	 * retrieves a queue object representing the designated queue
	 * 
	 * @param queueName
	 *            the name of the queue to retrieve
	 * @return a queue object representing the queue
	 * @throws IOException
	 * @throws JSONException
	 * @throws UnexpectedReplyTypeException
	 */
	public Queue get(String queueName) throws IOException, JSONException,
			UnexpectedReplyTypeException {
		Hashtable<String, String> headers = super.createHeaderToken();
		String url = baseurl + QUEUESURL + "/" + queueName;
		JSONObject json = super.get(headers, null, url);
		Queue q = gson.fromJson(json.toString(), Queue.class);
		return q;

	}

	/**
	 * 
	 * 
	 * retreives messages for the given queue
	 * 
	 * @param queueName
	 *            the queue to get messages from
	 * @param limit
	 *            the maximum number of messages to retrieve
	 * @return an array of message data
	 * 
	 * @throws IOException
	 * @throws JSONException
	 * @throws UnexpectedReplyTypeException
	 */

	public Message[] getMessages(String queueName, int limit)
			throws IOException, JSONException, UnexpectedReplyTypeException {
		Hashtable<String, String> headers = super.createHeaderToken();
		Hashtable<String, String> params = new Hashtable<String, String>();
		if (limit != -1)
			params.put(QUEUELIMIT, String.valueOf(limit));
		String url = baseurl + QUEUESURL + "/" + queueName + MESSAGES;
		JSONObject json = super.get(headers, params, url);
		MessageResponse msg = gson.fromJson(json.toString(),
				MessageResponse.class);
		return msg.getItems().toArray(new Message[0]);

	}

	/**
	 * 
	 * @param queueName
	 *            the queue to get messages from
	 * @return an array of message data
	 * 
	 * @throws IOException
	 * @throws JSONException
	 * @throws UnexpectedReplyTypeException
	 */

	public Message[] getMessages(String queueName) throws IOException,
			JSONException, UnexpectedReplyTypeException {
		return getMessages(queueName, -1);
	}

	/**
	 * 
	 * @param queueName
	 *            the name of the queue to post the message to
	 * @param message
	 *            a message object containing the message data
	 * @return a message response containing the id of the newly published
	 *         message and acknowledgement data
	 * 
	 * @throws IOException
	 * @throws JSONException
	 * @throws UnexpectedReplyTypeException
	 */
	public MessageResponse postMessage(String queueName, Message message)
			throws IOException, JSONException, UnexpectedReplyTypeException {
		String json = gson.toJson(message);
		Hashtable<String, String> headers = super.createHeaderToken();
		String url = baseurl + QUEUESURL + "/" + queueName + MESSAGES;
		JSONObject jsonObj = super.post(headers, null, json, url);
		MessageResponse msg = gson.fromJson(jsonObj.toString(),
				MessageResponse.class);
		return msg;
	}

	/**
	 * 
	 * provides an array of queue objects available to this account
	 * 
	 * @return an array of queue objects
	 * @throws IOException
	 * @throws JSONException
	 * @throws UnexpectedReplyTypeException
	 */
	public Queue[] list() throws IOException, JSONException,
			UnexpectedReplyTypeException {
		return this.list(null);
	}

	/**
	 * 
	 * provides an array of queue objects available to this account filtered by
	 * the tags provided
	 * 
	 * @param tags
	 *            an array of tags to filter by
	 * @return an array of queue objects
	 * @throws IOException
	 * @throws JSONException
	 * @throws UnexpectedReplyTypeException
	 */
	public Queue[] list(String[] tags) throws IOException, JSONException,
			UnexpectedReplyTypeException {

		Hashtable<String, String> headers = super.createHeaderToken();
		Hashtable<String, String> params = new Hashtable<String, String>();

		super.makeTags(tags, Client.TAGS, params);

		String url = baseurl + QUEUESURL;
		JSONObject result = super.get(headers, params, url);
		QueueResponse queues = gson.fromJson(result.toString(),
				QueueResponse.class);

		return queues.getItems().toArray(new Queue[0]);

	}

	/**
	 * 
	 * creates a new queue instance
	 * 
	 * @param queue
	 *            the queue object representing the queue to be created
	 * @return a response including the queue id
	 * 
	 * @throws IOException
	 * @throws JSONException
	 * @throws UnexpectedReplyTypeException
	 */
	public QueueResponse create(Queue queue) throws IOException, JSONException,
			UnexpectedReplyTypeException {

		String json = gson.toJson(queue);

		Hashtable<String, String> headers = super.createHeaderToken();
		String url = baseurl + QUEUESURL + "/" + queue.getName();

		JSONObject jsonObj = super.put(headers, null, json, url);

		QueueResponse response = gson.fromJson(jsonObj.toString(),
				QueueResponse.class);

		return response;

	}

	/**
	 * 
	 * removes the designated queue
	 * 
	 * @param queueName
	 *            the name of the queue to delete
	 * @param force
	 *            boolean value to force a delete
	 * @return a response message notifying of pending delete
	 * 
	 * @throws IOException
	 * @throws JSONException
	 * @throws UnexpectedReplyTypeException
	 */
	public QueueResponse delete(String queueName, boolean force)
			throws IOException, JSONException, UnexpectedReplyTypeException {
		Hashtable<String, String> headers = super.createHeaderToken();
		Hashtable<String, String> params = new Hashtable<String, String>();
		if (force) {

			params.put(FORCE, String.valueOf(force));
		}
		String url = Client.baseurl + QUEUESURL + "/" + queueName;
		JSONObject result = super.delete(headers, params, url);
		QueueResponse response = gson.fromJson(result.toString(),
				QueueResponse.class);

		return response;
	}

	/**
	 * 
	 * removes the designated queue
	 * 
	 * @param queueName
	 *            the name of the queue to delete
	 * @param messageId
	 *            the UUID of the message
	 * @return a response message notifying of pending delete
	 * 
	 * @throws IOException
	 * @throws JSONException
	 * @throws UnexpectedReplyTypeException
	 */
	public QueueResponse deleteMessage(String queueName, String messageId)
			throws IOException, JSONException, UnexpectedReplyTypeException {
		Hashtable<String, String> headers = super.createHeaderToken();
		Hashtable<String, String> params = new Hashtable<String, String>();

		String url = Client.baseurl + QUEUESURL + "/" + queueName + MESSAGES
				+ "/" + messageId;
		JSONObject result = super.delete(headers, params, url);
		QueueResponse response = gson.fromJson(result.toString(),
				QueueResponse.class);

		return response;
	}

}
