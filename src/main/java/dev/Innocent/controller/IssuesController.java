package dev.Innocent.controller;

import dev.Innocent.DTO.IssueDTO;
import dev.Innocent.DTO.request.IssueRequest;
import dev.Innocent.DTO.response.MessageResponse;
import dev.Innocent.Service.IssueService;
import dev.Innocent.Service.UserService;
import dev.Innocent.model.Issue;
import dev.Innocent.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/issues")
public class IssuesController {
    private IssueService issueService;
    private UserService userService;
    @Autowired
    public IssuesController(IssueService issueService, UserService userService) {
        this.issueService = issueService;
        this.userService = userService;
    }
    @GetMapping("/issueId")
    public ResponseEntity<Issue> getIssueById(
            @PathVariable Long issueId) throws Exception {
        return ResponseEntity.ok(issueService.getIssueById(issueId));
    }
    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<Issue>> getIssueByProjectId(
            @PathVariable Long projectId) throws Exception {
        return ResponseEntity.ok(issueService.getIssueByProjectId(projectId));
    }

    @PostMapping
    public ResponseEntity<IssueDTO> createIssue(
            @RequestBody IssueRequest issue,
            @RequestHeader("Authorization") String token) throws Exception {
        User tokenUser = userService.findUserProfileByJwt(token);
        User user = userService.findUserById(tokenUser.getId());

        Issue createIssue = issueService.createIssue(issue, tokenUser);
        IssueDTO issueDTO = new IssueDTO();
        issueDTO.setDescription(createIssue.getDescription());
        issueDTO.setDueDate(createIssue.getDueDate());
        issueDTO.setId(createIssue.getId());
        issueDTO.setPriority(createIssue.getPriority());
        issueDTO.setProject(createIssue.getProject());
        issueDTO.setProjectID(createIssue.getProjectID());
        issueDTO.setStatus(createIssue.getStatus());
        issueDTO.setTitle(createIssue.getTitle());
        issueDTO.setTags(createIssue.getTags());
        issueDTO.setAssignee(createIssue.getAssignee());

        return ResponseEntity.ok(issueDTO);
    }

    @DeleteMapping("/{issueId}")
    public ResponseEntity<MessageResponse> deleteIssue(
            @PathVariable Long issueId,
            @RequestHeader("Authorization") String token) throws Exception {
        User user = userService.findUserProfileByJwt(token);
        issueService.deleteIssue(issueId, user.getId());

        MessageResponse response = new MessageResponse();
        response.setMessage("Issue deleted");

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{issueId}/assignee/{userId}")
    public ResponseEntity<Issue> addUserToIssue(
            @PathVariable Long issueId,
            @PathVariable Long userId) throws Exception {
        Issue issue = issueService.addUserToIssue(issueId, userId);
        return ResponseEntity.ok(issue);
    }

    @PutMapping("/{issueId}/status/{status}")
    public ResponseEntity<Issue> updateIssueStatus(
            @PathVariable String status,
            @PathVariable Long issueId) throws Exception {
        Issue issue = issueService.updateStatus(issueId, status);
        return ResponseEntity.ok(issue);
    }
}
