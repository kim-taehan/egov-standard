package com.skcc.egovcore;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@DisplayName("[기능] Logging Test")
public class loggingTest {

    @Test
    @DisplayName("여러 내용은 log를 출력한다.")
    void loggingTest(){
        log.info("{}", log.getClass());
        log.trace("logging call=trace");
        log.debug("logging call=debug");
        log.warn("logging call=warn");
        log.info("logging call=info");
        log.error("logging call=error");

        assertThat(true).isTrue();
    }
}
