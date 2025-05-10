package pl.kurs.data;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.kurs.entity.Car;
import pl.kurs.repository.CarRepository;

@Component
@RequiredArgsConstructor
public class CarInitializer {
    private final CarRepository carRepository;

    @Transactional
    @PostConstruct
    public void init() {
     Car car = new Car("BMW", "M3", "S58", 3000, 530, 580);
     Car car1 = new Car("BMW", "740iL", "N55", 3000, 320, 400);
     carRepository.save(car);
     carRepository.save(car1);
    }
}
