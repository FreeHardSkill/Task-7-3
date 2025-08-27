package ru.itmentor.spring.boot_security.demo.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itmentor.spring.boot_security.demo.DAO.UserRepository;
import ru.itmentor.spring.boot_security.demo.model.User;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public void updateUser(User user) {
        User notUpdatedUser = findById(user.getId());

        notUpdatedUser.setFirstName(user.getFirstName());
        notUpdatedUser.setLastName(user.getLastName());
        notUpdatedUser.setEmail(user.getEmail());
        if(user.getPassword() != null && !user.getPassword().isBlank()) notUpdatedUser.setPassword(passwordEncoder.encode(user.getPassword()));
        if(user.getBirthDay() != null) notUpdatedUser.setBirthDay(user.getBirthDay());
        if(!user.getRoles().isEmpty()) notUpdatedUser.setRoles(user.getRoles());

        userRepository.save(notUpdatedUser);
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

}
