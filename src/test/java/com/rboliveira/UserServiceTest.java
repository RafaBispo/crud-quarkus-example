package com.rboliveira;

import com.rboliveira.dto.UserDTO;
import com.rboliveira.entity.UserEntity;
import com.rboliveira.exception.UserNotFoundException;
import com.rboliveira.mapper.UserMapper;
import com.rboliveira.repository.UserRepository;
import com.rboliveira.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    private UserDTO userDTO;
    private UserEntity userEntity;
    private UUID userId;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();

        userDTO = new UserDTO();
        userDTO.setId(userId);
        userDTO.setUsername("testuser");
        userDTO.setPassword("password123");
        userDTO.setEmail("testuser@example.com");

        userEntity = new UserEntity();
        userEntity.setId(userId);
        userEntity.setUsername("testuser");
        userEntity.setPassword("password123");
        userEntity.setEmail("testuser@example.com");
    }

    @Test
    void testCreateUserSuccess() {
        // Arrange
        when(userMapper.toEntity(any(UserDTO.class))).thenReturn(userEntity);
        when(userMapper.toDTO(any(UserEntity.class))).thenReturn(userDTO);
        doNothing().when(userRepository).persist(any(UserEntity.class));

        // Act
        UserDTO result = userService.createUser(userDTO);

        // Assert
        assertNotNull(result);
        assertEquals(userId, result.getId());
        assertEquals("testuser", result.getUsername());
        assertEquals("testuser@example.com", result.getEmail());
        verify(userRepository, times(1)).persist(any(UserEntity.class));
    }

    @Test
    void testFindByIdSuccess() {
        // Arrange
        when(userRepository.findById(userId)).thenReturn(userEntity);
        when(userMapper.toDTO(userEntity)).thenReturn(userDTO);

        // Act
        UserDTO result = userService.findById(userId);

        // Assert
        assertNotNull(result);
        assertEquals(userId, result.getId());
        assertEquals("testuser", result.getUsername());
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void testFindByIdNotFound() {
        // Arrange
        UUID nonExistentId = UUID.randomUUID();
        when(userRepository.findById(nonExistentId)).thenReturn(null);

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> userService.findById(nonExistentId));
        verify(userRepository, times(1)).findById(nonExistentId);
    }

    @Test
    void testUpdateUserSuccess() {
        // Arrange
        UserDTO updatedDTO = new UserDTO();
        updatedDTO.setUsername("updateduser");
        updatedDTO.setPassword("newpassword");
        updatedDTO.setEmail("updated@example.com");

        when(userRepository.findById(userId)).thenReturn(userEntity);
        when(userMapper.toDTO(userEntity)).thenReturn(new UserDTO(userId, "updateduser", "newpassword", "updated@example.com"));

        // Act
        UserDTO result = userService.updateUser(userId, updatedDTO);

        // Assert
        assertNotNull(result);
        assertEquals(userId, result.getId());
        assertEquals("updateduser", userEntity.getUsername());
        assertEquals("newpassword", userEntity.getPassword());
        assertEquals("updated@example.com", userEntity.getEmail());
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void testDeleteUserSuccess() {
        // Arrange
        when(userRepository.findById(userId)).thenReturn(userEntity);
        when(userMapper.toDTO(userEntity)).thenReturn(userDTO);
        when(userRepository.deleteById(userId)).thenReturn(true);

        // Act
        boolean result = userService.deleteById(userId);

        // Assert
        assertTrue(result);
        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void testDeleteUserNotFound() {
        // Arrange
        UUID nonExistentId = UUID.randomUUID();
        when(userRepository.findById(nonExistentId)).thenReturn(null);

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> userService.deleteById(nonExistentId));
    }

    @Test
    void testDeleteUserFailure() {
        // Arrange
        when(userRepository.findById(userId)).thenReturn(userEntity);
        when(userMapper.toDTO(userEntity)).thenReturn(userDTO);
        when(userRepository.deleteById(userId)).thenReturn(false);

        // Act
        boolean result = userService.deleteById(userId);

        // Assert
        assertFalse(result);
    }

}





