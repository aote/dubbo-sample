package com.aote.tmall.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.aote.tmall.bean.UserAddress;
import com.aote.tmall.service.OrderService;
import com.aote.tmall.service.UserService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 1、将服务提供者注册到注册中心（暴露服务）
 * 		1）、导入dubbo依赖（2.6.2）\操作zookeeper的客户端(curator)
 * 		2）、配置服务提供者
 * 
 * 2、让服务消费者去注册中心订阅服务提供者的服务地址
 * @author lfy
 *
 */
@Service
@Component
public class OrderServiceImpl implements OrderService {

	//配置url="127.0.0.1:20885" 使用dubbo直连，不需要经过zookeeper注册中心
//	@Reference(url="127.0.0.1:20885")
	@Reference
	UserService userService;

	@HystrixCommand(fallbackMethod = "hello")
	@Override
	public List<UserAddress> initOrder(String userId) {
		// TODO Auto-generated method stub
		System.out.println("用户id："+userId);
		//1、查询用户的收货地址
		List<UserAddress> addressList = userService.getUserAddressList(userId);
		return addressList;
	}

	public List<UserAddress> hello(String userId) {
		List<UserAddress> list = new ArrayList<>();
		list.add(new UserAddress(1, "北京市昌平区宏福科技园综合楼3层", "1", "李老师", "010-56253825", "Y"));
		return list;
	}

}
