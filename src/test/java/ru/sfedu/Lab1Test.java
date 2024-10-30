package ru.sfedu;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.sfedu.api.ImageAPI;

@Slf4j
@DisplayName("Lab #1")
class Lab1Test {

    @Test
    void apiInitAndOpenCVLoadTest() {
        ImageAPI imageAPI = new ImageAPI();
        log.info("OpenCV version: {}", imageAPI.getOpenCVVersion());
    }
}
