package academy.carX.controllersTests;

import academy.carX.controllers.CarController;
import academy.carX.dto.CarDTO;
import academy.carX.services.CarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CarControllerTests {

    @Mock
    private CarService carService;

    @Mock
    private Principal principal;

    @InjectMocks
    private CarController carController;

    private CarDTO carDTO;

    @BeforeEach
    void setUp() {
        carDTO = new CarDTO();
        carDTO.setId(1L);
        carDTO.setMake("VW");
        carDTO.setModel("Golf");
        carDTO.setPlateNumber("XYZ123");
    }

    @Test
    void testCreateCar() {
        when(principal.getName()).thenReturn("user");
        when(carService.createCar(carDTO, "user")).thenReturn(carDTO);

        ResponseEntity<CarDTO> response = carController.createCar(carDTO, principal);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(carDTO);
        verify(carService).createCar(carDTO, "user");
    }

    @Test
    void testGetCarById() {
        when(carService.getCarById(1L)).thenReturn(carDTO);

        ResponseEntity<CarDTO> response = carController.getCarById(1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(carDTO);
        verify(carService).getCarById(1L);
    }

    @Test
    void testUpdateCar() {
        when(carService.updateCar(1L, carDTO)).thenReturn(carDTO);

        ResponseEntity<CarDTO> response = carController.updateCar(1L, carDTO);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(carDTO);
        verify(carService).updateCar(1L, carDTO);
    }

    @Test
    void testDeleteCar() {
        doNothing().when(carService).deleteCar(1L);

        ResponseEntity<String> response = carController.deleteCar(1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo("Car deleted");
        verify(carService).deleteCar(1L);
    }

    @Test
    void testGetMyCars() {
        when(principal.getName()).thenReturn("user");
        when(carService.getCarsByOwner("user")).thenReturn(Arrays.asList(carDTO));

        ResponseEntity<List<CarDTO>> response = carController.getMyCars(principal);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).containsExactly(carDTO);
        verify(carService).getCarsByOwner("user");
    }
}
