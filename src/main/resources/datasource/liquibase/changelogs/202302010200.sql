--liquibase formatted sql

--changeset dmitry.krivenko:202302010200-1
INSERT INTO public.customer(id, login, password, balance, role) VALUES
(1, 'Admin', '1111', 0, 'ADMIN');

--changeset dmitry.krivenko:202302010200-2
INSERT INTO public.parking(id, name, coordinates, allowed_radius, type) VALUES
(1, 'ДГТУ', '47.236372918786934,39.71263323803597', 200, 'ALL'),
(2, 'ГОРЬКИЙ', '47.22245807938916,39.710144148070555', 200, 'ALL');

--changeset dmitry.krivenko:202302010200-3
INSERT INTO public.transport(id, id_parking, type, identification_number,
                             coordinates, condition, status, charge_percentage, max_speed) VALUES
(1, 1, 'BICYCLE', 'ВЕЛ-1', '47.236907,39.713001', 'EXCELLENT', 'FREE', null, null),
(2, 2, 'BICYCLE', 'ВЕЛ-2', '47.222892,39.709743', 'EXCELLENT', 'FREE', null, null),
(3, 1, 'SCOOTER', 'ЭСМ-3', '47.236477,39.711813', 'EXCELLENT', 'FREE', 100, 25),
(4, 2, 'SCOOTER', 'ЭСМ-4', '47.221953,39.710756', 'EXCELLENT', 'FREE', 100, 25);