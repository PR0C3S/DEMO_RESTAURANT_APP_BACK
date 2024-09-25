package com.dev.app_restaurant;

import com.dev.app_restaurant.service.SettingService;
import com.dev.app_restaurant.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class MyApplicationRunner implements ApplicationRunner {

	@Autowired
	private SettingService settingService;

	@Autowired
	private UserService userService;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		settingService.create();
		userService.createAdminUser();
	}
}
