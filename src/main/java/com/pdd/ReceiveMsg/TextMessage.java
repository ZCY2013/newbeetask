package com.pdd.ReceiveMsg;

import com.pdd.base.BaseReqMessage;

public class TextMessage extends BaseReqMessage{
	
	private String context;
	
	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

}