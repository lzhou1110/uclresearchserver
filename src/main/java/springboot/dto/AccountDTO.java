package springboot.dto;

import springboot.domain.Participant;

import java.util.Date;

public class AccountDTO extends Participant{
    private Integer id;
    private Integer version;
    private String email;
    private Date created;
    private Date updated;
    private String secret;
    private Integer invitedById;
    private Boolean emailVerified;
    private Integer totalParticipants;
    private Integer totalLevel1Children;
    private Integer totalLevel2Children;
    private Integer score;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public Integer getVersion() {
        return version;
    }

    @Override
    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public Date getCreated() {
        return created;
    }

    @Override
    public void setCreated(Date created) {
        this.created = created;
    }

    @Override
    public Date getUpdated() {
        return updated;
    }

    @Override
    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    @Override
    public String getSecret() {
        return secret;
    }

    @Override
    public void setSecret(String secret) {
        this.secret = secret;
    }

    @Override
    public Integer getInvitedById() {
        return invitedById;
    }

    @Override
    public void setInvitedById(Integer invitedById) {
        this.invitedById = invitedById;
    }

    @Override
    public Boolean getEmailVerified() {
        return emailVerified;
    }

    @Override
    public void setEmailVerified(Boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public Integer getTotalParticipants() {
        return totalParticipants;
    }

    public void setTotalParticipants(Integer totalParticipants) {
        this.totalParticipants = totalParticipants;
    }

    public Integer getTotalLevel1Children() {
        return totalLevel1Children;
    }

    public void setTotalLevel1Children(Integer totalLevel1Children) {
        this.totalLevel1Children = totalLevel1Children;
    }

    public Integer getTotalLevel2Children() {
        return totalLevel2Children;
    }

    public void setTotalLevel2Children(Integer totalLevel2Children) {
        this.totalLevel2Children = totalLevel2Children;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public AccountDTO(Participant participant) {
        this.id = participant.getId();
        this.version = participant.getVersion();
        this.email = participant.getEmail();
        this.created = participant.getCreated();
        this.updated = participant.getUpdated();
        this.secret = participant.getSecret();
        this.invitedById = participant.getInvitedById();
        this.emailVerified = participant.getEmailVerified();
    }
}
