# Sportradar
### Score Board
The boards support the following operations:
1. Start a game. When a game starts, it should capture (being initial score 0-0)
   a. Home team
   b. Away Team
2. Finish a game. It will remove a match from the scoreboard.
3. Update score. Receiving the pair score; home team score and away team score
   updates a game score
4. Get a summary of games by total score. Those games with the same total score
   will be returned ordered by the most recently added to our system.

As an example, being the current data in the system:
+ a. Mexico - Canada: 0 – 5
+ b. Spain - Brazil: 10 – 2
+ c. Germany - France: 2 – 2
+ d. Uruguay - Italy: 6 – 6
+ e. Argentina - Australia: 3 - 1

The summary would provide with the following information:

1. Uruguay 6 - Italy 6
2. Spain 10 - Brazil 2
3. Mexico 0 - Canada 5
4. Argentina 3 - Australia 1
5. Germany 2 - France 2

### Tech details
ScoreBoard implementation is not threadsafe and is not memory optimized.
There are four methods:

+ startGame(String, String): void
+ finishGame(String, String): void
+ updateScore(String, int, String, int): void
+ getSummaryOfTotalScoreGames: List<Game>

Exceptions:
+ AlreadyExistsException - throws when trying to startGame which is already existing
+ DoesNotExistsException - throws when team pair doesn't exist when we updateScore or finishGame
+ InvalidScoreException - throws when score not in the range of [0, 20) in case of updateScore
+ InvalidTeamNameException - throws when team name is not applicable for pattern "([A-Z][a-z]+)+" in cases of startGame,
finishGame or updateScore
