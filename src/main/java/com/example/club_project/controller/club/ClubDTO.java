package com.example.club_project.controller.club;

import com.example.club_project.domain.Club;
import com.example.club_project.domain.ClubJoinState;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotBlank;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.*;

@AllArgsConstructor
@Getter
@JsonNaming(SnakeCaseStrategy.class)
public class ClubDTO {

    private Long id;
    private String name;
    private String address;
    private String university;
    private String description;
    private String imageUrl;
    private Long category;
    private long clubMembers;

    /**
     * GET
     *
     * 조회 옵션
     */
    @Builder
    @Getter
    public static class SearchOption {
        List<Long> categories;
        String name;

        public static SearchOption of(String name, String category) {

            if (StringUtils.isEmpty(category)) {
                return SearchOption.builder()
                        .name(name)
                        .categories(Collections.emptyList())
                        .build();
            } else {
                /**
                 * <예시>
                 * queryString = ?name=친목&category=1,2,3,4,5
                 * category = 1,2,3,4,5
                 * categoryIds = [1,2,3,4,5]
                 */
                List<Long> categoryIds = Arrays.stream(category.split(","))
                        .map(Long::valueOf).collect(toList());

                return SearchOption.builder()
                        .name(name)
                        .categories(categoryIds)
                        .build();
            }
        }
    }

    /**
     * POST
     */
    @Getter
    @JsonNaming(SnakeCaseStrategy.class)
    public static class RegisterRequest {
        @NotBlank(message = "name 필드에는 빈 값이 허용되지 않습니다.")
        private String name;
        @NotBlank(message = "address 필드에는 빈 값이 허용되지 않습니다.")
        private String address;
        @NotBlank(message = "description 필드에는 빈 값이 허용되지 않습니다.")
        private String description;
        @NotBlank(message = "category 필드에는 빈 값이 허용되지 않습니다.")
        private Long category;
        private String imageUrl;
    }

    /**
     * PUT
     */
    @Getter
    @JsonNaming(SnakeCaseStrategy.class)
    public static class UpdateRequest {
        private String name;
        private String address;
        private String description;
        private Long category;
        private String imageUrl;
    }

    /**
     * ResponseBody
     */
    @Builder
    @Getter
    @JsonNaming(SnakeCaseStrategy.class)
    public static class Response {
        private Long id;
        private String name;
        private String address;
        private String university;
        private String description;
        private String imageUrl;
        private Long category;

        public static ClubDTO.Response from(Club club) {
            return Response.builder()
                    .id(club.getId())
                    .name(club.getName())
                    .address(club.getAddress())
                    .university(club.getUniversity())
                    .description(club.getDescription())
                    .imageUrl(StringUtils.isEmpty(club.getImageUrl()) ? "" : club.getImageUrl())
                    .category(club.getCategory().getId())
                    .build();
        }
    }

    @Builder
    @Getter
    @JsonNaming(SnakeCaseStrategy.class)
    public static class MemberResponse {
        private String joinState;
        private String name;

        public static MemberResponse from(ClubJoinState member) {
            return MemberResponse.builder()
                            .joinState(member.getJoinState().getState())
                            .name(member.getUser().getName())
                            .build();
        }
    }

    @Builder
    @Getter
    @JsonNaming(SnakeCaseStrategy.class)
    public static class DetailResponse {
        private Long id;
        private String name;
        private String address;
        private String university;
        private String description;
        private String imageUrl;
        private String category;
        private List<MemberResponse> members;

        public static ClubDTO.DetailResponse of(Club club, List<ClubJoinState> memberEntities) {
            return DetailResponse.builder()
                    .id(club.getId())
                    .name(club.getName())
                    .address(club.getAddress())
                    .university(club.getUniversity())
                    .description(club.getDescription())
                    .imageUrl(club.getImageUrl())
                    .category(club.getCategory().getName())
                    .members(memberEntities.stream()
                            .map(MemberResponse::from)
                            .collect(toList()))
                    .build();
        }
    }
}
