package com.pdd.domain;

public class AccessToken {
	private String token;  
    // ƾ֤��Чʱ�䣬��λ����  
    private int expiresIn;  
  
    public String getToken() {  
        return token;  
    }  
  
    public void setToken(String token) {  
        this.token = token;  
    }  
  
    public int getExpiresIn() {  
        return expiresIn;  
    }  
  
    public void setExpiresIn(int expiresIn) {  
        this.expiresIn = expiresIn;  
    }  
    
    public AccessToken(String token,int expiresIn){
    	this.token = token ;
    	this.expiresIn = expiresIn;
    }
    public AccessToken()
    {
    	
    }
}
