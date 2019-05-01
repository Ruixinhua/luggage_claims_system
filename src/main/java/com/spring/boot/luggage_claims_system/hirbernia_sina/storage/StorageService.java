package com.spring.boot.luggage_claims_system.hirbernia_sina.storage;

/**
 * @author Liu Dairui
 * @date 2019-04-30 13:26
 */

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageService {

    void init();

    void store(MultipartFile file);

    Stream<Path> loadAll();

    Path load(String filename);

    Resource loadAsResource(String filename);

    void deleteAll();

}