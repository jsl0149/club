
package com.example.club_project.controller.user;

import com.example.club_project.controller.comment.CommentDTO;
import com.example.club_project.controller.post.PostDTO;
import com.example.club_project.security.dto.AuthUserDTO;
import com.example.club_project.service.comment.CommentService;
import com.example.club_project.service.post.PostService;
import com.example.club_project.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/api/user")
@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;
    private final PostService postService;
    private final CommentService commentService;

    @GetMapping
    public UserDTO.UpdateResponse getUserInfo(@AuthenticationPrincipal AuthUserDTO authUserDTO) {
        return userService.getUserUpdateRespDTO(authUserDTO.getId());
    }

    @PutMapping
    public void updateUserInfo(@AuthenticationPrincipal AuthUserDTO authUser,
                               @RequestBody @Valid UserDTO.UpdateRequest userUpdateDTO) {
        userService.updateUser(
                authUser.getId(),
                userUpdateDTO.getName(),
                userUpdateDTO.getNickname(),
                userUpdateDTO.getUniversity(),
                userUpdateDTO.getIntroduction()
        );
    }

    // 비밀번호 변경화면에 프로필사진경로와 유저네임 보내줌
    @GetMapping("/password")
    public UserDTO.NicknameAndProfile getPasswordPageInfo(@AuthenticationPrincipal AuthUserDTO authUserDTO) {
        return userService.getUsernameAndPicture(authUserDTO.getId());
    }

    @PutMapping("/password")
    public void updatePassword(@AuthenticationPrincipal AuthUserDTO authUserDTO,
                               @RequestBody @Valid UserDTO.UpdatePassword passwordDTO) {

        userService.updatePassword(
                authUserDTO.getId(),
                passwordDTO.getOldPw(),
                passwordDTO.getNewPw(),
                passwordDTO.getCheckPw()
        );
    }

    @GetMapping("/posts")
    public List<PostDTO.Response> getMyPosts(@AuthenticationPrincipal AuthUserDTO authUser,
                                             @PageableDefault(
                                                     size = 20,
                                                     sort = "id",
                                                     direction = Sort.Direction.DESC) Pageable pageable) {

        return postService.getMyPosts(authUser.getId(), pageable);
    }

    @GetMapping("/comments")
    public List<CommentDTO.myComment> getMyComments(@AuthenticationPrincipal AuthUserDTO authUser,
                                                    @PageableDefault(
                                                            size = 20,
                                                            sort = "id",
                                                            direction = Sort.Direction.DESC) Pageable pageable) {

        return commentService.getMyComments(authUser.getId(), pageable);
    }
}