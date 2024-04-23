package dev.Innocent.Service.Impl;

import dev.Innocent.Service.CommentService;
import dev.Innocent.model.Comment;
import dev.Innocent.model.Issue;
import dev.Innocent.model.User;
import dev.Innocent.repository.CommentRepository;
import dev.Innocent.repository.IssueRepository;
import dev.Innocent.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {
    private CommentRepository commentRepository;
    private IssueRepository issueRepository;
    private UserRepository userRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository,
                              IssueRepository issueRepository,
                              UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.issueRepository = issueRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Comment createComment(Long issueId, Long userId, String content) throws Exception {
        Optional<Issue> issueOptional = issueRepository.findById(issueId);
        Optional<User> userOptional = userRepository.findById(userId);

        if(issueOptional.isEmpty()){
            throw new Exception("Issue not found with id " + issueId);
        }
        if(userOptional.isEmpty()){
            throw new Exception("User not found with id " + userId);
        }
        Issue issue = issueOptional.get();
        User user = userOptional.get();

        Comment comment = new Comment();
        comment.setIssue(issue);
        comment.setUser(user);
        comment.setCreatedDateTime(LocalDateTime.now());
        comment.setContent(content);

        Comment saveComment = commentRepository.save(comment);
        issue.getComments().add(saveComment);
        return saveComment;
    }

    @Override
    public void deleteComment(Long commentId, Long userId) throws Exception {
        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        Optional<User> userOptional = userRepository.findById(userId);

        if(optionalComment.isEmpty()){
            throw new Exception("Comment not found with id " + commentId);
        }
        if(userOptional.isEmpty()){
            throw new Exception("User not found with id " + userId);
        }
        Comment comment = optionalComment.get();
        User user = userOptional.get();
        if(comment.getUser().equals(user)){
            commentRepository.delete(comment);
        }else{
            throw new Exception("User does not have permission to delete this comment!");
        }
    }

    @Override
    public List<Comment> findCommentByIssueId(Long issueId) {
        return commentRepository.findByIssueId(issueId);
    }
}
