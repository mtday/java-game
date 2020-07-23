package game.common.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

public final class Game {
    private String id;
    private int turn;

    public Game() {
    }

    @JsonCreator
    private Game(@JsonProperty("id") String id,
                 @JsonProperty("turn") int turn) {
        this.id = id;
        this.turn = turn;
    }

    private Game(Game game) {
        this.id = game.id;
        this.turn = game.turn;
    }

    public String getId() {
        return id;
    }

    public Game setId(String id) {
        this.id = requireNonNull(id);
        return this;
    }

    public int getTurn() {
        return turn;
    }

    public Game setTurn(int turn) {
        this.turn = turn;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return turn == game.turn &&
                Objects.equals(id, game.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, turn);
    }

    @Override
    public String toString() {
        return "Game{" +
                "id='" + id + '\'' +
                ", turn=" + turn +
                '}';
    }
}
