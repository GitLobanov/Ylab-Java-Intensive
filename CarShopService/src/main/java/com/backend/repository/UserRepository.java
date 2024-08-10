package com.backend.repository;

import com.backend.model.User;
import com.backend.repository.parent.CrudRepository;
import lombok.Setter;

import java.util.*;

public class UserRepository implements CrudRepository<User> {

    @Setter
    private static UserRepository instance;
    private final Map<UUID, User> users;

    public UserRepository (){
        users = new HashMap<>();
    }

    public static UserRepository getInstance(){
        if(instance == null){
            synchronized (UserRepository.class){
                if(instance == null){
                    instance = new UserRepository();
                }
            }
        }

        return instance;
    }


    @Override
    public boolean save(User user) {
        if (user != null) {
            users.put(user.getId(), user);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean update(User user) {
        return users.put(user.getId(), user) != null;
    }

    @Override
    public boolean delete(User user) {
        return users.remove(user.getId()) != null;
    }

    @Override
    public User findById(UUID uid) {
        return users.get(uid);
    }

    public User findByUserNameAndPassword(String userName, String password) {
        Iterator<Map.Entry<UUID, User>> iterator = users.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<UUID, User> entry = iterator.next();
            if (entry.getValue().getUserName().equals(userName) && entry.getValue().getPassword().equals(password)) {
                return entry.getValue();
            }
        }

        return null;
    }


    public User findByUserName(String userName) {
        Iterator<Map.Entry<UUID, User>> iterator = users.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<UUID, User> entry = iterator.next();
            if (entry.getValue().getUserName().equals(userName)) {
                return entry.getValue();
            }
        }

        return null;
    }

    @Override
    public Map<UUID, User> findAll() {
        return users;
    }
}
