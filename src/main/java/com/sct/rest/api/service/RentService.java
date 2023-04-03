package com.sct.rest.api.service;

import com.sct.rest.api.model.dto.RentDto;
import com.sct.rest.api.model.enums.RentStatus;

import java.util.List;

/**
 * Сервис для работы с данными аренд
 */
public interface RentService {

    /**
     * Получить все аренды
     *
     * @return Список объектов типа {@link RentDto}
     */
    List<RentDto> getAllRent();

    /**
     * Получить все аренды для текущего пользователя в защищенной сессии по статусу аренды
     *
     * @param status Статус аренды {@link RentStatus}
     * @return Список объектов типа {@link RentDto}
     */
    List<RentDto> getAllRentForCurrentUserByStatus(RentStatus status);
}
