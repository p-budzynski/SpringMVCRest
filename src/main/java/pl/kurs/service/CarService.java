package pl.kurs.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.kurs.entity.Car;
import pl.kurs.repository.CarRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarService {
    private final CarRepository carRepository;

    public Car getCarById(Long id) {
        return carRepository.findById(id).orElseThrow();
    }

    public List<Car> getAll() {
        return carRepository.findAll();
    }

    public List<Car> getByProducerAndModel(String producer, String model) {
        return carRepository.findAllByProducerAndModel(producer, model);
    }

    public List<Car> getAllSorted(String property, Sort.Direction direction) {
        List<String> allowedProperties = List.of("id", "producer", "model", "engineType", "displacementCm3", "powerHp", "torqueNm");
        if (!allowedProperties.contains(property)) {
            throw new IllegalArgumentException("Invalid sort field: " + property);
        }
        return carRepository.findAll(Sort.by(direction, property));
    }
}
