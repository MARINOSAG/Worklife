package com.linkedin.features.experience;

import com.linkedin.config.errors.NotAuthorizedException;
import com.linkedin.config.errors.ObjectNotFoundException;
import com.linkedin.config.security.AuthenticationFacade;
import com.linkedin.entities.Experience;
import com.linkedin.entities.Login;
import com.linkedin.entities.repo.ExperienceRepository;
import com.linkedin.model.experience.ExperienceDto;
import com.linkedin.model.experience.ExperienceRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExperienceService {

  private ExperienceRepository experienceRepository;
  private ExperienceConverter experienceConverter;


  @Autowired
  public ExperienceService(ExperienceRepository experienceRepository, ExperienceConverter experienceConverter) {

    this.experienceRepository = experienceRepository;
    this.experienceConverter = experienceConverter;
  }


  public List<ExperienceDto> getExperiences() {
    Long userId = AuthenticationFacade.getUserId();
    return experienceRepository.findByUserId(userId).stream()
        .map(experienceConverter::toExperienceDto)
        .collect(Collectors.toList());
  }


  public List<ExperienceDto> getUsersExperiences(Long userId) {
    return experienceRepository.findByUserId(userId).stream()
        .map(experienceConverter::toExperienceDto)
        .collect(Collectors.toList());
  }

  public ExperienceDto createExperience(ExperienceRequestDto experienceRequestDto) {
    Login login = AuthenticationFacade.authenticatedUser();
    Long userId = login.getUserId();

    Experience experience = new Experience();
    experience.setCompany(experienceRequestDto.getCompany());
    experience.setEndDate(experienceRequestDto.getEndDate());
    experience.setStartDate(experienceRequestDto.getStartDate()
    );
    experience.setTitle(experienceRequestDto.getTitle());
    experience.setUserId(userId);
    experience.setVisible(experienceRequestDto.getVisible());

    experienceRepository.save(experience);
    return experienceConverter.toExperienceDto(experience);
  }

  public void removeExperience(Long experienceId) throws Exception {
    if (experienceRepository.existsById(experienceId)) {
      Login login = AuthenticationFacade.authenticatedUser();
      Long userId = login.getUserId();
      Experience experience = experienceRepository.findById(experienceId).orElse(null);

      if (userId.equals(experience.getUserId())) {
        throw new NotAuthorizedException(Experience.class);
      }

      experienceRepository.delete(experience);
    } else {
      throw new ObjectNotFoundException(Experience.class, experienceId);
    }

  }

  public ExperienceDto updateExperience(Long experienceId, ExperienceRequestDto experienceRequestDto) throws NotAuthorizedException, ObjectNotFoundException {
    if (!experienceRepository.existsById(experienceId)) {
      throw new ObjectNotFoundException(Experience.class, experienceId);
    }

    Long userId = AuthenticationFacade.getUserId();

    //we check if the user is updating hist experiences
    Experience experienceToUpdate = experienceRepository.findById(experienceId).orElse(null);
    if (!userId.equals(experienceToUpdate != null ? experienceToUpdate.getUserId() : null)) {
      throw new NotAuthorizedException(Experience.class);
    }

    experienceToUpdate.setTitle(experienceRequestDto.getTitle());
    experienceToUpdate.setCompany(experienceRequestDto.getCompany());
    experienceToUpdate.setStartDate(experienceRequestDto.getStartDate());
    experienceToUpdate.setEndDate(experienceRequestDto.getEndDate());
    experienceToUpdate.setVisible(experienceRequestDto.getVisible());

    experienceRepository.save(experienceToUpdate);
    return experienceConverter.toExperienceDto(experienceToUpdate);

  }

  public List<ExperienceDto> getAllExperiences() {
    return experienceRepository.findAll().stream().map(experienceConverter::toExperienceDto).collect(Collectors.toList());
  }
}
