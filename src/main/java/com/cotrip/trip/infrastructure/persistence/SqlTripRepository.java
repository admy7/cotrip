package com.cotrip.trip.infrastructure.persistence;

import com.cotrip.trip.application.ports.TripRepository;
import com.cotrip.trip.domain.models.Trip;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.util.Optional;


public class SqlTripRepository implements TripRepository {
    private final EntityManager entityManager;

    public SqlTripRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void save(Trip trip) {
        entityManager.persist(trip);
    }

    @Override
    public Optional<Trip> findById(String id) {
        return Optional.of(entityManager.find(Trip.class, id));
    }

    @Override
    @Transactional
    public void deleteAll() {
        entityManager.createQuery("DELETE FROM Trip").executeUpdate();
    }

}
