package com.sct.rest.api.service;

import com.sct.rest.api.model.dto.RentDto;
import com.sct.rest.api.model.enums.RentStatus;
import com.sct.rest.api.model.filter.RentPageableFilter;
import org.springframework.data.domain.Page;

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

    /**
     * Получение отфильтрованной страницы аренд
     *
     * @param filter Параметры фильтрации и пагинации аренд {@link RentPageableFilter}
     * @return Отфильтрованная страница аренд {@link RentDto}
     */
    Page<RentDto> getAllRentFilterAndPageable(RentPageableFilter filter);
}
