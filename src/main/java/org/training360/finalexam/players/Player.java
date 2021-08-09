package org.training360.finalexam.players;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.training360.finalexam.teams.Team;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "players")
public class Player {

    public Player(String name, LocalDate birthDate, PositionType positionType) {
        this.name = name;
        this.birthDate = birthDate;
        this.position = positionType;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "player_name")
    private String name;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Enumerated(value = EnumType.STRING)
    private PositionType position;

    @ManyToOne
    @JoinColumn(name = "team_id")
    @JsonBackReference
    private Team team;
}
