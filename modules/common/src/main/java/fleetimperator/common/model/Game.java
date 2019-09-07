package fleetimperator.common.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;
import java.util.UUID;

import static java.util.Objects.requireNonNull;
import static java.util.Optional.ofNullable;

public final class Game {
    private final String id;
    private final int turn;

    @JsonCreator
    private Game(@JsonProperty("id") String id,
                 @JsonProperty("turn") int turn) {
        this.id = id;
        this.turn = turn;
    }

    public String getId() {
        return id;
    }

    public int getTurn() {
        return turn;
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

    public static final class Builder {
        private String id;
        private int turn = 0;

        public Builder() {
        }

        public Builder(Game game) {
            withId(game.getId());
            withTurn(game.getTurn());
        }

        public Builder withId(String id) {
            this.id = requireNonNull(id);
            return this;
        }

        public Builder withTurn(int turn) {
            if (turn < 0) {
                throw new IllegalArgumentException("Turn must be >= 0");
            }
            this.turn = turn;
            return this;
        }

        public Game build() {
            return new Game(
                    ofNullable(id).orElseGet(() -> UUID.randomUUID().toString()),
                    turn
            );
        }
    }
}
