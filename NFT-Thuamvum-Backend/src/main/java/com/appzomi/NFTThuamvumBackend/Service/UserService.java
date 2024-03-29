package com.appzomi.NFTThuamvumBackend.Service;

import com.appzomi.NFTThuamvumBackend.Dao.Role;
import com.appzomi.NFTThuamvumBackend.Dao.User;
import com.appzomi.NFTThuamvumBackend.Dto.UserDto;
import com.appzomi.NFTThuamvumBackend.Repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> deleteUserById(Long id) {
        try {
            Optional<User> result = getUserById(id);
            userRepository.deleteById(id);

            if(result.isEmpty()) throw new Exception();

            return result;
        } catch (Exception e) {
            return null;
        }
    }

    public Optional<User> addUser(UserDto usr) {
        User saved = User.builder()
                .name(usr.getName())
                .username(usr.getUsername())
                .email(usr.getEmail())
                .password(passwordEncoder.encode(usr.getPassword()))
                .role(Role.USER)
                .build();

        return Optional.of(userRepository.save(saved));
    }
}
