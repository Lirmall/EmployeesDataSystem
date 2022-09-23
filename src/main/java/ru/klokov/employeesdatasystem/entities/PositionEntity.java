package ru.klokov.employeesdatasystem.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "positions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PositionEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "position_sequence")
    @GenericGenerator(
            name="position_sequence",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "position_sequence")
            }
    )
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "worktype_id", insertable = false, updatable = false)
    private Long worktypeId;

    @Column(name = "salary")
    private Double salary;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "worktype_id")
    private WorktypeEntity worktype;

    @OneToMany(mappedBy = "position", fetch = FetchType.LAZY)
    private List<EmployeePositionRangeEntity> positionRangeEntityList;
}