package pl.kurs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.kurs.entity.Car;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {

    @Query("SELECT c from Car c where c.producer = :producer and (c.model = :model or :model is null)")
    List<Car> findAllByProducerAndModel(String producer, String model);

    @Query("""
                SELECT c FROM Car c
                WHERE (:minPower IS NULL OR c.powerHp >= :minPower)
                  AND (:maxPower IS NULL OR c.powerHp <= :maxPower)
                  AND (:minTorque IS NULL OR c.torqueNm >= :minTorque)
                  AND (:maxTorque IS NULL OR c.torqueNm <= :maxTorque)
                  AND (:minDisplacement IS NULL OR c.displacementCm3 >= :minDisplacement)
                  AND (:maxDisplacement IS NULL OR c.displacementCm3 <= :maxDisplacement)
            """)
    List<Car> findBySpecs(@Param("minPower") Integer minPower,
                          @Param("maxPower") Integer maxPower,
                          @Param("minTorque") Integer minTorque,
                          @Param("maxTorque") Integer maxTorque,
                          @Param("minDisplacement") Integer minDisplacement,
                          @Param("maxDisplacement") Integer maxDisplacement);
}
