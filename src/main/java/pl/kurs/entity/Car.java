package pl.kurs.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "cars")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Car  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(nullable = false)
    private String producer;

    @Column(nullable = false)
    private String model;

    @Column(name = "engine_type", nullable = false)
    private String engineType;

    @Column(name = "displacement_cm3", nullable = false)
    private Integer displacementCm3;

    @Column(name = "power_hp", nullable = false)
    private Integer powerHp;

    @Column(name = "torque_nm", nullable = false)
    private Integer torqueNm;

    public Car(String producer, String model, String engineType, Integer displacementCm3, Integer powerHp, Integer torqueNm) {
        this.producer = producer;
        this.model = model;
        this.engineType = engineType;
        this.displacementCm3 = displacementCm3;
        this.powerHp = powerHp;
        this.torqueNm = torqueNm;
    }

}
