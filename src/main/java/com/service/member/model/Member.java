package com.service.member.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "MEMBER")
public class Member implements Serializable {

    @Id
    @JsonProperty
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "MEMBER_ID")
    private long memberId;

    @JsonProperty
    @Column(name = "FIRST_NAME", nullable = false)
    private String firstName;

    @JsonProperty
    @Column(name = "LAST_NAME", nullable = false)
    private String lastName;

    @JsonProperty
    @Column(name = "DOB", nullable = false)
    private String dateOfBirth;

    @JsonProperty
    @Column(name = "POSTAL_CODE", nullable = false)
    private String postalCode;

    @JsonProperty
    @JoinColumn(name = "IMAGE_ID")
    @OneToOne(cascade = CascadeType.ALL)
    private Image image;

}
