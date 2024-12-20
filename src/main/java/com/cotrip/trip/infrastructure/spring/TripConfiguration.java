package com.cotrip.trip.infrastructure.spring;

import com.cotrip.trip.application.ports.TripRepository;
import com.cotrip.trip.application.usecases.AddAccommodationCommand;
import com.cotrip.trip.application.usecases.AddAccommodationCommandHandler;
import com.cotrip.trip.application.usecases.AddTransportCommandHandler;
import com.cotrip.trip.application.usecases.CreateTripCommandHandler;
import com.cotrip.trip.infrastructure.persistence.SqlTripRepository;
import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TripConfiguration {
    @Bean
    public TripRepository tripRepository(EntityManager entityManager) {
        return new SqlTripRepository(entityManager);
    }

    @Bean
    public CreateTripCommandHandler createTripCommandHandler(TripRepository tripRepository) {
        return new CreateTripCommandHandler(tripRepository);
    }

    @Bean
    public AddTransportCommandHandler addTransportCommandHandler(TripRepository tripRepository) {
        return new AddTransportCommandHandler(tripRepository);
    }

    @Bean
    public AddAccommodationCommandHandler addAccommodationCommandHandler(TripRepository tripRepository) {
        return new AddAccommodationCommandHandler(tripRepository);
    }
}
