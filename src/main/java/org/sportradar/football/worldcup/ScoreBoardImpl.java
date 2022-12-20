package org.sportradar.football.worldcup;

import org.sportradar.football.worldcup.model.Game;
import org.sportradar.football.worldcup.exceptions.AlreadyExistsException;
import org.sportradar.football.worldcup.exceptions.DoesNotExistsException;
import org.sportradar.football.worldcup.exceptions.InvalidScoreException;
import org.sportradar.football.worldcup.exceptions.InvalidTeamNameException;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ScoreBoardImpl implements ScoreBoard {
    private static final String TEAM_NAME_REGEXP = "([A-Z][a-z]+)+";
    private static final Pattern TEAM_NAME_PATTERN = Pattern.compile(TEAM_NAME_REGEXP);
    private static final int SCORE_MIN = 0;
    private static final int SCORE_MAX = 19;

    private final HashMap<TeamPair, ScorePair> dataStorage = new HashMap<>();

    @Override
    public void startGame(String homeTeam, String awayTeam) throws AlreadyExistsException, InvalidTeamNameException {
        TeamPair teamPair = createTeamPair(homeTeam, awayTeam);
        if (dataStorage.get(teamPair) != null) {
            throw new AlreadyExistsException(String.format("Game %s - %s is already exists", homeTeam, awayTeam));
        }
        dataStorage.put(teamPair, new ScorePair(0, 0));
    }

    @Override
    public void finishGame(String homeTeam, String awayTeam) throws DoesNotExistsException, InvalidTeamNameException {
        TeamPair teamPair = createTeamPair(homeTeam, awayTeam);
        if (dataStorage.remove(teamPair) == null) {
            throw new DoesNotExistsException(String.format("Game %s - %s does not exists", homeTeam, awayTeam));
        }
    }

    @Override
    public void updateScore(String homeTeam, int homeScore, String awayTeam, int awayScore) throws DoesNotExistsException, InvalidTeamNameException, InvalidScoreException {
        TeamPair teamPair = createTeamPair(homeTeam, awayTeam);
        if (dataStorage.get(teamPair) == null) {
            throw new DoesNotExistsException(String.format("Game %s - %s does not exists", homeTeam, awayTeam));
        }
        dataStorage.put(teamPair, createScorePair(homeScore, awayScore));
    }

    @Override
    public List<Game> getSummaryOfTotalScoreGames() {
        return dataStorage.entrySet()
                .stream()
                .map(entry -> createGame(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    private Game createGame(TeamPair teamPair, ScorePair scorePair) {
        return new Game(teamPair.getHomeTeam(), teamPair.getAwayTeam(), scorePair.getHomeTeamScore(), scorePair.getAwayTeamScore());
    }

    private TeamPair createTeamPair(String homeTeam, String awayTeam) throws InvalidTeamNameException {
        validateTeamName(homeTeam);
        validateTeamName(awayTeam);
        return new TeamPair(homeTeam, awayTeam);
    }

    private void validateTeamName(String teamName) throws InvalidTeamNameException {
        if (teamName == null || !TEAM_NAME_PATTERN.matcher(teamName).matches())
            throw new InvalidTeamNameException(String.format("Team name:[%s] is not valid", teamName));
    }

    private ScorePair createScorePair(int homeScore, int awayScore) throws InvalidScoreException {
        validateScore(homeScore);
        validateScore(awayScore);
        return new ScorePair(homeScore, awayScore);
    }

    private void validateScore(int score) throws InvalidScoreException {
        if (score < SCORE_MIN || score > SCORE_MAX)
            throw new InvalidScoreException(String.format("Score:[%s] is not valid", score));
    }

    private static class TeamPair {
        private final String homeTeam;
        private final String awayTeam;

        public TeamPair(String homeTeam, String awayTeam) {
            this.homeTeam = homeTeam;
            this.awayTeam = awayTeam;
        }

        public String getHomeTeam() {
            return homeTeam;
        }

        public String getAwayTeam() {
            return awayTeam;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TeamPair teamPair = (TeamPair) o;
            return Objects.equals(homeTeam, teamPair.homeTeam) && Objects.equals(awayTeam, teamPair.awayTeam);
        }

        @Override
        public int hashCode() {
            return Objects.hash(homeTeam, awayTeam);
        }
    }

    private static class ScorePair {
        private final int homeTeamScore;
        private final int awayTeamScore;

        public ScorePair(int homeTeamScore, int awayTeamScore) {
            this.homeTeamScore = homeTeamScore;
            this.awayTeamScore = awayTeamScore;
        }

        public int getHomeTeamScore() {
            return homeTeamScore;
        }

        public int getAwayTeamScore() {
            return awayTeamScore;
        }
    }
}
