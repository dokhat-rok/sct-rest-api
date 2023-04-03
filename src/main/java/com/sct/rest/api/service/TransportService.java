package com.sct.rest.api.service;

import com.sct.rest.api.model.dto.TransportDto;
import com.sct.rest.api.model.dto.transport.TransportFilter;

import java.util.List;

/**
 * Сервис для работы с данными о транспортах
 */
public interface TransportService {

    /**
     * Получение отфильтрованного списка транспортов
     *
     * @param filter Параметры фильтрации {@link TransportFilter}
     * @return Список объектов типа {@link TransportDto}
     */
    List<TransportDto> getAllTransportByFilter(TransportFilter filter);

    /**
     * Создание нового транспорта
     *
     * @param transport Информация о новом транспорте
     */
    void createTransport(TransportDto transport);

    /**
     * Удаление транспорта
     *
     * @param transportDto Информация об удаляемом транспорте
     */
    void deleteTransport(TransportDto transportDto);
}
