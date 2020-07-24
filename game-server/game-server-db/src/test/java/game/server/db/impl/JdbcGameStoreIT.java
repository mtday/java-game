package game.server.db.impl;

import game.common.model.Game;
import org.junit.*;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

public class JdbcGameStoreIT {
    @Rule
    public DataSourceExternalResource dataSourceExternalResource = new DataSourceExternalResource();

    private JdbcGameStore gameStore;

    @Before
    public void setup() {
        gameStore = new JdbcGameStore(dataSourceExternalResource.get());
    }

    @After
    public void cleanup() {
        gameStore.truncate();
    }

    @Test
    public void testGetMissing() {
        assertFalse(gameStore.get("missing-id").isPresent());
    }

    @Test
    public void testGetExists() {
        Game game = new Game().setId("id").setTurn(0);
        assertEquals(1, gameStore.add(game));

        Optional<Game> fetched = gameStore.get(game.getId());
        assertTrue(fetched.isPresent());
        assertEquals(game, fetched.get());
    }

    @Test
    public void testGetAllNone() {
        assertTrue(gameStore.getAll().isEmpty());
    }

    @Test
    public void testGetAllSome() {
        Game game1 = new Game().setId("id1").setTurn(0);
        Game game2 = new Game().setId("id2").setTurn(0);
        assertEquals(2, gameStore.add(game1, game2));

        List<Game> fetched = gameStore.getAll();
        assertEquals(2, fetched.size());
        assertTrue(fetched.contains(game1));
        assertTrue(fetched.contains(game2));
    }

    @Test
    public void testAdd() {
        Game game1 = new Game().setId("id1").setTurn(0);
        assertEquals(1, gameStore.add(game1));

        Game game2 = new Game().setId("id2").setTurn(0);
        Game game3 = new Game().setId("id3").setTurn(0);
        assertEquals(2, gameStore.add(game2, game3));
    }

    @Test(expected = Exception.class)
    public void testAddConflict() {
        Game game = new Game().setId("id").setTurn(0);
        assertEquals(1, gameStore.add(game));
        assertEquals(1, gameStore.add(game));
    }

    @Test
    public void testUpdateMissing() {
        Game game = new Game().setId("id").setTurn(0);
        assertEquals(0, gameStore.update(game));
    }

    @Test
    public void testUpdate() {
        Game game = new Game().setId("id").setTurn(0);
        assertEquals(1, gameStore.add(game));

        game.setTurn(1);
        assertEquals(1, gameStore.update(game));

        Optional<Game> updated = gameStore.get(game.getId());
        assertTrue(updated.isPresent());
        assertEquals(game, updated.get());
    }

    @Test
    public void testDeleteMissing() {
        assertEquals(0, gameStore.delete("missing"));
    }

    @Test
    public void testDelete() {
        Game game = new Game().setId("id").setTurn(0);
        assertEquals(1, gameStore.add(game));
        assertEquals(1, gameStore.delete(game.getId()));

        assertFalse(gameStore.get(game.getId()).isPresent());
    }

    @Test
    public void testTruncate() {
        Game game1 = new Game().setId("id1").setTurn(0);
        Game game2 = new Game().setId("id2").setTurn(0);
        assertEquals(2, gameStore.add(game1, game2));
        assertEquals(2, gameStore.truncate());

        assertTrue(gameStore.getAll().isEmpty());
    }
}
