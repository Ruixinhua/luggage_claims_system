package com.spring.boot.luggage_claims_system.hirbernia_sina.storage;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StorageProperties {

    /**
     * Folder location for storing files
     */
//    private String location = "upload-dir";
    private String location = " .";

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}
