package ru.klokov.employeesdatasystem.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "genders")
@Getter
@Setter
@NoArgsConstructor
public class GenderEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "genders_sequence")
    @GenericGenerator(
            name="genders_sequence",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "genders_sequence")
            }
    )
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "gender", fetch = FetchType.LAZY)
    private List<EmployeeEntity> employeesList;
}
