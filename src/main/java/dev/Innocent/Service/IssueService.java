package dev.Innocent.Service;

import dev.Innocent.DTO.request.IssueRequest;
import dev.Innocent.model.Issue;

import java.util.List;
import java.util.Optional;

public interface IssueService {
    Optional<Issue> getIssueById(Long issueId) throws Exception;
    List<Issue> getIssueByProjectId(Long projectId) throws Exception;
    Issue createIssue(IssueRequest issue, Long userId) throws Exception;
    Optional<Issue> updateIssue(Long issueId, IssueRequest updateIssue, Long userId) throws Exception;
    String deleteIssue(Long issueId, Long userId) throws Exception;
    List<Issue> getIssueByAssigneeId(Long assignId);
    Issue addUserToIssue(Long issueId, Long userId);
    Issue updateStatus(Long issueId, String status);

}
