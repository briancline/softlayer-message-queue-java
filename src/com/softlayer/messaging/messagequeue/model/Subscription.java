package com.softlayer.messaging.messagequeue.model;

import java.util.Hashtable;

import com.softlayer.messaging.messagequeue.util.HttpMethod;
import com.softlayer.messaging.messagequeue.util.EndpointType;

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
 * an object for holding subscription data
 * 
 * @author haroldhannon
 * 
 */
public class Subscription {

	private String id;
	private EndpointType endpoint_type;
	private Endpoint endpoint;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public EndpointType getSubscriptionType() {
		return endpoint_type;
	}

	public void setSubscriptionType(EndpointType endpoint_type) {
		this.endpoint_type = endpoint_type;
	}

	public Endpoint getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(Endpoint endpoint) {
		this.endpoint = endpoint;
	}

	public class Endpoint {

		private HttpMethod method;
		private String url;
		private Hashtable<String, String> params;
		private Hashtable<String, String> headers;
		private String body;
		private String queue_name;

		public String getQueue_name() {
			return queue_name;
		}

		public void setQueue_name(String queue_name) {
			this.queue_name = queue_name;
		}

		public HttpMethod getMethod() {
			return method;
		}

		public void setMethod(HttpMethod method) {
			this.method = method;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public Hashtable<String, String> getParams() {
			return params;
		}

		public void setParams(Hashtable<String, String> params) {
			this.params = params;
		}

		public Hashtable<String, String> getHeaders() {
			return headers;
		}

		public void setHeaders(Hashtable<String, String> headers) {
			this.headers = headers;
		}

		public String getBody() {
			return body;
		}

		public void setBody(String body) {
			this.body = body;
		}

	}
}
