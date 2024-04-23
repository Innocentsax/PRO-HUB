package dev.Innocent.Service;

import dev.Innocent.DTO.request.IssueRequest;
import dev.Innocent.model.Issue;
import dev.Innocent.model.User;

import java.util.List;
import java.util.Optional;

public interface IssueService {
    Issue getIssueById(Long issueId) throws Exception;
    List<Issue> getIssueByProjectId(Long projectId) throws Exception;
    Issue createIssue(IssueRequest issueRequest, User user) throws Exception;
    Optional<Issue> updateIssue(Long issueId, IssueRequest updateIssue, Long userId) throws Exception;
    void deleteIssue(Long issueId, Long userId) throws Exception;
    List<Issue> getIssueByAssigneeId(Long assignId);
    Issue addUserToIssue(Long issueId, Long userId) throws Exception;
    Issue updateStatus(Long issueId, String status) throws Exception;

}
