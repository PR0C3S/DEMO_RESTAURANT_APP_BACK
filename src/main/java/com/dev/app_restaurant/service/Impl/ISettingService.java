package com.dev.app_restaurant.service.Impl;


import com.dev.app_restaurant.dto.SettingDTO;
import com.dev.app_restaurant.entity.Setting;
import java.io.IOException;

public interface ISettingService {
    public void create();
    public Setting get();
    public Boolean exists();
    public Setting update(SettingDTO settingDTO) throws IOException;
}
