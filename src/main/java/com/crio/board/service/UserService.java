package com.crio.board.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.crio.board.dto.CreateUserDTO;
import com.crio.board.dto.UserScoreDTO;
import com.crio.board.exceptions.BadgeLimitExceededException;
import com.crio.board.exceptions.InvalidUserException;
import com.crio.board.exceptions.NonUniqueUserException;
import com.crio.board.model.User;
import com.crio.board.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepo;

    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public List<User> fetchAllUsers() {
        return this.userRepo.findAll(Sort.by(Sort.Direction.DESC, "score"));
    }

    public User addUser(CreateUserDTO userDTO) throws NonUniqueUserException {
        if (Boolean.FALSE.equals(isUserPresent(userDTO.getUserId()))) {
            return this.userRepo.save(new User(userDTO.getUserId(), userDTO.getUserName(), 0, new String[] {}));
        }
        throw new NonUniqueUserException("User is already present");
    }

    public Boolean isUserPresent(String userId) {
        Optional<User> user = this.userRepo.findById(userId);
        return user.isPresent();
    }

    public User findUser(String userId) throws InvalidUserException {
        Optional<User> user = this.userRepo.findById(userId);
        if (user.isPresent())
            return user.get();
        throw new InvalidUserException("Invalid user");
    }

    public void deleteUser(String userId) throws InvalidUserException {
        Optional<User> user = this.userRepo.findById(userId);
        if (user.isPresent()) {
            this.userRepo.deleteById(user.get().getUserId());
            return;
        }
        throw new InvalidUserException("Invalid user");
    }

    public User updateUserScore(UserScoreDTO userScoreDTO) throws InvalidUserException, BadgeLimitExceededException {

        User user = this.findUser(userScoreDTO.getUserId());

        String scoreBadge = fetchBadge(userScoreDTO.getScore());

        String[] userBadges = user.getBadges();

        boolean userHasBadge = false;

        for (String badge : userBadges) {
            if (badge.equals(scoreBadge)) {
                userHasBadge = true;
                break;
            }
        }

        if (!userHasBadge) {
            String[] currentBadges = Arrays.copyOf(userBadges, userBadges.length + 1);
            currentBadges[currentBadges.length - 1] = scoreBadge;
            user.setBadges(currentBadges);
            user.setScore(userScoreDTO.getScore());
            return this.userRepo.save(user);
        }

        throw new BadgeLimitExceededException("User already has this badge");

    }

    private String fetchBadge(int score) {
        if (score >= 1 && score < 30) {
            return "Code Ninja";
        } else if (score >= 30 && score < 60) {
            return "Code Champ";
        }

        return "Code Master";
    }

}
