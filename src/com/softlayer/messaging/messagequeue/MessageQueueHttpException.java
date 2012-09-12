package com.softlayer.messaging.messagequeue;

import org.json.JSONObject;
import org.restlet.data.Status;
import org.restlet.resource.ResourceException;

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

@SuppressWarnings("serial")
public class MessageQueueHttpException extends ResourceException {

	private JSONObject errorMessageBody;

	public MessageQueueHttpException(int code) {
		super(code);
	}

	public MessageQueueHttpException(Status status) {
		super(status);
	}

	public MessageQueueHttpException(Throwable cause) {
		super(cause);
	}

	public MessageQueueHttpException(int code, Throwable cause) {
		super(code, cause);
	}

	public MessageQueueHttpException(Status status, String description) {
		super(status, description);
	}

	public MessageQueueHttpException(Status status, Throwable cause) {
		super(status, cause);
	}

	public MessageQueueHttpException(Status status, String description,
			Throwable cause) {
		super(status, description, cause);
	}

	public MessageQueueHttpException(int code, String name, String description,
			String uri) {
		super(code, name, description, uri);
	}

	public MessageQueueHttpException(int code, String name, String description,
			String uri, Throwable cause) {
		super(code, name, description, uri, cause);
	}

	public JSONObject getErrorMessageBody() {
		return errorMessageBody;
	}

	public void setErrorMessageBody(JSONObject errorMessageBody) {
		this.errorMessageBody = errorMessageBody;
	}

}
