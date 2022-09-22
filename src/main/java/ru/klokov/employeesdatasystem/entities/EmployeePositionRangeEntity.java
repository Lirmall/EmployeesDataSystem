package ru.klokov.employeesdatasystem.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "employee_position_range")
@Getter
@Setter
@NoArgsConstructor
public class EmployeePositionRangeEntity {
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

    @Column(name = "employee_id", insertable = false, updatable = false)
    private Long employee_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "worktype_id")
    private EmployeeEntity employee;

//    @Column(name = "position_id", insertable = false, updatable = false)
//    private Long position_id;
//
//    @Column(name = "position_range", insertable = false, updatable = false)
//    private Long position_range;
//
//    @Column(name = "position_change_date")
//    private LocalDate positionChangeDate;
}
