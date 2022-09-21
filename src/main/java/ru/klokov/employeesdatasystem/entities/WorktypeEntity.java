package ru.klokov.employeesdatasystem.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "worktypes")
@Getter
@Setter
@NoArgsConstructor
public class WorktypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "worktypes_sequence")
    @GenericGenerator(
            name="worktypes_sequence",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "worktypes_sequence")
            }
    )
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "worktype", fetch = FetchType.LAZY)
    private List<PositionEntity> positions;
}
