package com.sqy.service;

import com.sqy.domain.projectmember.ProjectMember;
import com.sqy.dto.ProjectMemberDto;
import com.sqy.mapper.Mapper;
import com.sqy.repository.ProjectMemberRepository;
import com.sqy.service.interfaces.ProjectMemberService;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ProjectMemberServiceImpl implements ProjectMemberService {
    private final ProjectMemberRepository projectMemberRepository;
    private final Mapper<ProjectMemberDto, ProjectMember> projectMemberMapper;

    public ProjectMemberServiceImpl(ProjectMemberRepository projectMemberRepository, Mapper<ProjectMemberDto, ProjectMember> projectMemberMapper) {
        this.projectMemberRepository = projectMemberRepository;
        this.projectMemberMapper = projectMemberMapper;
    }

    public List<ProjectMemberDto> getAll() {
        return projectMemberRepository.findAll()
                .stream()
                .map(projectMemberMapper::getDtoFromModel)
                .toList();
    }

    @Nullable
    public ProjectMemberDto getById(Long id) {
        return projectMemberRepository.findById(id)
                .stream()
                .map(projectMemberMapper::getDtoFromModel)
                .findAny()
                .orElse(null);
    }

    public void save(ProjectMemberDto projectMemberDto) {
        projectMemberRepository.save(projectMemberMapper.getModelFromDto(projectMemberDto));
    }

    public void update(ProjectMemberDto projectMemberDto) {
        if (!projectMemberRepository.existsById(projectMemberDto.id())) {
            throw new IllegalArgumentException();
        }
        projectMemberRepository.save(projectMemberMapper.getModelFromDto(projectMemberDto));
    }

    public void delete(Long id) {
        projectMemberRepository.deleteById(id);
    }

    public List<ProjectMemberDto> search() {
        return Collections.emptyList();
        // TODO: 17.05.2023
    }
}
