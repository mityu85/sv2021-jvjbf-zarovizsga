package org.training360.finalexam.teams;

import org.training360.finalexam.players.Player;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "teams")
public class Team {

    public Team(String name) {
        this.name = name;
    }

    public Team(String name, List<Player> players) {
        this.name = name;
        this.players = players;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "team_name")
    private String name;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, mappedBy = "team")
    private List<Player> players = new ArrayList<>();
}
