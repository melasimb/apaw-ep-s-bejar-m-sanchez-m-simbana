package es.upm.miw.apaw_ep_festivals.spectator_data;

import es.upm.miw.apaw_ep_festivals.ApiTestConfig;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ApiTestConfig
public class SpectatorCompositeIT {

    @Rule
    public ExpectedException exception = ExpectedException.none();
    private SpectatorComponent group1;
    private SpectatorComponent group2;
    private SpectatorComponent group12;
    private SpectatorComponent spectator;

    @Before
    public void ini() {
        this.spectator = new SpectatorLeaf(new Spectator("David", "Smith", LocalDateTime.now()));
        this.group1 = new SpectatorComposite("Gold");
        this.group12 = new SpectatorComposite("Gold - Plus");
        this.group2 = new SpectatorComposite("Platinum");

    }

    @Test
    public void testInfoIfLeaf() {
        assertEquals("David Smith", this.spectator.info());
    }

    @Test
    public void testInfoIfComposite() {
        assertEquals("Group name: Gold" + "\\n\\t spectators:\\n", this.group1.info());
    }

    @Test
    public void testAddIfComposite() {
        this.group1.add(this.spectator);
        assertEquals("Group name: Gold" + "\\n\\t spectators:\\n" +
                "\\t\\t" + "David Smith\\n", this.group1.info());
        this.group1.add(this.group12);
        assertEquals("Group name: Gold" + "\\n\\t spectators:\\n" +
                "\\t\\t" + "David Smith\\n" +
                "\\t\\t" + "Group name: Gold - Plus" + "\\n\\t spectators:\\n\\n", this.group1.info());
    }

    @Test
    public void testAddIfLeaf() {
        exception.expect(UnsupportedOperationException.class);
        this.spectator.add(this.group12);
    }

    @Test
    public void testRemoveIfComposite() {
        this.group2.add(this.spectator);
        assertEquals("Group name: Platinum" + "\\n\\t spectators:\\n" +
                "\\t\\t" + "David Smith\\n", this.group2.info());
        this.group2.remove(this.spectator);
        assertEquals("Group name: Platinum" + "\\n\\t spectators:\\n", this.group2.info());
    }
}