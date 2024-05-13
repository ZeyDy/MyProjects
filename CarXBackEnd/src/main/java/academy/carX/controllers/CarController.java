package academy.carX.controllers;

import academy.carX.dto.CarDTO;
import academy.carX.services.CarService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;

/**
 * Controller for handling car-related operations.
 * Allows creating, retrieving, updating, and deleting cars, as well as retrieving cars owned by a specific user.
 */
@NoArgsConstructor
@AllArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("/api")
public class CarController {

    @Autowired
    private CarService carService;

    /**
     * Creates a new car with the provided car data.
     * @param carDTO Car data transfer object containing the car details.
     * @param principal Principal object containing the name of the logged-in user.
     * @return ResponseEntity containing the created CarDTO and HTTP status.
     */
    @PostMapping("/createcar")
    public ResponseEntity<CarDTO> createCar(@RequestBody CarDTO carDTO, Principal principal) {
        String username = principal.getName();
        CarDTO savedCar = carService.createCar(carDTO, username);
        return new ResponseEntity<>(savedCar, HttpStatus.CREATED);
    }

    /**
     * Retrieves a car by its ID.
     * @param carId The ID of the car to retrieve.
     * @return ResponseEntity containing the CarDTO.
     */
    @GetMapping("{id}")
    public ResponseEntity<CarDTO> getCarById(@PathVariable("id") Long carId) {
        CarDTO carDTO = carService.getCarById(carId);
        return ResponseEntity.ok(carDTO);
    }

    /**
     * Updates the details of an existing car.
     * @param carId The ID of the car to update.
     * @param updatedCar CarDTO with updated car details.
     * @return ResponseEntity containing the updated CarDTO.
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<CarDTO> updateCar(@PathVariable("id") Long carId, @RequestBody CarDTO updatedCar) {
        CarDTO carDTO = carService.updateCar(carId, updatedCar);
        return ResponseEntity.ok(carDTO);
    }

    /**
     * Deletes a car by its ID.
     * @param carId The ID of the car to be deleted.
     * @return ResponseEntity with a confirmation message.
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCar(@PathVariable("id") Long carId) {
        carService.deleteCar(carId);
        return ResponseEntity.ok("Car deleted");
    }

    /**
     * Retrieves all cars owned by the logged-in user.
     * @param principal Principal object containing the name of the logged-in user.
     * @return ResponseEntity containing a list of CarDTOs.
     */
    @GetMapping("/mycars")
    public ResponseEntity<List<CarDTO>> getMyCars(Principal principal) {
        String username = principal.getName();
        List<CarDTO> cars = carService.getCarsByOwner(username);
        return ResponseEntity.ok(cars);
    }
}
