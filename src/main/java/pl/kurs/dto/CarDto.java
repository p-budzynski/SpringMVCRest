package pl.kurs.dto;

import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@XmlRootElement
public class CarDto {
    private Long id;
    private String producer;
    private String model;
    private String engineType;
    private Integer displacementCm3;
    private Integer powerHp;
    private Integer torqueNm;

}
