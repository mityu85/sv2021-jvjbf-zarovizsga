package org.training360.finalexam.teams;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.training360.finalexam.players.CreatePlayerCommand;
import org.training360.finalexam.players.PlayerDTO;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/teams")
public class TeamController {

    private TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping
    public List<TeamDTO> getTeams() {
        return teamService.getTeams();
    }

    @GetMapping("/{id}")
    public TeamDTO getTeamById(@PathVariable("id") Long id) {
        return teamService.getTeamById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TeamDTO createNewTeam(@Valid @RequestBody CreateTeamCommand command) {
        return teamService.createNewTeam(command);
    }

    @PostMapping("/{id}/players")
    @ResponseStatus(HttpStatus.CREATED)
    public TeamDTO addNewPlayerToExistingTeam(@PathVariable("id") Long id, @RequestBody CreatePlayerCommand command) {
        return teamService.addNewPlayerToTeam(id, command);
    }

    @PutMapping("/{id}/players")
    public TeamDTO updateTeamWithExistingPlayer(@PathVariable("id") Long id, @RequestBody UpdateWithExistingPlayerCommand command) {
        return teamService.updateWithExistingPlayer(id, command);
    }
}
