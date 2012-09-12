package com.softlayer.messaging.messagequeue.client;

import java.io.IOException;
import java.util.Hashtable;

import org.json.JSONException;
import org.json.JSONObject;

import com.softlayer.messaging.messagequeue.UnexpectedReplyTypeException;

import com.softlayer.messaging.messagequeue.model.Message;
import com.softlayer.messaging.messagequeue.model.MessageResponse;
import com.softlayer.messaging.messagequeue.model.Subscription;
import com.softlayer.messaging.messagequeue.model.SubscriptionResponse;
import com.softlayer.messaging.messagequeue.model.Topic;
import com.softlayer.messaging.messagequeue.model.TopicResponse;

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
 * Client for interacting with the Topic API
 * 
 * 
 * 
 * @author haroldhannon
 * 
 */
public class TopicClient extends Client {

	private static final String FORCE = "force";
	private static final String TOPICSURL = "/topics";
	private static final String MESSAGES = "/messages";
	private static final String SUBSCRIPTIONS = "/subscriptions";

	/**
	 * 
	 * @param baseUrl
	 *            the primary url string for the message queue service
	 * @param username
	 *            your username for IMS
	 * @param password
	 *            your API key for IMS
	 * 
	 * @throws IOException
	 * @throws JSONException
	 * @throws UnexpectedReplyTypeException
	 */
	public TopicClient(String baseUrl, String username, String password)
			throws IOException, JSONException, UnexpectedReplyTypeException {
		super(baseUrl, username, password);

	}

	/**
	 * 
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
	public TopicClient(String baseUrl, String username, String password,
			boolean ssl) throws IOException, JSONException,
			UnexpectedReplyTypeException {
		super(baseUrl, username, password, ssl);

	}
	
	/**
	 *
	 * @param token
	 */
	public TopicClient(String token) {
		super(token);
	}

	/**
	 * retrieves topic object for the given topic name
	 * 
	 * @param topicName
	 *            the name of the topic to retrieve
	 * @return the designated topic object
	 * @throws IOException
	 * @throws JSONException
	 * @throws UnexpectedReplyTypeException
	 */
	public Topic get(String topicName) throws IOException, JSONException,
			UnexpectedReplyTypeException {
		Hashtable<String, String> headers = super.createHeaderToken();
		String url = baseurl + TOPICSURL + "/" + topicName;
		JSONObject result = super.get(headers, null, url);
		Topic topic = gson.fromJson(result.toString(), Topic.class);

		return topic;

	}

	/**
	 * lists the topics for this account
	 * 
	 * @return an array of topics for this account
	 * @throws IOException
	 * @throws JSONException
	 * @throws UnexpectedReplyTypeException
	 */
	public Topic[] list() throws IOException, JSONException,
			UnexpectedReplyTypeException {
		return this.list(null);
	}

	/**
	 * lists topics filtered by a set of tags
	 * 
	 * @param tags
	 *            an array of tags to filter by
	 * @return an array of topics matching the filter tags
	 * @throws IOException
	 * @throws JSONException
	 * @throws UnexpectedReplyTypeException
	 */
	public Topic[] list(String[] tags) throws IOException, JSONException,
			UnexpectedReplyTypeException {

		Hashtable<String, String> headers = super.createHeaderToken();
		Hashtable<String, String> params = new Hashtable<String, String>();

		super.makeTags(tags, Client.TAGS, params);

		String url = baseurl + TOPICSURL;
		JSONObject result = super.get(headers, params, url);
		TopicResponse topics = gson.fromJson(result.toString(),
				TopicResponse.class);

		return topics.getItems().toArray(new Topic[0]);

	}

	/**
	 * creates a topic by the given name
	 * 
	 * @param topicName
	 *            the name for this topic
	 * @return a response containing a message indicating successful object
	 *         creation
	 * @throws IOException
	 * @throws JSONException
	 * @throws UnexpectedReplyTypeException
	 */
	public TopicResponse create(Topic topic) throws IOException, JSONException,
			UnexpectedReplyTypeException {
		String json = gson.toJson(topic);
		Hashtable<String, String> headers = super.createHeaderToken();
		String url = baseurl + TOPICSURL + "/" + topic.getName();
		JSONObject result = super.put(headers, null, json, url);
		TopicResponse response = gson.fromJson(result.toString(),
				TopicResponse.class);
		return response;
	}

