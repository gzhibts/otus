package ru.otus.crm.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "phone")
@Data
public class Phone implements Cloneable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "phonenumber")
    private String number;

    @ManyToOne(fetch = FetchType.LAZY)
    private Client client;

    public Phone(Long id, String number) {
        this.id = id;
        this.number = number;
    }

    public Phone(String number) {
        this.id = null;
        this.number = number;
    }

    public Phone() {
    }


    @Override
    public Phone clone() {
        try {
            Phone phone = (Phone) super.clone();
            phone.setId(this.id);
            phone.setNumber(this.number);
            return phone;
        } catch (CloneNotSupportedException e) {
            throw new UnsupportedOperationException(e);
        }

    }
}
