package com.sqy.service;

import com.sqy.dto.projectmember.ProjectMemberDto;
import com.sqy.mapper.ProjectMemberMapper;
import com.sqy.repository.ProjectMemberRepository;
import com.sqy.service.interfaces.ProjectMemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.sqy.mapper.ProjectMemberMapper.getModelFromDto;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectMemberServiceImpl implements ProjectMemberService {

    private final ProjectMemberRepository projectMemberRepository;

    @Override
    public List<ProjectMemberDto> getAll() {
        log.info("Invoke getAll().");
        return projectMemberRepository.findAll()
                .stream()
                .map(ProjectMemberMapper::getDtoFromModel)
                .toList();
    }

    @Nullable
    @Override
    public ProjectMemberDto getById(Long id) {
        log.info("Invoke getById({}).", id);
        return projectMemberRepository.findById(id)
                .stream()
                .map(ProjectMemberMapper::getDtoFromModel)
                .findAny()
                .orElse(null);
    }

    @Override
    public List<ProjectMemberDto> getAllByProjectId(Long projectId) {
        log.info("Invoke getAllByProjectId({}).", projectId);
        return projectMemberRepository.findProjectMemberByProjectProjectId(projectId)
                .stream()
                .map(ProjectMemberMapper::getDtoFromModel)
                .toList();
    }

    @Nullable
    @Override
    public Long save(ProjectMemberDto projectMemberDto) {
        log.info("Invoke save({}).", projectMemberDto);
        if (projectMemberDto.getId() != null) {
            projectMemberDto.setId(null);
        }
        try {
            return projectMemberRepository.save(getModelFromDto(projectMemberDto)).getProjectMemberId();
        } catch (DataIntegrityViolationException ex) {
            log.info("Invoke save({}) with exception.", projectMemberDto, ex);
        }
        return null;
    }

    @Override
    public boolean delete(Long id) {
        log.info("Invoke delete({}).", id);
        if (!projectMemberRepository.existsById(id)) {
            return false;
        }
        projectMemberRepository.deleteById(id);
        return true;
    }

}


