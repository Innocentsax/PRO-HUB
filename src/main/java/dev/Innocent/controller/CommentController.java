package dev.Innocent.controller;

import dev.Innocent.DTO.request.CreateCommentRequest;
import dev.Innocent.DTO.response.MessageResponse;
import dev.Innocent.Service.CommentService;
import dev.Innocent.Service.UserService;
import dev.Innocent.model.Comment;
import dev.Innocent.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    private CommentService commentService;
    private UserService userService;
    @Autowired
    public CommentController(CommentService commentService, UserService userService) {
        this.commentService = commentService;
        this.userService = userService;
    }
    @PostMapping()
    public ResponseEntity<Comment> createComment(
            @RequestBody CreateCommentRequest request,
            @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Comment createdComment = commentService.createComment(
                request.getIssueId(),
                user.getId(),
                request.getContent());
        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    }
    @DeleteMapping("/{commentId}")
    public ResponseEntity<MessageResponse> deleteComment(
            @PathVariable Long commentId, @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        commentService.deleteComment(commentId, user.getId());

        MessageResponse response = new MessageResponse();
        response.setMessage("Comment deleted successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{issueId}")
    public ResponseEntity<List<Comment>> getCommentsByIssueId(@PathVariable Long issueId){
        List<Comment> comments = commentService.findCommentByIssueId(issueId);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }
}
