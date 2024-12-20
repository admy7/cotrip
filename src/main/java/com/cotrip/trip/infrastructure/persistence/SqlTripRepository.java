package com.cotrip.trip.infrastructure.persistence;

import com.cotrip.trip.application.ports.TripRepository;
import com.cotrip.trip.domain.models.Trip;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.util.Optional;


@Transactional
public class SqlTripRepository implements TripRepository {
    private final EntityManager entityManager;

    public SqlTripRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void save(Trip trip) {
        entityManager.persist(trip);
    }

    @Override
    public void update(Trip trip) {
        entityManager.merge(trip);
    }

    @Override
    public Optional<Trip> findById(String id) {
        return Optional.ofNullable(entityManager.find(Trip.class, id));
    }

    @Override
    public void deleteAll() {
        entityManager.createQuery("DELETE FROM Trip").executeUpdate();
    }
}
