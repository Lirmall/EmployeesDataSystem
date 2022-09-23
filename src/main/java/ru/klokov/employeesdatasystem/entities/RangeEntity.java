package ru.klokov.employeesdatasystem.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "ranges")
@Getter
@Setter
@NoArgsConstructor
public class RangeEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ranges_sequence")
    @GenericGenerator(
            name="ranges_sequence",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "ranges_sequence")
            }
    )
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "bonus")
    private Double bonus;

    @OneToMany(mappedBy = "range", fetch = FetchType.LAZY)
    private List<EmployeePositionRangeEntity> positionRangeEntities;
}
