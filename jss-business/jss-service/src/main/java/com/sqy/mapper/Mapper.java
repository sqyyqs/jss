package com.sqy.mapper;

public interface Mapper<Dto, Model> {
    Model getModelFromDto(Dto dto);

    Dto getDtoFromModel(Model model);
}
