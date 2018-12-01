package com.example.util;

public class ResultSate {

	public static Result Success() {
		return new Result(1, "操作成功");
	}

	//方法写活
	public static Result Success(String msg) {
		return new Result(1, msg);
	}
	
	public static Result Fail() {
		return new Result(-1, "操作失败");
	}
}
