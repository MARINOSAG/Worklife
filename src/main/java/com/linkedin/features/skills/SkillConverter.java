package com.linkedin.features.skills;

import com.linkedin.entities.Skill;
import com.linkedin.model.skills.SkillDto;
import com.linkedin.model.skills.SkillRequestDto;
import org.springframework.stereotype.Component;

@Component

public class SkillConverter {
  public SkillDto toSkillDto(Skill skill) {
    SkillDto skillDto = new SkillDto();
    skillDto.setSkillId(skill.getSkillId());
    skillDto.setLevel(skill.getLevel());
    skillDto.setName(skill.getName());
    skillDto.setVisible(skill.getVisible());
    return skillDto;
  }

  public Skill toSkill(Long userId, SkillRequestDto skillRequestDto) {
    Skill skill = new Skill();
    skill.setUserId(userId);
    skill.setLevel(skillRequestDto.getLevel());
    skill.setName(skillRequestDto.getName());
    skill.setVisible(skillRequestDto.getVisible());
    return skill;
  }
}
