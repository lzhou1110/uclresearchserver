package springboot.domain;

import io.swagger.annotations.ApiModelProperty;
import springboot.utils.RandomStringGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Participant {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(notes = "The database generated product ID")
    private Integer id;
    @Version
    @ApiModelProperty(notes = "The auto-generated version of the product")
    private Integer version;
    @Column(unique = true)
    @ApiModelProperty(notes = "The email address of participant")
    private String email;
    @ApiModelProperty(notes = "Created datetime")
    private Date created;
    @ApiModelProperty(notes = "Updated datetime")
    private Date updated;
    @ApiModelProperty(notes = "Random String")
    private String secret;
    @ApiModelProperty(notes = "Invited by id")
    private Integer invitedById;
    @ApiModelProperty(notes = "Email verified")
    private Boolean emailVerified;

    @PrePersist
    protected void onCreate() {
        created = new Date();
        secret = RandomStringGenerator.generateString();
        emailVerified = Boolean.FALSE;
    }

    @PreUpdate
    protected void onUpdate() {
        updated = new Date();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public Integer getInvitedById() {
        return invitedById;
    }

    public void setInvitedById(Integer invitedById) {
        this.invitedById = invitedById;
    }

    public Boolean getEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(Boolean emailVerified) {
        this.emailVerified = emailVerified;
    }
}
