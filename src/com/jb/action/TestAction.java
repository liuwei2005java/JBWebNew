package com.jb.action;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class TestAction extends ActionSupport implements ModelDriven{

	public String test(){
		
		System.out.println("dddd");
		return SUCCESS;
	}

	public Object getModel() {
		// TODO Auto-generated method stub
		return null;
	}
}
