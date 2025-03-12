package ubb.scs.map.faptebune.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class Need extends Entity<Long> {
    private Long id;
    private String title;
    private String description;
    private LocalDateTime deadline;
    private Long savior;
    private Long manInNeed;
    private String status;

    public Need(String title, String description, LocalDateTime deadline, Long manInNeed) {
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.savior = null;
        this.manInNeed = manInNeed;
        this.status = "Caut erou!";
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getManInNeed() {
        return manInNeed;
    }

    public void setManInNeed(Long manInNeed) {
        this.manInNeed = manInNeed;
    }

    public Long getSavior() {
        return savior;
    }

    public void setSavior(Long savior) {
        this.savior = savior;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Need need = (Need) o;
        return Objects.equals(id, need.id) && Objects.equals(title, need.title) && Objects.equals(description, need.description) && Objects.equals(deadline, need.deadline) && Objects.equals(savior, need.savior) && Objects.equals(manInNeed, need.manInNeed) && Objects.equals(status, need.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, deadline, savior, manInNeed, status);
    }
}
