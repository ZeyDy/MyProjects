package academy.carX.services.impl;


import academy.carX.dto.CarDTO;
import academy.carX.exceptions.RecourseNotFoundException;
import academy.carX.mapper.CarMapper;
import academy.carX.models.Car;
import academy.carX.models.UserEntity;
import academy.carX.repositories.CarRepository;
import academy.carX.repositories.UserRepository;
import academy.carX.services.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class CarServiceImpl implements CarService {
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private UserRepository userRepository;

    public CarServiceImpl(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public CarDTO getCarById(Long carId) {
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new RecourseNotFoundException("Car is not exists"));
        return CarMapper.mapToCarDTO(car);
    }

    @Override
    public List<CarDTO> getAllCars() {
        List<Car> cars =  carRepository.findAll();
        return cars.stream().map((car) -> CarMapper.mapToCarDTO(car))
                .collect(Collectors.toList());
    }

    @Override
    public CarDTO updateCar(Long carId, CarDTO updatedCar) {
        Car car = carRepository.findById(carId).orElseThrow(
                () -> new RuntimeException("Car is not exists")
        );
        car.setPlateNumber(updatedCar.getPlateNumber());
        car.setMake(updatedCar.getMake());
        car.setModel(updatedCar.getModel());
        Car updatedCarObj =  carRepository.save(car);
        return CarMapper.mapToCarDTO(updatedCarObj);
    }

    @Override
    public void deleteCar(Long carId) {
        Car car = carRepository.findById(carId).orElseThrow(
                () -> new RuntimeException("Car is not exists")
        );
        carRepository.deleteById(carId);

    }



    @Override
    public List<CarDTO> getCarsByOwner(String username) {
        Optional<UserEntity> userEntity = userRepository.findByUsername(username);
        if (!userEntity.isPresent()) {
            return new ArrayList<>();
        }
        return carRepository.findByUser(userEntity.get())
                .stream()
                .map(this::convertToCarDTO)
                .collect(Collectors.toList());
    }

    private CarDTO convertToCarDTO(Car car) {
        CarDTO carDTO = new CarDTO();
        carDTO.setId(car.getId());
        carDTO.setMake(car.getMake());
        carDTO.setModel(car.getModel());
        carDTO.setPlateNumber(car.getPlateNumber());
        return carDTO;
    }

    @Override
    public CarDTO createCar(CarDTO carDTO, String username) {
        Car car = CarMapper.mapToCar(carDTO);
        UserEntity user = userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("User is not exists")
        );
        car.setUser(user);
        Car savedCar = carRepository.save(car);
        return CarMapper.mapToCarDTO(savedCar);
    }

}
