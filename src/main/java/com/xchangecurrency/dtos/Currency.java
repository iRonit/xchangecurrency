package com.xchangecurrency.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Currency {
    private String code;
    private String name;
}
