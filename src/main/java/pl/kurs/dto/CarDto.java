package pl.kurs.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.kurs.validation.CreateCar;
import pl.kurs.validation.DeleteCar;
import pl.kurs.validation.UpdateCar;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@XmlRootElement
public class CarDto {
    @NotNull(message = "ID is required for update", groups = {UpdateCar.class, DeleteCar.class})
    @Min(value = 1, message = "ID must be at least 1", groups = {UpdateCar.class, DeleteCar.class})
    private Long id;

    @NotBlank(message = "Producer must not be blank", groups = {CreateCar.class, UpdateCar.class})
    private String producer;

    @NotBlank(message = "Model must not be blank", groups = {CreateCar.class, UpdateCar.class})
    private String model;

    @NotBlank(message = "Engine type must not be blank", groups = {CreateCar.class, UpdateCar.class})
    private String engineType;

    @NotNull(message = "Displacement is required", groups = {CreateCar.class, UpdateCar.class})
    @Min(value = 100, message = "Displacement must be at least 100 cm³", groups = {CreateCar.class, UpdateCar.class})
    private Integer displacementCm3;

    @NotNull(message = "Power is required", groups = {CreateCar.class, UpdateCar.class})
    @Min(value = 1, message = "Power must be greater than 0 HP", groups = {CreateCar.class, UpdateCar.class})
    private Integer powerHp;

    @NotNull(message = "Torque is required", groups = {CreateCar.class, UpdateCar.class})
    @Min(value = 1, message = "Torque must be greater than 0 Nm", groups = {CreateCar.class, UpdateCar.class})
    private Integer torqueNm;

}
