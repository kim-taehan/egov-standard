package com.skcc.egovcore.core.mapper;

import com.skcc.egovcore.core.mvc.mapper.CamelMap;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("[utils] mybatis resultType 사용하는 Map")
class CamelMapTest {


    @CsvSource({"item_name, itemName", "ITEM_NAME, itemName", "ITEMNAME, itemname"})
    @ParameterizedTest
    @DisplayName("케밥형태로(DB)의 텍스트를 카멜형태로 변경한다.")
    void convert2Camel(String text, String expected){
        // given
        CamelMap camelMap = new CamelMap();
        String value = UUID.randomUUID().toString();

        // when
        camelMap.put(text, value);

        // then
        assertThat(camelMap.get(expected)).isEqualTo(value);
    }

    @DisplayName("key로 String 아닌 데이터를 입력시 IllegalArgumentException 오류가 발생한다.")
    @Test
    void convert2CamelException(){
        // given
        CamelMap camelMap = new CamelMap();

        // then
        Assertions.assertThatThrownBy(() -> camelMap.put(10, "test data"))
                .isInstanceOf(IllegalArgumentException.class);

    }



}