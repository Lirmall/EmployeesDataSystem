package ru.klokov.employeesdatasystem.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "employees")
@Getter
@Setter
@NoArgsConstructor
public class EmployeeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employees_sequence")
    @GenericGenerator(
            name="employees_sequence",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "employees_sequence")
            }
    )
    @Column(name = "id")
    private Long id;

    @Column(name = "second_name")
    private String secondName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "third_name")
    private String thirdName;

    @Column(name = "gender_id", insertable = false, updatable = false)
    private Long genderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gender_id")
    private GenderEntity gender;

    @Column(name = "birthday")
    private LocalDate birthday;

    @Column(name = "worktype_id", insertable = false, updatable = false)
    private Long worktypeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "worktype_id")
    private WorktypeEntity worktype;

    @Column(name = "salary")
    private Double salary;

    @Column(name = "workstart_date")
    private LocalDate workstartDate;

    @Column(name = "dismissed")
    private Boolean dismissed;

    @Column(name = "dismissed_date")
    private LocalDate dismissedDate;

    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY)
    private List<EmployeePositionRangeEntity> positionRangeEntityList;
}
