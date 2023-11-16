package com.example.SpringDataJPA.dto;

import java.util.List;

/**
 * A DTO representing detailed information about a project.
 * This DTO is typically used to retrieve and display detailed information about a project, including its ID, name,
 * description, scheduled effort, and associated personnel.
 */
public class ProjectDetailsDTO {
    private Integer projectId;
    private String projectName;
    private String projectDescription;
    private Integer scheduledEffort;
    private List<ProjectPersonDTO> projectPersonDTOS;
    private Integer currentPage;
    private Integer totalPages;
    private Integer pageSize;

    //getters and setters
    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer id) {
        this.projectId = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String name) {
        this.projectName = name;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String description) {
        this.projectDescription = description;
    }

    public Integer getScheduledEffort() {
        return scheduledEffort;
    }

    public void setScheduledEffort(Integer scheduledEffort) {
        this.scheduledEffort = scheduledEffort;
    }

    public List<ProjectPersonDTO> getProjectPersonDTOS() {
        return projectPersonDTOS;
    }

    public void setProjectPersonDTOS(List<ProjectPersonDTO> projectPersonDTOS) {
        this.projectPersonDTOS = projectPersonDTOS;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
