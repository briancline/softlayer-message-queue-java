package com.softlayer.messaging.messagequeue.client;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.Context;
import org.restlet.data.Form;
import org.restlet.data.MediaType;
import org.restlet.representation.EmptyRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;
import org.restlet.ext.json.JsonRepresentation;

import com.google.gson.Gson;
import com.softlayer.messaging.messagequeue.MessageQueueHttpException;
import com.softlayer.messaging.messagequeue.UnexpectedReplyTypeException;
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
 * 
 * base client for http rest connections
 * 
 * @author hhannon
 * 
 */
public class Client {

	private static final String USERNAME = "X-Auth-User";
	private static final String APIKEY = "X-Auth-Key";
	private static final String TOKEN = "X-Auth-Token";
	protected static final String TAGS = "tags";

	private static final String AUTHURL = "/auth";
	private static final String RESTLET_HTTP_HEADERS = "org.restlet.http.headers";

	protected static String baseurl;
	private String token;
	protected Gson gson;
	private boolean ssl = false;
	
	private AccountClient accountClient;
	private QueueClient queueClient;
	private TopicClient topicClient;
	
	Logger logger = Logger.getLogger(Client.class);

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
	public Client(String baseUrl, String username, String password)
			throws IOException, JSONException, UnexpectedReplyTypeException {
		gson = new Gson();
		baseurl = baseUrl;
		this.token = this.auth(username, password);

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
	public Client(String baseUrl, String username, String password, boolean ssl)
			throws IOException, JSONException, UnexpectedReplyTypeException {
		this.ssl = ssl;
		gson = new Gson();
		baseurl = baseUrl;
		this.token = this.auth(username, password);

	}
	
	/**
	 * 
	 * @param token
	 */
	public Client(String token) {
		gson = new Gson();
		this.token = token;
	}
	
	/**
	 * 
	 * @return an account client
	 */
	public AccountClient getAccountClient() {
		if(accountClient == null) {
			accountClient = new AccountClient(token);
		}
		
		return accountClient;
	}
	
	/**
	 * 
	 * @return a queue client
	 */
	public QueueClient getQueueClient() {
		if(queueClient == null) {
			queueClient = new QueueClient(token);
		}
		
		return queueClient;
	}
	
	/**
	 * 
	 * @return a topic client
	 */
	public TopicClient getTopicClient() {
		if(topicClient == null) {
			topicClient = new TopicClient(token);
		}
		
		return topicClient;
	}

	/**
	 * This authenticates users for interactions with the softlayer message
	 * queue
	 * 
	 * @param username
	 *            your username for IMS
	 * @param password
	 *            your API key for IMS
	 * @return the auth token for interacting with the message queue service
	 * @throws IOException
	 * @throws JSONException
	 * @throws UnexpectedReplyTypeException
	 */
	private String auth(String username, String apikey) throws IOException,
			JSONException, UnexpectedReplyTypeException {

		Hashtable<String, String> headers = new Hashtable<String, String>();
		headers.put(USERNAME, username);
		headers.put(APIKEY, apikey);
		String url = baseurl + AUTHURL;
		JSONObject reply = this.post(headers, null, null, url);

		return reply.getString("token");
	}

	/**
	 * This method creates a HTTP POST request
	 * 
	 * @param headers
	 *            http headers as key value pairs
	 * @param params
	 *            query string params as key value pairs
	 * @param url
	 *            the url address of the POST request
	 * @return the JSON response
	 * @throws IOException
	 * @throws JSONException
	 * @throws UnexpectedReplyTypeException
	 */
	protected JSONObject post(Hashtable<String, String> headers,
			Hashtable<String, String> params, String json, String url)
			throws IOException, JSONException, UnexpectedReplyTypeException {
		return this.httpRequest(headers, params, json, url, HttpMethod.post);
	}

	/**
	 * This method creates a HTTP PUT request
	 * 
	 * @param headers
	 *            http headers as key value pairs
	 * @param params
	 *            query string params as key value pairs
	 * @param json
	 *            the json document for the PUT request
	 * @param url
	 *            the url address of the PUT request
	 * @return the JSON response
	 * @throws IOException
	 * @throws JSONException
	 * @throws UnexpectedReplyTypeException
	 */
	protected JSONObject put(Hashtable<String, String> headers,
			Hashtable<String, String> params, String json, String url)
			throws IOException, JSONException, UnexpectedReplyTypeException {
		return this.httpRequest(headers, params, json, url, HttpMethod.put);
	}

	/**
	 * This method creates a HTTP GET request
	 * 
	 * @param headers
	 *            http headers as key value pairs
	 * @param params
	 *            query string params as key value pairs
	 * @param url
	 *            the url address of the GET request
	 * @return the JSON response
	 * @throws IOException
	 * @throws JSONException
	 * @throws UnexpectedReplyTypeException
	 */
	protected JSONObject get(Hashtable<String, String> headers,
			Hashtable<String, String> params, String url) throws IOException,
			JSONException, UnexpectedReplyTypeException {
		return this.httpRequest(headers, params, null, url, HttpMethod.get);
	}

	/**
	 * This method creates a HTTP DELETE request
	 * 
	 * @param headers
	 *            http headers as key value pairs
	 * @param params
	 *            query string params as key value pairs
	 * @param url
	 *            the url address of the DELETE request
	 * @return the JSON response
	 * @throws IOException
	 * @throws JSONException
	 * @throws UnexpectedReplyTypeException
	 */
	protected JSONObject delete(Hashtable<String, String> headers,
			Hashtable<String, String> params, String url) throws IOException,
			JSONException, UnexpectedReplyTypeException {
		return this.httpRequest(headers, params, null, url, HttpMethod.delete);
	}

	/**
	 * Creates and receives the response of the HTTP request
	 * 
	 * @param headers
	 *            http headers as key value pairs
	 * @param params
	 *            query string params as key value pairs
	 * @param json
	 *            the json document for the PUT request
	 * @param url
	 *            the url address of the HTTP request
	 * @param type
	 *            an enumerated type for the request type POST, PUT, GET, DELETE
	 * @return the JSON response
	 * @throws IOException
	 * @throws JSONException
	 * @throws UnexpectedReplyTypeException
	 */
	private JSONObject httpRequest(Hashtable<String, String> headers,
			Hashtable<String, String> params, String json, String url,
			HttpMethod type) throws IOException, JSONException,
			UnexpectedReplyTypeException {
		ClientResource requestResource = null;
		Context ctx = new Context();
		if (ssl) {
			ctx.getParameters().add("truststorePath",
					"messagequeue.jks");
			ctx.getParameters().add("truststorePassword", "password");
			ctx.getParameters().add("truststoreType", "JKS");
			requestResource = new ClientResource(ctx, url);
		} else {
			requestResource = new ClientResource(url);
		}
		Representation reply = null;
		if (json != null) {
			reply = new JsonRepresentation(json);
			reply.setMediaType(MediaType.APPLICATION_JSON);
		}
		if (params != null)
			addRequestParams(params, requestResource);
		if (headers != null)
			setCustomHttpHeader(headers, requestResource);
		try {
			switch (type) {

			case post:

				reply = requestResource.post(reply);
				break;

			case put:

				reply = requestResource.put(reply);
				break;

			case get:

				reply = requestResource.get();
				break;

			case delete:

				reply = requestResource.delete();
				break;

			}
		} catch (ResourceException re) {
			Representation response = requestResource.getResponseEntity();
			String jsonResponse = response.getText();
			if (jsonResponse != null) {
				JSONObject jsObjResponse = new JSONObject(jsonResponse);
				MessageQueueHttpException mqh = new MessageQueueHttpException(
						re);
				mqh.setErrorMessageBody(jsObjResponse);
				throw mqh;
			} else {
				throw re;
			}
		}

		String replyText = reply.getText();
		reply.release();
		if (reply.getMediaType().equals(new MediaType("application/json"))) {
			JSONObject jsObj = new JSONObject(replyText);
			this.logger.debug(jsObj.toString());
			return jsObj;
		} else if (reply instanceof EmptyRepresentation) {
			return null;
		} else {
			throw new UnexpectedReplyTypeException(
					"the reply was not of type: application/json");
		}

	}
	
	/**
	 * Set the value of a custom HTTP header
	 * 
	 * @param header
	 *            the custom HTTP header to set the value for, for example
	 *            <code>X-MyApp-MyHeader</code>
	 * @param value
	 *            the value of the custom HTTP header to set
	 */
	public static void setCustomHttpHeader(Hashtable<String, String> params,
			ClientResource client) {

		Map<String, Object> reqAttribs = client.getRequestAttributes();
		Form headers = (Form) reqAttribs.get(RESTLET_HTTP_HEADERS);
		if (headers == null) {
			headers = new Form();
			reqAttribs.put(RESTLET_HTTP_HEADERS, headers);
		}

		Enumeration<String> en = params.keys();
		while (en.hasMoreElements()) {
			String header = en.nextElement();
			String value = params.get(header);
			headers.add(header, value);
		}
	}

	/**
	 * adds query string params
	 * 
	 * @param params
	 *            a key value pair of query string params
	 * @param requestResource
	 *            the client request resource to set the query string to
	 */
	private static void addRequestParams(Hashtable<String, String> params,
			ClientResource requestResource) {

		Enumeration<String> en = params.keys();
		while (en.hasMoreElements()) {
			String key = en.nextElement();
			String value = params.get(key);
			requestResource.getReference().addQueryParameter(key, value);
		}

	}

	/**
	 * adds the auth token to the request header
	 * 
	 * @return hashtable of token key value
	 */
	protected Hashtable<String, String> createHeaderToken() {

		Hashtable<String, String> params = new Hashtable<String, String>();
		params.put(TOKEN, this.token);
		return params;
	}

	/**
	 * creates query string tags
	 * 
	 * @param tags
	 *            an array of tag values
	 * @param tagName
	 *            the tag name
	 * @param params
	 *            a key value pair of query string params to load the values
	 *            into
	 */
	protected void makeTags(String[] tags, String tagName,
			Hashtable<String, String> params) {
		if (tags != null) {
			String tagString = "";
			for (String tag : tags) {
				if (tagString.length() > 0) {
					tagString += ",";
				}
				tagString += tag;
			}

			params.put(tagName, tagString);

		}

	}

}
