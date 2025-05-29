package org.record.infrastructure.mappers;

import org.example.domain.user.dto.UserView;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.record.features.record.dto.RecordCreateRequest;
import org.record.features.record.dto.RecordUpdateReqeust;
import org.record.features.record.dto.RecordView;
import org.record.features.record.entity.Record;

@Mapper(
    componentModel = "spring",
    nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
@DecoratedWith(RecordMapperDecoder.class)
public interface RecordMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "userId", source = "user.id")
  Record toEntity(RecordCreateRequest dto, UserView user);

  RecordView toView(Record entity);

  void update(@MappingTarget Record entity, RecordUpdateReqeust dto);
}
