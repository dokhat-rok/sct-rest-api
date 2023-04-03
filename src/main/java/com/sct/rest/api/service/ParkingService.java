package com.sct.rest.api.service;

import com.sct.rest.api.model.dto.ParkingDto;
import com.sct.rest.api.model.dto.parking.AddTransportDto;

import java.util.List;

/**
 * Сервис для работы с данными парковок
 */
public interface ParkingService {

    /**
     * Получить все парковки
     *
     * @return Список объектов типа {@link ParkingDto}
     */
    List<ParkingDto> getAllParking();

    /**
     * Создать новую парковку
     *
     * @param parking Информация о новой парковке
     */
    void createParking(ParkingDto parking);

    /**
     * Изменить данные парковки
     *
     * @param parking Парковка с новыми данными
     */
    void updateParking(ParkingDto parking);

    /**
     * Добавить транспорт на парковку
     *
     * @param addTransport Идентификатор парковки и транспорта
     */
    void addTransport(AddTransportDto addTransport);

    /**
     * Удалить парковку
     *
     * @param parking Парковка для удаления
     */
    void deleteParking(ParkingDto parking);
}
