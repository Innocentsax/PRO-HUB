package dev.Innocent.Service.Impl;

import dev.Innocent.DTO.request.IssueRequest;
import dev.Innocent.Service.IssueService;
import dev.Innocent.model.Issue;
import dev.Innocent.repository.IssueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IssueServiceImpl implements IssueService {
    private IssueRepository issueRepository;

    @Autowired
    public IssueServiceImpl(IssueRepository issueRepository) {
        this.issueRepository = issueRepository;
    }

    @Override
    public Optional<Issue> getIssueById(Long issueId) throws Exception {
        Optional<Issue> issue = issueRepository.findById(issueId);
        if(issue.isPresent()){
            return issue;
        }
        throw new Exception("No issues found with issueId " + issueId);
    }

    @Override
    public List<Issue> getIssueByProjectId(Long projectId) throws Exception {
        return null;
    }

    @Override
    public Issue createIssue(IssueRequest issue, Long userId) throws Exception {
        return null;
    }

    @Override
    public Optional<Issue> updateIssue(Long issueId, IssueRequest updateIssue, Long userId) throws Exception {
        return Optional.empty();
    }

    @Override
    public String deleteIssue(Long issueId, Long userId) throws Exception {
        return null;
    }

    @Override
    public List<Issue> getIssueByAssigneeId(Long assignId) {
        return null;
    }

    @Override
    public Issue addUserToIssue(Long issueId, Long userId) {
        return null;
    }

    @Override
    public Issue updateStatus(Long issueId, String status) {
        return null;
    }
}
