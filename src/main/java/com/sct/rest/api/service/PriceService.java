package com.sct.rest.api.service;

import com.sct.rest.api.model.dto.PriceDto;
import com.sct.rest.api.model.enums.TransportType;

/**
 * Сервис для работы с ценой аренд
 */
public interface PriceService {

    /**
     * Получение актуальной цены аренды транспорта по его типу
     *
     * @param type Тип транспорта {@link TransportType}
     * @return Объект типа {@link PriceDto}
     */
    PriceDto getActualPrice(TransportType type);
}
