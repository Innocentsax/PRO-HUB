package dev.Innocent.Service.Impl;

import dev.Innocent.Service.ChatService;
import dev.Innocent.Service.ProjectService;
import dev.Innocent.Service.UserService;
import dev.Innocent.model.Chat;
import dev.Innocent.model.Project;
import dev.Innocent.model.User;
import dev.Innocent.repository.ProjectRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProjectServiceImpl implements ProjectService {
    private ProjectRepository projectRepository;
    private UserService userService;
    private ChatService chatService;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository,
                              UserService userService, ChatService chatService) {
        this.projectRepository = projectRepository;
        this.userService = userService;
        this.chatService = chatService;
    }

    @Override
    public Project createProject(Project project, User user) throws Exception {
        Project createdProject = new Project();
        createdProject.setOwner(user);
        createdProject.setTag(project.getTag());
        createdProject.setName(project.getName());
        createdProject.setCategory(project.getCategory());
        createdProject.setDescription(project.getDescription());
//        createdProject.getTeam().add(user);

        Project savedProject = projectRepository.save(createdProject);

        // Chat feat
        Chat chat = new Chat();
        chat.setProject(savedProject);

        Chat projectChat = chatService.createChat(chat);
        savedProject.setChat(projectChat);
        return savedProject;
    }

    @Override
    public List<Project> getProjectByTeam(User user, String category, String tag) throws Exception {
        List<Project> projects = projectRepository.findByTeamContainingOrOwner(user, user);
        if(category != null){
            projects = projects.stream().filter(project -> project.getCategory().equals(category))
                    .collect(Collectors.toList());
        }
        if(tag != null){
            projects = projects.stream().filter(project -> project.getTag().contains(tag))
                    .collect(Collectors.toList());
        }
        return projects;
    }

    @Override
    public Project getProjectById(Long projectId) throws Exception {
        Optional<Project> optionalProject = projectRepository.findById(projectId);
        if(optionalProject.isEmpty()){
            throw new Exception("Project not found");
        }
        return optionalProject.get();
    }

    @Override
    public void deleteProject(Long projectId, Long userId) throws Exception {
        getProjectById(projectId);
        projectRepository.deleteById(projectId);
    }

    @Override
    public Project updateProject(Project updateProject, Long id) throws Exception {
        Project project = getProjectById(id);
        project.setName(updateProject.getName());
        project.setDescription(updateProject.getDescription());
        project.setTag(updateProject.getTag());
        return projectRepository.save(project);
    }

    @Override
    public void addUserToProject(Long projectId, Long userId) throws Exception {
        Project project = getProjectById(projectId);
        User user = userService.findUserById(userId);
        if(!project.getTeam().contains(user)){
            project.getChat().getUser().add(user);
            project.getTeam().add(user);
        }
        projectRepository.save(project);
    }

    @Override
    public void removeUserFromToProject(Long projectId, Long userId) throws Exception {
        Project project = getProjectById(projectId);
        User user = userService.findUserById(userId);
        if(project.getTeam().contains(user)){
            project.getChat().getUser().remove(user);
            project.getTeam().remove(user);
        }
        projectRepository.save(project);
    }

    @Override
    public Chat getChatByProjectId(Long projectId) throws Exception {
        Project project = getProjectById(projectId);
        return project.getChat();
    }

    @Override
    public List<Project> searchProject(String keyword, User user) throws Exception {
        return projectRepository.findByNameContainingAndTeamContaining(keyword, user);
    }

}
