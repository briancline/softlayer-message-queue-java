package com.softlayer.messaging.messagequeue.test;


import java.io.IOException;


import org.apache.log4j.BasicConfigurator;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;

import com.softlayer.messaging.messagequeue.UnexpectedReplyTypeException;
import com.softlayer.messaging.messagequeue.client.Client;
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
 */
public class TestAccountClient {

	private String accountId;
	private String baseurl;
	private String username;
	private String password;
	private Client client;

	@Before
	public void setUp() throws Exception {
		BasicConfigurator.configure();
		accountId = "<your account id>";
		baseurl = "https://dal05.mq.softlayer.net/v1/" + accountId;
		username = "<your username>";
		password = "<your api key>";
		client = new Client(baseurl, username, password, true);
	}

	@Test
	public void testGetAccountStats() throws IOException, JSONException,
			UnexpectedReplyTypeException {
		AccountResponse response = client.getAccountClient().getAccountStats(AccountStatsDurationType.month);
		assert (response.getRequests().length > 0);
	}

}
