package com.microgames.website.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.microgames.website.dto.RegisterUsernameDTO;
import com.microgames.website.entities.UserData;
import com.microgames.website.entities.UserProfile;
import com.microgames.website.repository.UserDataRepository;
import com.microgames.website.repository.UserProfileRepository;

@Service
public class ProfileCompletionService {

    private static final int REGISTER_STATUS_COMPLETE = 2;

    private final UserDataRepository userDataRepository;
    private final UserProfileRepository userProfileRepository;

    public ProfileCompletionService(UserDataRepository userDataRepository,
                                     UserProfileRepository userProfileRepository) {
        this.userDataRepository = userDataRepository;
        this.userProfileRepository = userProfileRepository;
    }

    public List<String> validate(RegisterUsernameDTO dto) {
        List<String> errors = new ArrayList<>();

        if (userProfileRepository.existsByUsername(dto.getUsername())) {
            errors.add("Ese nombre de usuario ya está en uso.");
        }
        return errors;
    }

    @Transactional
    public void completeProfile(Long userId, RegisterUsernameDTO dto) {
        UserData userData = userDataRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("Usuario no encontrado: id=" + userId));

        UserProfile profile = userData.getUserProfile();
        if (profile == null) {
            profile = new UserProfile();
            userData.setUserProfile(profile); // encadena UserProfile.userData vía el setter de UserData
        }

        profile.setUsername(dto.getUsername());
        userData.setRegisterStatus(REGISTER_STATUS_COMPLETE);

        userDataRepository.save(userData); // cascade ALL persiste/actualiza el UserProfile
    }
}