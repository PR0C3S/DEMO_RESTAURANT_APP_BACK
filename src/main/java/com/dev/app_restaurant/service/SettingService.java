package com.dev.app_restaurant.service;


import com.dev.app_restaurant.dto.SettingDTO;
import com.dev.app_restaurant.entity.Image;
import com.dev.app_restaurant.entity.Setting;
import com.dev.app_restaurant.exception.BadRequestException;
import com.dev.app_restaurant.exception.ResourceNotFoundException;
import com.dev.app_restaurant.repository.SettingRepository;
import com.dev.app_restaurant.service.Impl.ISettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;

@Service
public class SettingService implements ISettingService {

    @Autowired
    private SettingRepository repository;

    @Autowired
    private ImageService imageService;

    @Override
    public void create() {
        if (!exists()) {
            System.out.println("Configuracion fue creada con exito.");
            Setting setting = new Setting(1, "Restaurant App", null, "(000) 000-0000", "", "Ubicacion por asignar", 18, 20, BigDecimal.valueOf(50), null);
            repository.save(setting);
        }
    }

    @Override
    public Setting get() {
        Setting settings = repository.findById(1).orElse(null);
        if (settings == null) {
            throw new ResourceNotFoundException("No existe una configuracion creada.");
        }
        return settings;
    }

    @Override
    public Boolean exists() {
        return repository.existsById(1);
    }

    @Override
    public Setting update(SettingDTO settingDTO) throws IOException {
        Setting old = get();


        if(settingDTO.getPrincipalPhone().equalsIgnoreCase(settingDTO.getSecondaryPhone())){
            throw new BadRequestException("Telefono secundario no puede ser igual al Telefono principal.");
        }
        old.setName(settingDTO.getName());
        old.setRnc(settingDTO.getRnc());
        old.setPrincipalPhone(settingDTO.getPrincipalPhone());
        old.setSecondaryPhone(settingDTO.getSecondaryPhone());
        old.setLocation(settingDTO.getLocation());
        old.setItbisPercentage(settingDTO.getItbisPercentage());
        old.setTaxesPercentage(settingDTO.getTaxesPercentage());
        old.setDeliveryPrice(settingDTO.getDeliveryPrice());

        if (settingDTO.getImage() != null) {
            if (old.getLogo() != null) {
                imageService.deleteByID(old.getLogo().getId());
            }
            Image image = imageService.create(settingDTO.getImage());
            old.setLogo(image);
        }
        return repository.save(old);
    }

}
