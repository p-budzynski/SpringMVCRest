package pl.kurs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.kurs.entity.Car;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {

    @Query("SELECT c from Car c where c.producer = :producer and (c.model = :model or :model is null)")
    List<Car> findAllByProducerAndModel(String producer, String model);
}
