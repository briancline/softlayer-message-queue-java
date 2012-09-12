package com.softlayer.messaging.messagequeue.client;

import java.io.IOException;
import java.util.Hashtable;

import org.json.JSONException;
import org.json.JSONObject;

import com.softlayer.messaging.messagequeue.UnexpectedReplyTypeException;
import com.softlayer.messaging.messagequeue.model.AccountResponse;
import com.softlayer.messaging.messagequeue.util.AccountStatsDurationType;

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
 * Client for interacting with the queue account api
 * 
 * 
 * @author haroldhannon
 * 
 */
public class AccountClient extends Client {

	private static final String STATS = "stats";

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
	public AccountClient(String baseUrl, String username, String password)
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
	public AccountClient(String baseUrl, String username, String password,
			boolean ssl) throws IOException, JSONException,
			UnexpectedReplyTypeException {
		super(baseUrl, username, password, ssl);

	}
	
	/**
	 *
	 * @param token
	 */
	public AccountClient(String token) {
		super(token);
	}

	/**
	 * Retrieves account statistics for requests and notifications
	 * 
	 * 
	 * 
	 * @param duration
	 *            an enumerated value for the duration of the data being
	 *            requested hour/day/week/month
	 * @return a key value array of data for the requested duration
	 * @throws IOException
	 * @throws JSONException
	 * @throws UnexpectedReplyTypeException
	 */
	public AccountResponse getAccountStats(AccountStatsDurationType duration)
			throws IOException, JSONException, UnexpectedReplyTypeException {
		Hashtable<String, String> headers = super.createHeaderToken();
		String url = baseurl + "/" + STATS + "/" + duration.toString();
		JSONObject result = super.get(headers, null, url);
		AccountResponse response = gson.fromJson(result.toString(),
				AccountResponse.class);
		return response;

	}

}
