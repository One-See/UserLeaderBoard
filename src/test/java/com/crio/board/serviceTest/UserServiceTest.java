package com.crio.board.serviceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.crio.board.dto.CreateUserDTO;
import com.crio.board.exceptions.NonUniqueUserException;
import com.crio.board.model.User;
import com.crio.board.repository.UserRepository;
import com.crio.board.service.UserService;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach()
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddUser() {
        User user = new User("user123", "John Doe", 100, new String[] {});
        when(userRepository.save(user)).thenReturn(user);
        when(userRepository.findById("user123")).thenReturn(Optional.of(user));
        try {
            User savedUser = userService.addUser(new CreateUserDTO("user123", "John doe"));
            assertNotNull(savedUser);
            assertEquals("John doe", savedUser.getUserName());
            assertEquals(0, savedUser.getScore());
        } catch (NonUniqueUserException exception) {
            System.out.println(exception.getMessage());
        }
    }

}
