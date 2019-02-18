package com.atguigu.security.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class KungfuController {
	private final String PREFIX = "pages/";
	/**
	 * 欢迎页
	 * @return
	 */
	@GetMapping("/")
	public String index() {
		return "welcome";
	}
	@PostMapping("/")
	public String index2() {
		return "welcome";
	}
	
	/**
	 * 登陆页
	 * @return
	 */

	@GetMapping("/userlogin")
	public String loginPage() {
		return PREFIX+"login";
	}
	
	
	/**
	 * level1页面映射
	 * @param path
	 * @return
	 */
	/*@PreAuthorize("hasAuthority('ADMIN')")和@PreAuthorize("hasAuthority('ROLE_ADMIN')")  // 都可以，只要和实现的getAuthoritie里面的role对上
			就可以

	@Secured({"ADMIN"})和@Secured({"ROLE_ADMIN"}) // 实现的getAuthoritie里面的role都必须要有ROLE_前缀*/

//	@Secured({ "ROLE_VIP1", "ROLE_VIP2" })//同时拥有VIP1和VIP2角色的用户
//	@Secured("ROLE_VIP1")//具有 VIP1 角色的人调用
//	@PreAuthorize("hasRole('VIP1') AND hasRole('VIP2')")//同时拥有VIP1和VIP2角色的用户
//	@PreAuthorize("hasRole('VIP1')")//具有 VIP1 角色的人调用
	@GetMapping("/level1/{path}")
	public String level1(@PathVariable("path")String path) {
		return PREFIX+"level1/"+path;
	}

	/**
	 * level2页面映射
	 * @param path
	 * @return
	 */

	@GetMapping("/level2/{path}")
	public String level2(@PathVariable("path")String path) {
		return PREFIX+"level2/"+path;
	}

	/**
	 * level3页面映射
	 * @param path
	 * @return
	 */
	@GetMapping("/level3/{path}")
	public String level3(@PathVariable("path")String path) {
		return PREFIX+"level3/"+path;
	}

	/**
	 * csrl进入页面
	 */
	@GetMapping("/csrlhtml")
	public String csrlhtml() {
		return PREFIX+"csrlhtml";
	}

	/**
	 * 测试csrl页面
	 */
	@PostMapping("/csrfurl")
	public String csrfurl() {
		return "welcome";
	}
}
