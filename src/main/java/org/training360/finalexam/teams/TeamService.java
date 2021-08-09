package org.training360.finalexam.teams;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.training360.finalexam.players.CreatePlayerCommand;
import org.training360.finalexam.players.Player;
import org.training360.finalexam.players.PlayerDTO;
import org.training360.finalexam.players.PlayerRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class TeamService {

    private ModelMapper modelMapper;
    private TeamsRepository teamsRepository;
    private PlayerRepository playerRepository;


    public List<TeamDTO> getTeams() {
        return teamsRepository.findAll().stream()
                .map(t -> modelMapper.map(t, TeamDTO.class))
                .collect(Collectors.toList());
    }

    public TeamDTO createNewTeam(CreateTeamCommand command) {

        return modelMapper.map(teamsRepository.save(new Team(
                command.getName()
        )), TeamDTO.class);
    }

    public TeamDTO getTeamById(Long id) {
        return modelMapper.map(teamsRepository.findById(id)
                .orElseThrow(() -> new TeamsNotFoundException(id)), TeamDTO.class);
    }

    public TeamDTO addNewPlayerToTeam(Long id, CreatePlayerCommand command) {
        Team team = modelMapper.map(getTeamById(id), Team.class);
        Player player = new Player(
                command.getName(),
                command.getBirthDate(),
                command.getPosition()
        );
        team.getPlayers().add(player);
        player.setTeam(team);
        teamsRepository.save(team);
        playerRepository.save(player);
        return modelMapper.map(team, TeamDTO.class);
    }

    public TeamDTO updateWithExistingPlayer(Long id, UpdateWithExistingPlayerCommand command) {
        Team team = modelMapper.map(getTeamById(id), Team.class);
        Player player = playerRepository.findById(command.getPlayerId())
                .orElseThrow(() -> new IllegalArgumentException("Id cannot be found " + id));
        int positionNumber = 0;
        for (Player p: team.getPlayers()) {
            if (p.getPosition().equals(player.getPosition())) {
                positionNumber++;
            }
        }
        if (player.getTeam() == null && positionNumber < 2) {
            team.getPlayers().add(player);
            player.setTeam(team);
            teamsRepository.save(team);
            playerRepository.save(player);
        }
        return modelMapper.map(team, TeamDTO.class);
    }
}