	/**
	 * removes the subscription from the topic
	 * 
	 * @param topicName
	 *            the name of the topic to remove the subscription from
	 * @param subscriptionId
	 *            the id of the subscription to remove
	 * @return the response indicating pending deletion
	 * @throws IOException
	 * @throws JSONException
	 * @throws UnexpectedReplyTypeException
	 */
	public TopicResponse deleteSubscription(String topicName,
			String subscriptionId) throws IOException, JSONException,
			UnexpectedReplyTypeException {
		Hashtable<String, String> headers = super.createHeaderToken();
		String url = Client.baseurl + TOPICSURL + "/" + topicName
				+ SUBSCRIPTIONS + "/" + subscriptionId;
		System.out.println(url);
		JSONObject result = super.delete(headers, null, url);
		TopicResponse response = gson.fromJson(result.toString(),
				TopicResponse.class);
		return response;
	}

	/**
	 * retrieves a subscription object array for the topic indicated
	 * 
	 * @param topicName
	 *            the name of the topic to retrieve the subscription list from
	 * @return a response object containing an item array with the subscription
	 *         values
	 * @throws IOException
	 * @throws JSONException
	 * @throws UnexpectedReplyTypeException
	 */
	public SubscriptionResponse getSubscriptions(String topicName)
			throws IOException, JSONException, UnexpectedReplyTypeException {
		Hashtable<String, String> headers = super.createHeaderToken();
		String url = baseurl + TOPICSURL + "/" + topicName + SUBSCRIPTIONS;
		JSONObject jsonObj = super.get(headers, null, url);
		SubscriptionResponse response = gson.fromJson(jsonObj.toString(),
				SubscriptionResponse.class);
		return response;

	}

	/**
	 * creates a subscription for to the given topic
	 * 
	 * @param topicName
	 *            the topic to subscribe to
	 * @param subscription
	 *            the subscription object describing the type of subscription
	 * @return a message indicating subscription creation including the unique
	 *         id for the subscription
	 * @throws IOException
	 * @throws JSONException
	 * @throws UnexpectedReplyTypeException
	 */
	public SubscriptionResponse subscribe(String topicName,
			Subscription subscription) throws IOException, JSONException,
			UnexpectedReplyTypeException {
		String json = gson.toJson(subscription);
		Hashtable<String, String> headers = super.createHeaderToken();
		String url = baseurl + TOPICSURL + "/" + topicName + SUBSCRIPTIONS;
		JSONObject jsonObj = super.post(headers, null, json, url);
		SubscriptionResponse response = gson.fromJson(jsonObj.toString(),
				SubscriptionResponse.class);
		return response;
	}

	/**
	 * removes the topic
	 * 
	 * @param topicName
	 *            the name of the topic to remove
	 * @param force
	 *            a boolean indicating to force the deletion even if messages
	 *            exist on the topic
	 * @return
	 * @throws IOException
	 * @throws JSONException
	 * @throws UnexpectedReplyTypeException
	 */
	public TopicResponse delete(String topicName, boolean force)
			throws IOException, JSONException, UnexpectedReplyTypeException {
		Hashtable<String, String> headers = super.createHeaderToken();
		Hashtable<String, String> params = new Hashtable<String, String>();
		if (force) {

			params.put(FORCE, String.valueOf(force));
		}
		String url = Client.baseurl + TOPICSURL + "/" + topicName;
		JSONObject result = super.delete(headers, params, url);
		TopicResponse response = gson.fromJson(result.toString(),
				TopicResponse.class);
		return response;
	}

	/**
	 * posts a message to the topic
	 * 
	 * @param topicName
	 *            the topic to post the message to
	 * @param message
	 *            the message object
	 * @return a response indicating success of posting the message
	 * @throws IOException
	 * @throws JSONException
	 * @throws UnexpectedReplyTypeException
	 */
	public MessageResponse postMessage(String topicName, Message message)
			throws IOException, JSONException, UnexpectedReplyTypeException {
		String json = gson.toJson(message);
		Hashtable<String, String> headers = super.createHeaderToken();
		String url = baseurl + TOPICSURL + "/" + topicName + MESSAGES;
		JSONObject jsonObj = super.post(headers, null, json, url);
		MessageResponse msg = gson.fromJson(jsonObj.toString(),
				MessageResponse.class);
		return msg;
	}

}
