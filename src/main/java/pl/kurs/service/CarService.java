package pl.kurs.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.kurs.dto.CarDto;
import pl.kurs.entity.Car;
import pl.kurs.exception.NoCarFoundException;
import pl.kurs.repository.CarRepository;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CarService {
    private final CarRepository carRepository;

    public Optional<Car> getCarById(Long id) {
        return carRepository.findById(id);
    }

    public List<Car> getAll() {
        return carRepository.findAll();
    }

    public List<Car> getByProducerAndModel(String producer, String model) {
        return carRepository.findAllByProducerAndModel(producer, model);
    }

    public List<Car> getAllSorted(String property, Sort.Direction direction) {
        List<String> allowedProperties = Arrays.stream(Car.class.getDeclaredFields())
                .map(Field::getName)
                .toList();

        if (!allowedProperties.contains(property)) {
            throw new IllegalArgumentException("Invalid sort field: " + property);
        }
        return carRepository.findAll(Sort.by(direction, property));
    }

    public Car saveCar(Car car) {
        return carRepository.save(car);
    }

    @Transactional
    public Car updateCar(Car car) {
        Car carToUpdate = carRepository.findById(car.getId()).orElseThrow(() -> new NoCarFoundException("No car with id found: " + car.getId()));
        BeanUtils.copyProperties(car, carToUpdate);
        return carRepository.save(carToUpdate);
    }

    public void deleteCarById(Long id) {
        carRepository.deleteById(id);
    }

    public void deleteCarByIds(List<Long> ids) {
        carRepository.deleteAllByIdInBatch(ids);
    }

    public void deleteCarsBySpecs(Integer minPower, Integer maxPower,
                                  Integer minTorque, Integer maxTorque,
                                  Integer minDisplacement, Integer maxDisplacement) {
        List<Long> carsToDelete = carRepository.findBySpecs(
                minPower, maxPower, minTorque, maxTorque, minDisplacement, maxDisplacement).stream()
                .map(Car::getId)
                .filter(Objects::nonNull)
                .toList();

        if (carsToDelete.isEmpty()) {
            return;
        }

        carRepository.deleteAllByIdInBatch(carsToDelete);
    }
}
