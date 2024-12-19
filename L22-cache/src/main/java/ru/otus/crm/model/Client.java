package ru.otus.crm.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "client")
public class Client implements Cloneable {

    @Id
    @SequenceGenerator(name = "client_gen", sequenceName = "client_seq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "client_gen")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, targetEntity = Phone.class, fetch = FetchType.EAGER, mappedBy = "client")
    private List<Phone> phones = new ArrayList<>();

    public Client(String name) {
        this.id = null;
        this.name = name;
    }

    public Client(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @SuppressWarnings("this-escape")
    public Client(Long id, String name, Address address, List<Phone> phones) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phones = phones;
        for (Phone phone : phones) {
            phone.setClient(this);
        }
    }

    @Override
    @SuppressWarnings({"java:S2975", "java:S1182"})
    public Client clone() {

        var clone = new Client(this.id, this.name);

        if (this.address != null) {
            clone.setAddress(this.address.clone());
        }

        if (this.phones != null) {
            List<Phone> clonedPhones = new ArrayList<>();

            for (var phone : this.phones) {
                Phone cloned = phone.clone();
                cloned.setClient(clone);
                clonedPhones.add(cloned);
            }

            clone.setPhones(clonedPhones);
        }

        return clone;
    }

    @Override
    public String toString() {
        String addressString = "";
        if (address != null) {
            addressString = "street=" + address.getStreet();
        }
        return "Client{" + "id=" + id + ", name='" + name + "', address='" + addressString + '}';
    }
}
