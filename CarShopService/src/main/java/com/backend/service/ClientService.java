package com.backend.service;

import com.backend.dto.CarDTO;
import com.backend.dto.ClientDTO;
import com.backend.mapper.CarMapper;
import com.backend.mapper.ClientMapper;
import com.backend.model.Car;
import com.backend.model.User;
import com.backend.repository.impl.CarRepository;
import com.backend.repository.impl.OrderRepository;
import com.backend.repository.impl.UserRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ClientService {

    Scanner scanner = new Scanner(System.in);

    OrderRepository orderRepository;
    CarRepository carRepository;
    UserRepository userRepository;

    private final ClientMapper clientMapper;
    private final CarMapper carMapper;

    OrderService orderService;

    public ClientService() {
        orderRepository = new OrderRepository();
        userRepository = new UserRepository();
        carRepository = new CarRepository();

        clientMapper = ClientMapper.INSTANCE;
        carMapper = CarMapper.INSTANCE;


        orderService = new OrderService();
    }

    public Optional<User> addClient(User client) {
        client.setRole(User.Role.CLIENT);
        return userRepository.save(client) ? getClientByUsername(client.getUsername()) : Optional.empty();
    }


    public boolean updateClient(String userName, User updatedClient) {

        if (userRepository.findByUserName(userName)==null) {
            return false;
        } else {
            userRepository.update(updatedClient);
            return true;
        }
    }


    public boolean removeClient(String userName) {
        User client = userRepository.findByUserName(userName);
        return userRepository.delete(client);
    }


    public List<CarDTO> getClientCars(String username) {
        List<Car> cars = carRepository.findCarsByClient(username);
        return carMapper.getDTOs(cars);
    }

    public Optional<User> getClientByUsername (String username){
        return userRepository.findByUserName(username) != null
                ? Optional.of(userRepository.findByUserName(username)) : Optional.empty();
    }

    public List<ClientDTO> getAllClients () {
        return clientMapper.getDTOs(userRepository.findUsersByRole(User.Role.CLIENT));
    }

    public List<ClientDTO> getClientsBySearch(ClientDTO clientDTO) {
        User user = clientMapper.toEntity(clientDTO);
        user.setRole(User.Role.CLIENT);
        List<User> users = userRepository.search(user);
        return clientMapper.getDTOs(users);
    }

}
