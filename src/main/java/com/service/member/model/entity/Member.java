package com.service.member.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
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
    private Long memberId;

    @JsonProperty
    @NotBlank(message = "First name cannot be empty")
    @Column(name = "FIRST_NAME", nullable = false)
    private String firstName;

    @JsonProperty
    @NotBlank(message = "Last name cannot be empty")
    @Column(name = "LAST_NAME", nullable = false)
    private String lastName;

    @JsonProperty
    @Pattern(regexp = "^([0-2][0-9]|(3)[0-1])(\\/)(((0)[0-9])|((1)[0-2]))(\\/)\\d{4}$")
    @Column(name = "DOB", nullable = false)
    private String dateOfBirth;

    @JsonProperty
    @NotBlank(message = "Postal code cannot be empty")
    @Column(name = "POSTAL_CODE", nullable = false)
    private String postalCode;

    @JsonProperty
    @JoinColumn(name = "IMAGE_ID")
    @OneToOne(cascade = CascadeType.ALL)
    private Image image;

}
