package com.rboliveira.service;

import com.rboliveira.dto.UserDTO;
import com.rboliveira.exception.UserNotFoundException;
import com.rboliveira.mapper.UserMapper;
import com.rboliveira.repository.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository,
                       UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Transactional
    public UserDTO createUser(UserDTO userDTO) {
        var userEntity = userMapper.toEntity(userDTO);
        userRepository.persist(userEntity);

        return userMapper.toDTO(userEntity);
    }

    public List<UserDTO> findAll(Integer page, Integer pageSize) {
        var users = userRepository.findAll()
                .page(page, pageSize)
                .list();
        return users.stream().map(userMapper::toDTO).toList();
    }

    public UserDTO findById(UUID userId) {
        return Optional.ofNullable(userRepository.findById(userId))
                .map(userMapper::toDTO)
                .orElseThrow(UserNotFoundException::new);
    }

    @Transactional
    public UserDTO updateUser(UUID userId, UserDTO userDTO) {
        var user = userRepository.findById(userId);

        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());

        return userMapper.toDTO(user);
    }

    @Transactional
    public boolean deleteById(UUID userId) {
        var user = findById(userId);
        return userRepository.deleteById(user.getId());
    }
}
