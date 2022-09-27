package ru.klokov.employeesdatasystem.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employee_position_range_sequence")
    @GenericGenerator(
            name="employee_position_range_sequence",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "employee_position_range_sequence")
            }
    )
    @Column(name = "id")
    private Long id;

    @Column(name = "employee_id", insertable = false, updatable = false)
    private Long employeeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private EmployeeEntity employee;

    @Column(name = "position_id", insertable = false, updatable = false)
    private Long positionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "position_id")
    private PositionEntity position;

    @Column(name = "position_range", insertable = false, updatable = false)
    private Long positionRange;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "position_range")
    private RangeEntity range;

    @Column(name = "position_change_date")
    private LocalDate positionChangeDate;
}
