package academy.carX.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CarDTO {
    private Long id;
    private String plateNumber;
    private String make;
    private String model;
    public CarDTO() {}

    public CarDTO(Long id, String plateNumber, String make, String model) {
        this.id = id;
        this.plateNumber = plateNumber;
        this.make = make;
        this.model = model;
    }

}
