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
    @Column(name = "id_car")
    private Long id;

    @Column(nullable = false)
    private String producer;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private String engineType;

    @Column(nullable = false)
    private Integer displacementCm3;

    @Column(nullable = false)
    private Integer powerHp;

    @Column(nullable = false)
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
