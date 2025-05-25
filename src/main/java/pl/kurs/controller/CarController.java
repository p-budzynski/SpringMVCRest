package pl.kurs.controller;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.kurs.dto.CarDto;
import pl.kurs.dto.CarDtoList;
import pl.kurs.entity.Car;
import pl.kurs.exception.NullFieldException;
import pl.kurs.mapper.CarMapper;
import pl.kurs.service.CarService;
import pl.kurs.validation.CreateCar;
import pl.kurs.validation.DeleteCar;
import pl.kurs.validation.UpdateCar;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/cars")
@AllArgsConstructor
@Validated
public class CarController {
    private CarService carService;
    private CarMapper carMapper;

//    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
//    public ResponseEntity<CarDto> getById(@PathVariable("id") Long id) {
//        Optional<Car> car = carService.getCarById(id);
//        if (car.isPresent()) {
//            return ResponseEntity.ok(carMapper.entityToDto(car.get()));
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }

    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<CarDto> getById(@PathVariable("id") @Min(1) Long id) {
        Optional<Car> car = carService.getCarById(id);
        return car.map(c -> ResponseEntity.ok(carMapper.entityToDto(c)))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CarDto> getAll() {
        List<Car> cars = carService.getAll();
        return carMapper.entitiesToDtos(cars);
    }

    @GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
    public CarDtoList getAllXml() {
        List<Car> cars = carService.getAll();
        List<CarDto> dtos = carMapper.entitiesToDtos(cars);
        return new CarDtoList(dtos);
    }

    @GetMapping("/search")
    public List<CarDto> getByParams(
            @RequestParam(value = "producer") String producer,
            @RequestParam(value = "model", required = false, defaultValue = "M3") String model) {
        List<Car> cars = carService.getByProducerAndModel(producer, model);
        return carMapper.entitiesToDtos(cars);
    }

    @GetMapping(value = "/sort", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public CarDtoList getAllSortedByParams(
            @RequestParam(value = "property", defaultValue = "producer") String property,
            @RequestParam(value = "direction", defaultValue = "asc") String direction) {

        Sort.Direction sortDirection = direction.equalsIgnoreCase("desc")
                ? Sort.Direction.DESC : Sort.Direction.ASC;

        List<Car> cars = carService.getAllSorted(property, sortDirection);
        return new CarDtoList(carMapper.entitiesToDtos(cars));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CarDto createCar(@RequestBody @Validated(CreateCar.class) CarDto carDto) {
        Car car = carMapper.dtoToEntity(carDto);
        Car savedCar = carService.saveCar(car);
        return carMapper.entityToDto(savedCar);
    }

    @PutMapping
    // sa dwa podejscia, mozna przekazac id w PutMapping jako PathVariable np. @PutMapping("/{id}"), badz przekazac je w srodku Dto
    public CarDto updateCar(@RequestBody @Validated(UpdateCar.class) CarDto carDto) {
        if (carDto.getId() == null) {
            throw new NullFieldException("Car id cannot be null!");
        }

        Car car = carMapper.dtoToEntityWithId(carDto);
        Car updatedCar = carService.updateCar(car);
        return carMapper.entityToDto(updatedCar);
    }

    @DeleteMapping("/{id}")
    public void deleteCarById(@PathVariable("id") @Min(1) Long id) {
        carService.deleteCarById(id);
    }

    @DeleteMapping
    public void deleteCars(@RequestBody @Validated List<CarDto> carDtos) {
        List<Long> ids = carDtos.stream()
                .map(CarDto::getId)
                .filter(Objects::nonNull)
                .toList();
        carService.deleteCarByIds(ids);
    }

    @DeleteMapping("/by-specs")
    public void deleteCarsBySpecs(
            @RequestParam(required = false) Integer minPower,
            @RequestParam(required = false) Integer maxPower,
            @RequestParam(required = false) Integer minTorque,
            @RequestParam(required = false) Integer maxTorque,
            @RequestParam(required = false) Integer minDisplacement,
            @RequestParam(required = false) Integer maxDisplacement
    ) {
        carService.deleteCarsBySpecs(minPower, maxPower, minTorque, maxTorque, minDisplacement, maxDisplacement);
    }


}
