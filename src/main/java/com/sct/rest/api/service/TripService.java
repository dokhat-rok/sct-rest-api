package com.sct.rest.api.service;

import com.sct.rest.api.model.dto.RentDto;
import com.sct.rest.api.model.dto.trip.TripBeginDto;
import com.sct.rest.api.model.dto.trip.TripEndDto;
import org.springframework.transaction.annotation.Transactional;

/**
 * Сервис с бизнес-логикой связанной с арендами пользователей
 */
public interface TripService {

    /**
     * Начало поездки текущего пользователя в защищенной сессии
     *
     * @param tripBegin Параметры для начала поездки {@link TripBeginDto}
     * @return Созданная поездка {@link RentDto}
     */
    @Transactional
    RentDto beginRent(TripBeginDto tripBegin);

    /**
     * Окончание поездки текущего пользователя в защищенной сессии
     *
     * @param tripEnd Параметры для окончания поездки {@link TripEndDto}
     * @return Оконченная поездка {@link RentDto}
     */
    @Transactional
    RentDto endRent(TripEndDto tripEnd);
}