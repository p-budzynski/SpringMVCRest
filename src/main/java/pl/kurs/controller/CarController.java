package pl.kurs.controller;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.kurs.dto.CarDto;
import pl.kurs.dto.CarDtoList;
import pl.kurs.entity.Car;
import pl.kurs.mapper.CarMapper;
import pl.kurs.service.CarService;

import java.util.List;

@RestController
@RequestMapping("/cars")
@AllArgsConstructor
public class CarController {
    private CarService carService;
    private CarMapper carMapper;

    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public CarDto getById(@PathVariable("id") Long id) {
        Car car = carService.getCarById(id);
        return carMapper.entityToDto(car);
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

}
