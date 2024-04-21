package io.psquared.blog.mapper;

import io.psquared.blog.dto.PostResponse;
import io.psquared.blog.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PostMapper {
        PostMapper INSTANCE = Mappers.getMapper( PostMapper.class );

        @Mapping(source = "category.name", target = "category")
        PostResponse toResponse(Post post);
}
