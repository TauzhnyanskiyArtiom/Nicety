package ua.nicety.database.entity;


import lombok.*;

import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper=false, of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "usr")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username;

    private String password;

    @Column(unique = true, nullable = false)
    private String email;

    private Role role;

}