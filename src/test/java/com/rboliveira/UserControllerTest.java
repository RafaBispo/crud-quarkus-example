package com.rboliveira;

import com.rboliveira.controller.UserController;
import com.rboliveira.dto.UserDTO;
import com.rboliveira.exception.UserNotFoundException;
import com.rboliveira.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private UserDTO userDTO;
    private UUID userId;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();

        userDTO = new UserDTO();
        userDTO.setId(userId);
        userDTO.setUsername("testuser");
        userDTO.setPassword("password123");
        userDTO.setEmail("testuser@example.com");
    }

    @Test
    void testGetAllUsersSuccess() {
        // Arrange
        UserDTO user2 = new UserDTO(UUID.randomUUID(), "user2", "password456", "user2@example.com");
        List<UserDTO> users = List.of(userDTO, user2);
        when(userService.findAll(0, 10)).thenReturn(users);

        // Act
        var response = userController.getAllUsers(0, 10);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatus());
        verify(userService, times(1)).findAll(0, 10);
    }

    @Test
    void testGetAllUsersEmptyList() {
        // Arrange
        when(userService.findAll(0, 10)).thenReturn(List.of());

        // Act
        var response = userController.getAllUsers(0, 10);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatus());
        verify(userService, times(1)).findAll(0, 10);
    }

    @Test
    void testGetAllUsersWithCustomPagination() {
        // Arrange
        List<UserDTO> users = List.of(userDTO);
        when(userService.findAll(1, 5)).thenReturn(users);

        // Act
        var response = userController.getAllUsers(1, 5);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatus());
        verify(userService, times(1)).findAll(1, 5);
    }

    @Test
    void testCreateUserSuccess() {
        // Arrange
        when(userService.createUser(any(UserDTO.class))).thenReturn(userDTO);

        // Act
        var response = userController.createUser(userDTO);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatus());
        verify(userService, times(1)).createUser(any(UserDTO.class));
    }

    @Test
    void testCreateUserWithValidData() {
        // Arrange
        UserDTO newUserDTO = new UserDTO(null, "newuser", "password", "newuser@example.com");
        when(userService.createUser(newUserDTO)).thenReturn(userDTO);

        // Act
        var response = userController.createUser(newUserDTO);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatus());
    }

    @Test
    void testGetUserByIdSuccess() {
        // Arrange
        when(userService.findById(userId)).thenReturn(userDTO);

        // Act
        var response = userController.getById(userId);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatus());
        verify(userService, times(1)).findById(userId);
    }

    @Test
    void testGetUserByIdNotFound() {
        // Arrange
        UUID nonExistentId = UUID.randomUUID();
        when(userService.findById(nonExistentId)).thenThrow(new UserNotFoundException());

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> userController.getById(nonExistentId));
        verify(userService, times(1)).findById(nonExistentId);
    }

    @Test
    void testUpdateUserSuccess() {
        // Arrange
        UserDTO updatedDTO = new UserDTO(userId, "updateduser", "newpassword", "updated@example.com");
        when(userService.updateUser(userId, updatedDTO)).thenReturn(updatedDTO);

        // Act
        var response = userController.updateUser(userId, updatedDTO);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatus());
        verify(userService, times(1)).updateUser(userId, updatedDTO);
    }

    @Test
    void testUpdateUserNotFound() {
        // Arrange
        UUID nonExistentId = UUID.randomUUID();
        when(userService.updateUser(nonExistentId, userDTO)).thenThrow(new UserNotFoundException());

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> userController.updateUser(nonExistentId, userDTO));
    }

    @Test
    void testDeleteUserSuccess() {
        // Arrange
        when(userService.deleteById(userId)).thenReturn(true);

        // Act
        var response = userController.deleteById(userId);

        // Assert
        assertNotNull(response);
        assertEquals(204, response.getStatus());
        verify(userService, times(1)).deleteById(userId);
    }

    @Test
    void testDeleteUserNotFound() {
        // Arrange
        UUID nonExistentId = UUID.randomUUID();
        when(userService.deleteById(nonExistentId)).thenThrow(new UserNotFoundException());

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> userController.deleteById(nonExistentId));
    }

    @Test
    void testDeleteUserFailure() {
        // Arrange
        when(userService.deleteById(userId)).thenReturn(false);

        // Act
        var response = userController.deleteById(userId);

        // Assert
        assertNotNull(response);
        assertEquals(404, response.getStatus());
    }

}

