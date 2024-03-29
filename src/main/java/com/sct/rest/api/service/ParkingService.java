package com.sct.rest.api.service;

import com.sct.rest.api.model.dto.ParkingDto;
import com.sct.rest.api.model.dto.parking.AddTransportDto;
import com.sct.rest.api.model.filter.ParkingPageableFilter;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    void createParking(ParkingDto parking);

    /**
     * Изменить данные парковки
     *
     * @param parking Парковка с новыми данными
     */
    @Transactional
    void updateParking(ParkingDto parking);

    /**
     * Добавить транспорт на парковку
     *
     * @param addTransport Идентификатор парковки и транспорта
     */
    @Transactional
    void addTransport(AddTransportDto addTransport);

    /**
     * Удалить парковку
     *
     * @param parking Парковка для удаления
     */
    @Transactional
    void deleteParking(ParkingDto parking);

    /**
     * Получение отфильтрованных страниц парковок
     *
     * @param filter Параметры фильтрации и пагинации парковок {@link ParkingPageableFilter}
     * @return Отфильтрованная страница парковок {@link ParkingDto}
     */
    Page<ParkingDto> getAllParkingFilterAndPageable(ParkingPageableFilter filter);
}
