package ru.geekbrains.supershop.persistence.entities;

import lombok.*;

import ru.geekbrains.supershop.persistence.entities.enums.Role;
import ru.geekbrains.supershop.persistence.entities.utils.PersistableEntity;

import javax.persistence.*;

import java.util.List;

@Builder
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Shopuser extends PersistableEntity {

    private String phone;

    private String password;

    private String firstName;

    private String lastName;

    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

//    @OneToMany(mappedBy = "shopuser", fetch = FetchType.EAGER)
//    private List<Purchase> purchases;
//
//    @OneToMany(mappedBy = "shopuser", fetch = FetchType.EAGER)
//    private List<Review> reviews;

}