package com.sqy.service;

import com.sqy.dto.ProjectMemberDto;
import com.sqy.mapper.ProjectMemberMapper;
import com.sqy.repository.ProjectMemberRepository;
import com.sqy.service.interfaces.ProjectMemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.sqy.mapper.ProjectMemberMapper.getModelFromDto;

@Service
public class ProjectMemberServiceImpl implements ProjectMemberService {
    private static final Logger logger = LoggerFactory.getLogger(ProjectMemberServiceImpl.class);
    private final ProjectMemberRepository projectMemberRepository;

    public ProjectMemberServiceImpl(ProjectMemberRepository projectMemberRepository) {
        this.projectMemberRepository = projectMemberRepository;
    }

    public List<ProjectMemberDto> getAll() {
        logger.info("Invoke getAll().");
        return projectMemberRepository.findAll()
                .stream()
                .map(ProjectMemberMapper::getDtoFromModel)
                .toList();
    }

    @Nullable
    public ProjectMemberDto getById(Long id) {
        logger.info("Invoke getById({}).", id);
        return projectMemberRepository.findById(id)
                .stream()
                .map(ProjectMemberMapper::getDtoFromModel)
                .findAny()
                .orElse(null);
    }

    @Override
    public List<ProjectMemberDto> getAllByProjectId(Long projectId) {
        logger.info("Invoke getAllByProjectId({}).", projectId);
        return projectMemberRepository.findProjectMemberByProjectProjectId(projectId)
                .stream()
                .map(ProjectMemberMapper::getDtoFromModel)
                .toList();
    }

    public boolean save(ProjectMemberDto projectMemberDto) {
        logger.info("Invoke save({}).", projectMemberDto);
        if (projectMemberDto.getId() != null) {
            projectMemberDto.setId(null);
        }
        try {
            projectMemberRepository.save(getModelFromDto(projectMemberDto));
            return true;
        } catch (DataIntegrityViolationException ex) {
            logger.info("Invoke save({}) with exception.", projectMemberDto, ex);
        }
        return false;
    }

    @Override
    public boolean delete(Long id) {
        logger.info("Invoke delete({}).", id);
        if (!projectMemberRepository.existsById(id)) {
            return false;
        }
        projectMemberRepository.deleteById(id);
        return true;
    }

}
