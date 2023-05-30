package com.sct.rest.api.service;

import com.sct.rest.api.model.dto.RoutePointDto;

public interface RoutePointService {

    /**
     * Сохранение геопозиции маршрута пользователя
     * @param routePoint Геопозиция пользователя в текущий момент мршрута {@link RoutePointDto}
     */
    void save(RoutePointDto routePoint);
}
